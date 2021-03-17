package edu.duke.ece651.risc.shared;

import java.util.*;
public interface Board<T> {
  /*private ArrayList<Territory<T>> territories;
  private int[][] worldMap;
  public Board(){
    territories = new ArrayList<>();
    worldMap = new int[1][1];
  }
  public Board(ArrayList<Territory<T>> territories, int[][] worldMap){
    this.territories = territories;
    this.worldMap = worldMap;  
    }*/
  public String whatIsIn(Territory<T> territory, boolean isSelf);
 public boolean occupyTerritory(int groupNum, int owner);

  public boolean deployUnits(int territoryId, int amount, int player);
 public void addOwnUnits(int territoryId, int amount);
 public void addEnemyUnits(int territoryId, int amount, int playerId);
 public void removeUnits(int territory, int amount);

  public ArrayList<Territory<T>> getTerritories();

  public int[][] getWorldMap();

  abstract public void updateAllPrevDefender();
}













