package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackOrderTest {
  @Test
  public void test_AttackOrder() {

    String s1 = "0 1 2";
    AttackOrder<String> a1 = new AttackOrder<String>(s1);
    assertEquals(0, a1.getSrcTerritory());
    assertEquals(1, a1.getDestTerritory());
    assertEquals(2, a1.getUnitAmount());

    String s2 = "";
    String s3 = "kasdjla";
    String s4 = "12 *& ajdo";
    assertThrows(IllegalArgumentException.class, () -> new AttackOrder<String>(s2));
    assertThrows(IllegalArgumentException.class, () -> new AttackOrder<String>(s3));
    assertThrows(IllegalArgumentException.class, () -> new AttackOrder<String>(s4));

    // Create the Board
    BoardFactory<String> f = new V1BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);

    AttackOrder<String> a2 = new AttackOrder<>("7 7 7");
    assertEquals(false, a2.execute(b));
    assertEquals(0, b.getTerritories().get(a1.getSrcTerritory()).getUnitAmount());
    assertEquals(true, a1.execute(b));
  }

}
