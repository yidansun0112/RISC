package edu.duke.ece651.risc.shared;

import java.util.*;

public class V1GameBoard<T> implements Board<T> {
  /**
   * Fields required by Serializable
   */
  private static final long serialVersionUID = 11L;

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
   * This helper function is used to display the name of the territories and all
   * of its neighbors.
   * 
   * Called by whatIsIn()
   * 
   * @since evolution 1
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
   * This helper function is used to display amount of each types of units in the
   * territory.
   * 
   * Called by whatIsIn()
   * 
   * @since evolution 1
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
   * @since evolution 1
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
   * Getter for territories
   * 
   * @since evolution 1
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
   * @since evolution 1
   * 
   * @return worldMap
   */
  @Override
  public int[][] getWorldMap() {
    return worldMap;
  }

  /**
   * Occupy a group a territories for the owner
   * 
   * @since evolution 1
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
   * @since evolution 1
   * 
   * @param territoryId is the id of the territory to be deployed
   * @param amount      is the amount of units to be deployed
   * @param player      is the player id
   * @return true if deploy units succeed, otherwise false
   */
  @Override
  public boolean deployUnits(int territoryId, int amount, int player) {
    Territory<T> territory = territories.get(territoryId);
    int owner = territory.getOwner();
    if (owner == player) {
      addBasicDefendUnitsTo(territoryId, amount);
      return true;
    }
    return false;
  }

  /**
   * Add own units (with level 0) for a territory
   * 
   * @since evolution 1
   * 
   * @param territoryId
   * @param amount
   */
  @Override
  public void addBasicDefendUnitsTo(int territoryId, int amount) {
    addDefendUnitsTo(territoryId, 0, amount);
  }

  /**
   * Remove units from one territory
   * 
   * @since evolution 1
   * 
   * @param territory
   * @param amount
   * 
   */
  @Override
  public void removeBasicDefendUnitsFrom(int territoryId, int amount) {
    removeDefendUnitsFrom(territoryId, 0, amount);
  }

  /**
   * Add enemy units for a territory
   * 
   * @since evolution 1
   * 
   * @param territoryId
   * @param amount
   * @param playerId
   */
  @Override
  public void addBasicEnemyUnitsTo(int territoryId, int amount, int playerId) {
    addEnemyUnitsTo(territoryId, 0, amount, playerId);
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

  /*****************************************************************************
   * Below are the methods introduced in evolution 2
   * 
   * To satisfy LSP, here are the implementation suitable for evolution 1, which
   * ignore the parameter level
   *****************************************************************************/

  @Override
  public void addDefendUnitsTo(int territoryId, int level, int amount) {
    Territory<T> territory = territories.get(territoryId);
    int curr = territory.getBasicDefendUnitAmount();
    territory.setBasicDefendUnitAmount(amount + curr);
  }

  @Override
  public void removeDefendUnitsFrom(int territoryId, int level, int amount) {
    Territory<T> territory = territories.get(territoryId);
    int curr = territory.getBasicDefendUnitAmount();
    territory.setBasicDefendUnitAmount(curr - amount);
  }

  @Override
  public void addEnemyUnitsTo(int territoryId, int level, int amount, int playerId) {
    Territory<T> territory = territories.get(territoryId);
    territory.addBasicEnemy(playerId, amount);
  }
}
