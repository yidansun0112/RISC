package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Vector;
import java.util.*;
import org.junit.jupiter.api.Test;

public class V1GameBoardTest {
  @Test
  public void test_gameboard2() {
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
    assertEquals(b.occupyTerritory(1, 1), true); 
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
    b.addOwnUnits(0,3);
    assertEquals(terr.getUnitAmount(),3);
    b.addEnemyUnits(0,2,1);
    Army<String> temp = terr.getEnemyArmy().get(0);
    assertEquals(temp.getUnits(),2);
    assertEquals(temp.getCommanderId(),1);
    b.addOwnUnits(0,2);
    assertEquals(terr.getUnitAmount(),5);
    b.updateAllPrevDefender(); //update
    b.removeUnits(0,1);
    assertEquals(terr.getUnitAmount(),4);
    int[][] worldMap = b.getWorldMap();
    Vector<Integer> neigh =terr.getNeigh();
    Vector<Integer> expected = new Vector<Integer>();
    for(int i=1;i<6;i++){
      expected.add(i);
    }
    assertEquals(expected,neigh);
    HashMap<String, Integer> displayInfoSelf = terr.getDisplayInfo(true);
    assertEquals(displayInfoSelf.get("units"),4);
    HashMap<String, Integer> displayInfoEnemy = terr.getDisplayInfo(false);
    assertEquals(displayInfoEnemy.get("units"),5);
    assertEquals(terr.getId(), 0);
    assertEquals(terr.getName(), "Narnia");
  }

  @Test
  public void test_gameboard3() {
    BoardFactory<String> f = new V1BoardFactory<>();
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












