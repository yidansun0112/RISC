package edu.duke.ece651.risc.shared;

import java.util.*;

public class V1BoardFactory implements BoardFactory<String> {
  protected HashMap<Integer, String> terriName;

  public V1BoardFactory() {
    terriName = new HashMap<Integer, String>();
    initTerriName();
  }

  /**
   * Set up the map between territory id and territory name  
   */
  protected void initTerriName() {
    terriName.put(0, "Narnia");
    terriName.put(1, "Midkemia");
    terriName.put(2, "Oz");
    terriName.put(3, "Elantris");
    terriName.put(4, "Roshar");
    terriName.put(5, "Scadrial");
    terriName.put(6, "Gondor");
    terriName.put(7, "Mordor");
    terriName.put(8, "Hogwarts");
    terriName.put(9, "Narnia");
    terriName.put(10, "Britt");
    terriName.put(12, "Calibre");
    terriName.put(13, "Elk");
    terriName.put(14, "Floyd");

  }

  /**
   * Generate a v1 game board for 2 players  
   */
  protected Board<String> make2PlayerBoard() {
    int[][] worldMap = new int[][] { { 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 0, 0, 1 }, { 1, 1, 1, 1, 0, 0 },
        { 1, 0, 1, 1, 1, 0 }, { 1, 0, 0, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1 } };
    ArrayList<Territory<String>> territories = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      territories.add(new V1Territory<String>(i, terriName.get(i), i / 3, worldMap[i]));
    }
    Board<String> b = new V1GameBoard(territories, worldMap);
    return b;
  }

  /**
   * Generate a v1 game board for 3 players  
   */
  protected Board<String> make3PlayerBoard() {
    int[][] worldMap = new int[][] { { 1, 1, 1, 1, 0, 0, 1, 0, 0 }, { 1, 1, 1, 0, 0, 0, 0, 0, 1 },
        { 1, 1, 1, 0, 0, 1, 0, 0, 0 }, { 1, 0, 0, 1, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1, 1, 0, 1, 0 },
        { 0, 0, 1, 1, 1, 1, 0, 0, 0 }, { 1, 0, 0, 1, 0, 0, 1, 1, 1 }, { 0, 0, 0, 0, 1, 0, 1, 1, 1 },
        { 0, 1, 0, 0, 0, 0, 1, 1, 1 } };
    ArrayList<Territory<String>> territories = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      territories.add(new V1Territory<String>(i, terriName.get(i), i / 3, worldMap[i]));
    }
    Board<String> b = new V1GameBoard(territories, worldMap);
    return b;
  }
  
  /**
   * Generate a v1 game board for 4 players  
   */
  protected Board<String> make4PlayerBoard() {
    int[][] worldMap = new int[][] { { 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 }, { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0 },
        { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 },
        { 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0 },
        { 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 } };
    ArrayList<Territory<String>> territories = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
      territories.add(new V1Territory<String>(i, terriName.get(i), i / 3, worldMap[i]));
    }
    Board<String> b = new V1GameBoard(territories, worldMap);
    return b;
  }

  /**
   * Generate a v1 game board for 5 players  
   */
  protected Board<String> make5PlayerBoard() {
    int[][] worldMap = new int[][] { { 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
        { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, { 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
        { 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0 },
        { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1 } };
    ArrayList<Territory<String>> territories = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      territories.add(new V1Territory<String>(i, terriName.get(i), i / 3, worldMap[i]));
    }
    Board<String> b = new V1GameBoard(territories, worldMap);
    return b;
  }

  /**
   * Generate a game board based on the given player number  
   */
  @Override
  public Board<String> makeGameBoard(int playerNum) {
    Board<String> b = new V1GameBoard();
    switch(playerNum){
    case 2:
      b =  make2PlayerBoard();
      break;
    case 3:
      b = make3PlayerBoard();
      break;
    case 4:
      b =  make4PlayerBoard();
     break;
    case 5:
     b = make5PlayerBoard();
     break;
    }
    return b;
  }
}





