package edu.duke.ece651.risc.shared;
import java.util.*;
public class AttackOrderPathChecker<T> extends OrderRuleChecker<T> {

  public AttackOrderPathChecker(OrderRuleChecker<T> next){
       super(next);
  }
	@Override
	protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
		Territory<T> srcTerri = board.getTerritories().get(order.getSrcTerritory());
    int dest = order.getDestTerritory();
    Vector<Integer> neigh = srcTerri.getNeigh();
    if (!neigh.contains(dest)) {
      return "invalid order: You can only attack an enemy territory that is next to you!\n";
    }
		return null;
	}

}










