package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackOrderEffectCheckerTest {
  @Test
  public void test_effect() {
    BoardFactory<String> f = new V1BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    OrderRuleChecker<String> r = new AttackOrderPathChecker<String>(new AttackOrderEffectChecker<String>(null));
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);
    b.updateAllPrevDefender();
    b.addBasicDefendUnitsTo(0, 3);

    // Build a gamestatus to test the evo 2 execution
    GameStatus<String> gs = new GameStatus<>(null, b, false);

    Order<String> o1 = new AttackOrder<String>("0 3 2");
    assertEquals(null, r.checkOrder(0, o1, b));

    assertEquals(null, r.checkOrder(0, o1, gs));
    
    Order<String> o2 = new AttackOrder<String>("0 3 4");
    assertEquals("invalid order: Units on your territory is not enough\n", r.checkOrder(0, o2, b));

    assertEquals("invalid order: Units on your territory is not enough\n", r.checkOrder(0, o2, gs));
  }

}
