package edu.duke.ece651.risc.server;

import java.util.List;
import java.util.Vector;

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.BoardView;
import edu.duke.ece651.risc.shared.Constant;

public class GameRoom<T> {

  /** The number of players that participates in the game in this room */
  int playerNum;
  /** The number of units that each player can deploy at start */
  int totalUnits;
  /** The list that store all the players (including owner) in this room */
  List<PlayerEntity<T>> players;
  /** The game board for the game in this room */
  Board<T> gameBoard;
  /** The BoardView that used to display the game board */
  BoardView<T> view;

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
  public GameRoom(int playerNum, int totalUnits, List<PlayerEntity<T>> players,
      Board<T> gameBoard, BoardView<T> view) {
    this.playerNum = playerNum;
    this.totalUnits = totalUnits;
    this.players = players;
    this.gameBoard = gameBoard;
    this.view = view;
  }

  public void initRoom() {
  }

  public void chooseMap() {
  }

  /**
   * Add a new player to this room.
   * @param newPlayer a new player that connected to the server
   */
  public void addPlayer(PlayerEntity<T> newPlayer) {
    players.add(newPlayer);
  }


}
