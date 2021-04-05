package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2AttackOrderFoodCheckerTest {
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


    p0.harvestAllResource(b);
    // We need to make sure the food resource is correct before starting the real test
    assertEquals(Constant.TERRITORY_FOOD_PRODUCTIVITY * 4, p0.getFoodResourceAmount());
    assertEquals(Constant.TERRITORY_TECH_PRODUCTIVITY * 4, p0.getTechResourceAmount());

    V2AttackOrderFoodChecker<String> checker = new V2AttackOrderFoodChecker<String>(null);

    HashMap<Integer, Integer> armyMap0 = new HashMap<>(); // key is level, value is the number of units at this level
    armyMap0.put(0, 1);
    armyMap0.put(1, 3);
    armyMap0.put(2, 4);
    armyMap0.put(3, 6);

    V2AttackOrder<String> o0 = new V2AttackOrder<String>(0, 1, armyMap0);
    String failReason = "Sorry, you don't have enough food to attack.";

    assertEquals(14, o0.getUnitAmount());

    // Now testing

    assertEquals(null, checker.checkMyRule(0, o0, new GameStatus<String>(p0, b, false)));
    armyMap0.put(4, 25); // BVA - now need 39 food resource, and the player has 40 resource

    V2AttackOrder<String> o1 = new V2AttackOrder<String>(0, 1, armyMap0);
    assertEquals(null, checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));

    armyMap0.put(5, 1);
    V2AttackOrder<String> o2 = new V2AttackOrder<String>(0, 1, armyMap0);
    assertEquals(null, checker.checkMyRule(0, o2, new GameStatus<String>(p0, b, false)));

    armyMap0.put(6, 1);
    V2AttackOrder<String> o3 = new V2AttackOrder<String>(0, 1, armyMap0);
    assertEquals(failReason, checker.checkMyRule(0, o3, new GameStatus<String>(p0, b, false)));

  }

}
