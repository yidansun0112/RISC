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

    assertEquals(15, b.findMinPathDistance(5, 2, 1)); 
    assertEquals(Integer.MAX_VALUE, b.findMinPathDistance(4, 1, 0));
  }

}
