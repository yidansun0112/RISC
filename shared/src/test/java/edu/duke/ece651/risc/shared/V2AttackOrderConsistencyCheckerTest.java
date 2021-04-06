package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2AttackOrderConsistencyCheckerTest {
  @Test
  public void test_checkMyRule() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    String failReasonAttackSelf = "Sorry, you are not allowed to attack your self.";
    String failReasonAttackFromOther = "Sorry, you must attack from your own territory.";
    String failReasonAttackNoneAdjacent = "Sorry, the two territory is not adjacent.";

    GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    GUIPlayerEntity<String> p1 = new GUIPlayerEntity<String>(null, null, 1, "ck", 1,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    V2AttackOrderConsistencyChecker<String> checker = new V2AttackOrderConsistencyChecker<String>(null);

    // Test attacking self

    V2AttackOrder<String> o1 = new V2AttackOrder<String>(0, 0, new HashMap<Integer, Integer>());
    assertEquals(failReasonAttackSelf, checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));
    V2AttackOrder<String> o2 = new V2AttackOrder<String>(1, 2, new HashMap<Integer, Integer>());
    assertEquals(failReasonAttackSelf, checker.checkMyRule(0, o2, new GameStatus<String>(p0, b, false)));

    b.getTerritories().get(1).setOwner(1);
    b.getTerritories().get(2).setOwner(1);
    // Now terr 1 and 2 belong to p1, terr 0 3 4 5 belong to p0

    V2AttackOrder<String> o3 = new V2AttackOrder<String>(1, 2, new HashMap<Integer, Integer>());
    assertEquals(failReasonAttackSelf, checker.checkMyRule(1, o3, new GameStatus<String>(p1, b, false)));

    // Test attacking from other's territory

    V2AttackOrder<String> o4 = new V2AttackOrder<String>(1, 2, new HashMap<Integer, Integer>());
    // player 0 attack from territory 1 to territory 2, which belongs to player 2
    assertEquals(failReasonAttackFromOther, checker.checkMyRule(0, o4, new GameStatus<String>(p0, b, false)));

    V2AttackOrder<String> o5 = new V2AttackOrder<String>(0, 3, new HashMap<Integer, Integer>());
    // player 1 attack from territory 0 to territory 3, which belongs to player 0
    assertEquals(failReasonAttackFromOther, checker.checkMyRule(1, o5, new GameStatus<String>(p1, b, false)));

    // Test attack a non-adjacent territory
    V2AttackOrder<String> o6 = new V2AttackOrder<String>(4, 2, new HashMap<Integer, Integer>());
    // player 0 attack from territory 4 to territory 2, which is not adjacent
    assertEquals(failReasonAttackNoneAdjacent, checker.checkMyRule(0, o6, new GameStatus<String>(p0, b, false)));

    V2AttackOrder<String> o7 = new V2AttackOrder<String>(4, 1, new HashMap<Integer, Integer>());
    // player 0 attack from territory 4 to territory 1, which is not adjacent
    assertEquals(failReasonAttackNoneAdjacent, checker.checkMyRule(0, o7, new GameStatus<String>(p0, b, false)));

    V2AttackOrder<String> o8 = new V2AttackOrder<String>(2, 5, new HashMap<Integer, Integer>());
    // player 1 attack from territory 2 to territory 5, which is not adjacent
    assertEquals(failReasonAttackNoneAdjacent, checker.checkMyRule(1, o8, new GameStatus<String>(p1, b, false)));

    // Test legal situation
    V2AttackOrder<String> o9 = new V2AttackOrder<String>(0, 1, new HashMap<Integer, Integer>());
    // player 0 attack from territory 0 to territory 1, which is legal
    assertNull(checker.checkMyRule(0, o9, new GameStatus<String>(p0, b, false)));

    V2AttackOrder<String> o10 = new V2AttackOrder<String>(0, 2, new HashMap<Integer, Integer>());
    assertNull(checker.checkMyRule(0, o10, new GameStatus<String>(p0, b, false)));
    V2AttackOrder<String> o11 = new V2AttackOrder<String>(2, 0, new HashMap<Integer, Integer>());
    assertNull(checker.checkMyRule(1, o11, new GameStatus<String>(p1, b, false)));

  }

}
