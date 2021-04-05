package edu.duke.ece651.risc.shared;

import java.util.*;

public class V1BoardFactory<T> extends BoardFactory<T> {

  public V1BoardFactory() {
    terriName = new HashMap<Integer, String>();
    initTerriName();
  }

  /**
   * Generate a v1 game board for 2 players  
   */
  @Override
  protected Board<T> make2PlayerBoard() {
    ArrayList<Territory<T>> territories = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      territories.add(new V1Territory<T>(i, terriName.get(i), i / 3, worldMap2[i]));
    }
    Board<T> b = new V1GameBoard<>(territories, worldMap2);
    return b;
  }

  /**
   * Generate a v1 game board for 3 players  
   */
  @Override
  protected Board<T> make3PlayerBoard() {
    ArrayList<Territory<T>> territories = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      territories.add(new V1Territory<T>(i, terriName.get(i), i / 3, worldMap3[i]));
    }
    Board<T> b = new V1GameBoard<>(territories, worldMap3);
    return b;
  }
  
  /**
   * Generate a v1 game board for 4 players  
   */
  @Override
  protected Board<T> make4PlayerBoard() {
    ArrayList<Territory<T>> territories = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
      territories.add(new V1Territory<T>(i, terriName.get(i), i / 3, worldMap4[i]));
    }
    Board<T> b = new V1GameBoard<>(territories, worldMap4);
    return b;
  }

  /**
   * Generate a v1 game board for 5 players  
   */
  @Override
  protected Board<T> make5PlayerBoard() {
    ArrayList<Territory<T>> territories = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      territories.add(new V1Territory<T>(i, terriName.get(i), i / 3, worldMap5[i]));
    }
    Board<T> b = new V1GameBoard<>(territories, worldMap5);
    return b;
  }

}





