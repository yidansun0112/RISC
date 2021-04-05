package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

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
    GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    GUIPlayerEntity<String> p1 = new GUIPlayerEntity<String>(null, null, 1, "kc", 1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    
    OrderRuleChecker<String> checker = new V2UpgradeMaxTechOrderResourceChecker<>(null);
    String failReason = "Sorry, You don't have enough technology resource to update your Max Tech Level.";

    // 50 tech resource for player 0 and 10 tech resource for 1
    p0.harvestAllResource(b);
    p1.harvestAllResource(b);
    assertEquals(Constant.INIT_MAX_TECH_LEVEL, p0.getTechLevel());
    assertEquals(Constant.INIT_MAX_TECH_LEVEL, p1.getTechLevel());
    assertEquals(Constant.TERRITORY_FOOD_PRODUCTIVITY * 5, p0.getFoodResourceAmount());
    assertEquals(Constant.TERRITORY_TECH_PRODUCTIVITY * 5, p0.getTechResourceAmount());
    assertEquals(Constant.TERRITORY_FOOD_PRODUCTIVITY * 1, p1.getFoodResourceAmount());
    assertEquals(Constant.TERRITORY_TECH_PRODUCTIVITY * 1, p1.getTechResourceAmount());

    Order<String> o1 = new V2UpgradeTechLevelOrder<>(0);
    Order<String> o2 = new V2UpgradeTechLevelOrder<>(1);

    assertNull(checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));
    assertEquals(failReason, checker.checkMyRule(1, o2, new GameStatus<String>(p1, b, false)));

    p0.techLevel = 2; // 2 -> 3 cost 75, but player 0 has 50, not enough
    assertEquals(failReason, checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));
  }

}

