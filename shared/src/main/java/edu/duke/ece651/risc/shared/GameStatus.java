package edu.duke.ece651.risc.shared;

/**
 * This is a new class introduced in evolution 2 to wrap and represent all the
 * infomation in the current game, including a specific player entitiy in a game
 * host thread, and the whole board. The whole board, in turns, contains all the
 * territories.
 * 
 * This class is used in order checking, and render GUI at the client side. This
 * means that the server will build an instance of this class and send it to the
 * client when needed.
 * 
 * @author zl246
 */
public class GameStatus<T> {
  /** The player in the game host thread */
  PlayerEntity<T> currPlayer;
  
  // we might need this field in evo3, for now, we remove it.
  
  // /** All players in the current game room */
  // List<PlayerEntity<T>> allPlayers;
  
  /** The whole game board which contains all territories */
  Board<T> gameBoard;

  /**
   * The GUI will check this boolean to decide whether show prevDefenderArmy or
   * currDefenderArmy to the GUI player.
   * 
   * When displaying the territory belongs to myself, always show the
   * currDefenderArmy.
   * 
   * WHen displaying the territory that does not belongs to myself, which to show
   * depends on the value of canShowLatest:
   * 
   * a) If true, then GUI will show currDefenderArmy to GUI (i.e., the latest
   * territory status).
   * 
   * b) If false, then GUI will show prevDefenderArmy to GUI
   * 
   */
  boolean canShowLatest;

  /**
   * Constructor that initializes the fields with corresponding parameters
   * 
   * @param currPlayer    the PlayerEntity object that represent a specific player
   *                      in a game host thread
   * @param gameBoard     the GameBoard object that players are playing in
   * @param canShowLatest whether can show the latest territory status (i.e.,
   *                      currDefenderArmy) to show to the client
   */
  public GameStatus(PlayerEntity<T> currPlayer, Board<T> gameBoard, boolean canShowLatest) {
    this.currPlayer = currPlayer;
    this.gameBoard = gameBoard;
    this.canShowLatest = canShowLatest;
  }

  /**
   * @return the gameBoard
   */
  public Board<T> getGameBoard() {
    return gameBoard;
  }

  /**
   * @return the currPlayer
   */
  public PlayerEntity<T> getCurrPlayer() {
    return currPlayer;
  }

  /**
   * @param currPlayer the currPlayer to set
   */
  public void setCurrPlayer(PlayerEntity<T> currPlayer) {
    this.currPlayer = currPlayer;
  }

  /**
   * @return the canShowLatest
   */
  public boolean getCanShowLatest() {
    return canShowLatest;
  }
  
}
