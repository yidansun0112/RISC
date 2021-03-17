package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackOrderEffectCheckerTest {
  @Test
  public void test_effect() {
    BoardFactory<String> f = new V1BoardFactory();
    Board<String> b = f.makeGameBoard(2);
    OrderRuleChecker<String> r = new AttackOrderPathChecker<String>(new AttackOrderEffectChecker<String>(null));
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);
    b.addOwnUnits(0,3);
    Order<String> o1 = new AttackOrder<String>("0 3 2");
    assertEquals(r.checkOrder(0,o1,b),null);
    Order<String> o2 = new AttackOrder<String>("0 3 4");
    assertEquals(r.checkOrder(0,o2,b),"invalid order: Units on your territory is not enough\n");
  }

}











