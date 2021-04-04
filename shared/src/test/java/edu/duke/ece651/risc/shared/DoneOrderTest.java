package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DoneOrderTest {
  @Test
  public void test_done_order() {
    Order<String> order = new DoneOrder<String>();
    BoardFactory<String> f = new V1BoardFactory<String>();
    Board<String> b = f.makeGameBoard(2);

    GameStatus<String> gs = new GameStatus<>(null, b, false);

    assertEquals(true, order.execute(b));
    assertEquals(true, order.execute(gs));

    assertEquals(order.getSrcTerritory(), -1);
    assertEquals(order.getDestTerritory(), -1);
    assertEquals(order.getUnitAmount(), -1);

  }

}
