package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class V2GameBoardTest {
  @Test
  public void test_V2_gameboard() {
    
  }

  @Test
  public void test_V2_findPathFunc(){
    BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    b.getTerritories().get(0).setOwner(1);
    b.getTerritories().get(1).setOwner(0);
    b.getTerritories().get(2).setOwner(1);
    b.getTerritories().get(3).setOwner(0);
    b.getTerritories().get(4).setOwner(0);
    b.getTerritories().get(5).setOwner(1);
    b.addDefendUnitsTo(0, 0, 2);
    assertEquals(b.getTerritories().get(0).getBasicDefendUnitAmount(), 2);
    b.removeDefendUnitsFrom(0, 0, 2);
    assertEquals(b.getTerritories().get(0).getBasicDefendUnitAmount(), 0);
    b.addEnemyUnitsTo(0, 0, 0, 2);
    assertEquals(b.getTerritories().get(0).getEnemyArmy().get(0).getBasicUnits(), 2);
    b.getTerritories().get(0).updatePrevDefender();
    assertEquals(b.getTerritories().get(0).getPrevDefenderArmy().get(0).getBasicUnits(),0);

    assertEquals(15, b.findMinPathDistance(5, 2, 1));
    assertEquals(10, b.findMinPathDistance(5, 0, 1)); 
    assertEquals(Integer.MAX_VALUE, b.findMinPathDistance(4, 1, 0));
    b.getTerritories().get(1).setOwner(1);
    assertEquals(10, b.findMinPathDistance(5, 0, 1)); 
    b.getTerritories().get(1).setSize(1);
    assertEquals(11, b.findMinPathDistance(5, 2, 1));
  
    // ToDo 3 players Map Testing
    // 4 players Map Testing
    Board<String> b4 = f.make4PlayerBoard();
    for(int i = 0 ; i < 12 ; i ++){
      b4.getTerritories().get(i).setOwner(1);  
    }
    b4.getTerritories().get(0).setOwner(0);
    b4.getTerritories().get(3).setOwner(0);
    b4.getTerritories().get(6).setOwner(0);
    b4.getTerritories().get(9).setOwner(0);
    
    assertEquals(10, b4.findMinPathDistance(3, 6, 0));

    b4.getTerritories().get(7).setSize(1);
    
    b4.getTerritories().get(1).setSize(1);

    b4.getTerritories().get(4).setSize(3);

    assertEquals(19, b4.findMinPathDistance(11, 5, 1));
    
    
  }

}
