package edu.duke.ece651.risc.shared;

import java.util.*;

public class V1GameBoard<T> implements Board<T> {
  ArrayList<Territory<T>> territories;
  int[][] worldMap;

  /**
   * Default constructor for V1GameBoard
   */
  public V1GameBoard() {
    territories = new ArrayList<>();
    worldMap = new int[1][1];
  }

  /**
   * Constructor for V1GameBoard, given territories and worlpmap
   */
  public V1GameBoard(ArrayList<Territory<T>> territories, int[][] worldMap) {
    this.territories = territories;
    this.worldMap = worldMap;
  }

  /**
   * This function is used to display the name of the territories and all of its
   * neighbors
   * 
   * @return String in the format like Narnia(0) (next to: Oz, Mordor, Roshar)
   */
  protected String whatisInTerritory(String name, int territoryId, Vector<Integer> neigh) {
    StringBuilder s = new StringBuilder();
    s.append(name + "(" + territoryId + ") ");
    s.append("(next to:");
    String temp = " ";
    for (Integer n : neigh) {
      Territory<T> neighTerritory = territories.get(n);
      String neighName = neighTerritory.getName();
      s.append(temp);
      s.append(neighName);
      temp = ", ";
    }
    s.append(")");
    return s.toString();
  }

  /**
   * This function is used to display amount of each types of units in the
   * territory
   * 
   * @return String is either in the format like: 10 units in or just a ""
   */
  protected String unitsInfo(HashMap<String, Integer> infoMap) {
    Iterable<String> keySet = infoMap.keySet();
    StringBuilder s = new StringBuilder();
    String temp = "";
    boolean addIn = false;
    for (String key : keySet) {
      s.append(temp);
      int amount = infoMap.get(key);
      if (amount != -1) {
        s.append(amount + " " + key);
        temp = ",";
        addIn = true;
      }
    }
    if (addIn) {
      s.append(" in ");
    }
    return s.toString();
  }

  /**
   * Display the info of one territory
   * 
   * @return String in the format like: 3 units in Roshar (next to: Hogwarts,
   *         Scadrial, Elantris) or Roshar (next to: Hogwarts, Scadrial, Elantris)
   */
  @Override
  public String whatIsIn(Territory<T> territory, boolean isSelf) {
    HashMap<String, Integer> infoMap = new HashMap<>();
    infoMap = territory.getDisplayInfo(isSelf);
    Vector<Integer> neigh = new Vector<>();
    neigh = territory.getNeigh();
    String name = territory.getName();
    int territoryId = territory.getId();
    StringBuilder s = new StringBuilder();
    s.append(unitsInfo(infoMap));
    s.append(whatisInTerritory(name, territoryId, neigh));
    s.append("\n");
    return s.toString();
  }

  /**
   * Occupy a group a territories for the owner
   * 
   * @param groupNum is the group number of the territories to be assigned
   * @param owner    is the owner of the group
   * @return true if the territory has been occupied, otherwise false
   */
  @Override
  public synchronized boolean occupyTerritory(int groupNum, int owner) {
    boolean ifOccupy = true; // true if a territory already has its owner (i.e., Territory.owner >= 0)

    for (Territory<T> t : territories) {
      if (t.getGroup() == groupNum) {
        if (t.getOwner() == -1) {
          ifOccupy = false;
          t.setOwner(owner);
          t.initCurrDefender(owner);
        }
      }
    }

    return ifOccupy;
  }

  /**
   * Deploy certain amount of units on a territory for a player
   * 
   * @param territoryId is the id of the territory to be deployed
   * @param amount      is the amount of units to be deployed
   * @param player      is the player id
   * @return true if deploy units succeed, othereise false
   */
  @Override
  public boolean deployUnits(int territoryId, int amount, int player) {
    Territory<T> territory = territories.get(territoryId);
    int owner = territory.getOwner();
    if (owner == player) {
      addOwnUnits(territoryId, amount);
      return true;
    }
    return false;
  }

  /**
   * Add own units for a territory
   * 
   * @param territoryId
   * @param amount
   */
  @Override
  public void addOwnUnits(int territoryId, int amount) {
    Territory<T> territory = territories.get(territoryId);
    int curr = territory.getUnitAmount();
    territory.setUnitAmount(amount + curr);
  }

  /**
   * Add enemy units for a territory
   * 
   * @param territoryId
   * @param amount
   * @param playerId
   */
  @Override
  public void addEnemyUnits(int territoryId, int amount, int playerId) {
    Territory<T> territory = territories.get(territoryId);
    territory.addEnemy(playerId, amount);
  }

  /**
   * Remove units from one territory
   * 
   * @param territory
   * @param amount
   * 
   */
  @Override
  public void removeUnits(int territoryId, int amount) {
    Territory<T> territory = territories.get(territoryId);
    int curr = territory.getUnitAmount();
    territory.setUnitAmount(curr - amount);

  }

  /**
   * This function is used to update every Territory's previous defender army
   * after one round done or when a territory is occupied by a player in picking
   * territory group stage.
   */
  @Override
  public synchronized void updateAllPrevDefender() {
    for (Territory<T> t : territories) {
      t.updatePrevDefender();
    }
  }

  /**
   * Getter for territories
   * 
   * @return territories
   */
  @Override
  public ArrayList<Territory<T>> getTerritories() {
    return territories;
  }

  /**
   * Getter for worldMap
   * 
   * @return worldMap
   */
  @Override
  public int[][] getWorldMap() {
    return worldMap;
  }

  @Override
  public void addOwnUnits(int territoryId, HashMap<Integer, Integer> army) {
    
    
  }

  @Override
  public void addEnemyUnits(int territoryId, HashMap<Integer, Integer> amount, int playerId) {
    
    
  }

  @Override
  public void removeUnits(int territoryId, HashMap<Integer, Integer> army) {
    
  }

}
