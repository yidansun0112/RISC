package edu.duke.ece651.risc.shared;

public class V2MoveOrderConsistencyChecker<T> extends OrderRuleChecker<T> {

  public V2MoveOrderConsistencyChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  /**
   * Check it If The Source Territory have the enough army
   */
  @Override
  protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {

    Territory<T> territory_src = board.getTerritories().get(order.getSrcTerritory());
    Territory<T> territory_dest = board.getTerritories().get(order.getDestTerritory());

    if (order.getSrcTerritory() == order.getDestTerritory()) {
      return "You cannot move to the same place.\n";
    }
    if (territory_src.getOwner() != playerId) {
      return "The source territory doesn't belong to this player.\n";
    }
    if (territory_dest.getOwner() != playerId) {
      return "The destination territory doesn't belong to this player.\n";
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