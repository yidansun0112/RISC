package edu.duke.ece651.risc.shared;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2AttackOrderTest {
  @Test
  public void test_v2attack_order() {
    BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    b.getTerritories().get(0).setOwner(0);
    b.getTerritories().get(1).setOwner(0);
    b.getTerritories().get(2).setOwner(0);
    b.getTerritories().get(3).setOwner(1);
    b.getTerritories().get(4).setOwner(1);
    b.getTerritories().get(5).setOwner(1);
    b.addDefendUnitsTo(0, 0, 2);
    HashMap<Integer, Integer> levelToUnitAmount = new HashMap<>();
    levelToUnitAmount.put(0,2);
    GUIPlayerEntity<String> gp = new GUIPlayerEntity<String>(null, null, 0, "Red", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    V2AttackOrder<String> order = new V2AttackOrder<>(0,3,levelToUnitAmount);
    GameStatus<String> gs = new GameStatus<>(gp, b, false);
    //order.execute(gs);
  }

}
