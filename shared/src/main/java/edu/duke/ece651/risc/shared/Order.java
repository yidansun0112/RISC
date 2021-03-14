package edu.duke.ece651.risc.shared;

import java.util.*;

public interface Order<T> {
  /*
   * This Function will help to execute the Order;
   */
  public boolean execute(ArrayList<T> territories);

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
  public int unitAmount();
}
