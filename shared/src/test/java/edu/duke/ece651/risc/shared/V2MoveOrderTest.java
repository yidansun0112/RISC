package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class V2MoveOrderTest {
  @Test
  public void test_V2_move_order() {
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
    Order<String> o1 = new V2MoveOrder<>(0, 1, levelToUnitAmount);
    GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    assertEquals(true, o1.execute(new GameStatus<String>(p0, b, false)));
  }

}











