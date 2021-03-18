package edu.duke.ece651.risc.shared;

public class AttackOrderConsistencyChecker<T> extends OrderRuleChecker<T> {
  /**
   * Contructor for AttackOrderConsistencyChecker, given the next checker
   * @param next is the next OrderRuleChecker 
   */
  public AttackOrderConsistencyChecker(OrderRuleChecker<T> next){
       super(next);
  }
  /**
   * Check if the src territory belongs to the player and dest territory not belongs to the player
   * @param playerId is the player to be checked
   * @param order is an Order to be checked
   * @param board is the Board the order to be executed on
   * @retune NULL if src territory belongs to the player and dest territory not belongs to the player
   *         error message if the order didn't pass the checker
   */
	@Override
	protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
    int src = order.getSrcTerritory();
    int dest = order.getDestTerritory();
    if(!checkOwner(playerId, src,board)){
        return "Order is invalid: source territory you pick doesn't belong to you\n";
    }
    if(checkOwner(playerId, dest,board)){
        return "Order is invalid: destination territory you pick shouldn't belong to you\n";
    }
      return null;
   }
  
   /**
    * Check if the territory belongs to a certain player
    * @param is the playerId of the player
    * @param terriId is the territory id of the territory to be checked
    * @param board is the board the territory is on
    */
  protected boolean checkOwner(int playerId, int terriId, Board<T> board){
    Territory<T> t = board.getTerritories().get(terriId);
    int owner = t.getOwner();
    if(owner == playerId){
      return true;
    }
    return false;
  }
}



