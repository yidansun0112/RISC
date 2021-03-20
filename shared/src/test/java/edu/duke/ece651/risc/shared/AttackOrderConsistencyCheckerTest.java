package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackOrderConsistencyCheckerTest {
  @Test
  public void test_consistency() {
    BoardFactory<String> f = new V1BoardFactory();
    Board<String> b = f.makeGameBoard(2);
    OrderRuleChecker<String> r = new AttackOrderConsistencyChecker<String>(null);
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);
    Order<String> o1 = new AttackOrder<String>("0 3 2");
    int dest = o1.getDestTerritory();
    assertEquals(dest, 3);
    assertEquals(b.getTerritories().get(3).getOwner(), 1);
    assertEquals(r.checkOrder(0, o1, b), null);
    Order<String> o2 = new AttackOrder<String>("4 3 2");
    assertEquals(r.checkOrder(0, o2, b), "Order is invalid: source territory you pick doesn't belong to you\n");
    Order<String> o3 = new AttackOrder<String>("0 2 2");
    assertEquals(r.checkOrder(0, o3, b), "Order is invalid: destination territory you pick shouldn't belong to you\n");
  }

}
