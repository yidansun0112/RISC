package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2UpgradeUnitOrderResourceCheckerTest {
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

    b.getTerritories().get(0).addDefendUnits(0, 10);
    b.getTerritories().get(0).addDefendUnits(1, 10);
    b.getTerritories().get(0).addDefendUnits(2, 10);
    b.getTerritories().get(0).addDefendUnits(3, 10);
    b.getTerritories().get(0).addDefendUnits(4, 10);
    b.getTerritories().get(0).addDefendUnits(5, 10);
    b.getTerritories().get(0).addDefendUnits(6, 10);

    GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    String failReason = "Sorry, you don't have enough Tech resouce to update your units.";

    V2UpgradeUnitOrderResourceChecker<String> checker = new V2UpgradeUnitOrderResourceChecker<String>(null);

    p0.harvestAllResource(b); // now player 0 should have 40 tech and 40 food resource
    V2UpgradeUnitOrder<String> o0 = new V2UpgradeUnitOrder<String>(0, 0, 1, 1); // cost 3
    assertNull(checker.checkMyRule(0, o0, new GameStatus<String>(p0, b, false)));
    V2UpgradeUnitOrder<String> o1 = new V2UpgradeUnitOrder<String>(0, 0, 1, 10); // cost 30
    assertNull(checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));
    V2UpgradeUnitOrder<String> o2 = new V2UpgradeUnitOrder<String>(0, 0, 3, 1); // cost 30
    assertNull(checker.checkMyRule(0, o2, new GameStatus<String>(p0, b, false)));
    V2UpgradeUnitOrder<String> o3 = new V2UpgradeUnitOrder<String>(0, 4, 5, 1); // cost 35
    assertNull(checker.checkMyRule(0, o3, new GameStatus<String>(p0, b, false)));

    p0.harvestAllResource(b); // now player 0 should have 80 tech and 80 food resource
    V2UpgradeUnitOrder<String> o4 = new V2UpgradeUnitOrder<String>(0, 4, 5, 3); // cost 105
    assertEquals(failReason, checker.checkMyRule(0, o4, new GameStatus<String>(p0, b, false)));

  }

}
