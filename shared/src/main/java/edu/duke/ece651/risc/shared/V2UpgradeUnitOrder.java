package edu.duke.ece651.risc.shared;

import java.util.HashMap;

public class V2UpgradeUnitOrder<T> implements Order<T> {

  /**
   * Fields required by Serializable
   */
  private static final long serialVersionUID = 5L;

  /** The territory where the units to be upgrade are located */
  public int targetTerritory;

  /** From which level to upgrade */
  public int levelFrom;

  /** Upgrade to which level */
  public int levelTo;

  /**
   * The amount of units to upgrade. Note these units should be all on the same
   * level with levelFrom
   */
  public int howMany;

  /**
   * Constructor that initialize fields with corresponding parameters
   * 
   * @param targetTerritory    the territory which contains the units to upgrade
   * @param levelFrom          the level of these units
   * @param levelTo            the level these units will be upgrade to
   * @param howMany            the amount of units you want to upgrade
   */
  public V2UpgradeUnitOrder(int targetTerritory, int levelFrom, int levelTo, int howMany) {
    this.targetTerritory = targetTerritory;
    this.levelFrom = levelFrom;
    this.levelTo = levelTo;
    this.howMany = howMany;
  }

  /**
   * Execute the upgrade unit order. It will do the following:
   * 1. upgrade the unit in currDefenderArmy of the target territory.
   * 2. consume the corresponding amount of technology resources for the 
   *    player who issue this order.
   * 
   * @since evolution 2.
   */
  @Override
  public boolean execute(GameStatus<T> gs) {
    Territory<T> toModify = gs.getGameBoard().getTerritories().get(targetTerritory);
    PlayerEntity<T> playerWhoWantUpgrade = gs.getCurrPlayer();
    
    // First we remove the amount of units on the same level with levelFrom
    toModify.removeDefendUnits(levelFrom, howMany);
    
    // Then we add the same amount of units with the new level speficied by levelTo
    toModify.addDefendUnits(levelTo, howMany);
    
    // Finally we remove the corresponding amount of food resource for this player
    playerWhoWantUpgrade.consumeTechResource(calcTechCostPerUnit(levelFrom, levelTo) * howMany);

    return true;
  }

  /**
   * Provide the implementation suitable for this class.
   */
  @Override
  public int getSrcTerritory() {
    return targetTerritory;
  }

  /**
   * Provide the implementation suitable for this class.
   */
  @Override
  public int getDestTerritory() {
    return targetTerritory;
  }

  /**
   * @return the amount of units that this order wants to manipulate
   */
  @Override
  public int getUnitAmount() {
    return howMany;
  }

  /**
   * Helper method to calculate the cost of upgrading 1 (one) unit from
   * levelBefore to levelAfter. The rule detail can be found in the evo2 pdf,
   * 7.(e).
   * 
   * @param levelBefore the level upgrade from
   * @param levelAfter  the level upgrade to
   * @return the cost of upgrading 1 (one) unit from levelBefore to levelAfter
   */
  protected int calcTechCostPerUnit(int levelBefore, int levelAfter) {
    if (levelBefore > levelAfter) { // fail fast, in case there is bug in rule checker
      throw new IllegalArgumentException("Invalid input parameter!");
    }
    return Constant.UP_UNIT_COST.get(levelAfter) - Constant.UP_UNIT_COST.get(levelBefore);
  } 


  /**
   * This method SHOULD NOT be used in evo 2. This violate LSP.. you can let the
   * new execute call this one, but the code would be somehow wired to write..
   * 
   * WARNING: DO NOT USE THIS METHOD SINCE EVO 2!! Otherwise there would be bug in
   * code...
   * 
   * @deprecated since evolution 2.
   */
  @Override
  @Deprecated
  public boolean execute(Board<T> board) {
    return false;
  }

  @Override
  public HashMap<Integer, Integer> getArmyHashMap() {
    return null;
  }
}
