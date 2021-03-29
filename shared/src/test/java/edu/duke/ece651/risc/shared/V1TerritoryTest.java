package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1TerritoryTest {
  @Test
  public void test_V1Territory() {
    Territory<String> t = new V1Territory<String>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 });
    t.setOwner(0);

    t.initCurrDefender(0);

    t.addBasicEnemy(1, 2);
    t.addBasicEnemy(1, 1);
    t.addBasicEnemy(2, 3);
    t.combineEnemyArmy();
    assertEquals(2, t.getEnemyArmy().size());
    assertEquals(3, t.getEnemyArmy().get(0).getBasicUnits());
    assertEquals(3, t.getEnemyArmy().get(1).getBasicUnits());

    // Test methods for evo 2, even they are not used in V1Territory
    t.addDefendUnits(2, 5); // level should be ignore, has no actual effect
    assertEquals(5, t.getBasicDefendUnitAmount());
    t.removeDefendUnits(2, 1);
    assertEquals(4, t.getBasicDefendUnitAmount());
  }

  /**
   * Test the get size and get resources in V1Territory, although they are not
   * used in evo 1. We need to be 100% sure there is no surprising. Be careful
   * would always better than not.
   */
  @Test
  public void test_dummy_implementation_getters() {
    Territory<String> t = new V1Territory<String>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 });
    t.setOwner(0);
    t.initCurrDefender(0);

    assertEquals(0, t.getSize());
    assertEquals(0, t.getFoodProduction());
    assertEquals(0, t.getTechProduction());
  }
}












