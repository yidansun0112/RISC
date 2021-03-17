package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.shared.Board;

public interface Resolver<T> {
  public void combineEnemyArmy(Board<T> board);
}
