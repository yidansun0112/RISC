package edu.duke.ece651.risc.shared;

import java.util.HashMap;

public class V2UpgradeTechLevelOrder<T> implements Order<T> {

  /**
   * Fields required by Serializable
   */
  private static final long serialVersionUID = 6L;

  int playerId; // I realized that we need to know *who* issue this kind of order to
                // set the the boolean needUpTechLv in GUIPlayerEntity..
                // this requires that the client code need to store the player id in the which
                // corresponding to the game room the player currently in.

  /**
   * Constructor that initialize fields with corresponding parameters
   * 
   * @param playerId the player id who issues this order
   */
  public V2UpgradeTechLevelOrder(int playerId) {
    this.playerId = playerId;
  }

  /**
   * Execute this kind of order.
   */
  @Override
  public boolean execute(GameStatus<T> gs) {
    // Attention: we only need to set the boolean field needUpTechLv in
    // playerEntity, and let the game room to really do the final step (i.e., call
    // the upgradeTechLevel method) after resolving
    // all battles.
    PlayerEntity<T> playerWhoWantUpgrade = gs.getCurrPlayer();
    playerWhoWantUpgrade.setNeedUpTechLv();
    
    // Now consume the tech resource immediately
    int currTechLevel = playerWhoWantUpgrade.getTechLevel();
    playerWhoWantUpgrade.consumeTechResource(Constant.UP_TECH_LEVEL_COST.get(currTechLevel));
    return true;
  }

  /************************************************************
   * These three getters should not used in this kind of order.
   ***********************************************************/

  @Override
  public int getSrcTerritory() {
    return -1;
  }

  @Override
  public int getDestTerritory() {
    return -1;
  }

  @Override
  public int getUnitAmount() {
    return -1;
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
