package edu.duke.ece651.risc.shared;

public class AttackOrder<T> implements Order<T> {

  /**
   * Fields required by Serializable
   */
  static final long serialVersionUID = 2L;

  public int srcTerritory;
  public int destTerritory;
  public int unitAmount;

  /**
   * Constructor that takes a string.
   * 
   * @param order is the string used to construct the object
   * @throw IllegalArgumentException if the format is not correct
   */
  public AttackOrder(String order) {
    String[] element = order.split(" ");
    if (element.length != 3) {
      throw new IllegalArgumentException("There shoudl be 3 parts in an order\n");
    }
    try {
      srcTerritory = Integer.parseInt(element[0]);
      destTerritory = Integer.parseInt(element[1]);
      unitAmount = Integer.parseInt(element[2]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The order should only be made up of positive integers\n");
    }
    if (unitAmount < 0) {
      throw new IllegalArgumentException("The order should only be made up of positive integers\n");
    }
  }

  /**
   * Execute attack order which means remove units in attacker territory and add
   * enemy army in destination territory
   * 
   * @param board is the board to be executed attack order on
   */
  @Override
  public boolean execute(Board<T> board) {
    try {
      Territory<T> src = board.getTerritories().get(srcTerritory);
      int attackerId = src.getOwner();
      board.addBasicEnemyUnitsTo(destTerritory, unitAmount, attackerId);
      board.removeBasicDefendUnitsFrom(srcTerritory, unitAmount);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * Getter for SrcTerritory
   * 
   * @return SrcTerritory
   */
  @Override
  public int getSrcTerritory() {
    return srcTerritory;
  }

  /**
   * Getter for Desterritory
   * 
   * @return DestTerritory
   */
  @Override
  public int getDestTerritory() {
    return destTerritory;
  }

  /**
   * Getter for unitAmount
   * 
   * @return DestTerritory
   */
  @Override
  public int getUnitAmount() {
    return unitAmount;
  }

  /********************************
   * New method used in evolution 2
   ********************************/

  /**
   * Provide the dummy implementation here. This method should not be used in evo1
   * code.
   * 
   * Provided for LSP satisfaction.
   */
  @Override
  public boolean execute(GameStatus<T> gs) {
    return execute(gs.getGameBoard());
  }
}
