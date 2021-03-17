package edu.duke.ece651.risc.shared;

import java.util.*;

public abstract class BoardView<T> {
  protected Board<T> toDisplay;
  protected HashMap<Integer, ArrayList<Territory<T>>> res;

  public BoardView(Board<T> display) {
    this.toDisplay = display;
    this.res = new HashMap<>();
  }

  public void classifyHelper(Territory<T> t, int index) {
    if (!res.containsKey(index)) {
      ArrayList<Territory<T>> temp = new ArrayList<>();
      temp.add(t);
      res.put(index, temp);
    } else {
      res.get(index).add(t);
    }
  }

  public void classify() {
    res.clear();
    for (Territory<T> t : toDisplay.getTerritories()) {
      // If the Territory is initial
      if (t.getOwner() == -1) {
        int index = -t.getGroup() - 2;
        classifyHelper(t, index);
      }
      // If the Territory has been assigned an owner
      else {
        classifyHelper(t, t.getOwner());
      }
    }
  }

  public abstract T displayFullBoard();

  public abstract T displayBoardFor(int playerId);

  public abstract T displayGroup(int player);

  //public abstract T showTerritoryGroup();
}
