package edu.duke.ece651.risc.shared;

import java.util.List;

/**
 * This is a new class introduced in evolution 2 to wrap and represent all the
 * infomation in the current game, including all player entities and the whole
 * board. The whole board, in turns, contains all the territories.
 * 
 * This class is used in order checking, and render GUI at the client side. This
 * means that the server will build an instance of this class and send it to the
 * client when needed.
 * 
 * @author zl246
 */
public class GameStatus<T> {
  /** All players in the current game room */
  List<PlayerEntity<T>> allPlayers;

  /** The whole game board which contains all territories */
  Board<T> gameBoard;

  /**
   * COnstructor that initializes the fields with corresponding parameters
   * 
   * @param allPlayers
   * @param gameBoard
   */
  public GameStatus(List<PlayerEntity<T>> allPlayers, Board<T> gameBoard) {
    this.allPlayers = allPlayers;
    this.gameBoard = gameBoard;
  }

  /**
   * @return the allPlayers
   */
  public List<PlayerEntity<T>> getAllPlayers() {
    return allPlayers;
  }

  /**
   * @return the gameBoard
   */
  public Board<T> getGameBoard() {
    return gameBoard;
  }
}
