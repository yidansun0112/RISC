package edu.duke.ece651.risc.shared;

import java.util.ArrayList;

public class AttackOrder<T> implements Order<T> {

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

  @Override
  public int getSrcTerritory() {
    return SrcTerritory;
  }

  @Override
  public int getDestTerritory() {
    return DestTerritory;
  }

  @Override
  public int getUnitAmount() {
    return unitAmount;
  }

}
