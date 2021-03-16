package edu.duke.ece651.risc.shared;

public class MoveOrderEffectChecker<T> extends OrderRuleChecker<T> {

  /*
   * This is the Constructor
   */
  public MoveOrderEffectChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  /*
   * This will check if the Source Territory has the enough army.
   */
  public String checkMyRule(int playerId, Order<T> order, Board<T> board) {

    // Get the needed Information from the parameter.
    Territory<T> src = board.getTerritories().get(order.getSrcTerritory());
    int srcNum = src.getUnitAmount();

    int requiredNum = order.getUnitAmount();

    // Compare and return the result.
    if (requiredNum > srcNum) {
      return "Sorry, the source territory have " + srcNum + " units while we need " + requiredNum + " units.\n";
    }
    return null;
  }
}
