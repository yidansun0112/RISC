package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2MoveOrderConsistencyCheckerTest {
  @Test
  public void test_V2MoveOrderConsistencyCheckerTest() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    V2MoveOrderConsistencyChecker<String> checker = new V2MoveOrderConsistencyChecker<String>(null);
    GUIPlayerEntity<String> p = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    V2MoveOrder<String> o1 = new V2MoveOrder<>(0, 1, new HashMap<Integer, Integer>());

    assertNull(checker.checkMyRule(0, o1, new GameStatus<String>(p, b, false)));

    b.getTerritories().get(0).setOwner(1);
    Order<String> o2 = new V2MoveOrder<>(0,1,new HashMap<Integer, Integer>());
    String s1 = "The source territory doesn't belong to this player.\n";
    assertEquals(s1, checker.checkMyRule(0, o2, new GameStatus<String>(p, b, false)));
    String s2 = "The destination territory doesn't belong to this player.\n";
    assertEquals(s2, checker.checkMyRule(1, o2, new GameStatus<String>(p, b, false)));
  }

}
