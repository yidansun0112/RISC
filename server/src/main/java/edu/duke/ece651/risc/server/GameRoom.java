package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.Random;

import edu.duke.ece651.risc.shared.*;

/**
 * This class serves as a gameroom for the game. It holds all players, let the
 * first player to choose map, execute battle and check player status in each
 * round.
 */
public abstract class GameRoom<T> {

  /** The number of players that participates in the game in this room */
  protected int playerNum;
  /** The number of units that each player can deploy at start */
  protected int totalUnits;
  /** The list that store all the players (including owner) in this room */
  protected List<PlayerEntity<T>> players; // this field should be a thread-safe data structure, in evo1, we use a
                                           // vector
  /** The game board for the game in this room */
  protected Board<T> gameBoard;
  /** The BoardView that used to display the game board */
  protected BoardView<T> view;
  /** rule checker for move order */
  protected OrderRuleChecker<T> moveChecker;
  /** rule checker for attack order */
  protected OrderRuleChecker<T> attackChecker;
  /** battle resolver */
  protected Resolver<T> resolver;
  /** barrier */
  protected CyclicBarrier barrier;

  /**
   * Constructor that initialize class fields with corresponding parameters.
   * 
   * @param playerNum
   * @param totalUnits
   * @param players
   * @param gameBoard
   * @param view
   */
  public GameRoom(int playerNum, int totalUnits, List<PlayerEntity<T>> players, Board<T> gameBoard, BoardView<T> view) {
    this.playerNum = playerNum;
    this.totalUnits = totalUnits;
    this.players = players;
    this.gameBoard = gameBoard;
    this.view = view;
    this.moveChecker = new MoveOrderConsistencyChecker<T>(
        new MoveOrderPathChecker<T>(new MoveOrderEffectChecker<T>(null)));
    this.attackChecker = new AttackOrderConsistencyChecker<T>(
        new AttackOrderPathChecker<T>(new AttackOrderEffectChecker<T>(null)));
    this.resolver = new BattleResolver<T>(new Random(1));
  }

  /**
   * Let the first player to choose a map for this room
   */
  public abstract void chooseMap() throws IOException, ClassNotFoundException;

  /**
   * Add a new player to this room.
   * 
   * @param newPlayer a new player that connected to the server
   */
  public void addPlayer(PlayerEntity<T> newPlayer) {
    players.add(newPlayer);
  }

  /**
   * @param playerNum the playerNum to set
   */
  public void setPlayerNum(int playerNum) {
    this.playerNum = playerNum;
  }

  /**
   * @return the playerNum
   */
  public int getPlayerNum() {
    return playerNum;
  }

  /**
   * Play the game.
   * 
   * Create threads for each player. In each thread let the corresponding player
   * pick territory and deploy their units. When all the player finish their
   * deployment, start to play serverial turns, which involve:
   * 
   * 1. Receive, check and execute orders; 2. Execute battle after every player
   * finish issuing orders. 3. Increment one unit on each territory. 4. Update
   * player status.
   * 
   * When each turn ends, check if the game has a winner: if has a winner, end the
   * whole game else continue the game for the rest of the player who has not lost
   * yet.
   * 
   * @throws InterruptedException
   * @throws BrokenBarrierException
   * @throws IOException
   */
  public void playGame() throws InterruptedException, BrokenBarrierException, IOException {
    barrier = new CyclicBarrier(playerNum + 1);
    for (int i = 0; i < playerNum; i++) {
      Thread t = new GameHostThread<T>(players.get(i), Constant.TOTAL_UNITS, gameBoard, view, moveChecker,
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

      System.out.println("Battle finished, now check anyone wins");

      if (checkEnd()) {
        barrier.await();
        barrier.await();
        closeAllStreams();
        break;
      }
      barrier.await();

      System.out.println("No one wins, now waiting all players done again");

    }

  }

  /**
   * Increment one unit for every territory on the board.
   */
  public void incrementUnits() {
    int size = gameBoard.getTerritories().size();
    for (int i = 0; i < size; i++) {
      gameBoard.addOwnUnits(i, 1);
    }
  }

  /**
   * Thia method will check each players current status, e.g, how many territories
   * does each player have, to see whether a player has lost or win the game. It
   * will set all the players' status with appropriate value.
   * 
   * We abstract this method out of checkEnd() for single responsibility.
   */
  protected void updatePlayerStatus() {
    int numTerritory = gameBoard.getTerritories().size(); // how many territories in total

    int[] eachHaving = new int[playerNum]; // index is player id, value is # of territory
                                           // this player currently has

    // Count the current occupying
    for (Territory<T> t : gameBoard.getTerritories()) {
      eachHaving[t.getOwner()]++;
    }

    int winnerId = -1; // the player's id who wins the game. If no one wins, set it to -1.
    for (int i = 0; i < eachHaving.length; i++) {
      if (eachHaving[i] == numTerritory) {
        winnerId = i;
        break;
      }
    }

    // Set the player status according to whether the game has winner
    for (int i = 0; i < eachHaving.length; i++) {
      PlayerEntity<T> player = players.get(i);
      player.setPlayerStatus((winnerId != -1)
          ? ((eachHaving[i] == 0) ? Constant.SELF_LOSE_OTHER_WIN_STATUS : Constant.SELF_WIN_STATUS)
          : ((eachHaving[i] == 0) ? Constant.SELF_LOSE_NO_ONE_WIN_STATUS : Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS));
    }
  }

  /**
   * Update all players satuts (whether a player win or lose), to decide whether
   * we will end this game. If no one wins the game, the game should continue. If
   * someone wins the game, the game should end.
   * 
   * @return true if the game has winner. return false if no one wins the game
   */
  public boolean checkEnd() {
    updatePlayerStatus();
    for (PlayerEntity<T> player : players) {
      if (player.playerStatus == Constant.SELF_WIN_STATUS) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method will close the ObjectInputStreams and ObjectOutputStreams each
   * player entity holds to release the resource.
   * 
   * @throws IOException
   */
  protected void closeAllStreams() throws IOException {
    for (PlayerEntity<T> player : players) {
      player.getFromPlayer().close();
      player.getToPlayer().close();
    }
  }
}
