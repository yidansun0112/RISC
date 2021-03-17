package edu.duke.ece651.risc.shared;

import java.io.Serializable;

public interface Order<T> extends Serializable {
  /*
   * This Function will help to execute the Order;
   */
  public boolean execute(Board<T> board);

  /*
   * This is the SrcTerritory getter Function.
   */
  public int getSrcTerritory();

  /*
   * This is the DestTerritory getter Function.
   */
  public int getDestTerritory();

  /*
   * This is the unitAmount getter Function.
   */
  public int getUnitAmount();
}
