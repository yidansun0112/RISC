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

    // V2MoveOrderConsistencyChecker<String> checker = new V2MoveOrderConsistencyChecker<String>(null);
    OrderRuleChecker<String> checker = new V2UpgradeMaxTechOrderOpenChecker<>(null);
    GUIPlayerEntity<String> p = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    // 60 Food Resource. 
    p.harvestAllResource(b);
    HashMap<Integer, Integer> army = new HashMap<Integer, Integer>();
    Order<String> o1 = new V2UpgradeTechLevelOrder<>(0);
    assertNull(checker.checkMyRule(0, o1, b));
    p.upgradeTechLevel();
    String s1 = "Sorry, you have updated your Max Tech Level in this round.";
    assertEquals(s1, checker.checkMyRule(0, o1, b));
  }

}
