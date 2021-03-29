package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public class V2TerritoryTest {
  @Test
  public void test_getters() {
    int size = 5;
    int foodProductivity = 10;
    int techProductivity = 10;
    V2Territory<String> test = new V2Territory<>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 }, size, foodProductivity, techProductivity);
    test.initCurrDefender(0);

    assertEquals(foodProductivity, test.getFoodProduction());
    assertEquals(techProductivity, test.getTechProduction());
    assertEquals(size, test.getSize());
  }

  @Test
  public void test_addDefendUnits_removeDefendUnits() {
    int size = 5;
    int foodProductivity = 10;
    int techProductivity = 10;
    V2Territory<String> test = new V2Territory<>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 }, size, foodProductivity,
        techProductivity);
    test.initCurrDefender(0);

    test.addDefendUnits(0, 1);
    assertEquals(1, test.getBasicDefendUnitAmount()); // getBasicDefendUnitAmount is not quite useful in evo2, most of
                                                      // the case we directly get the currDefendArmy and call the V2Army
                                                      // API...--->
    test.addDefendUnits(0, 5);
    assertEquals(6, test.getBasicDefendUnitAmount());
    test.addDefendUnits(1, 3);
    assertEquals(3, test.getCurrDefenderArmy().get(0).getUnitAmtByLevel(1)); // <---- ...like this

    test.addDefendUnits(3, 10);
    test.removeDefendUnits(1, 3);
    assertEquals(0, test.getCurrDefenderArmy().get(0).getUnitAmtByLevel(1));
    assertEquals(10, test.getCurrDefenderArmy().get(0).getUnitAmtByLevel(3));
    test.removeDefendUnits(3, 3);
    assertEquals(7, test.getCurrDefenderArmy().get(0).getUnitAmtByLevel(3));
  }

  @Test
  public void test_addEnemy() {
    int size = 5;
    int foodProductivity = 10;
    int techProductivity = 10;
    V2Territory<String> test = new V2Territory<>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 }, size, foodProductivity,
        techProductivity);
    test.initCurrDefender(0);

    assertEquals(0, test.getEnemyArmy().size()); // before, there should be no army in enemyArmy list
    test.addEnemy(1, 0, 5); // the territory belongs to player 0, and now player 1 put some units to attack
                            // player 0
    assertEquals(1, test.getEnemyArmy().size());
    assertEquals(5, test.getEnemyArmy().get(0).getUnitAmtByLevel(0));
    assertEquals(5, test.getEnemyArmy().get(0).getBasicUnits());

    test.addEnemy(1, 0, 5); // player 1 make 5 more units to attack this territory
    assertEquals(1, test.getEnemyArmy().size()); // since the addEnemy in evo2 will automatically find the attacker's
                                                 // army, does not neet to call the combineEnemyArmy(), so there should
                                                 // be still one V2Army instance after add 5 more units.
  
  
  }

  @Test
  public void test_V2Territory() {
    int size = 5;
    int foodProductivity = 10;
    int techProductivity = 10;
    V2Territory<String> test = new V2Territory<>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 }, size, foodProductivity, techProductivity);
    test.initCurrDefender(0);

    test.updatePrevDefender();
    // Now this territory should only has one V2Army instance in prevDefenderArmy and currDefenderArmy, with 0 level-0 units in it, and the army owner is player 0
    assertEquals(1, test.prevDefenderArmy.size());
    assertEquals(0, test.prevDefenderArmy.get(0).getCommanderId());
    assertEquals(0, test.prevDefenderArmy.get(0).getBasicUnits());

    assertEquals(1, test.currDefenderArmy.size());
    assertEquals(0, test.currDefenderArmy.get(0).getCommanderId());
    assertEquals(0, test.currDefenderArmy.get(0).getBasicUnits());
    
    // HashMap<Integer, Integer> army = new HashMap<>();
    
    // for(int i = 0 ; i <= 6 ; i++){
    //   army.put(i , i);
    // }

    // test.addUnitAmount(army);

    // assertEquals(3, test.getUnitAmountV2().get(3));

    // test.removeUnitAmount(army);
    
    // assertEquals(0, test.getUnitAmountV2().get(3));


    // test.addEnemy(0, army);
    // test.addEnemy(1, army);
    // test.addEnemy(0, army);
    // assertEquals(4, test.getEnemyArmy().get(0).getUnitAmtByLevel(2));
    // assertEquals(2, test.getEnemyArmy().get(1).getUnitAmtByLevel(2));
  }
}
