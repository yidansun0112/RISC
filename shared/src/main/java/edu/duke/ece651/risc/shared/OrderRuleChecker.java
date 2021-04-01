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
  protected abstract String checkMyRule(int playerId, Order<T> order, Board<T> board);

  /*
   * This Function will be called every time to execute the check Chain.
   */
  public String checkOrder(int playerId, Order<T> order, Board<T> board) {
    String message = checkMyRule(playerId, order, board);
    if (message != null) {
      return message;
    }
    if (next != null) {
      return next.checkOrder(playerId, order, board);
    }
    return message;
  }

  /********************************************************************************
   *                       New methods in evolution 2
   * 
   * Note that we remain the evo 1 version of these method to avoid changing to
   * much of evo 1 code. And since we can add new info into GameStatus in evo 3 if
   * needed, the price to refactoring this class in evo 3 (if necessary) would be
   * low. -- this is also the reason to avoid primitive obesession.
   *******************************************************************************/

  /**
   * Check a specific rule for a type of order
   * 
   * @param playerId the player id who issue this order
   * @param order    the order object to check
   * @param gs       the GameStatus object which contains the neccesary info to
   *                 check the order
   * @return {@code null} if the order passed all rule checking; otherwise return
   *         a string of why the order failed the checking.
   */
  protected abstract String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs);

  /**
   * Check all rules for a type of order, start checking from the current rule
   * checker on the head of checker chain, in a tail recursion manner.
   * 
   * @since evolution 2
   * 
   * @param playerId the player id who issue this order
   * @param order    the order object to check
   * @param gs       the GameStatus object which contains the neccesary info to
   *                 check the order
   * @return {@code null} if the order passed all rule checking; otherwise return
   *         a string of the rule that this order first failed with.
   */
  public String checkOrder(int playerId, Order<T> order, GameStatus<T> gs) {
    String message = checkMyRule(playerId, order, gs);
    if (message != null) {
      return message;
    }
    if (next != null) {
      return next.checkOrder(playerId, order, gs);
    }
    return message;
  }
}
