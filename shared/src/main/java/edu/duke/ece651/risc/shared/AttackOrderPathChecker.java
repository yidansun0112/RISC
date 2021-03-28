package edu.duke.ece651.risc.shared;

import java.util.*;

public class AttackOrderPathChecker<T> extends OrderRuleChecker<T> {
  /**
   * Contructor for AttackOrderPathChecker, given the next checker
   * 
   * @param next is the next OrderRuleChecker
   */
  public AttackOrderPathChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  /**
   * Check if the dest territory is the neighbor of src territory
   * 
   * @param playerId is the player to be checked
   * @param order    is an Order to be checked
   * @param board    is the Board the order to be executed on
   * @retune NULL if the dest territory is the neighbor of src territory error
   *         message if the order didn't pass the checker
   */
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
