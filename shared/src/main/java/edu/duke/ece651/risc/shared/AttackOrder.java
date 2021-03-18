package edu.duke.ece651.risc.shared;

public class AttackOrder<T> implements Order<T> {

  /**
   * Fields required by Serializable 
   */
  static final long serialVersionUID = 2L;

  private int SrcTerritory;
  private int DestTerritory;
  private int unitAmount;

  /**
   * Constructor that takes a string.
   * @param order is the string used to construct the object  
   * @throw IllegalArgumentException if the format is not correct
   */
  public AttackOrder(String order) {
    String[] element = order.split(" ");
    if (element.length != 3) {
      throw new IllegalArgumentException("There shoudl be 3 parts in an order\n");
    }
    try {
      SrcTerritory = Integer.parseInt(element[0]);
      DestTerritory = Integer.parseInt(element[1]);
      unitAmount = Integer.parseInt(element[2]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The order should only be made up of positive integers\n");
    }
    if(unitAmount<0){
      throw new IllegalArgumentException("The order should only be made up of positive integers\n");
    }
  }

  /**
   * Execute attack order which means remove units in attacker territory and add enemy army in destination territory
   * @param board is the board to be executed attack order on
   */
  @Override
  public boolean execute(Board<T> board) {
    try {
      Territory<T> src = board.getTerritories().get(SrcTerritory);
      int attackerId = src.getOwner();
      board.addEnemyUnits(DestTerritory, unitAmount, attackerId);
      board.removeUnits(SrcTerritory, unitAmount);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
  /**
   * Getter for SrcTerritory
   * @return  SrcTerritory
   */
  @Override
  public int getSrcTerritory() {
    return SrcTerritory;
  }
  /**
   * Getter for Desterritory
   * @return  DestTerritory
   */
  @Override
  public int getDestTerritory() {
    return DestTerritory;
  }

  /**
   * Getter for  unitAmount
   * @return  DestTerritory
   */
  @Override
  public int getUnitAmount() {
    return unitAmount;
  }

}
