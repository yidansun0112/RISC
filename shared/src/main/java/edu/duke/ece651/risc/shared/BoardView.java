package edu.duke.ece651.risc.shared;

public abstract class BoardView<T> {
  /*
   * @param toDisplay The Board to be displayed
   */
  protected Board<T> toDisplay;

  public BoardView(Board<T> display) {
    this.toDisplay = display;
  }

  public abstract T displayFullBoard();

  /*
   * For different player, the view will be different
   */
  public abstract T displayBoardFor(int playerId);

  /*
   * Display the territory in a group.
   */
  public abstract T displayGroup(int player);

  // public abstract T showTerritoryGroup();
}
