package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1TerritoryTest {
  @Test
  public void test_v1territory() {
    Territory<String> t = new V1Territory<String>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 });
    t.setOwner(0);
    t.addEnemy(1, 2);
    t.addEnemy(1, 1);
    t.addEnemy(2, 3);
    t.combineEnemyArmy();
    assertEquals(2, t.getEnemyArmy().size());
    assertEquals(3, t.getEnemyArmy().get(0).getBasicUnits());
    assertEquals(3, t.getEnemyArmy().get(1).getBasicUnits());
  }
}
