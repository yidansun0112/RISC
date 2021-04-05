package edu.duke.ece651.risc.shared;

import java.io.Serializable;
import java.util.*;

public interface Board<T> extends Serializable {
  /**
   * Return the units deployment on a teritory based on if from self view
   * 
   * @since evolution 1
   * 
   * @param territory is the Territory to be operated on
   * @param isSelf    is to indicate if forom self view
   * @return String that show the deploment info of a territory
   */
  public String whatIsIn(Territory<T> territory, boolean isSelf);

  /**
   * Getter for territories
   * 
   * @since evolution 1
   * 
   * @return territories
   */
  public ArrayList<Territory<T>> getTerritories();

  /**
   * Getter for worldMap
   * 
   * @since evolution 1
   * 
   * @return worldMap
   */
  public int[][] getWorldMap();

  /**
   * Occupy a group a territories for the owner
   * 
   * @since evolution 1
   * 
   * @param groupNum is the group number of the territories to be assigned
   * @param owner    is the owner of the group
   * @return true if the territory has been occupied, otherwise false
   */
  public boolean occupyTerritory(int groupNum, int owner);

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
  public boolean deployUnits(int territoryId, int amount, int player);

  /**
   * Add own units for a territory
   * 
   * @since evolution 1
   * 
   * @param territoryId
   * @param amount
   */
  public void addBasicDefendUnitsTo(int territoryId, int amount);

  /**
   * Remove units from one territory
   * 
   * @since evolution 1
   * 
   * @param territory
   * @param amount
   * 
   */
  public void removeBasicDefendUnitsFrom(int territory, int amount);

  /**
   * Add enemy units for a territory
   * 
   * @since evolution 1
   * 
   * @param territoryId
   * @param amount
   * @param playerId
   */
  public void addBasicEnemyUnitsTo(int territoryId, int amount, int playerId);

  /**
   * Update previous defenders for all territories on the board
   * 
   * @since evolution 1
   */
  abstract public void updateAllPrevDefender();

  /**************************************************
   * Below are the methods introduced in evolution 2
   **************************************************/

  /**
   * @since evolution 2
   * 
   * @param territoryId the territory id to add units to
   * @param level       the level of the units which to be added
   * @param amount      the amount of units to add
   */
  public void addDefendUnitsTo(int territoryId, int level, int amount);

  /**
   * Remove the specified amount of units with specified level from the specified
   * territory's defender army.
   * 
   * @since evolution 2
   * 
   * @param territoryId the territory id to remove units from
   * @param level       the level of the units which to be removed
   * @param amount      the amount of units to remove
   */
  public void removeDefendUnitsFrom(int territoryId, int level, int amount);

  // TODO: adjust the parameter order in the end? terr id, player id, level, amt?
  /**
   * This method will add specified amount of units with specified level into the
   * enemy army, which is on the specified territory and belongs to the specified
   * player.
   * 
   * @since evolution 2
   * 
   * @param territoryId the territory id to add the units to
   * @param playerId    the player who wants to attack this territory (add enemy
   *                    into it)
   * @param level       the level of the units
   * @param amt         the amount of the units to add
   */
  public void addEnemyUnitsTo(int territoryId, int level, int amount, int playerId);

  /**
   * This method is for Path Finding. 
   * @param srcTerritoryId the source territoryId
   * @param destTerritoryId the destination territoryId
   * @return The min value of the path. 
   */
  public int findMinPathDistance(int srcTerritoryId, int destTerritoryId, int playerId);
}
