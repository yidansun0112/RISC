package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.shared.*;

import java.util.Random;

public class V2BattleResolver<T> extends BattleResolver<T>{
  public V2BattleResolver(Random rdm){
    super(rdm);
  }

  @Override
  public Army<T> combatBetween(Army<T> defender, Army<T> attacker){
    int round=0;
    while(defender.getTotalUnitAmount()>0&&attacker.getTotalUnitAmount()>0){
      int defendLevel, attackLevel;
      if(round%2==0){
        defendLevel=defender.getMinUnitLevel();
        attackLevel=attacker.getMaxUnitLevel();
      }
      else{
        defendLevel=defender.getMaxUnitLevel();
        attackLevel=attacker.getMinUnitLevel();
      }
      int forDefender=rdm.nextInt(Constant.DICE_SIDE)+getBonus(defendLevel);
      int forAttacker=rdm.nextInt(Constant.DICE_SIDE)+getBonus(attackLevel);
      if(forDefender>=forAttacker){
        attacker.removeUnit(attackLevel, 1);
      }else{
        defender.removeUnit(defendLevel,1);
      }
      round++;
    }
    if (defender.getTotalUnitAmount() > 0) {
      return defender;
    } else {
      return attacker;
    }
  }

  protected int getBonus(int level){
    switch(level){
      case 0:
        return 0;
      case 1:
        return 1;
      case 2:
        return 3;
      case 3:
        return 5;
      case 4:
        return 8;
      case 5:
        return 11;
      case 6:
        return 15;
      default:
        return 0;
    }
  }

  
}
