package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import edu.duke.ece651.risc.shared.AttackOrderConsistencyChecker;
import edu.duke.ece651.risc.shared.AttackOrderEffectChecker;
import edu.duke.ece651.risc.shared.AttackOrderPathChecker;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.MoveOrderConsistencyChecker;
import edu.duke.ece651.risc.shared.MoveOrderEffectChecker;
import edu.duke.ece651.risc.shared.MoveOrderPathChecker;
import edu.duke.ece651.risc.shared.OrderRuleChecker;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.V1BoardFactory;

/**
 * README: in evo1, we start let the first player to choose map is when all
 * players are in this room. Then after choosing the map, we immediately call
 * the playGame(). Now we need a new method called addPlayer() and check whether
 * we have enough player to start chooseMap and playGame in this method in
 * evo2!!!
 */

public class V2GameRoom extends GameRoom<String> {

  /** The id of this room in the sevrer */
  int roomId; // in evo2, we have multiple rooms now, so we need an id to name a room

  /**
   * The status of this game room, will be one of the following: (a) waiting for
   * players (b) running the game (c) game is finished
   */
  int roomStatus;

  /** rule checker for upgrade unit order */
  OrderRuleChecker<String> upgradeUnitChecker;

  /** rule checker for upgrade max technology level order */
  OrderRuleChecker<String> upgradeTechLevelChecker;

  /**
   * The thread that will run the game (chooseMap(), playGame()) when we have
   * enough players in this room. This thread is created and started by
   * addPlayerAndCheckToPlay() method, which is called by the server.
   * 
   * Here we need a new thread to call chooseMap(), playGame(), because these two
   * method will cost a long time (we return from these method only after the game
   * ends), and we don't want the server be blocked. We want the server call the
   * addPlayerAndCheckToPlay() and return quickly to give the resource back to the
   * thread pool. So we use a seperate thread to run the whole game inside the
   * addPlayerAndCheckToPlay() method.
   */
  Thread gameRunner;

  /**
   * Constructor that initailizes fields with corresponding parameters. Note that
   * the board is initialized after the first player has decided the map, and the
   * view may not be used in evo2.
   * 
   * @param roomId
   * @param roomCreator
   */
  public V2GameRoom(int roomId, PlayerEntity<String> roomCreator) {
    // TODO check this contructor again when most of other code are finished!!!
    super(0, Constant.TOTAL_UNITS, roomCreator);
    this.roomId = roomId;
    this.roomStatus = Constant.ROOM_STATUS_WAITING_PLAYERS;
    this.moveChecker = makeMoveOrderRuleCheckerChain();
    this.attackChecker = makeAttackOrderRuleCheckerChain();
    this.upgradeUnitChecker = makeUpgradeUnitOrderRuleCheckerChain();
    this.upgradeTechLevelChecker = makeUpgradeTechLevelOrderRuleCheckerChain();
    this.resolver = makeBattleResolver();
    this.gameRunner = null; // we will use this thread only after we have enough players

  }

  // --- Below is the helper method that make the evo2 Battle Resolver --- //
  public static Resolver<String> makeBattleResolver() {
    return new BattleResolver<String>(new Random(Constant.randomSeed));
  }

  // --- Below are the helper methods that make order rule checker chains --- //

  public static OrderRuleChecker<String> makeMoveOrderRuleCheckerChain() {
    // TODO: replace the checker into version 2 checker when they are finished!!!
    return new MoveOrderConsistencyChecker<String>(
        new MoveOrderPathChecker<String>(new MoveOrderEffectChecker<String>(null)));
  }

  public static OrderRuleChecker<String> makeAttackOrderRuleCheckerChain() {
    // TODO: replace the checker into version 2 checker when they are finished!!!
    return new AttackOrderConsistencyChecker<String>(
        new AttackOrderPathChecker<String>(new AttackOrderEffectChecker<String>(null)));
  }

  public static OrderRuleChecker<String> makeUpgradeUnitOrderRuleCheckerChain() {
    // TODO: finish this when checkers are finished!!!
    return null;
  }

  public static OrderRuleChecker<String> makeUpgradeTechLevelOrderRuleCheckerChain() {
    // TODO: finish this when checkers are finished!!!
    return null;
  }

