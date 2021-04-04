package edu.duke.ece651.risc.shared;

import java.util.*;

public class V2GameBoard<T> extends V1GameBoard<T> {
  /**
   * Fields required by Serializable
   */
  private static final long serialVersionUID = 12L;

  public V2GameBoard(ArrayList<Territory<T>> territories, int[][] worldMap) {
    super(territories, worldMap);
  }

  // TODO: whatIsIn maybe deprecated in evolution 2 ?

  /**************************************************
   * Below are the methods introduced in evolution 2
   * 
   * The Javadoc is same with those in the Board.java
   **************************************************/

  @Override
  public void addDefendUnitsTo(int territoryId, int level, int amount) {
    Territory<T> toAddTo = territories.get(territoryId);
    toAddTo.addDefendUnits(level, amount);
  }

  @Override
  public void removeDefendUnitsFrom(int territoryId, int level, int amount) {
    Territory<T> toRemoveFrom = territories.get(territoryId);
    toRemoveFrom.removeDefendUnits(level, amount);
  }

  @Override
  public void addEnemyUnitsTo(int territoryId, int playerId, int level, int amt) {
    Territory<T> territory = territories.get(territoryId);
    territory.addEnemy(playerId, level, amt);
  }
}
