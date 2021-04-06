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

  @Override
  public int findMinPathDistance(int srcTerritoryId, int destTerritoryId, int playerId) {
    HashSet<Integer> s = new HashSet<>();

    // HashSet to help to make sure that every territory is unique.
    HashSet<Integer> helper = new HashSet<>();
    // For the int[], int[0] is the territory ID, int[1] is the previour Territory
    // Id, int[2] is the value.
    PriorityQueue<int[]> priorityQ = new PriorityQueue<int[]>(new Comparator<int[]>() {
      public int compare(int[] edge1, int[] edge2) {
        return edge1[2] - edge2[2];
      }
    });
    // Iterate Over all of the territories to add the Player's Territory to the
    // PriorityQueue.
    for (Territory<T> terr : territories) {
      if (terr.getOwner() == playerId) { // && terr.getId() != srcTerritoryId){
        // int curr = terr.getId();
        for (int curr : terr.getNeigh()) {
          // Make Sure that every territory will only be added once.
          if (curr == srcTerritoryId || helper.contains(curr) || territories.get(curr).getOwner() != playerId) {
            continue;
          }
          priorityQ.offer(new int[] { curr, curr, Integer.MAX_VALUE });
          helper.add(curr);
        }
      }
    }

    priorityQ.offer(new int[] { srcTerritoryId, srcTerritoryId, territories.get(srcTerritoryId).getSize() });
    while (!priorityQ.isEmpty()) {
      PriorityQueue<int[]> helperQ = new PriorityQueue<int[]>(new Comparator<int[]>() {
        public int compare(int[] edge1, int[] edge2) {
          return edge1[2] - edge2[2];
        }
      });
      int[] u = priorityQ.poll();
      // As s alway maintain the minPathSet. So if we encounter the srcTerritoryId, we
      // can just return the value;
      if (u[0] == destTerritoryId) {
        return u[2] < 0 ? Integer.MAX_VALUE : u[2];
      }
      s.add(u[0]);
      // Relex all of the vertex
      for (int[] terr : priorityQ) {
        // If the terr is the Player's terr and adjacent, we relax
        if (worldMap[u[0]][terr[0]] > 0) {
          if (terr[2] > u[2] + territories.get(terr[0]).getSize()) {
            terr[1] = u[0];
            terr[2] = u[2] + territories.get(terr[0]).getSize();
          }
          helperQ.offer(terr);
        } else {
          helperQ.offer(terr);
          continue;
        }
      }
      priorityQ = helperQ;
    }
    return Integer.MAX_VALUE;
  }

}
