package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class V2UpgradeUnitOrderLevelCheckerTest {
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

    String failReason = "Sorry, the new level must be greater than the current level.";
    V2UpgradeUnitOrderLevelChecker<String> checker = new V2UpgradeUnitOrderLevelChecker<String>(null);

    V2UpgradeUnitOrder<String> o0 = new V2UpgradeUnitOrder<String>(0, 0, 1, 1); // valid
    V2UpgradeUnitOrder<String> o1 = new V2UpgradeUnitOrder<String>(0, 1, 1, 1); // illegal, levelTo is same with levelFrom
    V2UpgradeUnitOrder<String> o2 = new V2UpgradeUnitOrder<String>(0, 1, 0, 1); // illegal, levelTo is less than levelFrom
    V2UpgradeUnitOrder<String> o3 = new V2UpgradeUnitOrder<String>(0, 3, 0, 1); // illegal, levelTo is less than levelFrom

    assertEquals(null, checker.checkMyRule(0, o0, new GameStatus<String>(p0, b, false)));
    assertEquals(failReason, checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));
    assertEquals(failReason, checker.checkMyRule(0, o2, new GameStatus<String>(p0, b, false)));
    assertEquals(failReason, checker.checkMyRule(0, o3, new GameStatus<String>(p0, b, false)));
  }
}
