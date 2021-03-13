package edu.duke.ece651.risc.shared;

import java.util.*;
public abstract class Board<T> {
  private ArrayList<Territory<T>> territories;
  private ArrayList<ArrayList<Integer>> worldMap;
  public Board(ArrayList<Territory<T>> territories, ArrayList<ArrayList<Integer>> worldMap){
    this.territories = territories;
    this.worldMap = worldMap;  
  }
  abstract public T whatIsIn(Territory<T> territory, boolean isSelf);
  abstract public boolean occupyTerritory(int owner, int territoryId);
  abstract public void addUnits(int territoryId, int amount);
  abstract public void removeUnits(int territory, int amount);
  public ArrayList<Territory<T>> getTerritories(){
    return territories;
  }
  public ArrayList<ArrayList<Integer>> getWorldMap(){
    return worldMap;
  }
}
