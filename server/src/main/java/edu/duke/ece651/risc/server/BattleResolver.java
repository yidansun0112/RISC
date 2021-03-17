package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.Territory;
import java.util.Random;

public class BattleResolver<T> implements Resolver<T> {
  
  private Random rdm;

  public BattleResolver(){
    rdm=new Random();
  }

  @Override
  public void combineEnemyArmy(Board<T> board){
    //go through every territory
    //go through every enemyArmy
    //and combine

  }

  public void executeAllBattle(Board<T> board){
    //combineEnemyArmy
    //go through territory
    //combatOnTerritory
  }

  public void combatOnTerritory(Territory<T> battleField){
    //while loop untile enemyArmy is empty
    //get an random index amony (0-enemyArmy size)
    //then get currDefender and enemy, and pass it to combatBetween
    //winner = conbatBetween return
    //delete enemy
    //currDefender=winner
  }

  /**
   * combat between two army.
   */
  public Army<T> combatBetween(Army<T> defender, Army<T> attacker){
    //while loop (defender and attacker left both >0)
    while(defender.getUnits()>0&&attacker.getUnits()>0){
      //get two random int, (0-19),
      int forDefender=rdm.nextInt(Constant.DICE_SIDE);
      int forAttacker=rdm.nextInt(Constant.DICE_SIDE);
      //compare, little one unit -1
      if(forDefender>=forAttacker){
        attacker.minusUnit(1);
      }
      else{
        defend.minusUnit(1);
      }
    }
    if(defender.getUnits()>0){
      return defender;
    }
    else{
      return attacker;
    }
  }
}
