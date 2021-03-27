package edu.duke.ece651.risc.shared;

public class MoveOrder<T> implements Order<T> {
  /**
   * Fields required by Serializable
   */
  static final long serialVersionUID = 1L;

  private final int srcTerritory;
  private final int destTerritory;
  private final int unitAmount;

  /*
   * This Constructor will perform Format Check and construct. If it failed. it
   * will return an IllegalArumentException.
   * 
   * @Parameter s is the order which has format "src dest unit"
   */
  public MoveOrder(String s) {

    String[] element = s.split(" ");
    if (element.length != 3) {
      throw new IllegalArgumentException("There should be 3 parts in an order\n");
    }
    try {
      srcTerritory = Integer.parseInt(element[0]);
      destTerritory = Integer.parseInt(element[1]);
      unitAmount = Integer.parseInt(element[2]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("There should only be made up of integers\n");
    }

    if (unitAmount < 0) {
      throw new IllegalArgumentException("There should be positive.\n");
    }
  }

  /*
   * If the execute succeed, it will return true, else it will return false;
   */
  @Override
  public boolean execute(Board<T> board) {
    // Remove the units of Srouce and add Units in the Destination.
    try {
      board.removeUnits(srcTerritory, unitAmount);
      board.addOwnUnits(destTerritory, unitAmount);
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
    return this.srcTerritory;
  }

  /*
   * This Function will return DestTerritory ID.
   */
  @Override

  public int getDestTerritory() {
    return this.destTerritory;
  }

  /*
   * This Function will return Units Amount.
   */
  @Override
  public int getUnitAmount() {
    return this.unitAmount;
  }

}
