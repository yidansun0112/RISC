package edu.duke.ece651.risc.shared;

import java.io.Serializable;
import java.util.*;

public interface Territory<T> extends Serializable{
  /**
   * This function gets the amount of units in defender army of current defender
   * (when display for self) or prev defender (when display to enemy players)
   * 
   * @return HashMap<String, Integer> that records the amount of units in each
   *         army of current defender.
   */
  public HashMap<String, Integer> getDisplayInfo(boolean isSelf);

  /**
   * Set the Owner
   */
  public void setOwner(int owner);

  /**
   * Return the Territory Owner.
   */
  public int getOwner();

  /**
   * Set the amount of units in first army of current Defender Army (Actually in
   * evol1, there would be and only be 1 army in current defender army)
   */
  public void setBasicDefendUnitAmount(int amount);

  /**
   * get the amount of units in first army of current Defender Army (Actually in
   * evol1, there would be and only be 1 army in current defender army)
   */
  public int getBasicDefendUnitAmount();

  /**
   * return id
   */
  public int getId();

  /**
   * return name.
   */
  public String getName();

  /**
   * return Group Num.
   */
  public int getGroup();

  /**
   * Return neighbor Vectore
   */
  public Vector<Integer> getNeigh();

  /**
   * return DefenderArmy Vectore
   */
  public Vector<Army<T>> getCurrDefenderArmy();

  /**
   * return PrevDefenderArmy Vectore
   */
  public Vector<Army<T>> getPrevDefenderArmy();

  /**
   * return enemyArmy Vectore
   */
  public Vector<Army<T>> getEnemyArmy();

  /**
   * This add an army of enemy which only contains basic units, into the territory
   */
  public void addBasicEnemy(int playerId, int amount);

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

  /**
   * Combine the Army with the same Commander.
   */
  public void combineEnemyArmy();

  /***************************************
   * The Following is The Evolution 2 Code
   ***************************************/

  /**
   * Add the specified amount of units with specified level, into the army which
   * belongs to the specified player.
   * 
   * @since evolution 2
   * 
   * @param playerId the player id which the unit(army) belongs to
   * @param level    the level of the units
   * @param amt      the amount of the units to add
   */
  public void addEnemy(int playerId, int level, int amt);

  /**
   * This pair of Function will replace the setDefendUnitAmount in Evol1 to add or
   * remove units from the army.
   */

  /**
   * Add the specified amount of units with specifed level to the defender army
   * 
   * @since evolution 2
   * 
   * @param level the level of the units to add
   * @param amt   the amount of units to add
   */
  public void addDefendUnits(int level, int amt);

  /**
   * This will remove the specifed amount of units with spcified level from the
   * Current Defender Army
   * 
   * @since evolution 2
   * 
   * @param level the level of the units to remove
   * @param amt   the amount of units to remove
   */
  public void removeDefendUnits(int level, int amt);

  /**
   * Return the amount of food resources produced in one turn
   * 
   * @since evolution 2
   * 
   * @return the amount of food resource
   */
  public int getFoodProduction();

  /**
   * Return the amount of technology resources produced in one turn
   * 
   * @since evolution 2
   * 
   * @return the amount of technology resource
   */
  public int getTechProduction();

  /**
   * Get the size of the territory
   * 
   * @since evolution 2
   * 
   * @return the size of the territory
   */
  public int getSize();
}
