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

  V1Territory(int Id, String name, int group, ArrayList<Integer> adjacentList) {
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
    prevDefenderArmy.add(initArmy);
    enemyArmy.add(initArmy);
    for (int i = 0; i < adjacentList.size(); i++) {
      if (adjacentList.get(i) != 0) { // in case need distance in the future
        neigh.add(i);
      }
    }
  }

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

  @Override
  public void setUnitAmount(int amount) {
    currDefenderArmy.get(0).setUnits(amount);
  }

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
  public Vector<Army<T>> getCurrDefenderArmy(){
    return this.currDefenderArmy;
  }

  @Override
  public Vector<Army<T>> getEnemyArmy(){
    return this.enemyArmy;
  }

  @Override
  public void addEnemy(int playerId, int amount){
    Army<T> temp = new Army<T>(playerId, amount);
    this.enemyArmy.add(temp);
  }
}
