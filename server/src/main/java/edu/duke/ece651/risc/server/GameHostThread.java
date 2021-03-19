package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.shared.*;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import java.io.IOException;
import java.lang.Thread;

/**
 * This class handles one thread in the game room.
 * 
 * It handles for each player to pick territory, deploy units, and receive orders from player.
 * When a player wins, it prompts to ask the player to watch or quit the game.
 * This thread will end when somebody wins.
 * 
 * @param T String for Version 1.
 */
public class GameHostThread<T> extends Thread {

  /** The player to serve with */
  private PlayerEntity<T> player;
  /** Remained units for this player */
  private int remainedUnits;
  /** Board to player with */
  private Board<T> board;
  /** BoardView to help display the board for the player */
  private BoardView<T> view;
  /** OrderRuleChecker to check move order */
  private OrderRuleChecker<T> moveChecker;
  /** OrderRuleChecker to check attack order */
  private OrderRuleChecker<T> attackChecker;
  /** CycinBarrier to help synchronize each thread */
  private CyclicBarrier barrier;

  /**
   * Constructor for GameHostThread
   * 
   * @param PlayerEntity<T> player
   * @param int units
   * @param Board<T> board
   * @param BoardView<T> view
   * @param OrderRuleChecker<T> moveChecker
   * @param OrderRuleChecker<T> attackChecker
   * @param CyclicBarrier barrier
   */
  public GameHostThread(PlayerEntity<T> player, int units, Board<T> board, BoardView<T> view,
      OrderRuleChecker<T> moveChecker, OrderRuleChecker<T> attackChecker, CyclicBarrier barrier) {
    this.player = player;
    this.remainedUnits = units;
    this.board = board;
    this.view = view;
    this.moveChecker = moveChecker;
    this.attackChecker = attackChecker;
    this.barrier = barrier;
  }

  /**
   * This method serve to let player pick territory group.
   * 
   * Send board view to player and receive choice from player.
   * Then call occupyterritory.
   * If return false, send success info.
   * if return true, send fail info, and then wait for receive choice again.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void pickTerritory() throws IOException, ClassNotFoundException {
    player.sendObject(view.displayFullBoard());
    while (true) {
      String choice = (String) player.receiveObject();
      // TODO: occupy should be synchronized
      boolean occupied = board.occupyTerritory(Integer.parseInt(choice), player.getPlayerId());
      if (!occupied) {
        player.sendObject(Constant.VALID_MAP_CHOICE_INFO);
        break;
      } else {
        player.sendObject(Constant.INVALID_MAP_CHOICE_INFO);
      }
    }
    board.updateAllPrevDefender();
  }

  /**
   * This method serves to let player deploy units.
   * 
   * Send self group info to player, receive deployment from player.
   * If legal, send legal info.
   * If illegal, send illegal info
   * Continue send group info and receive unilt no units remained, and then send FINISH_DEPLOY_INFO.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void deployUnits() throws IOException, ClassNotFoundException {
    while (remainedUnits > 0) {
      String msg = view.displayGroup(player.getPlayerId()) + "You have " + remainedUnits + " left.";
      player.sendObject(msg);
      ArrayList<Integer> deployment = (ArrayList<Integer>) player.receiveObject();
      int territoryId = deployment.get(0);
      int unitAmount = deployment.get(1);
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
    player.sendObject(Constant.FINISH_DEPLOY_INFO);
  }

  /**
   * This method handles order receiving from player.
   * 
   * Send the map, receive order from player, check whether legal or not.
   * If legal, execute it and send legal info.
   * If illgal, send illegal info.
   * Repeat unitl receive DoneOrder.
   * Send lastest map after receiving DoneOrder.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void receiveOrder() throws IOException, ClassNotFoundException {
    while (true) {
      player.sendObject(view.displayBoardFor(player.getPlayerId()));
      Order<T> order = (Order<T>) player.receiveObject();
      String message = null;
      if (order instanceof DoneOrder) {
        player.sendObject(Constant.LEGAL_ORDER_INFO);
        break;
      } else if (order instanceof MoveOrder) {
        message = moveChecker.checkOrder(player.getPlayerId(), order, board);
      } else if (order instanceof AttackOrder) {
        message = attackChecker.checkOrder(player.getPlayerId(), order, board);
      }
      if (message == null) {
        order.execute(board);
        player.sendObject(Constant.LEGAL_ORDER_INFO);
      } else {
        player.sendObject(message);
      }
    }
    player.sendObject(view.displayBoardFor(player.getPlayerId()));
  }

  /**
   * This method checks whether to end this thread.
   * 
   * If this player does not lose, return to run() to continue play.
   * If this player loses, let the player to do losechoice.
   * If this player wins, send win info and end this thread.
   * If somebody wins, send end info and end this thread.
   * @return false if continue, true to end.
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws InterruptedException
   * @throws BrokenBarrierException
   */
  public boolean checkWinLose() throws IOException, ClassNotFoundException, InterruptedException, BrokenBarrierException {
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
   * Send the confirm info at first.
   * Then barrier await twice for each round and send the lastest game map info.
   * If the somebody wins, end the game.
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
      player.sendObject(view.displayFullBoard());
      if (player.getPlayerStatus() == Constant.SELF_LOSE_OTHER_WIN_STATUS) {
        player.sendObject(Constant.GAME_END_INFO);
        break;
      } else {
        player.sendObject(Constant.GAME_CONTINUE_INFO);
      }
    }
    player.receiveObject(); // at the client side the player is informed that the game is end by this
                            // method.
                            // the client will automatically enter the V1GamePlayer.doEndPhase() method,
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
   * Start point of this thread.
   * Let the player to pick territory and deploy units at first.
   * And the repeatly receive order, wait for result and send it to player.
   * Do appropriate action based on player's status.
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
        barrier.await();
        barrier.await();

        synchronized(System.in){
          System.out.println(player.playerId + " " + player.playerStatus);
        }
        
        player.sendObject(view.displayFullBoard());
        if (checkWinLose()) {
          break;
        }
      }
      // Now wait for the GameRoom close the streams hold by PlayerEntity to release resources
      barrier.await();
      // Now the streams are closed, quit the thread alone with the GameRoom
      // barrier.await();
    } catch (IOException e) {
      return;
    } catch (InterruptedException e) {
      return;
    } catch (BrokenBarrierException e) {
      return;
    } catch (ClassNotFoundException e) {
      return;
    }
  }
}
