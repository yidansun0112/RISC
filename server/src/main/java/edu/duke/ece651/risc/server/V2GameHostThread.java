package edu.duke.ece651.risc.server;

import java.util.concurrent.CyclicBarrier;

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.OrderRuleChecker;
import edu.duke.ece651.risc.shared.PlayerEntity;

/**
 * This class is used to serve each player in a game room in evolution 2. It is
 * not a Is-A relationship with V1GameHostThread, so it does not extend
 * V1GameHostThread.
 */
public class V2GameHostThread<T> extends Thread {
  /** The player to serve with */
  PlayerEntity<T> player;
  /** Remained units for this player */
  int remainedUnits;
  /** Board to player with */
  Board<T> board;
  /** The GameStatus instance hold by each V2GameHostThread */
  GameStatus<T> gameStatus;
  
  /** OrderRuleChecker to check move order */
  OrderRuleChecker<T> moveChecker;
  /** OrderRuleChecker to check attack order */
  OrderRuleChecker<T> attackChecker;
  /** rule checker for upgrade unit order */
  OrderRuleChecker<T> upgradeUnitChecker;
  /** rule checker for upgrade max technology level order */
  OrderRuleChecker<T> upgradeTechLevelChecker;

  /** CycinBarrier to help synchronize each thread */
  CyclicBarrier barrier;

  /**
   * @param player
   * @param remainedUnits
   * @param board
   * @param moveChecker
   * @param attackChecker
   * @param upgradeUnitChecker
   * @param upgradeTechLevelChecker
   * @param barrier
   */
  public V2GameHostThread(PlayerEntity<T> player, int remainedUnits, Board<T> board, OrderRuleChecker<T> moveChecker,
      OrderRuleChecker<T> attackChecker, OrderRuleChecker<T> upgradeUnitChecker,
      OrderRuleChecker<T> upgradeTechLevelChecker, CyclicBarrier barrier) {
    this.player = player;
    this.remainedUnits = remainedUnits;
    this.board = board;
    this.moveChecker = moveChecker;
    this.attackChecker = attackChecker;
    this.upgradeUnitChecker = upgradeUnitChecker;
    this.upgradeTechLevelChecker = upgradeTechLevelChecker;
    this.barrier = barrier;
  }

  
}
