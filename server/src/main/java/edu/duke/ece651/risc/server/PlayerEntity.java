package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is to represent a player that in a game room in server side.
 */
public abstract class PlayerEntity<T> {
  /** Send data to player */
  protected ObjectOutputStream toPlayer;
  /** Read data from player */
  protected ObjectInputStream fromPlayer;
  /** The id of this player in a game room */
  protected int playerId;
  /** The symbol that to display the player */
  protected T playerSymbol; // in evolution1, a String is enough. This field is to display a player in
                            // GUI. We remain this field as a a place holder for GUI requirements.
  /** The index of territory group that the player picked at the start */
  protected int ownedGroup; // before picking any territory group, this is -1
  /** An integer indicating the status of player, defined in Constant class */
  protected int playerStatus;

  /**
   * Constructor that initialize corresponding fields with parameters.
   * 
   * @param toPlayer     the ObjectOutputStream that sends data to this player
   * @param fromPlayer   the ObjectInputStream that receive data from this player
   * @param playerId     the player ID of this player
   * @param playerSymbol the symbol that to display this player
   * @param ownedGroup   the territory group number owned by this player
   * @param playerStatus the status of this player
   */
  public PlayerEntity(ObjectOutputStream toPlayer, ObjectInputStream fromPlayer, int playerId, T playerSymbol,
      int ownedGroup, int playerStatus) {
    this.toPlayer = toPlayer;
    this.fromPlayer = fromPlayer;
    this.playerId = playerId;
    this.playerSymbol = playerSymbol;
    this.ownedGroup = ownedGroup;
    this.playerStatus = playerStatus;
  }

  /**
   * Send an object to the player via ObjectOutputStream. This method is used to
   * send self-defined object as well as simple String messages.
   * 
   * @param o
   * @throws IOException
   */
  public void sendObject(Object o) throws IOException {
    toPlayer.writeObject(o);
  }

  /**
   * Receive an Object from the player via ObjectInputStream. This method is used
   * to receive self-defined object as well as simple String messages.
   * 
   * @return the Object recieved
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public Object receiveObject() throws ClassNotFoundException, IOException {
    return fromPlayer.readObject();
  }

  /**
   * @param fromPlayer the fromPlayer to set
   */
  public void setFromPlayer(ObjectInputStream fromPlayer) {
    this.fromPlayer = fromPlayer;
  }

  /**
   * @param toPlayer the toPlayer to set
   */
  public void setToPlayer(ObjectOutputStream toPlayer) {
    this.toPlayer = toPlayer;
  }

  /**
   * @return the playerId
   */
  public int getPlayerId() {
    return playerId;
  }

  /**
   * @param playerId the playerId to set
   */
  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  /**
   * @return the playerSymbol
   */
  public T getPlayerSymbol() {
    return playerSymbol;
  }

  /**
   * @param playerSymbol the playerSymbol to set
   */
  public void setPlayerSymbol(T playerSymbol) {
    this.playerSymbol = playerSymbol;
  }

  /**
   * @return the ownedGroup
   */
  public int getOwnedGroup() {
    return ownedGroup;
  }

  /**
   * @param ownedGroup the ownedGroup to set
   */
  public void setOwnedGroup(int ownedGroup) {
    this.ownedGroup = ownedGroup;
  }

  /**
   * @return the playerStatus
   */
  public int getPlayerStatus() {
    return playerStatus;
  }

  /**
   * @param playerStatus the playerStatus to set
   */
  public void setPlayerStatus(int playerStatus) {
    this.playerStatus = playerStatus;
  }

  /**
   * @return the toPlayer
   */
  public ObjectOutputStream getToPlayer() {
    return toPlayer;
  }

  /**
   * @return the fromPlayer
   */
  public ObjectInputStream getFromPlayer() {
    return fromPlayer;
  }

}
