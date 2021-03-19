package edu.duke.ece651.risc.shared;

import java.util.*;
public interface Board<T> {
  /**
   * Return the units deployment on a teritory based on if from self view
   * @param territory is the Territory to be operated on
   * @param isSelf is to indicate if forom self view
   * @return String that show the deploment info of a territory
   */
 public String whatIsIn(Territory<T> territory, boolean isSelf);

 /**
  * Occupy a group a territories for the owner
  * @param groupNum is the group number of the territories to be assigned
  * @param owner is the owner of the group
  * @return true if the territory has been occupied, otherwise false
  */
 public boolean occupyTerritory(int groupNum, int owner);

 /** 
  * Deploy certain amount of units on a territory for a player
  * @param territoryId is the id of the territory to be deployed
  * @param amount is the amount of units to be deployed
  * @param player is the player id 
  * @return true if deploy units succeed, othereise false
  */
 public boolean deployUnits(int territoryId, int amount, int player);

 /**
  * Add own units for a territory
  * 
  * @param territoryId
  * @param amount
  */
 public void addOwnUnits(int territoryId, int amount);

 /**
  * Add enemy units for a territory
  * @param territoryId
  * @param amount
  * @param playerId
  */
 public void addEnemyUnits(int territoryId, int amount, int playerId);

 /**
  * Remove units from one territory
  * @param territory 
  * @param amount 
  * 
  */
 public void removeUnits(int territory, int amount);

 /**
  * Getter for territories
  * @return territories
  */

  public ArrayList<Territory<T>> getTerritories();
  /**
   * Getter for worldMap
   * @return worldMap
   */
  public int[][] getWorldMap();

  /**
   * update previous defenders for all terriotories on the board
   */
  abstract public void updateAllPrevDefender();
  //public String unitsInfo(HashMap<String, Integer> infoMap);

  //public String whatisInTerritory(String name, int territoryId, Vector<Integer> neigh);
}













