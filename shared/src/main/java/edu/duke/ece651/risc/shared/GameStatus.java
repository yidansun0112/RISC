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
   * Constructor that initializes the fields with corresponding parameters
   * 
   * @param currPlayer the PlayerEntity object that represent a specific player in
   *                   a game host thread
   * @param gameBoard  the GameBoard object that players are playing in
   */
  public GameStatus(PlayerEntity<T> currPlayer, Board<T> gameBoard) {
    this.currPlayer = currPlayer;
    this.gameBoard = gameBoard;
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
}
