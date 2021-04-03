package edu.duke.ece651.risc.server;

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

  @Override
  public int getRoomId() {
    return this.roomId;
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
      newPlayer.sendObject("" + idForNewPlayer); // NOTE: SEND String to client - send the player id to this player
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
            // NOTE: SEND int to client - the number of total player in this game room
            sendToAllPlayer(playerNum);
            playGame();
          } catch (InterruptedException | BrokenBarrierException | ClassNotFoundException e) {
            // TODO: see whether we need to do something here or just ignore it.
            e.printStackTrace();
          }
        });
        gameRunner.start();
      }
    } else {
      // The room is fulled just now. Send String -1 to indicate the join is failed.
      newPlayer.sendObject("-1");
    }
  }

  /**
   * This method will let the first player (who create this game room) select the
   * map. This method should be called only after all players have in the room.
   */
  @Override
  public void chooseMap() throws ClassNotFoundException {
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
  public void playGame() throws InterruptedException, BrokenBarrierException {
    barrier = new CyclicBarrier(playerNum + 1);
    for (int i = 0; i < playerNum; i++) {
      Thread t = new V2GameHostThread<>(players.get(i), Constant.TOTAL_UNITS, gameBoard, moveChecker, attackChecker,
          upgradeUnitChecker, upgradeTechLevelChecker, barrier);
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
      updateAllPlayer(); // we need to update AFTER all combats finished
      System.out.println("Battle finished, now check anyone wins");

      if (checkEnd()) {
        setRoomStatus(Constant.ROOM_STATUS_GAME_FINISHED);
        barrier.await();
        sendToAllPlayer(getWinnerId()); // send the player id of the winner to all players
        barrier.await();
        // closeAllStreams();
        break;
      }
      barrier.await();

      System.out.println("No one wins, now waiting all players done again");

    }
  }

  /**
   * Note that this method should only be called AFTER a player won the game!
   * 
   * @since evolution 2
   * @return the player id of the winner
   * @throws IllegalStateException if there is no winner in this game yet.
   */
  protected int getWinnerId() {
    int winnerId = -1;
    for (PlayerEntity<String> p : players) {
      if (p.getPlayerStatus() == Constant.SELF_WIN_STATUS) {
        winnerId = p.getPlayerId();
        break;
      }
    }
    if (winnerId == -1) { // should not happen
      throw new IllegalStateException("There is no winner yet!"); // fail fast. May remove after finished.
    }
    return winnerId;
  }

  /**
   * This method will add resources (food and tech) to each player.
   */
  protected void updateAllPlayerResource() {
    for (PlayerEntity<String> p : players) {
      p.harvestAllResource(gameBoard);
    }
  }

  /**
   * This method will deal with all the UpgradeTechLevelOrder if a player has
   * issued one in this turn, which will increment the max tech level by one for
   * those player who issued this order, and reset the needUpTechLv field to
   * false. These are done by calling upgradeTechLevel() method for each player.
   */
  protected void updateAllPlayerMaxTechLevel() {
    for (PlayerEntity<String> p : players) {
      if (p.getNeedUpTechLv()) {
        p.upgradeTechLevel();
      }
    }
  }

  /**
   * A wrapper method to update various status of each player based on the game
   * rule. In evo2, it will update the resources and the max tech level (if needed
   * to upgrade) for each player.
   * 
   * @apiNote This method can only be called AFTER resolved all combats.
   * @since evolution 2
   */
  protected void updateAllPlayer() {
    updateAllPlayerResource();
    updateAllPlayerMaxTechLevel();
  }

  /**
   * Send an object to all the players in this game room.
   * 
   * @param o the object to be sent
   */
  protected void sendToAllPlayer(Object o) {
    for (PlayerEntity<String> p : players) {
      p.sendObject(o);
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
