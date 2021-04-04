package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.DoneOrder;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.Order;
import edu.duke.ece651.risc.shared.OrderRuleChecker;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.V2AttackOrder;
import edu.duke.ece651.risc.shared.V2MoveOrder;
import edu.duke.ece651.risc.shared.V2UpgradeTechLevelOrder;
import edu.duke.ece651.risc.shared.V2UpgradeUnitOrder;

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
   * Constructor that initialze the fields with corresponding parameters.
   * 
   * @param player                  the player entity served by this thread
   * @param remainedUnits           the remained unit that can be deployed
   * @param board                   the game board of this game room
   * @param moveChecker             order rule checker chain head node
   * @param attackChecker           order rule checker chain head node
   * @param upgradeUnitChecker      order rule checker chain head node
   * @param upgradeTechLevelChecker order rule checker chain head node
   * @param barrier                 the barrier that used to sync with other game
   *                                host thread and the game runner thread in the
   *                                game room
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

  /**
   * This method is used to make a latest game status, which can be used in order
   * checking or send the latest game info to the client.
   * 
   * @since evolution 2. This is a new method that evo1 doesn't have.
   * 
   * @return the latest GameStatus instance that contains the current player
   *         entity and the current board (which contains all the latest
   *         territories).
   */
  protected GameStatus<T> makeLatestGameStatus(boolean canShowLatest) {
    return new GameStatus<T>(player, board, canShowLatest);
  }

  /**
   * This method serve to let player pick territory group.
   * 
   * Send GameStatus to player and receive choice from player. Then call
   * occupyterritory. If return false, send success info. if return true, send
   * fail info, and then wait for receive choice again.
   * 
   * @since evolution 2. This method has some lines changed in evo2, which are
   *        marked in the comment in those lines. The rest part of this method is
   *        same with evo1.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void pickTerritory() throws IOException, ClassNotFoundException {
    // NOTE: SEND GameStatus to client - used to show the board when picking
    // territory
    // player.sendObject(makeLatestGameStatus(true)); // this line is different with evo 1
    while (true) {
      // NOTE: RECEIVE String from client - used to try to occupy the specified
      // territory
      String choice = (String) player.receiveObject();
      boolean occupied = board.occupyTerritory(Integer.parseInt(choice), player.getPlayerId());
      if (!occupied) {
        player.sendObject(Constant.VALID_MAP_CHOICE_INFO);
        break;
      } else {
        player.sendObject(Constant.INVALID_MAP_CHOICE_INFO);
      }
      // TODO: add catch to deal with NumberFormatException here? Do this after finish
      // the min system if needed.
    }
    board.updateAllPrevDefender();
  }

  /**
   * This method serves to let player deploy units.
   * 
   * Send GameStauts to player to display self group info in GUI, and send the
   * remained deployable units. receive deployment from player. If legal, send
   * legal info. If illegal, send illegal info Continue send group info and
   * receive unilt no units remained, and then send FINISH_DEPLOY_INFO.
   * 
   * @since evolution 2. This method has some lines changed in evo2, which are
   *        marked in the comment in those lines. The rest part of this method is
   *        same with evo1.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void deployUnits() throws IOException, ClassNotFoundException {
    while (remainedUnits > 0) {
      // String msg = view.displayGroup(player.getPlayerId()) + "You have " +
      // remainedUnits + " left.";
      // player.sendObject(msg);
      // NOTE: SEND GameStatus to client - used to show the self group of territory
      // when deploying units
      player.sendObject(makeLatestGameStatus(false)); // this line is different with evo 1
      // NOTE: SEND int to client - used to show the scentence "You have XXX unit
      // remained" in GUI window
      player.sendObject(remainedUnits); // this line is new in evo 2

      // TODO: add a new class DeployUnitOrder here?
      ArrayList<Integer> deployment = (ArrayList<Integer>) player.receiveObject();
      int territoryId = deployment.get(0);
      int unitAmount = deployment.get(1);
      // TODO: check the negative number at client side! And/Or check whether the
      // following lines of new code will cause stuck!!!!
      if (territoryId < 0 || unitAmount < 0) {
        player.sendObject("Invalid deploy input! Should not be a negative value!");
        continue;
      }
      if (territoryId >= board.getTerritories().size()) {
        player.sendObject("This territory does not exist!");
        continue;
      }
      // check units
      // >0 continue
      if (remainedUnits >= unitAmount) {
        boolean result = board.deployUnits(territoryId, unitAmount, player.getPlayerId());
        if (result) {
          remainedUnits -= unitAmount;
          player.sendObject(Constant.LEGAL_DEPLOY_INFO);
        } else {
          player.sendObject(Constant.NOT_OWNER_INFO);
        }
      } else {
        player.sendObject("You don't have enough units remained!");
      }
    }
    board.updateAllPrevDefender();
    player.sendObject(Constant.FINISH_DEPLOY_INFO);
  }

  /**
   * Similar to the method in evo1, this method handles order receiving from
   * player.
   * 
   * Send the map, receive order from player, check whether legal or not. If
   * legal, execute it and send legal info. If illgal, send illegal info. Repeat
   * unitl receive DoneOrder. Send lastest map after receiving DoneOrder.
   * 
   * @since evolution 2. This method has some lines changed in evo2, which are
   *        marked in the comment in those lines. The rest part of this method is
   *        same with evo1.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void receiveOrder() throws IOException, ClassNotFoundException {
    while (true) {
      // NOTE: SEND GameStatus to client - used to show the board after the player
      // issued an order each time
      GameStatus<T> latestStatus = makeLatestGameStatus(false); // this line is different with evo 1
      player.sendObject(latestStatus);
      Order<T> order = (Order<T>) player.receiveObject();
      String message = null;
      if (order instanceof DoneOrder) {
        player.sendObject(Constant.LEGAL_ORDER_INFO);
        break;
      }
      // The lollowing lines are different with evo 1. Here we have new kinds of
      // orders, and need to use the new checkOrder method in evo 2, which takes in a
      // GameStatus
      else if (order instanceof V2MoveOrder) {
        message = moveChecker.checkOrder(player.getPlayerId(), order, latestStatus);
      } else if (order instanceof V2AttackOrder) {
        message = attackChecker.checkOrder(player.getPlayerId(), order, latestStatus);
      } else if (order instanceof V2UpgradeTechLevelOrder) {
        message = upgradeTechLevelChecker.checkOrder(player.getPlayerId(), order, latestStatus);
      } else if (order instanceof V2UpgradeUnitOrder) {
        message = upgradeUnitChecker.checkOrder(player.getPlayerId(), order, latestStatus);
      }

      if (message == null) {
        order.execute(board);
        player.sendObject(Constant.LEGAL_ORDER_INFO);
      } else {
        player.sendObject(message);
      }
    }
    // NOTE: SEND GameStatus to client - used to show the board after the player
    // issued an order each time
    player.sendObject(makeLatestGameStatus(false)); // this line is different with evo 1
  }

  /**
   * This method checks whether to end this thread.
   * 
   * Currently this method is same with evo 1. But maybe changed in following
   * commits.
   * 
   * If this player does not lose, return to run() to continue play. If this
   * player loses, let the player to do losechoice. If this player wins, send win
   * info and end this thread. If somebody wins, send end info and end this
   * thread.
   * 
   * @return false if continue, true to end.
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws InterruptedException
   * @throws BrokenBarrierException
   */
  public boolean checkWinLose()
      throws IOException, ClassNotFoundException, InterruptedException, BrokenBarrierException {
    switch (player.getPlayerStatus()) {
    case Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS:
      player.sendObject(Constant.NOT_LOSE_INFO);
      return false;
    case Constant.SELF_LOSE_NO_ONE_WIN_STATUS:
      player.sendObject(Constant.LOSE_INFO);
      loseChoice();
      return true;
    case Constant.SELF_WIN_STATUS:
      player.sendObject(Constant.WIN_INFO);
      doEndPhase();
      return true;
    case Constant.SELF_LOSE_OTHER_WIN_STATUS:
      player.sendObject(Constant.GAME_END_INFO);
      doEndPhase();
      return true;
    default:
      return false;
    }
  }

  /**
   * Let the player to choice watch or quit the game.
   * 
   * Receive choice from player and decide to watch or to end.
   * 
   * Currently this method is same with evo 1. But maybe changed in following
   * commits.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws InterruptedException
   * @throws BrokenBarrierException
   */
  public void loseChoice() throws IOException, ClassNotFoundException, InterruptedException, BrokenBarrierException {
    String choice = (String) player.receiveObject();
    switch (choice) {
    case Constant.TO_WATCH_INFO:
      doWatchPhase();
      return;
    case Constant.TO_QUIT_INFO:
      doEndPhase();
      return;
    default:
      return;
    }
  }

  /**
   * Do watch phase for the player.
   * 
   * Send the confirm info at first. Then barrier await twice for each round and
   * send the lastest game map info. If the somebody wins, end the game.
   * 
   * @since evolution 2. This method has some lines changed in evo2, which are
   *        marked in the comment in those lines. The rest part of this method is
   *        same with evo1.
   * 
   * @throws IOException
   * @throws InterruptedException
   * @throws BrokenBarrierException
   * @throws ClassNotFoundException
   */
  public void doWatchPhase() throws IOException, InterruptedException, BrokenBarrierException, ClassNotFoundException {
    player.sendObject(Constant.CONFIRM_INFO);
    while (true) {
      barrier.await();
      barrier.await();
      player.sendObject(makeLatestGameStatus(true)); // this line is different with evo 1
      if (player.getPlayerStatus() == Constant.SELF_LOSE_OTHER_WIN_STATUS) {
        player.sendObject(Constant.GAME_END_INFO);
        break;
      } else {
        player.sendObject(Constant.GAME_CONTINUE_INFO);
      }
    }
    player.receiveObject(); // at the client side the player is informed that the game is end by this
                            // method.
                            // the client will automatically enter the doEndPhase() method,
                            // which will first send a message to here.
                            // so here we need to receive this message, then call doEndPhase() at the last
                            // line.
    doEndPhase();
  }

  /**
   * Do end phase for this thread. Enter this method when the whole game needs to
   * end (current player wins the game, or loses but someone else wins), or a
   * player loses and wants to directly disconnect the game server.
   * 
   * Currently this method is same with evo 1. But maybe changed in following
   * commits.
   * 
   * Send confirm info at first. If the game is going to end, end this thread.
   * Else, barrier await twice and check status in every round.
   * 
   * @throws IOException
   * @throws InterruptedException
   * @throws BrokenBarrierException
   */
  public void doEndPhase() throws IOException, InterruptedException, BrokenBarrierException {
    player.sendObject(Constant.CONFIRM_INFO);
    while (true) {
      int status = player.getPlayerStatus();
      if (status == Constant.SELF_WIN_STATUS || status == Constant.SELF_LOSE_OTHER_WIN_STATUS) {
        break;
      }
      barrier.await();
      barrier.await();
    }
  }

  /**
   * Override method from Thread.
   *
   * Start point of this thread. Let the player to pick territory and deploy units
   * at first. And the repeatly receive order, wait for result and send it to
   * player. Do appropriate action based on player's status.
   * 
   * @since evolution 2. This method has some lines changed in evo2, which are
   *        marked in the comment in those lines. The rest part of this method is
   *        same with evo1.
   */
  @Override
  public void run() {
    try {
      pickTerritory();
      deployUnits();
      // Wait all the player finishing their deployment
      barrier.await();
      while (true) {
        receiveOrder();
        barrier.await(); // waiting for other players done
        barrier.await(); // waiting for the server finish all the battle and other stuff

        // synchronized(System.in){
        // System.out.println(player.playerId + " " + player.playerStatus);
        // }

        // One turn is done, now send the latest game status to player
        player.sendObject(makeLatestGameStatus(true)); // this line is different with evo 1
        if (checkWinLose()) {
          break;
        }
      }
      // Now wait for the GameRoom close the streams hold by PlayerEntity to release
      // resources
      barrier.await();
      // Now the streams are closed, quit the thread alone with the GameRoom
      // barrier.await();

      // synchronized(System.in){
      // System.out.println("Thread for " + player.playerId + " exits, the player
      // status is" + player.playerStatus);
      // }
    } catch (IOException | InterruptedException | BrokenBarrierException | ClassNotFoundException e) {
      return;
    }
  }
}
