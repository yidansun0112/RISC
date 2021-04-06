package edu.duke.ece651.risc.shared;

import java.util.*;

public class V2AttackOrder<T> implements Order<T> {

  /**
   * Fields required by Serializable
   */
  private static final long serialVersionUID = 4L;

  private int srcTerritory;
  private int destTerritory;

  /** Key: level, Value: the amount of units of this level */
  private HashMap<Integer, Integer> levelToUnitAmount;

  // String order ? -- Nope. We use a drop down menu in GUI and let the code to
  // build an order instance according the selection of the drop down menu.
  public V2AttackOrder(int SrcTerritory, int DestTerritory, HashMap<Integer, Integer> unitAmount) {
    this.srcTerritory = SrcTerritory;
    this.destTerritory = DestTerritory;
    this.levelToUnitAmount = unitAmount;
  }

  /**
   * Execute the V2Attack order. It will do the following: 1. get the source and
   * destination territory instances. 2. move units with specified amount and
   * level from the src to dest. 3. reduce the food resource for the attacke
   * player.
   * 
   * @param gs GameStatus object which contains the instances necessary to execute
   *           this order.
   * @return {@code true} if is successfully executed.
   * 
   * @apiNote In this method I use some Java HashMap (Map) API. Please refer to
   *          the official documentation:
   *          https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
   *          https://docs.oracle.com/javase/8/docs/api/java/util/Map.Entry.html
   * 
   * @implNote before the return value is checked by the caller, I remove the
   *           try-catch to let the exception propogate out to fail fast.
   * 
   */
  @Override
  public boolean execute(GameStatus<T> gs) {
    Territory<T> leaveFrom = gs.getGameBoard().getTerritories().get(srcTerritory);
    Territory<T> attackOn = gs.getGameBoard().getTerritories().get(destTerritory);

    PlayerEntity<T> attacker = gs.getCurrPlayer(); // note the player id who issues this order
                                                   // should be same with the owner id of
                                                   // the source territory.

    // First, we need to remove the units with corresponding level and amount out of
    // the currDefenderArmy in source territory, and add them into the target
    // territory's enemyArmy.
    for (Map.Entry<Integer, Integer> entry : levelToUnitAmount.entrySet()) {
      leaveFrom.removeDefendUnits(entry.getKey(), entry.getValue());
      attackOn.addEnemy(attacker.getPlayerId(), entry.getKey(), entry.getValue());
    }

    // Second we need to reduce the food resource for the attacker player.
    attacker.consumeFoodResource(getUnitAmount() * 1); // An attack order costs 1 food per unit attacking.

    return true;
  }

  @Override
  public int getSrcTerritory() {
    return srcTerritory;
  }

  @Override
  public int getDestTerritory() {
    return destTerritory;
  }

  /**
   * This method will return the total amount of units which participated in this
   * attacking action.
   */
  @Override
  public int getUnitAmount() {
    int totalAmount = 0;
    for (Integer amtOfALevel : levelToUnitAmount.values()) {
      totalAmount += amtOfALevel.intValue();
    }
    return totalAmount;
  }

  /**
   * getter for levelToUnit HashMap. Used in checker
   */
  @Override
  public HashMap<Integer, Integer> getArmyHashMap(){
    return this.levelToUnitAmount;
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
}
