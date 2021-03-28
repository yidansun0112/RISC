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
    int srcNum = src.getBasicDefendUnitAmount();

    int requiredNum = order.getUnitAmount();

    // Compare and return the result.
    if (requiredNum > srcNum || requiredNum < 0) {
      return "Sorry, the source territory have " + srcNum + " units while we need " + requiredNum + " units.\n";
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
