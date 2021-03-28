package edu.duke.ece651.risc.shared;

import java.util.*;

public class V1Territory<T> implements Territory<T> {
  protected int group;
  protected int id;
  protected int currOwner;
  protected Vector<Army<T>> currDefenderArmy;
  protected Vector<Army<T>> prevDefenderArmy;
  protected Vector<Army<T>> enemyArmy;
  protected String name;
  protected Vector<Integer> neigh;

  V1Territory(int id, String name, int group, int[] adjacentList) {
    this.id = id;
    this.name = name;
    this.group = group;
    this.currOwner = -1;
    neigh = new Vector<Integer>();
    Army<T> initArmy = new Army<T>();// helper instance
    currDefenderArmy = new Vector<Army<T>>();
    prevDefenderArmy = new Vector<Army<T>>();
    enemyArmy = new Vector<Army<T>>();
    currDefenderArmy.add(initArmy);
    // prevDefenderArmy.add(initArmy);
    for (int i = 0; i < adjacentList.length; i++) {
      if (adjacentList[i] != 0 && i != id) { // in case need distance in the future
        neigh.add(i);
      }
    }
  }

  // TODO: pay the technical debt here... after finish all refactoring...
  // do not add an army when create a territory, and before get curr defender army
  // we first check whether currDefender.size == 0
  V1Territory(int id, String name, int group, int[] adjacentList, int currOwner) {
    this.id = id;
    this.name = name;
    this.group = group;
    this.currOwner = -1;
    neigh = new Vector<Integer>();
    Army<T> initArmy = new V2Army<T>(currOwner);// helper instance
    currDefenderArmy = new Vector<Army<T>>();
    prevDefenderArmy = new Vector<Army<T>>();
    enemyArmy = new Vector<Army<T>>();
    currDefenderArmy.add(initArmy);
    // prevDefenderArmy.add(initArmy);
    for (int i = 0; i < adjacentList.length; i++) {
      if (adjacentList[i] != 0 && i != id) { // in case need distance in the future
        neigh.add(i);
      }
    }
  }

  /**
   * This function gets the amount of units in defender army of current defender
   * (when display for self) or prev defender (when display to enemy players)
   * 
   * @return HashMap<String, Integer> that records the amount of units in each
   *         army of current defender.
   */
  @Override
  public HashMap<String, Integer> getDisplayInfo(boolean isSelf) {
    HashMap<String, Integer> res = new HashMap<>();
    if (isSelf) {
      res.put("units", currDefenderArmy.get(0).getBasicUnits());
    } else {
      res.put("units", prevDefenderArmy.get(0).getBasicUnits());
    }
    return res;
  }

  /**
   * Set the Owner
   */
  @Override
  public void setOwner(int owner) {
    currOwner = owner;
  }

  /**
   * Return the Territory Owner.
   */
  @Override
  public int getOwner() {
    return currOwner;
  }

  // TODO: after fix all syntax error, rename this method into setBasicDefendUnit
  /**
   * Set the amount of units in first army of current Defender Army (Actually in
   * evol1, there would be and only be 1 army in current defender army)
   */
  @Override
  public void setDefendUnitAmount(int amount) {
    Army<T> armyToSet = currDefenderArmy.get(0);
    armyToSet.setBasicUnits(0);
    addUnitAmount(0, amount);
  }

  // TODO: after fix all syntax error, rename this method into getBasicDefendUnit
  /**
   * get the amount of units in first army of current Defender Army (Actually in
   * evol1, there would be and only be 1 army in current defender army)
   */
  @Override
  public int getDefendUnitAmount() {
    return currDefenderArmy.get(0).getBasicUnits();
  }

  /**
   * return id
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * return name.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * return Group Num.
   */
  @Override
  public int getGroup() {
    return group;
  }

  /**
   * Return neighbor Vectore
   */
  @Override
  public Vector<Integer> getNeigh() {
    return neigh;
  }

  /*
   * return DefenderArmy Vector
   */
  @Override
  public Vector<Army<T>> getCurrDefenderArmy() {
    return this.currDefenderArmy;
  }

  /**
   * return enemyArmy Vector
   */
  @Override
  public Vector<Army<T>> getEnemyArmy() {
    return this.enemyArmy;
  }

  /**
   * This add an army of enemy in the territory For Evolution 1
   */
  @Override
  public void addBasicEnemy(int playerId, int amount) {
    addEnemy(playerId, 0, amount);
  }
  
  /**
   * This update the previous defender army to be the same with current defender
   * army after all the combat ends.
   */
  @Override
  public void updatePrevDefender() {
    prevDefenderArmy.clear();
    for (Army<T> a : currDefenderArmy) {
      prevDefenderArmy.add(new Army<T>(a.getCommanderId(), a.getBasicUnits()));
    }
  }
  
  /**
   * Set units number of each army in current defender army to be 0 only call this
   * when one territory is assigned an owner
   */
  @Override
  public void initCurrDefender(int owner) {
    for (Army<T> army : currDefenderArmy) {
      army.setBasicUnits(0);
      army.setCommanderId(owner);
    }
  }
  
  /**
   * Combine the Army with the same Commander.
   */
  @Override
  public void combineEnemyArmy() {
    HashMap<Integer, Army<T>> enemy = new HashMap<Integer, Army<T>>();
    for (Army<T> a : enemyArmy) {
      int commander = a.getCommanderId();

      if (enemy.containsKey(commander)) {
        int prev = enemy.get(commander).getBasicUnits();
        enemy.get(commander).setBasicUnits(a.getBasicUnits() + prev);
      } else {
        enemy.put(commander, new Army<T>(commander, a.getBasicUnits()));
      }
    }
    enemyArmy.clear();
    for (Integer key : enemy.keySet()) {
      enemyArmy.add(enemy.get(key));
    }
  }
  
  /**
   * This method is the impementation suitable for evo1, which will ignore the
   * level, and just create an army, add the specified amount of units into it.
   */
  @Override
  public void addEnemy(int playerId, int level, int amt) {
    Army<T> temp = new Army<T>(playerId, amt);
    this.enemyArmy.add(temp);
  }

  // TODO: after fix all syntax error, rename this method into removeDefendUnit
  /**
   * This will help to remove the Unit in the Current Defender Army Evol2
   * 
   * Provide implementation in evo1 for LSP satisfaction
   */
  @Override
  public void removeUnitAmount(int level, int amt) {
    currDefenderArmy.get(0).removeUnit(level, amt);
  }

  // TODO: after fix all syntax error, rename this method into addDefendUnit
  /**
   * This will help to add the Unit in the Current Defender Army Evol2
   * 
   * Provide implementation in evo1 for LSP satisfaction
   */
  @Override
  public void addUnitAmount(int level, int amt) {
    currDefenderArmy.get(0).addUnit(level, amt);
  }
}
