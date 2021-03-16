package edu.duke.ece651.risc.shared;

public class MoveOrderConsistencyChecker<T> extends OrderRuleChecker<T> {

  public MoveOrderConsistencyChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  /*
   * Check it If The Source Territory have the enough army
   */
  @Override
  protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
    
    Territory<T> territory_src = board.getTerritories().get(order.getSrcTerritory());
    Territory<T> territory_dest = board.getTerritories().get(order.getDestTerritory());

    if (territory_src.getOwner() != playerId) {
      return "The Source Destination doesn't belong to this player.\n";
    }
    if (territory_dest.getOwner() != playerId) {
      return "The Source Destination doesn't belong to this player.\n";
    }
    return null;
  }

}






