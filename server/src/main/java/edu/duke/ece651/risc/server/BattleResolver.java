package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.shared.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * This class handles resolving a battle in each round.
 * 
 * @param T String for Version 1
 */
public class BattleResolver<T> implements Resolver<T> {

  /** The Random object to get random index when combating */
  private Random rdm;

  /**
   * Constructor for BattleResolver
   * 
   * @param Random set rdm as a parameter here for testing
   */
  public BattleResolver(Random r) {
    rdm = r;
  }

  /**
   * This method helps to combine all enemy armies on one territory from one
   * player together.
   * 
   * @param Board<T> board with all territories on.
   */
  @Override
  public void combineEnemyArmy(Board<T> board) {
    // go through every territory
    // go through every enemyArmy
    // and combine
    ArrayList<Territory<T>> territory = board.getTerritories();
    for (Territory<T> t : territory) {
      t.combineEnemyArmy();
    }
  }

  /**
   * Executes battles on each territory on the board.
   * 
   * @param Board<T> board with all territories on.
   */
  public void executeAllBattle(Board<T> board) {
    combineEnemyArmy(board);
    // go through territory and combatOnTerritory
    ArrayList<Territory<T>> territories = board.getTerritories();
    for (int i = 0; i < territories.size(); i++) {
      combatOnTerritory(territories.get(i));
    }
  }

  /**
   * Execute combat on one territory
   * 
   * Randomly choose one Army from enemy to combat. The winner will become next
   * round's defender. Repeat until no enemy left.
   * 
   * @param Territory<T> battleField
   */
  public void combatOnTerritory(Territory<T> battleField) {
    Vector<Army<T>> currDefender = battleField.getCurrDefenderArmy();
    Vector<Army<T>> enemyArmy = battleField.getEnemyArmy();
    // while loop until enemyArmy is empty
    while (enemyArmy.size() > 0) {
      // get an random index amony (0-enemyArmy size)
      int index = rdm.nextInt(enemyArmy.size());
      Army<T> defender = currDefender.get(0);
      Army<T> attacker = enemyArmy.get(index);
      // winner = conbatBetween return
      currDefender.set(0, combatBetween(defender, attacker));
      // delete enemy
      enemyArmy.remove(index);
    }
    battleField.setOwner(currDefender.get(0).getCommanderId());
  }

  /**
   * One round combat between two armies.
   * 
   * Randomly get two int between 0-19 for defender and attacker. The smaller side
   * lose one unit. When there is a tie, the attacker lose one unit. Repeat until
   * one side has no unit left.
   * 
   * @param Army<T> defender
   * @param Army<T> attacker
   * @return Army<T> winner
   */
  public Army<T> combatBetween(Army<T> defender, Army<T> attacker) {
    // while loop (defender and attacker left both >0)
    while (defender.getBasicUnits() > 0 && attacker.getBasicUnits() > 0) {
      // get two random int, (0-19),
      int forDefender = rdm.nextInt(Constant.DICE_SIDE);
      int forAttacker = rdm.nextInt(Constant.DICE_SIDE);
      // compare, little one unit -1
      if (forDefender >= forAttacker) {
        attacker.minusBasicUnit(1);
      } else {
        defender.minusBasicUnit(1);
      }
    }
    if (defender.getBasicUnits() > 0) {
      return defender;
    } else {
      return attacker;
    }
  }
}
