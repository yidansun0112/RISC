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

<<<<<<< HEAD
 public boolean deployUnits(int territoryId, int amount, int player);
=======
  public boolean deployUnits(int territoryId, int amount, int player);
>>>>>>> 9c6ac981db6f34314fc2f66e3bcfb6eb93c3d835
 public void addOwnUnits(int territoryId, int amount);
 public void addEnemyUnits(int territoryId, int amount, int playerId);
 public void removeUnits(int territory, int amount);

  public ArrayList<Territory<T>> getTerritories();

  public int[][] getWorldMap();

  abstract public void updateAllPrevDefender();
  public String unitsInfo(HashMap<String, Integer> infoMap);

  public String whatisInTerritory(String name, int territoryId, Vector<Integer> neigh);
}













