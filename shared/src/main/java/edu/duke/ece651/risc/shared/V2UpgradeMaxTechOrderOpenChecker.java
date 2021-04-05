package edu.duke.ece651.risc.shared;

public class V2UpgradeMaxTechOrderOpenChecker<T> extends OrderRuleChecker<T> {

  private PlayerEntity<T> player;

  public V2UpgradeMaxTechOrderOpenChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
    if (this.player.getNeedUpTechLv()) {
      return "Sorry, you have already issued Upgrade Max Tech Level order in this round.";
    }
    return null;
  }

  @Override
  protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
    this.player = gs.getCurrPlayer();
    return checkMyRule(playerId, order, gs.getGameBoard());
  }

}
