package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.shared.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class BattleResolver<T> implements Resolver<T> {
  
  private Random rdm;

  public BattleResolver(Random r){
    rdm = r;
  }

  @Override
  public void combineEnemyArmy(Board<T> board){
    //go through every territory
    //go through every enemyArmy
    //and combine
    ArrayList<Territory<T>> territory = board.getTerritories();
    for(Territory<T> t: territory){
      t.combineEnemyArmy();
    }
  }

  public void executeAllBattle(Board<T> board){
    combineEnemyArmy(board);
    //go through territory and combatOnTerritory
    ArrayList<Territory<T>> territories=board.getTerritories();
    for(int i=0;i<territories.size();i++){
      combatOnTerritory(territories.get(i));
    }
    //board.updateAllPrevDefender();
  }

  public void combatOnTerritory(Territory<T> battleField){
    Vector<Army<T>> currDefender=battleField.getCurrDefenderArmy();
    Vector<Army<T>> enemyArmy=battleField.getEnemyArmy();
    //while loop until enemyArmy is empty
    while(enemyArmy.size()>0){
      //get an random index amony (0-enemyArmy size)
      int index=rdm.nextInt(enemyArmy.size());
      Army<T> defender=currDefender.get(0);
      Army<T> attacker=enemyArmy.get(index);
      //winner = conbatBetween return
      currDefender.set(0,combatBetween(defender,attacker));
      //delete enemy
      enemyArmy.remove(index);
    }
    battleField.setOwner(currDefender.get(0).getCommanderId());
    //maybe need to reset currDefender and enemyArmy on this territory
    //do not know whether origin value will be changed or not
  }

  /**
   * combat between two army.
   * 
   * @return winner
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
        defender.minusUnit(1);
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
