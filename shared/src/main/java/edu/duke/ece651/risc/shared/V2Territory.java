package edu.duke.ece651.risc.shared;

import java.util.HashMap;

public class V2Territory<T> extends V1Territory<T> {

  // TODO: pay the technical debt here... see the todo at the second constructor
  // of V1Territory..
  public V2Territory(int id, String name, int group, int[] adjacentList, int currOwner) {
    super(id, name, group, adjacentList, currOwner);
  }

  /**
   * This update the previous defender army to be the same with current defender
   * army after all the combat ends.
   */
  @Override
  public void updatePrevDefender() {
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
      }
      else {
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

  /**
   * This method will add the specified amount of units with specified level into
   * the army which belongs to the player, who has the specified player id
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
   * This Pair of the Function will help to add or remove the Unit from the
   * CurrentDefender Army.
   */

  /**
   * Add specified amount of units with specified level to the defender army
   */
  @Override
  public void addUnitAmount(int level, int amt) {
    currDefenderArmy.get(0).addUnit(level, amt);
  }

  /**
   * Remove specified amount of units with specified level from the defender army
   */
  @Override
  public void removeUnitAmount(int level, int amt) {
    currDefenderArmy.get(0).removeUnit(level, amt);
  }
}