  /**
   * This method will add a new player who wants to join in this game room, then
   * check whether we have enough player to start the game (i.e., call the
   * chooseMap() and playGame() method).
   * 
   * In ther server, if a player wish to join a room that is waiting for the rest
   * of players, the corresponding handler will call this method.
   * 
   * In evolution 1, we make sure all players are in the room and then we call the
   * chooseMap() method and playGame() method inside the sevrer code. But now we
   * cannot let the server directly do this since the server needs to process
   * other requests. So we add this new method in V2GameRoom to let the room be
   * able to automatically launch the game at the appropriate time.
   * 
   * @since evolution 2
   * 
   * @apiNote This method should only be called AFTER the room creator has decided
   *          the total number of players in this room.
   * @param newPlayer the new PlayerEntity(actually should be GUIPlayerEntity)
   *                  that joined in this game room
   */

  public void addPlayerAndCheckToPlay(PlayerEntity<String> newPlayer) {
    if (roomStatus == Constant.ROOM_STATUS_WAITING_PLAYERS) {
      addPlayer(newPlayer); // simply add the player first
      
      // Now we need to set the id of this player and send the id to this player
      int idForNewPlayer = players.size() - 1; // our player id starts from 0, so size - 1 here.
      newPlayer.setPlayerId(idForNewPlayer);
      try {
        newPlayer.sendObject(idForNewPlayer);
      } catch (IOException e) {
        // TODO: here we don't want the exception propogate out to the server code, need
        // a appropriate way to handle the IOException.
        // One possible situation that will throw the exception is the player disconnect
        // (want to switch to other game room so he needs to logout first), we need to
        // do something here, rather than in the server.
        e.printStackTrace();
        // TODO: set a new field in player entity (like isOnline/isAbsent) be false/true
        // here?
      }
      // Check whether we have enough players
      if (players.size() == getPlayerNum()) { // the room has enough players, let's choose map and start the game
        setRoomStatus(Constant.ROOM_STATUS_RUNNING_GAME);
        // We dont want to block the server code who call the addPlayerAndCheckToPlay()
        // method. We want the server code return from this method call quickly, so we
        // use a seperate thread to run the game.

        // More concise code: using a lambda to create a anoyminous runnable and use
        // this runnable to create a thread.
        // Supported in java8.
        // For more info, refer to:
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1306580710588449
        this.gameRunner = new Thread(() -> {
          try {
            chooseMap();
            playGame();
          } catch (InterruptedException | BrokenBarrierException | IOException | ClassNotFoundException e) {
            // TODO: see whether we need to do something here or just ignore it. The
            // IOEception should be resolved here (i.e., mark the player disconnected ?)
            e.printStackTrace();
          }
        });
        gameRunner.start();
      }
    }
  }

  /**
   * This method will let the first player (who create this game room) select the
   * map. This method should be called only after all players have in the room.
   */
  @Override
  public void chooseMap() throws IOException, ClassNotFoundException {
    PlayerEntity<String> firstPlayer = players.get(0);
    // TODO: replace V1BoardFactory with V2BoardFactory later!
    BoardFactory<String> factory = new V1BoardFactory<String>();
    gameBoard = factory.makeGameBoard(playerNum);
    gameBoard.updateAllPrevDefender();

    // In evo 2 we make the map choice be type of int
    String choice = (String) firstPlayer.receiveObject(); // not used in evolution 2, since there's only one map
    // firstPlayer.sendObject(Constant.VALID_MAP_CHOICE_INFO);
  }

  @Override
  public void playGame() throws InterruptedException, BrokenBarrierException, IOException {
    barrier = new CyclicBarrier(playerNum + 1);
    for (int i = 0; i < playerNum; i++) {
      // TODO: using V2GameHostThread here
      Thread t = new V1GameHostThread<String>(players.get(i), Constant.TOTAL_UNITS, gameBoard, view, moveChecker,
          attackChecker, barrier);
      t.start();
    }
    // Wait all the player finishing their deployment
    barrier.await();
    while (true) {
      barrier.await();

      System.out.println("All players done with their orders");

      resolver.executeAllBattle(gameBoard);
      incrementUnits();
      gameBoard.updateAllPrevDefender();
      // TODO: update players resources here

      System.out.println("Battle finished, now check anyone wins");

      if (checkEnd()) {
        barrier.await();
        barrier.await();
        // TODO: broad cast winner info here
        // closeAllStreams();
        break;
      }
      barrier.await();

      System.out.println("No one wins, now waiting all players done again");

    }
  }

  /**
   * @return the roomStatus
   */
  @Override
  public int getRoomStatus() {
    return roomStatus;
  }

  /**
   * @param roomStatus the roomStatus to set
   */
  @Override
  public void setRoomStatus(int roomStatus) {
    this.roomStatus = roomStatus;
  }

}
