package edu.duke.ece651.risc.shared;

import java.util.*;

public class V1Territory<T> implements Territory<T> {
  private int group;
  private int Id;
  private int currOwner;
  private Vector<Army<T>> currDefenderArmy;
  private Vector<Army<T>> prevDefenderArmy;
  private Vector<Army<T>> enemyArmy;
  private String name;
  private Vector<Integer> neigh;

  V1Territory(int Id, String name, int group, int[] adjacentList) {
    this.Id = Id;
    this.name = name;
    this.group = group;
    this.currOwner = -1;
    neigh = new Vector<Integer>();
    Army<T> initArmy = new Army<T>();// helper instance
    currDefenderArmy = new Vector<Army<T>>();
    prevDefenderArmy = new Vector<Army<T>>();
    enemyArmy = new Vector<Army<T>>();
    currDefenderArmy.add(initArmy);
    //prevDefenderArmy.add(initArmy);
    for (int i = 0; i < adjacentList.length; i++) {
      if (adjacentList[i] != 0&& i!=Id) { // in case need distance in the future
        neigh.add(i);
      }
    }
  }

  /**
   * This function gets the amount of units in each army of current defender
   * @return HashMap<String, Integer> that records the amount of units in each army of current defender.
   */
  @Override
  public HashMap<String, Integer> getDisplayInfo(boolean isSelf) {
    HashMap<String, Integer> res = new HashMap<>();
    if (isSelf) {
      res.put("units", currDefenderArmy.get(0).getUnits());
    } else {
      res.put("units", prevDefenderArmy.get(0).getUnits());
    }
    return res;
  }

  @Override
  public void setOwner(int owner) {
    currOwner = owner;

  }

  @Override
  public int getOwner() {
    return currOwner;
  }

  /**
   * Set the amount of units in first army of current Defender Army 
   * (Actually in evol1, there would be and only be 1 army in current defender army)
   */
  @Override
  public void setUnitAmount(int amount) {
    currDefenderArmy.get(0).setUnits(amount);
  }

  /**
   * get the amount of units in first army of current Defender Army 
   * (Actually in evol1, there would be and only be 1 army in current defender army)
   */
  @Override
  public int getUnitAmount() {
    return currDefenderArmy.get(0).getUnits();
  }

  
  @Override
  public int getId() {
    return Id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getGroup() {
    return group;
  }

  @Override
  public Vector<Integer> getNeigh() {
    return neigh;
  }


  @Override
  public Vector<Army<T>> getCurrDefenderArmy() {
    return this.currDefenderArmy;
  }

  @Override
  public Vector<Army<T>> getEnemyArmy(){
    return this.enemyArmy;
  }

  /**
   * This add an army of enemy in the territory
   */
  @Override
  public void addEnemy(int playerId, int amount){
    Army<T> temp = new Army<T>(playerId, amount);
    this.enemyArmy.add(temp);
  }

  /**
  *  This update the previous defender army to be the same with current defender army after all the combat ends.
  */
  @Override
  public void updatePrevDefender() {
    
    for(Army<T> a : currDefenderArmy) {
      // BUG: if two threads call board.updateAllPrevDefender(), for one territory, 
      // this will add Armies for twice, which errorly doubled the prevDefenderArmy!!!
      prevDefenderArmy.add(new Army<T>(a.getCommanderId(),a.getUnits()));
    }
    
  }

  /**
   * Set units number of each army in current defender army to be 0
   * only call this when one territory is assigned an owner
   */
  @Override
  public void initCurrDefender(int owner) {
    for(Army<T> army: currDefenderArmy){
      army.setUnits(0);
      army.setCommanderId(owner);
    }
	
  }

@Override
public void combineEnemyArmy() {
  HashMap<Integer, Army<T>> enemy = new HashMap<Integer,Army<T>>();
  for(Army<T> a : enemyArmy){
    int commander = a.getCommanderId();
 
    if(enemy.containsKey(commander)){
      int prev = enemy.get(commander).getUnits();
      enemy.get(commander).setUnits(a.getUnits()+prev);
    }
    else{
      enemy.put(commander, new Army<T>(commander,a.getUnits()));
    }
  }
  enemyArmy = new Vector<Army<T>>();
  for(Integer key : enemy.keySet()){
    enemyArmy.add(enemy.get(key));
  }
	
}

}












