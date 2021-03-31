package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackOrderPathCheckerTest {
  @Test
  public void test_path() {
    BoardFactory<String> f = new V1BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    OrderRuleChecker<String> r = new AttackOrderPathChecker<String>(null);
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);

    // Build a gamestatus to test the evo 2 execution
    GameStatus<String> gs = new GameStatus<>(null, b);

    Order<String> o1 = new AttackOrder<String>("0 3 2");
    assertEquals(null, r.checkOrder(0, o1, b));
    assertEquals(null, r.checkOrder(0, o1, gs)); // test evo2 execute

    Order<String> o2 = new AttackOrder<String>("1 4 2");
    assertEquals("invalid order: You can only attack an enemy territory that is next to you!\n",
        r.checkOrder(0, o2, b));
    assertEquals("invalid order: You can only attack an enemy territory that is next to you!\n",
        r.checkOrder(0, o2, gs)); // test evo2 execute
  }
}