package edu.duke.ece651.risc.shared;

public abstract class OrderRuleChecker<T> {
  private final OrderRuleChecker<T> next;

  /*
   * @Constructor to construct the RuleChecker and @Param checker is the next
   * checker in the Chain.
   */
  public OrderRuleChecker(OrderRuleChecker<T> checker) {
    this.next = checker;
  }

  /*
   * This function will be overwritten by others to realize other checker.
   */
  public abstract String checkMyRule(int playerId, T order, Board<T> board);

  /*
   * This Function will be called every time to execute the check Chain.
   */
  public String checkOrder(int playerId, T order, Board<T> board) {
    String message = checkMyRule(playerId, order, board);
    if (message != null) {
      return message;
    }
    if (next != null) {
      return next.checkOrder(playerId, order, board);
    }
    return message;
  }
}
