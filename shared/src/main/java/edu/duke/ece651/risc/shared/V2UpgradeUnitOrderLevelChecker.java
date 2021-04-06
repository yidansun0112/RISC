package edu.duke.ece651.risc.shared;

public class V2UpgradeUnitOrderLevelChecker<T> extends OrderRuleChecker<T> {

  public V2UpgradeUnitOrderLevelChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
    V2UpgradeUnitOrder<T> thisOrder = ((V2UpgradeUnitOrder<T>) order);
    if (thisOrder.levelFrom >= thisOrder.levelTo) {
      return "Sorry, the new level must be greater than the current level.";
    }
    return null;
  }

  @Override
  protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
    return checkMyRule(playerId, order, gs.getGameBoard());
  }
  
}
