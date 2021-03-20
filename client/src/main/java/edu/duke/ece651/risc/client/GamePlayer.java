package edu.duke.ece651.risc.client;

import java.io.IOException;

/**
 * This class handles a basic GamePlayer interface
 * 
 * @param T String for V1
 */
public interface GamePlayer<T> {

  /**
   * The initial step of a game.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void initGame() throws IOException, ClassNotFoundException;

  /**
   * Let the player pick the territory
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void pickTerritory() throws IOException, ClassNotFoundException;

  /**
   * Let the player deploy all his/her units
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void deployUnits() throws IOException, ClassNotFoundException;

  /**
   * Let the player actually player the game (i.e., issuing orders, etc.)
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void doPlayPhase() throws IOException, ClassNotFoundException;
}