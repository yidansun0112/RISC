package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class V1GameBoardTest {
  @Test
  public void test_gameboard() {
    BoardFactory<String> f = new V1BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    ArrayList<Territory<String>> t = b.getTerritories();
    assertEquals(t.size(), 6);
    for (int i = 0; i < 6; i++) {
      assertEquals(t.get(i).getOwner(), -1);
      assertEquals(t.get(i).getUnitAmount(), -1);

    }
    assertEquals(b.occupyTerritory(0, 1), false);

  }

}
