package edu.duke.ece651.risc.shared;

import java.util.*;

public class V2AttackOrder<T> implements Order<T> {

  /**
   * Fields required by Serializable
   */
  static final long serialVersionUID = 2L;

  private int srcTerritory;
  private int destTerritory;
  private HashMap<Integer, Integer> unitAmount;

  // String order ?
  public V2AttackOrder(int SrcTerritory, int DestTerritory, HashMap<Integer, Integer> unitAmount) {
    this.srcTerritory = SrcTerritory;
    this.destTerritory = DestTerritory;
    this.unitAmount = unitAmount;
  }

  @Override
  public boolean execute(Board<T> board) {
    // TODO: finish this after testing the v2 board and v2 territory!
    // try {
    //   Territory<T> src = board.getTerritories().get(srcTerritory);
    //   int attackerId = src.getOwner();
    //   board.addEnemyUnits(destTerritory, unitAmount, attackerId);
    //   board.removeUnits(srcTerritory, unitAmount);
    // } catch (Exception e) {
    //   return false;
    // }
    return true;
  }

  @Override
  public int getSrcTerritory() {
    return srcTerritory;
  }

  @Override
  public int getDestTerritory() {
    return destTerritory;
  }

  @Override
  public int getUnitAmount() {
    return 0;
  }
}
