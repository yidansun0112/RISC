package edu.duke.ece651.risc.shared;

import java.io.Serializable;
import java.util.HashMap;

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

  /********************************
   * New method used in evolution 2
   ********************************/

  /**
   * This is the execute method used in evolution 2. We remained the former
   * execute method in evo 1 to aovid changing too many evo 1 code.
   * 
   * @since evolution 2
   * 
   * @param gs the GameStatus object that contains all the territories and the
   *           players, which will be used in executing this order.
   * @return {@code true} if successfully executed; return {@code false} if the
   *         execution if failed.
   */
  public boolean execute(GameStatus<T> gs);

  /**
   * THis Function will help to get the Army HashMap, which is levelToUnit.
   */
  public HashMap<Integer, Integer> getArmyHashMap();
}
