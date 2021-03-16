package edu.duke.ece651.risc.shared;

public class AttackOrderEffectChecker<T> extends OrderRuleChecker<T> {

  public AttackOrderEffectChecker(OrderRuleChecker<T> next){
       super(next);
  }
	@Override
	protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
	  Territory<T> srcTerri = board.getTerritories().get(order.getSrcTerritory());
    int amount = order.getUnitAmount();
    if(srcTerri.getUnitAmount()< amount){
      return "invalid order: Units on your territory is not enough\n";
    }
    return null;
	}

}












