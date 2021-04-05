package edu.duke.ece651.risc.shared;

import java.util.HashMap;

public class DoneOrder<T> implements Order<T> {
  /**
   * Fields required by Serializable
   */
  private static final long serialVersionUID = 0L;

  private String msg;

  public DoneOrder() {
    msg = Constant.DONE_ORDER_INFO;
  }

  @Override
  public boolean execute(Board<T> board) {
    return true;
  }

  /*
   * This is the SrcTerritory getter Function.
   */
  @Override
  public int getSrcTerritory() {
    return -1;
  }

  /*
   * This is the DestTerritory getter Function.
   */
  @Override
  public int getDestTerritory() {
    return -1;
  }

  /*
   * This is the unitAmount getter Function.
   */
  @Override
  public int getUnitAmount() {
    return -1;
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
    return true;
  }

  @Override
  public HashMap<Integer, Integer> getArmyHashMap() {
    // TODO Auto-generated method stub
    return null;
  }
}