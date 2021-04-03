package edu.duke.ece651.risc.shared;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class provide a concret implementation with type String as <T> for
 * evolution 1.
 */
public class TextPlayerEntity<T> extends PlayerEntity<T> {

  /**
   * Constructor that takes in parameters and call the constructor of super class
   * to initialize corresponding fields.
   * 
   * @param toPlayer     the ObjectOutputStream that sends data to this player
   * @param fromPlayer   the ObjectInputStream that receive data from this player
   * @param playerId     the player ID of this player
   * @param playerSymbol the symbol that to display this player
   * @param ownedGroup   the territory group number owned by this player
   * @param playerStatus the status of this player
   */
  public TextPlayerEntity(ObjectOutputStream toPlayer, ObjectInputStream fromPlayer, int playerId, T playerSymbol,
      int ownedGroup, int playerStatus) {
    super(toPlayer, fromPlayer, playerId, playerSymbol, ownedGroup, playerStatus);
  }

  /******************************************************************************
   * Below are the evo1 version of dummy implementation for the methods that are
   * actually used in evo2
   ******************************************************************************/

  @Override
  public void harvestAllResource(Board<T> board) {
    return;
  }

  @Override
  public int getFoodResourceAmount() {
    return 0;
  }

  @Override
  public void consumeFoodResource(int amt) {
    return;
  }

  @Override
  public int getTechResourceAmount() {
    return 0;
  }

  @Override
  public void consumeTechResource(int amt) {
    return;
  }

  @Override
  public int getTechLevel() {
    return 0;
  }

  @Override
  public void upgradeTechLevel() {
    return;
  }

  // @Override
  // public boolean canUpTechLevel() {
  //   return false;
  // }

  @Override
  public void setNeedUpTechLv() {
    return;
  }

  @Override
  public boolean getNeedUpTechLv() {
    return false;
  }

}
