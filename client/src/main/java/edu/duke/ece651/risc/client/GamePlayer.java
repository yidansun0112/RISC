package edu.duke.ece651.risc.client;

import java.io.IOException;

/**
 * This class handles a basic GamePlayer interface
 * 
 * @param T String for V1
 */
public interface GamePlayer<T> {
  // TODO: check what method in V1GamePlayer can also be put here


  public void initGame() throws IOException, ClassNotFoundException;

  public void doPlayPhase() throws IOException, ClassNotFoundException;
}