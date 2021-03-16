package edu.duke.ece651.risc.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.duke.ece651.risc.shared.Constant;

/**
 * This class provide a concret implementation with type String as <T> for
 * evolution 1.
 */
public class TextPlayerEntity extends PlayerEntity<String> {

  /**
   * Constructor that called when a player just get in a game room. The game has
   * not actullay began, so initialize some fields with -1 value
   * 
   * @param toPlayer
   * @param fromPlayer
   */
  public TextPlayerEntity(ObjectOutputStream toPlayer, ObjectInputStream fromPlayer, int playerId) {
    this(toPlayer, fromPlayer, playerId, "BasicPlayer", -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
  }

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
  public TextPlayerEntity(ObjectOutputStream toPlayer, ObjectInputStream fromPlayer, int playerId, String playerSymbol,
      int ownedGroup, int playerStatus) {
    super(toPlayer, fromPlayer, playerId, playerSymbol, ownedGroup, playerStatus);
  }

}
