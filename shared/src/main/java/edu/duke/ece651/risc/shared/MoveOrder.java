package edu.duke.ece651.risc.shared;

import java.util.ArrayList;

public class MoveOrder<T> implements Order<T> {
  private final int SrcTerritory;
  private final int DestTerritory;
  private final int unitAmount;

  /*
   * This Constructor will perform Format Check and construct. If it failed. it
   * will return an IllegalArumentException.
   */
  public MoveOrder(String s) {

    String[] element = s.split(" ");
    if (element.length != 3) {
      throw new IllegalArgumentException("There should be 3 parts in an order\n");
    }
    try {
      SrcTerritory = Integer.parseInt(element[0]);
      DestTerritory = Integer.parseInt(element[1]);
      unitAmount = Integer.parseInt(element[2]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("There should only be made up of integers\n");
    }
  }

  /*
   * If the execute succeed, it will return true, else it will return false;
   */
  @Override
  public boolean execute(Board<T> board) {
    // Remove the units of Srouce and add Units in the Destination.
    try {
      board.removeUnits(SrcTerritory, unitAmount);
      board.addOwnUnits(DestTerritory, unitAmount);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /*
   * This Function will return SrcTerritory ID.
   */
  @Override
  public int getSrcTerritory() {
    return this.SrcTerritory;
  }

  /*
   * This Function will return DestTerritory ID.
   */
  @Override

  public int getDestTerritory() {
    return this.DestTerritory;
  }

  /*
   * This Function will return Units Amount.
   */
  @Override
  public int getUnitAmount() {
    return this.unitAmount;
  }
}
