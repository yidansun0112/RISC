package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2UpgradeUnitOrderConsistencyCheckerTest {
  @Test
  public void test_checkMyRule() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }
    b.getTerritories().get(1).setOwner(1);
    b.getTerritories().get(2).setOwner(1);
    // Now terr 1 and 2 belong to p1, terr 0 3 4 5 belong to p0

    GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    // GUIPlayerEntity<String> p1 = new GUIPlayerEntity<String>(null, null, 1, "ck", 1,
    //     Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    V2UpgradeUnitOrderConsistencyChecker<String> checker = new V2UpgradeUnitOrderConsistencyChecker<String>(null);
  
    String failReason = "Sorry, You are not allowed to update other's unit.";

    // Player 0 wants to upgrade the units in territory 1 and 2, which belongs to another
    // player.
    V2UpgradeUnitOrder<String> o0 = new V2UpgradeUnitOrder<String>(1, 0, 1, 1);
    assertEquals(failReason, checker.checkMyRule(0, o0, new GameStatus<String>(p0, b, false)));

    V2UpgradeUnitOrder<String> o1 = new V2UpgradeUnitOrder<String>(2, 0, 2, 3);
    assertEquals(failReason, checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));

    V2UpgradeUnitOrder<String> o2 = new V2UpgradeUnitOrder<String>(0, 0, 2, 3);
    assertNull(checker.checkMyRule(0, o2, new GameStatus<String>(p0, b, false)));

  }

}
