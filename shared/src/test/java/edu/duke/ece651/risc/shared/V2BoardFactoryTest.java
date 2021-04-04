package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class V2BoardFactoryTest {
  @Test
  public void test_gameboard2() {
    BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    ArrayList<Territory<String>> t = b.getTerritories();
    assertEquals(t.size(), 6);
    for(int i = 0 ; i < 6 ; i++){
      assertEquals(t.get(i).getCurrDefenderArmy().get(0).getTotalUnitAmount() , 0);
    }

    assertEquals(b.occupyTerritory(0, 0), false);
    assertEquals(b.occupyTerritory(1, 1), false);
    assertEquals(b.occupyTerritory(1, 1), true);
    b.addDefendUnitsTo(0, 0, 10);
    b.addDefendUnitsTo(0, 1, 11);
    assertEquals(10, b.getTerritories().get(0).getCurrDefenderArmy().get(0).getUnitAmtByLevel(0));
    assertEquals(11, b.getTerritories().get(0).getCurrDefenderArmy().get(0).getUnitAmtByLevel(1));

  }

  @Test
  public void test_gameboard3(){
    BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b3 = f.makeGameBoard(3);
    ArrayList<Territory<String>> t3 = b3.getTerritories();
    assertEquals(t3.size(), 9);
    Board<String> b4 = f.makeGameBoard(4);
    ArrayList<Territory<String>> t4 = b4.getTerritories();
    assertEquals(t4.size(), 12);
    Board<String> b5 = f.makeGameBoard(5);
    ArrayList<Territory<String>> t5 = b5.getTerritories();
    assertEquals(t5.size(), 15);
  }

}
