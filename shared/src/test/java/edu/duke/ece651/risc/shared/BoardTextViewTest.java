package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class BoardTextViewTest {
  @Test
  public void test_makeHead() {

    BoardFactory<String> f = new V1BoardFactory();
    Board<String> b = f.makeGameBoard(2);
    assertEquals(b.occupyTerritory(0, 0), false); // group 0 owner is 0
    assertEquals(b.occupyTerritory(1, 1), false); // group 1 owner is 1

    BoardTextView bt1 = new BoardTextView(b);
    String parseLine = "------------------\n";
    String s1 = "Player 1:\n" + parseLine;
    String s2 = "Group 1:\n" + parseLine;
    assertEquals(s1, bt1.makeHead(true, 1));
    assertEquals(s2, bt1.makeHead(false, 1));

  }

  @Test
  public void test_displayFullBoard() {
    BoardFactory<String> f = new V1BoardFactory();
    Board<String> b = f.makeGameBoard(2);
    b.updateAllPrevDefender();

    BoardTextView bt1 = new BoardTextView(b);
    ArrayList<Territory<String>> territories = b.getTerritories();

    // String h1 = bt1.makeHead(false, 0);
    // String h2 = bt1.makeHead(false, 1);

    StringBuffer sb1 = new StringBuffer();
    sb1.append(bt1.makeHead(false, 0));
    for (int i = 0; i < 3; i++) {
      sb1.append(b.whatIsIn(territories.get(i), false));
    }
    sb1.append(bt1.makeHead(false, 1));
    for (int i = 3; i < 6; i++) {
      sb1.append(b.whatIsIn(territories.get(i), false));
    }

    assertEquals(sb1.toString(), bt1.displayFullBoard());

    assertEquals(b.occupyTerritory(0, 0), false); // group 0 owner is 0
    // assertEquals(b.occupyTerritory(1, 1), false); // group 1 owner is 1
    b.updateAllPrevDefender();

    StringBuffer sb2 = new StringBuffer();
    sb2.append(bt1.makeHead(true, 0));
    for (int i = 0; i < 3; i++) {
      sb2.append(b.whatIsIn(territories.get(i), false));
    }
    sb2.append(bt1.makeHead(false, 1));
    for (int i = 3; i < 6; i++) {
      sb2.append(b.whatIsIn(territories.get(i), false));
    }

    assertEquals(sb2.toString(), bt1.displayFullBoard());
  }

  @Test
  public void test_displayBoardFor_displayGroup() {
    BoardFactory<String> f = new V1BoardFactory();
    Board<String> b = f.makeGameBoard(2);
    BoardTextView bt1 = new BoardTextView(b);

    assertEquals(b.occupyTerritory(0, 0), false);
    assertEquals(b.occupyTerritory(1, 1), false);

    ArrayList<Territory<String>> territories = b.getTerritories();

    for (int i = 0; i < 6; i++) {
      b.deployUnits(i, 5, i / 3);
    }

    b.updateAllPrevDefender();

    StringBuffer sb1 = new StringBuffer();
    StringBuffer sb2 = new StringBuffer();
    // sb1.append("1");

    sb1.append(bt1.makeHead(true, 0));
    sb2.append(bt1.makeHead(true, 0));

    for (int i = 0; i < 3; i++) {
      sb1.append(b.whatIsIn(territories.get(i), true));
      sb2.append(b.whatIsIn(territories.get(i), false));
    }

    sb1.append(bt1.makeHead(true, 1));
    sb2.append(bt1.makeHead(true, 1));
    for (int i = 3; i < 6; i++) {
      sb1.append(b.whatIsIn(territories.get(i), false));
      sb2.append(b.whatIsIn(territories.get(i), true));
    }

    assertEquals(sb1.toString(), bt1.displayBoardFor(0));
    assertEquals(sb2.toString(), bt1.displayBoardFor(1));

    StringBuffer sb3 = new StringBuffer();
    for(int i = 0 ; i < 3 ; i ++){
      sb3.append(b.whatIsIn(territories.get(i), true));
    }

    assertEquals(sb3.toString(), bt1.displayGroup(0));
  }

}




