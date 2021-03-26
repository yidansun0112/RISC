package edu.duke.ece651.risc.shared;
import java.util.*;

public class V2GameBoard<T> extends V1GameBoard<T> {
  public V2GameBoard(ArrayList<Territory<T>> territories, int[][] worldMap){
    super(territories, worldMap);
  }

  public void addOwnUnits(int territoryId, HashMap<Integer, Integer> army) {
    Territory<T> territory = territories.get(territoryId);
    territory.addUnitAmount(army);
  }

  public void addEnemyUnits(int territoryId, HashMap<Integer, Integer> army, int playerId) {
    Territory<T> territory = territories.get(territoryId);
    territory.addEnemy(playerId, army);
  }


  public void removeUnits(int territoryId, HashMap<Integer, Integer> army) {
    Territory<T> territory = territories.get(territoryId);
    territory.removeUnitAmount(army);
  }

}












