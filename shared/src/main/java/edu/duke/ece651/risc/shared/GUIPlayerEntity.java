package edu.duke.ece651.risc.shared;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GUIPlayerEntity<T> extends PlayerEntity<T> {

  /** The food resource that a player currently has */
  Resource foodResource;

  /** The technology resource that a player currently has */
  Resource techResource;

  /** The current max tech level */
  int techLevel;

  /**
   * Whether the player decide to upgrade the max tech level in a turn. Only when
   * server recieves a valid upgrade max tech level order can this field set to
   * true.
   * 
   * true: the techLevel will increment by 1 before the next turn begins. false:
   * the techLevel will keep unchanged in the next turn.
   */
  boolean needUpTechLv;

  public GUIPlayerEntity(ObjectOutputStream toPlayer, ObjectInputStream fromPlayer, int playerId, T playerSymbol,
      int ownedGroup, int playerStatus) {
    super(toPlayer, fromPlayer, playerId, playerSymbol, ownedGroup, playerStatus);

    this.foodResource = new Resource(Constant.INIT_FOOD_RESOURCE);
    this.techResource = new Resource(Constant.INIT_TECH_RESOURCE);
    this.techLevel = Constant.INIT_MAX_TECH_LEVEL;
    this.needUpTechLv = false;
  }

  @Override
  public void harvestAllResource() {
    // TODO add new method and field in V2 territory then back here to finish this
    return;
  }

  @Override
  public Resource getFoodResource() {
    return foodResource;
  }

  @Override
  public void consumeFoodResource(int amt) {
    foodResource.consumeResource(amt);
  }

  @Override
  public Resource getTechResource() {
    return techResource;
  }

  @Override
  public void consumeTechResource(int amt) {
    techResource.consumeResource(amt);
  }

  @Override
  public int getTechLevel() {
    return techLevel;
  }

  /**
   * This method is used to try to upgrade max tech level.
   * 
   * @apiNote this method can only be called when all players are done with their
   *          orders in the game room, not by the execute method of
   *          "UpTechLvOrder" in GameHostThread, since this method will
   *          immediately increment the max tech level!
   * 
   * @return {@code true} if successfully upgrated tech level; {@code false} if
   *         currently cannot upgrade tech level.
   * @throws IllegalStateException if the player cannot upgrade tech level
   *                               currently
   */
  @Override
  public void upgradeTechLevel() {
    // Fail fast. In actual game process, we need to ensure the order rule checker
    // do robust checking, and these throws should not be executed. Add them here
    // just for the convenience for debugging and fail fast.
    if (techLevel == Constant.TOTAL_LEVELS) {
      throw new IllegalStateException("Already on hightest tech level!");
    }
    if (needUpTechLv == false) {
      throw new IllegalStateException("Already on hightest tech level!");
    }
    techLevel++;
    needUpTechLv = false;
  }

  // /**
  // * Add this method in prevention of LSP issues in evo3...
  // * The rule checker can call this method
  // */
  // @Override
  // public boolean canUpTechLevel() {
  // // TODO: when finished v2 territory and cost calculation, finish this method!
  // return needUpTechLv;
  // }

  @Override
  public void setNeedUpTechLv() {
    this.needUpTechLv = true;
  }

  /**
   * Currenly this method is same as canUpTechLevel. We may still want to keep
   * canUpTechLevel, in case in evo3 we have a moew complicated rule about whether
   * a player can upgrade the max tech level.
   */
  @Override
  public boolean getNeedUpTechLv() {
    return this.needUpTechLv;
  }
}
