package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2AttackOrderArmyCheckerTest {
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
    V2AttackOrderArmyChecker<String> checker = new V2AttackOrderArmyChecker<String>(null);

    // Test the unit is not enough
    HashMap<Integer, Integer> armyMap0 = new HashMap<>(); // key is level, value is the number of units at this level
    armyMap0.put(0, 1);
    armyMap0.put(1, 3);
    armyMap0.put(2, 4);
    armyMap0.put(3, 6);
    armyMap0.put(4, 7);
    armyMap0.put(5, 2);
    armyMap0.put(6, 5);
    // Currently there is no any units at any level on all territories.
    
    V2AttackOrder<String> o0 = new V2AttackOrder<String>(0, 1, armyMap0);
    assertEquals(make_fail_reason_helper(0), checker.checkMyRule(0, o0, new GameStatus<String>(p0, b, false)));
    
    b.getTerritories().get(0).addDefendUnits(0, 1); // now territory 0 has one lv0 unit

    V2AttackOrder<String> o1 = new V2AttackOrder<String>(0, 2, armyMap0);
    assertEquals(make_fail_reason_helper(1), checker.checkMyRule(0, o1, new GameStatus<String>(p0, b, false)));
  
    b.getTerritories().get(0).addDefendUnits(1, 2);

    V2AttackOrder<String> o2 = new V2AttackOrder<String>(0, 2, armyMap0);
    assertEquals(make_fail_reason_helper(1), checker.checkMyRule(0, o2, new GameStatus<String>(p0, b, false)));

    b.getTerritories().get(0).addDefendUnits(1, 1);
    
    V2AttackOrder<String> o3 = new V2AttackOrder<String>(0, 2, armyMap0);
    assertEquals(make_fail_reason_helper(2), checker.checkMyRule(0, o3, new GameStatus<String>(p0, b, false)));

    b.getTerritories().get(0).addDefendUnits(2, 3);
    b.getTerritories().get(0).addDefendUnits(3, 6);
    b.getTerritories().get(0).addDefendUnits(4, 7);
    b.getTerritories().get(0).addDefendUnits(5, 2);
    b.getTerritories().get(0).addDefendUnits(6, 5);

    V2AttackOrder<String> o4 = new V2AttackOrder<String>(0, 2, armyMap0);
    assertEquals(make_fail_reason_helper(2), checker.checkMyRule(0, o4, new GameStatus<String>(p0, b, false)));

    // Now its legal situation
    b.getTerritories().get(0).addDefendUnits(2, 1);
    V2AttackOrder<String> o5 = new V2AttackOrder<String>(0, 2, armyMap0);
    assertNull(checker.checkMyRule(0, o5, new GameStatus<String>(p0, b, false)));

    // Now its not enough again
    b.getTerritories().get(0).removeDefendUnits(6, 1);
    V2AttackOrder<String> o6 = new V2AttackOrder<String>(0, 2, armyMap0);
    assertEquals(make_fail_reason_helper(6), checker.checkMyRule(0, o6, new GameStatus<String>(p0, b, false)));

  }

  /**
   * A helper function that build the failing reason
   * 
   * @param level the level that the units are not enough
   * @return a string of failing reason
   */
  private String make_fail_reason_helper(int level) {
    return "Sorry, the level " + level + " army is not enough to attack";
  }

}
