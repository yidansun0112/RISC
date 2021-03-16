package edu.duke.ece651.risc.shared;

public class AttackOrderConsistencyChecker<T> extends OrderRuleChecker<T> {

  public AttackOrderConsistencyChecker(OrderRuleChecker<T> next){
       super(next);
  }
	@Override
	protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
    int src = order.getSrcTerritory();
    int dest = order.getDestTerritory();
    if(!checkOwner(playerId, src,board)){
        return "Order is invalid: source territory you pick doesn't belong to you\n";
    }
    if(!checkOwner(playerId, dest,board)){
        return "Order is invalid: destination territory you pick shouldn't belong to you\n";
    }
      return null;
   }
  
  protected boolean checkOwner(int playerId, int terriId, Board<T> board){
    Territory<T> t = board.getTerritories().get(terriId);
    int owner = t.getOwner();
    if(owner == playerId){
      return true;
    }
    return false;
  }
}



