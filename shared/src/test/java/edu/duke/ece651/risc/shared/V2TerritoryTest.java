package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;
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

    // Here we add the army in sequetial manner, no concurrency problem, so the
    // first element in enemyArmy vector is what we added just now.
    assertEquals(10, test.getEnemyArmy().get(0).getUnitAmtByLevel(0));

    test.addEnemy(2, 0, 3);
    assertEquals(2, test.getEnemyArmy().size());
    assertEquals(3, test.getEnemyArmy().get(1).getUnitAmtByLevel(0));

    test.addEnemy(2, 1, 1);
    assertEquals(2, test.getEnemyArmy().size());
    assertEquals(1, test.getEnemyArmy().get(1).getUnitAmtByLevel(1));
  }

  @Test
  public void test_updatePrevDefender() {
    int size = 5;
    int foodProductivity = 10;
    int techProductivity = 10;
    V2Territory<String> test = new V2Territory<>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 }, size, foodProductivity,
        techProductivity);
    test.initCurrDefender(0);

    test.updatePrevDefender();
    // Now this territory should only has one V2Army instance in prevDefenderArmy
    // and currDefenderArmy, with 0 level-0 units in it, and the army owner is
    // player 0
    assertEquals(1, test.prevDefenderArmy.size());
    assertEquals(0, test.prevDefenderArmy.get(0).getCommanderId());
    assertEquals(0, test.prevDefenderArmy.get(0).getBasicUnits());

    assertEquals(1, test.currDefenderArmy.size());
    assertEquals(0, test.currDefenderArmy.get(0).getCommanderId());
    assertEquals(0, test.currDefenderArmy.get(0).getBasicUnits());

    // Manually add some defender units into this territory
    test.addDefendUnits(0, 1);
    assertEquals(1, test.getBasicDefendUnitAmount());
    test.addDefendUnits(0, 3);
    assertEquals(4, test.getBasicDefendUnitAmount());
    test.addDefendUnits(1, 5);
    test.addDefendUnits(2, 2);
    // Ensure that we added them correctly
    assertEquals(5, test.getCurrDefenderArmy().get(0).getUnitAmtByLevel(1));
    assertEquals(2, test.getCurrDefenderArmy().get(0).getUnitAmtByLevel(2));

    // Now this territory should have 4 lv0 defender units, 5 lv1 defender units, 2 lv2 defender units

    test.updatePrevDefender();
    assertEquals(4, test.prevDefenderArmy.get(0).getUnitAmtByLevel(0));
    assertEquals(5, test.prevDefenderArmy.get(0).getUnitAmtByLevel(1));
    assertEquals(2, test.prevDefenderArmy.get(0).getUnitAmtByLevel(2));
  }

  /**
   * Test the combineEnemy method, even if the addEnemy already can do this job,
   * in order to make sure that there is no bug in combineEnemy method.
   */
  @Test
  public void test_combineEnemy() {
    int size = 5;
    int foodProductivity = 10;
    int techProductivity = 10;
    V2Territory<String> test = new V2Territory<>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 }, size, foodProductivity,
        techProductivity);
    test.initCurrDefender(0);

    // Manually add enemy V2Army instance without calling the addEnemy method, in
    // order to test combineEnemy
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        Army<String> am = new V2Army<>(j);
        am.addUnit(0, j + 1);
        am.addUnit(1, j + 3);
        test.enemyArmy.add(am);
      }
    }

    // Now the enemy armies in this territory should be
    // army 0: commanderId: 0, 1 lv0 units, 3 lv1 units
    // army 1: commanderId: 1, 2 lv0 units, 4 lv1 units
    // army 2: commanderId: 2, 3 lv0 units, 5 lv1 units
    // army 3: commanderId: 0, 1 lv0 units, 3 lv1 units
    // army 4: commanderId: 1, 2 lv0 units, 4 lv1 units
    // army 5: commanderId: 2, 3 lv0 units, 5 lv1 units
    assertEquals(6, test.getEnemyArmy().size());

    test.combineEnemyArmy();

    // Now start assert
    assertEquals(3, test.getEnemyArmy().size());
    assertNull(get_enemy_army_by_commanderId(3, test));
    assertEquals(2, get_enemy_army_by_commanderId(0, test).getUnitAmtByLevel(0));
    assertEquals(6, get_enemy_army_by_commanderId(0, test).getUnitAmtByLevel(1));

    assertEquals(4, get_enemy_army_by_commanderId(1, test).getUnitAmtByLevel(0));
    assertEquals(8, get_enemy_army_by_commanderId(1, test).getUnitAmtByLevel(1));

    assertEquals(6, get_enemy_army_by_commanderId(2, test).getUnitAmtByLevel(0));
    assertEquals(10, get_enemy_army_by_commanderId(2, test).getUnitAmtByLevel(1));
  }

  /**
   * Heper method that will return the first occurance of the army instance in
   * enemyArmy in territory t which commanderId matches the specified one.
   * 
   * @param <T>         the type of territory and army
   * @param commanderId the commanderId of the army
   * @param t           the territory to find the army
   * @return null if there is no such an army. otherwise return the army instance
   *         which matches the specified commanderId
   */
  private <T> Army<T> get_enemy_army_by_commanderId(int commanderId, Territory<T> t) {
    for (Army<T> am : t.getEnemyArmy()) {
      if (am.getCommanderId() == commanderId) {
        return am;
      }
    }
    return null;
  }
}
