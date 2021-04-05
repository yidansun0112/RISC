package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2UpgradeMaxTechOrderResourceCheckerTest {
  @Test
  public void test_V2UpgradeMaxTechOrderResourceChecker() {
    V2BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.make2PlayerBoard();
    for (int i = 0; i < 6; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    b.getTerritories().get(0).setOwner(1);
    // V2MoveOrderConsistencyChecker<String> checker = new V2MoveOrderConsistencyChecker<String>(null);
    OrderRuleChecker<String> checker = new V2UpgradeMaxTechOrderResourceChecker<>(null);
    GUIPlayerEntity<String> p = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    GUIPlayerEntity<String> p1 = new GUIPlayerEntity<String>(null, null, 1, "kc", 1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
  
    // 60 Food Resource. 50 for player 0 and 10 for 10
    p.harvestAllResource(b);
    p1.harvestAllResource(b);
    HashMap<Integer, Integer> army = new HashMap<Integer, Integer>();
    Order<String> o1 = new V2UpgradeTechLevelOrder<>(0);
    Order<String> o2 = new V2UpgradeTechLevelOrder<>(1);

    assertEquals(null, checker.checkMyRule(0, o1, new GameStatus<String>(p, b, false)));
    String s1 = "Sorry, You don't have enough technology resource to update your Max Tech Level.";
    //assertEquals(s1, checker.checkMyRule(1, o2, new GameStatus<String>(p, b, false)));
  }

}

