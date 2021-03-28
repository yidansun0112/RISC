package edu.duke.ece651.risc.shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is to represent a player that in a game room in server side.
 */
public abstract class PlayerEntity<T> {
  // TODO: two fields below (object input/output streams) may need to be @Transit
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
   * This method will harvest various kinds of resources for the player, which are
   * currently allowed to be harvested according to the game rules
   */
  public abstract void harvestAllResource();

  /**
   * This method will return the food resource possessed by this player.
   * 
   * @return Resource object which contains the information about the current
   *         amount of this kind of resource.
   */
  public abstract int getFoodResourceAmount();

  /**
   * This method will decrease the food resources by specified amount
   * 
   * @param amt the amount of food resources to be consumed
   */
  public abstract void consumeFoodResource(int amt);

  /**
   * This method will return the food resource possessed by this player.
   * 
   * @return Resource object which contains the information about the current
   *         amount of this kind of resource.
   */
  public abstract int getTechResourceAmount();

  /**
   * This method will decrease the tech resources by specified amount
   * 
   * @param amt the amount of tech resources to be consumed
   */
  public abstract void consumeTechResource(int amt);

  /**
   * This method will return the current max tech level of this player
   * 
   * @return the value max technology level
   */
  public abstract int getTechLevel();

  /**
   * This method will set boolean needUpTechLv to true
   */
  public abstract void setNeedUpTechLv();

  /**
   * Getter for needUpTechLv
   * 
   * @return needUpTechLv
   */
  public abstract boolean getNeedUpTechLv();

  /**
   * This method is used to upgrade max tech level.
   * 
   * @apiNote note that this method can only be called after passed the
   *          corresponding order rule checker!
   */
  public abstract void upgradeTechLevel();

  // /**
  //  * This method can be used in order rule checker for a more concise code in rule
  //  * checker. Also add this method for better support LSP principle.
  //  * 
  //  * You can also write your own rule checking code in rule checker and not use
  //  * this method, if more easy to implementation without technical debt.
  //  * 
  //  * @return {@code true} if the player can upgrade his/her max tech level;
  //  *         {@code false} if cannot upgrade tech level currently.
  //  */
  // public abstract boolean canUpTechLevel();

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
