package edu.duke.ece651.risc.server;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.BoardTextView;
import edu.duke.ece651.risc.shared.BoardView;
import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.OrderRuleChecker;
import edu.duke.ece651.risc.shared.V1BoardFactory;
import edu.duke.ece651.risc.shared.V1GameBoard;

public class GameRoom<T> {

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
  }

  // public void initRoom() {
  // }

  /**
   * Let the first player to choose a map for this room
   */
  public void chooseMap() throws IOException, ClassNotFoundException{
    PlayerEntity<T> firstPlayer=players.get(0);
    V1BoardFactory factory=new V1BoardFactory();
    // gameBoard=factory.makeGameBoard(playerNum);
    // view=new BoardTextView(gameBoard);
    // String msg=view.displayFullBoard()+"We only have one map now, please type any number to continue.";
    // firstPlayer.sendObject(msg);
    // String choice=(String)firstPlayer.receiveObject();

  }

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

  public void playGame(){
    for(int i=0;i<playerNum;i++){
      Thread t=new GameHostThread<T>(players.get(i), Constant.TOTAL_UNITS, gameBoard, view, new OrderRuleChecker<T>(), barrier);
    }
  }
}
