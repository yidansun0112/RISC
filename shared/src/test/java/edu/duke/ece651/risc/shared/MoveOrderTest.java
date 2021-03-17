package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MoveOrderTest {
  @Test
  public void test_MoveOrder() {

    String s1 = "0 1 2";
    MoveOrder<String> m1 = new MoveOrder<>(s1);
    assertEquals(0, m1.getSrcTerritory());
    assertEquals(1, m1.getDestTerritory());
    assertEquals(2, m1.getUnitAmount());

    String s2 = "";
    String s3 = "kasdjla";
    String s4 = "12 *& ajdo";
    assertThrows(IllegalArgumentException.class, () -> new MoveOrder<String>(s2));
    assertThrows(IllegalArgumentException.class, () -> new MoveOrder<String>(s3));
    assertThrows(IllegalArgumentException.class, () -> new MoveOrder<String>(s4));

    // Create the Board
    BoardFactory<String> f = new V1BoardFactory();
    Board<String> b = f.makeGameBoard(2);
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);

    MoveOrder<String> m2 = new MoveOrder<>("7 7 7");
    assertEquals(false, m2.execute(b));
    assertEquals(0, b.getTerritories().get(m1.getSrcTerritory()).getUnitAmount());
    assertEquals(true, m1.execute(b));

    assertThrows(IllegalArgumentException.class, () -> new MoveOrder<>("0 1 -1"));
  }

}
