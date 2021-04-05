package edu.duke.ece651.risc.shared;

import java.util.HashMap;

public class V2MoveOrder<T> implements Order<T> {

  /**
   * Fields required by Serializable
   */
  private static final long serialVersionUID = 3L;

  private int srcTerritory;
  private int destTerritory;

  /** Key: level, Value: the amount of units of this level */
  private HashMap<Integer, Integer> levelToUnitAmount;

  /**
   * Constructor that initialize fields with corresponding parameters
   * 
   * @param srcTerritory      the territory id of the source territory
   * @param destTerritory     the territory id of the destination territory
   * @param levelToUnitAmount a HashMap that contains the level of the units, and
   *                          the amount of the units on this level
   */
  public V2MoveOrder(int srcTerritory, int destTerritory, HashMap<Integer, Integer> levelToUnitAmount) {
    this.srcTerritory = srcTerritory;
    this.destTerritory = destTerritory;
    this.levelToUnitAmount = levelToUnitAmount;
  }

  @Override
  public boolean execute(GameStatus<T> gs) {
    // TODO finish this method when move the BFS/Dijkstra method into Board.java
    // This is the advice given by TA in the feedback of evo1 - I think this is reasonable.
    Board<T> board = gs.getGameBoard();
    // Don't Know the serialVersionUID represent, so I just use it as the User ID(which is playerID in Evol1)
    PlayerEntity<T> currPlayer = gs.currPlayer;
    int pathLength = board.findMinPathDistance(this.srcTerritory, this.destTerritory, currPlayer.getPlayerId());
    if(pathLength == Integer.MAX_VALUE){
      return false;
    }
    else{
      // Remove the Unit in Source Territory and Add the Unit in Destination Territory
      for(int i : levelToUnitAmount.keySet()){
        board.removeDefendUnitsFrom(this.srcTerritory, i, levelToUnitAmount.get(i));
        board.addDefendUnitsTo(this.destTerritory, i, levelToUnitAmount.get(i));
      }
      currPlayer.consumeFoodResource(pathLength * this.getUnitAmount());
    }
    return pathLength > 0;
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
   * getter for levelToUnit HashMap. Used in checker
   */
  @Override
  public HashMap<Integer, Integer> getArmyHashMap(){
    return this.levelToUnitAmount;
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
