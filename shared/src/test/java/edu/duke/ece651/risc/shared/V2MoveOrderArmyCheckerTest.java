package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2MoveOrderArmyCheckerTest {
  @Test
  public void test_V2MoveOrderArmyCheckerTest() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    // V2MoveOrderConsistencyChecker<String> checker = new V2MoveOrderConsistencyChecker<String>(null);
    OrderRuleChecker<String> checker = new V2MoveOrderArmyChecker<>(null);
    GUIPlayerEntity<String> p = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    
    HashMap<Integer, Integer> army = new HashMap<Integer, Integer>();
    
    V2MoveOrder<String> o1 = new V2MoveOrder<>(0, 1, army);

    // Level 0, 1, 3 have 5 units respectively.
    b.getTerritories().get(0).addDefendUnits(0, 5);
    
    b.getTerritories().get(0).addDefendUnits(1, 5);

    b.getTerritories().get(0).addDefendUnits(3, 5);

    assertNull(checker.checkMyRule(0, o1, new GameStatus<String>(p, b, false)));

    army.put(0, 5);
    army.put(1, 5);
    Order<String> o2 = new V2MoveOrder<>(0, 1, army);
    assertEquals(null, checker.checkMyRule(0, o2, new GameStatus<String>(p, b, false)));

    army.put(3, 10);
    Order<String> o3 = new V2MoveOrder<>(0, 1, army);
    String s1 = "Sorry, the level 3 army is not enough to move";
    assertEquals(s1, checker.checkMyRule(0, o3, new GameStatus<String>(p, b, false)));
    army.put(2, 10);
    Order<String> o4 = new V2MoveOrder<>(0, 1, army);
    String s2 = "Sorry, the level 2 army is not enough to move";
    assertEquals(s2, checker.checkMyRule(0, o4, new GameStatus<String>(p, b, false)));
  }

}
