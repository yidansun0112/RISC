package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.Random;

import edu.duke.ece651.risc.shared.*;

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
  /**rule checker for move order */
  protected OrderRuleChecker<T> moveChecker;
  /** rule checker for attack order */
  protected OrderRuleChecker<T> attackChecker;
  /** battle resolver */
  protected Resolver<T> resolver;
  /** barrier */
  protected CyclicBarrier barrier;

  /**
   * Default constructor. Here we only initialize the fields players with a
   * vector. Other fields will be initialized after the owner has decided the map.
   */
  public GameRoom() {
    this(0, Constant.TOTAL_UNITS, new Vector<PlayerEntity<T>>(), null, null);
  }

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
    this.moveChecker=new MoveOrderConsistencyChecker<T>(new MoveOrderPathChecker<T>(new MoveOrderEffectChecker<T>(null)));
    this.attackChecker=new AttackOrderConsistencyChecker<T>(new AttackOrderPathChecker<T>(new AttackOrderEffectChecker<T>(null)));
    this.resolver=new BattleResolver<T>(new Random());
    //this.barrier=new CyclicBarrier(playerNum);
  }

  // public void initRoom() {
  // }

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

  public void playGame() throws InterruptedException, BrokenBarrierException{
    barrier=new CyclicBarrier(playerNum);
    for(int i=0;i<playerNum;i++){
      Thread t=new GameHostThread<T>(players.get(i), Constant.TOTAL_UNITS, gameBoard, view, moveChecker,attackChecker, barrier);
      t.start();
    }
    while(true){
      barrier.await();
      resolver.executeAllBattle(gameBoard);
      incrementUnits();
      if(checkEnd()){
        break;
      }
      barrier.await();
    }
  }

  public void incrementUnits(){
    int size = gameBoard.getTerritories().size();
    for(int i=0;i<size;i++){
      gameBoard.addOwnUnits(i, 1);
    }
  }

  //TODO: to write this
  public boolean checkEnd(){
    //check
    //if somebody win, set one to win, all others to lose_some_win， and return true
    //else, if somebody lose, set its status to lose, and return false
    return false;
  }
}
