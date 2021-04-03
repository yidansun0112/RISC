package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackOrderConsistencyCheckerTest {
  @Test
  public void test_consistency() {
    BoardFactory<String> f = new V1BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    OrderRuleChecker<String> r = new AttackOrderConsistencyChecker<String>(null);
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);
    Order<String> o1 = new AttackOrder<String>("0 3 2");
    int dest = o1.getDestTerritory();

    // Test checkOrder method which takes in a game status, even if this method is not used in evo 2
    GameStatus<String> gs = new GameStatus<>(null, b, false); // player entity is not used in attack order in evo 1

    assertEquals(dest, 3);
    assertEquals(b.getTerritories().get(3).getOwner(), 1);
    assertEquals(r.checkOrder(0, o1, b), null);
    assertEquals(r.checkOrder(0, o1, gs), null); // test evo 2 checkOrder
    Order<String> o2 = new AttackOrder<String>("4 3 2");
    assertEquals(r.checkOrder(0, o2, b), "Order is invalid: source territory you pick doesn't belong to you\n");
    assertEquals(r.checkOrder(0, o2, gs), "Order is invalid: source territory you pick doesn't belong to you\n");
    Order<String> o3 = new AttackOrder<String>("0 2 2");
    assertEquals(r.checkOrder(0, o3, b), "Order is invalid: destination territory you pick shouldn't belong to you\n");
    assertEquals(r.checkOrder(0, o3, gs), "Order is invalid: destination territory you pick shouldn't belong to you\n");
  }

}
