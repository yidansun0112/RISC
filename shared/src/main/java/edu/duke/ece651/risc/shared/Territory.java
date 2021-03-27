package edu.duke.ece651.risc.shared;

import java.util.*;

public interface Territory<T> {
  /**
   * This function gets the amount of units in each army of current defender
   * 
   * @return HashMap<String, Integer> that records the amount of units in each
   *         army of current defender.
   */

  public HashMap<String, Integer> getDisplayInfo(boolean isSelf);

  /*
   * Set the Owner
   */
  public void setOwner(int owner);

  /*
   * Return the Territory Owner.
   */
  public int getOwner();

  /**
   * Set the amount of units in first army of current Defender Army (Actually in
   * evol1, there would be and only be 1 army in current defender army)
   */

  public void setDefendUnitAmount(int amount);

  /**
   * get the amount of units in first army of current Defender Army (Actually in
   * evol1, there would be and only be 1 army in current defender army)
   */

  public int getDefendUnitAmount();

  /*
   * return Id
   */

  public int getId();
  /*
   * return name.
   */

  public String getName();
  /*
   * return Group Num.
   */

  public int getGroup();
  /*
   * Return neighbor Vectore
   */

  public Vector<Integer> getNeigh();
  /*
   * return DefenderArmy Vectore
   */

  public Vector<Army<T>> getCurrDefenderArmy();

  /*
   * return enemyArmy Vectore
   */
  public Vector<Army<T>> getEnemyArmy();

  /**
   * This add an army of enemy in the territory
   */

  public void addEnemy(int playerId, int amount);

  /**
   * This update the previous defender army to be the same with current defender
   * army after all the combat ends.
   */

  public void updatePrevDefender();

  /**
   * Set units number of each army in current defender army to be 0 only call this
   * when one territory is assigned an owner
   */

  public void initCurrDefender(int owner);

  /*
   * Combine the Army with the same Commander.
   */
  public void combineEnemyArmy();

  /***************************
   * The Following is The Evolution 2 Code
   *************************************/

  /**
   * This add an army of enemy in the territory For Evolution 2
   */
  public void addEnemy(int playerId, HashMap<Integer, Integer> army);

  /**
   * This will get The Evol 2 Unit with a HashMap<Level, Amount>
   */
  public HashMap<Integer, Integer> getUnitAmountV2();

  /**
   * This will help to remove the Unit in the Current Defender Army
   */

  /**
   * This pair of Function will replace the setUnitAmount in Evol1 to add or
   * remove units from the army.
   */
  public void removeUnitAmount(HashMap<Integer, Integer> army);

  public void addUnitAmount(HashMap<Integer, Integer> army);

}
