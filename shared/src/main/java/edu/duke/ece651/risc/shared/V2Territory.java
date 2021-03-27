package edu.duke.ece651.risc.shared;

import java.util.*;

public class V2Territory<T> extends V1Territory<T> {

  public V2Territory(int Id, String name, int group, int[] adjacentList, int currOwner) {
    super(Id, name, group, adjacentList, currOwner);
  }

  /**
   * This Pair of the Function will help to add or remove the Unit from the
   * CurrentDefender Army.
   */

  @Override
  public void addUnitAmount(HashMap<Integer, Integer> army) {
    for (int level : army.keySet()) {
      currDefenderArmy.get(0).addUnit(level, army.getOrDefault(level, 0));
    }
  }

  @Override
  public void removeUnitAmount(HashMap<Integer, Integer> army) {
    for (int level : army.keySet()) {
      currDefenderArmy.get(0).removeUnit(level, army.getOrDefault(level, 0));
    }
  }

  /**
   * This will be the Evolution2 getUnitAmount(), this is just a piece of Code.
   */

  public HashMap<Integer, Integer> getUnitAmountV2() {
    HashMap<Integer, Integer> res = new HashMap<>();
    for (int i = 0; i <= 6; i++) {
      res.put(i, currDefenderArmy.get(0).getUnitAmtByLevel(i));
    }
    return res;
  }

  /**
   * 
   * @param playerId Is the PlayerId of the Enemy
   * @param army     is the HashMap<Level, Amount>
   *
   *                 This add an army of enemy in the territory
   */
  @Override
  public void addEnemy(int playerId, HashMap<Integer, Integer> army) {
    for (Army<T> temp : enemyArmy) {
      // If we find the cooresponding enemy , then return.
      if (temp.getCommanderId() == playerId) {
        for (int level : army.keySet()) {
          temp.addUnit(level, army.getOrDefault(level, 0));
        }
        break;
      }
    }

    // If there is no suitable
    Army<T> newArmy = new V2Army<>(playerId);
    for (int level : army.keySet()) {
      newArmy.addUnit(level, army.getOrDefault(level, 0));
    }

    enemyArmy.add(newArmy);
  }

}
