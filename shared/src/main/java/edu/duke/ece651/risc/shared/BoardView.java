package edu.duke.ece651.risc.shared;

public abstract class BoardView<T> {
  protected Board<T> toDisplay;

  public BoardView(Board<T> display) {
    this.toDisplay = display;
  }

  public abstract T displayFullBoard();

  public abstract T displayBoardFor(int playerId);

  public abstract T displayGroup(int player);

  // public abstract T showTerritoryGroup();
}
