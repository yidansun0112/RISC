package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2MoveOrderFoodCheckerTest {
  @Test
  public void test_V2MoveOrderFoodCheckerTest() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    // V2MoveOrderConsistencyChecker<String> checker = new V2MoveOrderConsistencyChecker<String>(null);
    OrderRuleChecker<String> checker = new V2MoveOrderFoodChecker<>(null);
    GUIPlayerEntity<String> p = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    // 60 Food Resource. 
    p.harvestAllResource(b);
    HashMap<Integer, Integer> army = new HashMap<Integer, Integer>();

    army.put(0,5);
    Order<String> o1 = new V2MoveOrder<String>(0,1,army);
    assertNull(checker.checkMyRule(0, o1, new GameStatus<String>(p, b, false)));
    army.put(1,5);
    army.put(3,6);
    army.put(6,2);
    Order<String> o2 = new V2MoveOrder<String>(0,1,army);
    String s1 = "Sorry, you don't have enough food resource to move your army.";
    assertEquals(s1, checker.checkMyRule(0, o2, new GameStatus<String>(p, b, false)));

    



  }

}
