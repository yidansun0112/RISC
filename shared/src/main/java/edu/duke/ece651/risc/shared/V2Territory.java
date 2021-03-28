package edu.duke.ece651.risc.shared;

import java.util.HashMap;

public class V2Territory<T> extends V1Territory<T> {

  /** The size of this territory */
  int size;

  /**
   * The amount of food resource this territory can produce to its owner per turn
   */
  int foodProductivity;

  /**
   * The amount of technology resource this territory can produce to its owner per
   * turn
   */
  int techProductivity;

  /**
   * COnstructor that will initailize fields with corresponding parameters, except
   * the currOwner will be initialized with -1 (the territory should not have a
   * owner when just created.).
   * 
   * This constuctor will also put a instance of army of the corresponding type
   * (here is the V2Army) with commanderId equals -1 into a currDefenderArmy. This
   * instance will help to display the amount of unit at the start of the game.
   * 
   * @param id           the id of the territory.
   * @param name         the name of the territory
   * @param group        the group this territory belongs to
   * @param adjacentList the ids of territories which this territory adjacent to
   */
  public V2Territory(int id, String name, int group, int[] adjacentList, int size, int foodProductivity,
      int techProductivity) {
    super(id, name, group, adjacentList);
    this.size = size;
    this.foodProductivity = foodProductivity;
    this.techProductivity = techProductivity;
  }

  /**
   * This update the previous defender army to be the same with current defender
   * army after all the combat ends.
   */
  @Override
  public void updatePrevDefender() {
    if (currDefenderArmy.isEmpty()) { // will only this if-clause once at the beginning of the game before
                                      // pickTerritory
      Army<T> helperArmy = new V2Army<T>(-1); // helper instance
      currDefenderArmy.add(helperArmy);
    }
    prevDefenderArmy.clear();
    for (Army<T> currArmy : currDefenderArmy) {
      int currArmyOwner = currArmy.getCommanderId();
      V2Army<T> prevArmyToAdd = new V2Army<T>(currArmyOwner);

      // Now "copy" the unit info into prevDefenderArmy
      for (int lv = 0; lv <= Constant.TOTAL_LEVELS; lv++) { // max level is level 6, so use <= here
        int howMany = currArmy.getUnitAmtByLevel(lv);
        if (howMany > 0) {
          prevArmyToAdd.addUnit(lv, howMany);
        }
      }
      // Finish building the prevArmyToAdd, now add it
      prevDefenderArmy.add(prevArmyToAdd);
    }
  }

  /**
   * Combine the Army with the same Commander.
   * 
   * Actually, in evo2, the addEnemy() will automatically find the same
   * commander's army and add units into it. But the evo1 version of this method
   * is not suitable for evo2. To satisfy the LSP, we need to provide an evo2
   * version implementation.
   */
  @Override
  public void combineEnemyArmy() {
    HashMap<Integer, Army<T>> commanderToArmy = new HashMap<Integer, Army<T>>();
    for (Army<T> a : enemyArmy) {
      int commander = a.getCommanderId();
      if (commanderToArmy.containsKey(commander)) {
        Army<T> alreadyExist = commanderToArmy.get(commander);
        combineTwoArmies(alreadyExist, a);
      } else {
        commanderToArmy.put(commander, a);
      }
    }

    // Now commanderToArmy contains all combined army, replace the army instance
    // with new ones.
    enemyArmy.clear();
    for (Army<T> combinedArmy : commanderToArmy.values()) {
      enemyArmy.add(combinedArmy);
    }
  }

  /**
   * Helper method to combine two armies if these two armies can combine together.
   * Combine a2 into a1.
   * 
   * @param a1 the army which will receive the combination
   * @param a2 the army which will be combined into a1
   */
  protected void combineTwoArmies(Army<T> a1, Army<T> a2) {
    for (int lv = 0; lv <= Constant.TOTAL_LEVELS; lv++) {
      int howMany = a2.getUnitAmtByLevel(lv);
      a1.addUnit(lv, howMany);
    }
  }

  /***************************************
   * The Following is The Evolution 2 Code
   ***************************************/

  /**
   * This method will add the specified amount of units with specified level into
   * the army which belongs to the player, who has the specified player id
   * 
   * Note: this method will automatically find the army which belongs to the
   * player, and add units into it. Only if army is not exist will this method
   * create an army for this player. It will not create a new army each time
   * called.
   * 
   * @since evolution 2
   * 
   * @param playerId the player id which the unit(army) belongs to
   * @param level    the level of the units
   * @param amt      the amount of the units to add
   */
  @Override
  public void addEnemy(int playerId, int level, int amt) {
    // First we will find the army which matches the player id
    Army<T> armyToAdd = null;
    for (Army<T> army : enemyArmy) {
      if (army.getCommanderId() == playerId) {
        armyToAdd = army;
        break;
      }
    }

    // If there is no army belongs to the player yet, created one
    if (armyToAdd == null) {
      armyToAdd = new V2Army<>(playerId);
    }

    // Now add the units into this army
    armyToAdd.addUnit(level, amt);

    // Finally remember to put this army into enemyArmy field
    enemyArmy.add(armyToAdd);
  }

  /**
   * Add specified amount of units with specified level to the defender army
   * 
   * @since evolution 2
   * 
   * @param level the level of the units to add
   * @param amt   the amount of units to add
   */
  @Override
  public void addDefendUnits(int level, int amt) {
    currDefenderArmy.get(0).addUnit(level, amt);
  }

  /**
   * Remove specified amount of units with specified level from the defender army
   * 
   * @since evolution 2
   * 
   * @param level the level of the units to remove
   * @param amt   the amount of units to remove
   */
  @Override
  public void removeDefendUnits(int level, int amt) {
    currDefenderArmy.get(0).removeUnit(level, amt);
  }

  /**
   * Return the amount of technology resources produced in one turn
   * 
   * @since evolution 2
   * 
   * @return the amount of technology resource
   */
  @Override
  public int getFoodProduction() {
    return this.foodProductivity;
  }

  /**
   * Return the amount of technology resources produced in one turn
   * 
   * @since evolution 2
   * 
   * @return the amount of technology resource
   */
  @Override
  public int getTechProduction() {
    return this.techProductivity;
  }

  /**
   * Get the size of this territory.
   * 
   * @return the size of this territory
   */
  @Override
  public int getSize() {
    return this.size;
  }
}
