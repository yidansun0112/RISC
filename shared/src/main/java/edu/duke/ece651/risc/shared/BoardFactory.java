package edu.duke.ece651.risc.shared;

public interface BoardFactory<T> {

  public Board<T> makeGameBoard(int playerNum);

}
