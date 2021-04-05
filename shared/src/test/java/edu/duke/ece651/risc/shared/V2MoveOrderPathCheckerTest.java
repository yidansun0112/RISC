package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;


public class V2MoveOrderPathCheckerTest {
  @Test
  public void test_V2MoveOrderPathChecker() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    // V2MoveOrderConsistencyChecker<String> checker = new V2MoveOrderConsistencyChecker<String>(null);
    OrderRuleChecker<String> checker = new V2MoveOrderPathChecker<>(null);
    GUIPlayerEntity<String> p = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    // 60 Food Resource. 
    p.harvestAllResource(b);
    HashMap<Integer, Integer> army = new HashMap<Integer, Integer>();

    b.getTerritories().get(0).setOwner(1);
    b.getTerritories().get(2).setOwner(1);
    b.getTerritories().get(5).setOwner(1);

    Order<String> o1 = new V2MoveOrder<>(0, 2, army);
    assertNull(checker.checkMyRule(1, o1, new GameStatus<String>(p, b, false)));
    Order<String> o2 = new V2MoveOrder<>(0, 3, army);
    String s1 = "There is no path from Narnia to Elantris";
    assertEquals(s1, checker.checkMyRule(1, o2, new GameStatus<String>(p, b, false)));
    Order<String> o3 = new V2MoveOrder<>(3, 1, army);
    String s2 = "There is no path from Elantris to Midkemia";
    assertEquals(s2, checker.checkMyRule(0, o3, new GameStatus<String>(p, b, false)));
  
  }

}
