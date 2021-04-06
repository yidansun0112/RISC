package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2UpgradeMaxTechOrderOpenCheckerTest {
  @Test
  public void test_V2UpgradeMaxTechOrderOpenCheckerTest() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    OrderRuleChecker<String> checker = new V2UpgradeMaxTechOrderOpenChecker<>(null);
    String failReason = "Sorry, you have already issued Upgrade Max Tech Level order in this round.";
    GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    Order<String> o1 = new V2UpgradeTechLevelOrder<>(0);
    p0.needUpTechLv = false;
    assertEquals(null, checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));
    
    p0.setNeedUpTechLv();
    assertEquals(failReason, checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));

  }

}
