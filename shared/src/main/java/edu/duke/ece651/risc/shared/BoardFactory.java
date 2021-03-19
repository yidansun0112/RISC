package edu.duke.ece651.risc.shared;

public interface BoardFactory<T> {
  /**
   * Generate a game board based on the given player number 
   * @param playerNum is the player number
   * @return a Board
   */
  public Board<T> makeGameBoard(int playerNum);

}
