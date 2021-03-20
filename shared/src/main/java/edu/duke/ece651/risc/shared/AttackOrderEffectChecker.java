package edu.duke.ece651.risc.shared;

public class AttackOrderEffectChecker<T> extends OrderRuleChecker<T> {
  /**
   * Contructor for AttackOrderEffectChecker, given the next checker
   * 
   * @param next is the next OrderRuleChecker
   */
  public AttackOrderEffectChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  /**
   * Check if the src territory has the enough units to be moved away
   * 
   * @param playerId is the player to be checked
   * @param order    is an Order to be checked
   * @param board    is the Board the order to be executed on
   * @retune NULL if src territory has the enough units to be moved away error
   *         message if the order didn't pass the checker
   */
  @Override
  protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
    Territory<T> srcTerri = board.getTerritories().get(order.getSrcTerritory());
    int amount = order.getUnitAmount();
    if (srcTerri.getUnitAmount() < amount) {
      return "invalid order: Units on your territory is not enough\n";
    }
    return null;
  }

}
