package edu.duke.ece651.risc.shared;

import java.util.*;
public abstract class Board<T> {
  private ArrayList<Territory<T>> territories;
  private int[][] worldMap;
  public Board(){
    territories = new ArrayList<>();
    worldMap = new int[1][1];
  }
  public Board(ArrayList<Territory<T>> territories, int[][] worldMap){
    this.territories = territories;
    this.worldMap = worldMap;  
  }
  abstract public String whatIsIn(Territory<T> territory, boolean isSelf);
  abstract public boolean occupyTerritory(int owner, int territoryId);

  abstract public void addOwnUnits(int territoryId, int amount);
  abstract public void addEnemyUnits(int territoryId, int amount, int playerId);
  abstract public void removeUnits(int territory, int amount);
  public ArrayList<Territory<T>> getTerritories(){
    return territories;
  }
  public int[][] getWorldMap(){
    return worldMap;
  }

  abstract public void updateAllPrevDefender();
}













