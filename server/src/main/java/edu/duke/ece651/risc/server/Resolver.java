package edu.duke.ece651.risc.server;

import java.util.ArrayList;

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.Territory;

/**
 * This class handles battle resolving in each round.
 * 
 * @param T String for first round.
 */
public interface Resolver<T> {
  /**
   * Combine the enemy army on a territory to prepare for the battle. If two
   * armies instance belong to the same player, this method will combine them.
   * 
   * @param board
   */
  public void combineEnemyArmy(Board<T> board);

  /**
   * Execute the battling process on all the territories a board has.
   * 
   * @param board
   */
  public void executeAllBattle(Board<T> board);

  public void executeAllBattle(Board<T> board, ArrayList<String> combatInfo);


  /**
   * Execute all th battle on a territory.
   * 
   * @param battleField
   */
  public void combatOnTerritory(Territory<T> battleField, ArrayList<String> combatInfo);
}
