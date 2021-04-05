package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2UpgradeMaxTechOrderLevelCheckerTest {
  @Test
  public void test_V2UpgradeMaxTechOrderLevelCheckerTest() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    OrderRuleChecker<String> checker = new V2UpgradeMaxTechOrderLevelChecker<>(null);
    GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    // 60 Food Resource.
    p0.harvestAllResource(b);

    Order<String> o1 = new V2UpgradeTechLevelOrder<>(0);
    assertNull(checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));
    p0.techLevel = 2;
    assertNull(checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));

    p0.techLevel = 6;
    String s1 = "Sorry, You have already on the Max Tech level, cannot upgrade anymore.";
    assertEquals(s1, checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));

  }

}
