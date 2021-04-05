package edu.duke.ece651.risc.shared;

public class V2MoveOrderRuleChecker<T> extends OrderRuleChecker<T> {

  /*
   * This is the Constructor
   */
  public V2MoveOrderRuleChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  /*
   * This will check if the Source Territory has the enough army.
   */
  public String checkMyRule(int playerId, Order<T> order, Board<T> board) {

    // Get the needed Information from the parameter.
    int srcTerritoryId = order.getSrcTerritory();
    int destTerritoryId = order.getDestTerritory();
    int pathLength = board.findMinPathDistance(srcTerritoryId, destTerritoryId, playerId);

    if(pathLength == Integer.MAX_VALUE){
      return "There is no path from " + board.getTerritories().get(srcTerritoryId).getName() + " to " + board.getTerritories().get(destTerritoryId).getName();
    }
    return null;
  }

  /********************************
   * New method used in evolution 2
   ********************************/

  /**
   * Implementation which is suitable for evo 1, but this method should not be
   * used in evo 1.
   * 
   * Provide this for LSP satisfaction
   */
  @Override
  protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
    return checkMyRule(playerId, order, gs.getGameBoard());
  }
}