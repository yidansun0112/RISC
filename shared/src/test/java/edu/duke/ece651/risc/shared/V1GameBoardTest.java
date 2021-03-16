package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Vector;

import org.junit.jupiter.api.Test;

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
    Territory<String> terr = t.get(0);
    assertEquals(b.occupyTerritory(0, 0), false); // group 0 owner is 0
    assertEquals(b.occupyTerritory(1, 1), false); // group 1 owner is 1
    for (int i = 0; i < 3; i++) {
      assertEquals(t.get(i).getOwner(), 0);
      Army<String> a = terr.getCurrDefenderArmy().get(0);
      assertEquals(a.getUnits(), 0);
    }
    
    for (int i = 3; i < 6; i++) {
      assertEquals(t.get(i).getOwner(), 1);
      assertEquals(t.get(i).getUnitAmount(), 0);
    }
    terr.initCurrDefender(0);
    Vector<Army<String>> defenderArmy = terr.getCurrDefenderArmy();
    assertEquals(defenderArmy.size(), 1);
    assertEquals(defenderArmy.get(0).getUnits(), 0);

    assertEquals(defenderArmy.get(0).getCommanderId(), 0);
    assertEquals(terr.getUnitAmount(), 0);
    
  }

}



