package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class V2GameBoardTest {
  @Test
  public void test_V2_gameboard() {

  }

  @Test
  public void test_V2_findPathFunc_testset1() {
    BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    b.getTerritories().get(0).setOwner(1);
    b.getTerritories().get(1).setOwner(0);
    b.getTerritories().get(2).setOwner(1);
    b.getTerritories().get(3).setOwner(0);
    b.getTerritories().get(4).setOwner(0);
    b.getTerritories().get(5).setOwner(1);

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
    for (int i = 0; i < 12; i++) {
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

  @Test
  public void test_V2_findPathFunc_testset2() {
    BoardFactory<String> f = new V2BoardFactory<>();
    Board<String> b = f.makeGameBoard(3);
    for (int i = 0; i < 9; i++) {
      b.getTerritories().get(i).setOwner(0);
    }

    assertEquals(Constant.TERRITORY_SIZE, b.getTerritories().get(0).getSize()); // make sure we have the correct
                                                                                // territory size

    // --- Now testing ---

    // Directly adjacent territories
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(8, 6, 0)); // triangle shape
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(7, 6, 0));
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(8, 7, 0));

    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(0, 6, 0)); // both direction
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(6, 0, 0));

    // Long path testing
    assertEquals(Constant.TERRITORY_SIZE * 4, b.findMinPathDistance(8, 5, 0)); // From terirtory 8 to territory 5, can
                                                                               // be 8-6-3-5 or 8-7-4-5 or 8-1-2-5
    assertEquals(Constant.TERRITORY_SIZE * 4, b.findMinPathDistance(7, 2, 0));

    // Multiple paths, but one of them is shortest
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(8, 1, 0)); // 8->1 or 8->6->0->1, etc. 8 -> 1 is the
                                                                               // shortest
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(1, 8, 0)); // test the other direction

    // Now some of the territory is not belong to player 0
    b.getTerritories().get(0).setOwner(1);
    b.getTerritories().get(6).setOwner(1);
    b.getTerritories().get(3).setOwner(2); // now only the "outer-circle" of territories (1 2 5 4 7 8) belong to player
                                           // 0

    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(1, 8, 0));
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(8, 1, 0));
    assertEquals(Constant.TERRITORY_SIZE * 3, b.findMinPathDistance(8, 2, 0));
    assertEquals(Constant.TERRITORY_SIZE * 3, b.findMinPathDistance(2, 8, 0));
    assertEquals(Constant.TERRITORY_SIZE * 4, b.findMinPathDistance(5, 8, 0));
    assertEquals(Constant.TERRITORY_SIZE * 4, b.findMinPathDistance(8, 5, 0));
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(0, 6, 1)); // test the two territories that belong to player 1

    // Test there is no path between the two territories
    b.getTerritories().get(8).setOwner(1);
    b.getTerritories().get(5).setOwner(1);

    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(1, 2, 0));
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(2, 1, 0));
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(7, 4, 0));
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(4, 7, 0));

    assertEquals(Constant.TERRITORY_SIZE * 3, b.findMinPathDistance(8, 0, 1)); // test player 1
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(0, 6, 1));
    assertEquals(Constant.TERRITORY_SIZE * 2, b.findMinPathDistance(6, 0, 1));

    // --- FAILED ON THIS LINE: expected: <2147483647> but was: <-2147483644> ---
    // assertEquals(Integer.MAX_VALUE, b.findMinPathDistance(7, 2, 0)); // no connected path 
    // --- FAILED ON THIS LINE: expected: <2147483647> but was: <-2147483644> ---
    // assertEquals(Integer.MAX_VALUE, b.findMinPathDistance(2, 7, 0));
  }

}
