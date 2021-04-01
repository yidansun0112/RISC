package edu.duke.ece651.risc.shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class MoveOrderPathChecker<T> extends OrderRuleChecker<T> {
  public MoveOrderPathChecker(OrderRuleChecker<T> next) {
    super(next);
  }

  /*
   * This Funtion will use BFS to search for the valid Path.
   */

  @Override
  public String checkMyRule(int playerId, Order<T> order, Board<T> board) {
    ArrayList<Territory<T>> territories = board.getTerritories();
    Queue<Territory<T>> queue = new LinkedList<>();

    Territory<T> src = territories.get(order.getSrcTerritory());
    queue.offer(src);

    Territory<T> dest = territories.get(order.getDestTerritory());
    HashSet<Integer> visit = new HashSet<>();
    while (!queue.isEmpty()) {
      Territory<T> curr = queue.poll();

      // If it is the destination. For example, move in the same territory. So it is
      // necessary.
      if (dest.getId() == curr.getId()) {
        return null;
      }

      // Avoid visit a territory multiple times.
      visit.add(curr.getId());
      Vector<Integer> neigh = curr.getNeigh();

      // Iterate over the neighbor of the current territory.
      for (int i : neigh) {
        // If We have got to the Destination.
        if (i == dest.getId()) {
          return null;
        }
        if (territories.get(i).getOwner() == playerId && !visit.contains(i)) {
          queue.offer(territories.get(i));
        }

        /*
         * // Continue to Search Territory<T> next = territories.get(i); // Check if the
         * territory belongs to this order. // Not! Then We continue if (next.getOwner()
         * != playerId) { continue; }
         * 
         * // Yes, we can pass this territory. Add the neighbor to the queue.
         * Vector<Integer> nextNeigh = next.getNeigh(); for (int j : nextNeigh) { if
         * (territories.get(j).getOwner() == playerId && !visit.contains(j)) {
         * queue.offer(territories.get(j)); } }
         */
      }

    }

    return "Sorry, there is no path from " + src.getName() + " to " + dest.getName() + ".\n";
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
