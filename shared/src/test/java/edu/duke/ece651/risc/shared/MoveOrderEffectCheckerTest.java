package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MoveOrderEffectCheckerTest {
  @Test
  public void test_MoveOrderEffectChecker() {

    BoardFactory<String> f = new V1BoardFactory();
    Board<String> b = f.makeGameBoard(2);
    assertEquals(b.occupyTerritory(0, 0), false); // group 0 owner is 0
    assertEquals(b.occupyTerritory(1, 1), false); // group 1 owner is 1

    MoveOrder<String> m1 = new MoveOrder<>("0 1 5");
    MoveOrder<String> m2 = new MoveOrder<>("0 2 6");

    MoveOrder<String> m3 = new MoveOrder<String>("3 3 0");
    MoveOrder<String> m4 = new MoveOrder<String>("3 4 2");

    for (int i = 0; i < 3; i++) {
      b.getTerritories().get(i).setBasicDefendUnitAmount(5);
      b.getTerritories().get(i + 3).setBasicDefendUnitAmount(5);
    }

    MoveOrderEffectChecker<String> checker = new MoveOrderEffectChecker<String>(null);
    assertEquals(null, checker.checkOrder(0, m1, b));
    String s1 = "Sorry, the source territory have 5 units while we need 6 units.\n";
    assertEquals(s1, checker.checkOrder(0, m2, b));

  }

}
