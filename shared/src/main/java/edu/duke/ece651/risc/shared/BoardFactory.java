package edu.duke.ece651.risc.shared;

import java.util.*;
public abstract class BoardFactory<T> {
  /**
   * Generate a game board based on the given player number
   * 
   * @param playerNum is the player number
   * @return a Board
   */
  protected HashMap<Integer, String> terriName;
  
  protected int[][] worldMap2 = new int[][] { { 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 0, 0, 1 }, { 1, 1, 1, 1, 0, 0 },
        { 1, 0, 1, 1, 1, 0 }, { 1, 0, 0, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1 } };
  protected int[][] worldMap3 = new int[][] { { 1, 1, 1, 1, 0, 0, 1, 0, 0 }, { 1, 1, 1, 0, 0, 0, 0, 0, 1 },
  { 1, 1, 1, 0, 0, 1, 0, 0, 0 }, { 1, 0, 0, 1, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1, 1, 0, 1, 0 },
  { 0, 0, 1, 1, 1, 1, 0, 0, 0 }, { 1, 0, 0, 1, 0, 0, 1, 1, 1 }, { 0, 0, 0, 0, 1, 0, 1, 1, 1 },
  { 0, 1, 0, 0, 0, 0, 1, 1, 1 } };

  protected int[][] worldMap4 = new int[][] {
    //  0  1  2  3  4  5  6  7  8  9  10  11
      { 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 }, // 0
      { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0 }, // 1
      { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 2
      { 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 3
      { 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0 }, // 4
      { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, // 5
      { 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0 }, // 6
      { 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0 }, // 7
      { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 }, // 8
      { 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0 }, // 9
      { 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 }, // 10
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 }  // 11
    };

  protected int[][] worldMap5 = new int[][] { 
      //  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14
        { 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, // 0
        { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, // 1
        { 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 2
        { 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, // 3
        { 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, // 4
        { 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, // 5
        { 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 6
        { 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0 }, // 7
        { 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0 }, // 8
        { 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0 }, // 9
        { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0 }, // 10
        { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1 }, // 11
        { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0 }, // 12
        { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 }, // 13
        { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1 }  // 14
      };
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
    terriName.put(9, "London");
    terriName.put(10, "Britt");
    terriName.put(11, "Calibre");
    terriName.put(12, "Elk");
    terriName.put(13, "Floyd");
    terriName.put(14, "Niflheim");
  }

  /**
   * different method to generate Board with different number of players.
   * @return
   */
  protected abstract Board<T> make2PlayerBoard();
  protected abstract Board<T> make3PlayerBoard();
  protected abstract Board<T> make4PlayerBoard();
  protected abstract Board<T> make5PlayerBoard();

  /**
   * Generate a game board based on the given player number 
   * @param playerNum is the player number
   * @return a Board
   */
  public Board<T> makeGameBoard(int playerNum){
    Board<T> b = new V1GameBoard<>();
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
