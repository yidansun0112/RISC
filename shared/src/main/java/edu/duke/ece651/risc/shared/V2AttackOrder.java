package edu.duke.ece651.risc.shared;

import java.util.*;
public class V2AttackOrder<T> implements Order<T> {

    /**
   * Fields required by Serializable
   */
  static final long serialVersionUID = 2L;

  private int SrcTerritory;
  private int DestTerritory;
  private HashMap<Integer, Integer> unitAmount;
  //String order ?  
  public V2AttackOrder(int SrcTerritory, int DestTerritory, HashMap<Integer, Integer> unitAmount){
    this.SrcTerritory = SrcTerritory;
    this.DestTerritory = DestTerritory;
    this.unitAmount = unitAmount;
  }
    @Override
    public boolean execute(Board<T> board) {
        try{
            Territory<T> src = board.getTerritories().get(SrcTerritory);
            int attackerId = src.getOwner();
            board.addEnemyUnits(DestTerritory, unitAmount, attackerId);  
            board.removeUnits(SrcTerritory, unitAmount);  
        }catch(Exception e){
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
        return 0;
    }
    
    

}
