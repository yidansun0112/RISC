package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MoveOrderPathCheckerTest {
  @Test
  public void test_MoveOrderPathChecker() {
    
    BoardFactory<String> f = new V1BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);
    assertEquals(b.occupyTerritory(0, 0), false); // group 0 owner is 0
    assertEquals(b.occupyTerritory(1, 1), false); // group 1 owner is 1

    MoveOrder<String> m1 = new MoveOrder<>("0 1 2");
    MoveOrder<String> m2 = new MoveOrder<>("1 4 3");

    MoveOrder<String> m3 = new MoveOrder<String>("0 3 0");
    MoveOrder<String> m4 = new MoveOrder<String>("3 3 2");
    
    MoveOrderPathChecker<String> checker = new MoveOrderPathChecker<>(null);

    //Now the player 0 has 0,1,2, all of them are connected to each other.
    assertEquals(null, checker.checkOrder(0,m1,b));

    //Now make the Territory 0 belongs to player1 and make the Territory 4 belongs to player0
    b.getTerritories().get(0).setOwner(1);
    b.getTerritories().get(4).setOwner(0);
    String s1 = "Sorry, there is no path from Midkemia to Roshar.\n";
    assertEquals(s1, checker.checkOrder(0, m2, b));
    assertEquals(null, checker.checkOrder(0, m3, b));
    assertEquals(null, checker.checkOrder(1, m4, b));
  }

}
