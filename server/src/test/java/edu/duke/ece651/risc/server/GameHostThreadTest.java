package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import edu.duke.ece651.risc.shared.*;

public class GameHostThreadTest {

  @Mock
  private PlayerEntity<String> playerMock;

  private GameHostThread<String> createGameHostThread(int num) {
    BoardFactory<String> factory = new V1BoardFactory<String>();
    Board<String> board = factory.makeGameBoard(num);
    board.updateAllPrevDefender();
    BoardView<String> view = new BoardTextView(board);
    OrderRuleChecker<String> moveChecker = new MoveOrderConsistencyChecker<String>(
        new MoveOrderPathChecker<String>(new MoveOrderEffectChecker<String>(null)));
    OrderRuleChecker<String> attackChecker = new AttackOrderConsistencyChecker<String>(
        new AttackOrderPathChecker<String>(new AttackOrderEffectChecker<String>(null)));
    CyclicBarrier barrier = new CyclicBarrier(num + 1);
    return new GameHostThread<String>(playerMock, Constant.TOTAL_UNITS, board, view, moveChecker, attackChecker,
        barrier);
  }

  @Test
  public void test_pickTerritory() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    when(playerMock.getPlayerId()).thenReturn(0);
    when(playerMock.receiveObject()).thenReturn("4", "0");
    GameHostThread<String> gameThread = createGameHostThread(2);
    gameThread.pickTerritory();
  }

  @Test
  public void test_deployUnits() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    when(playerMock.getPlayerId()).thenReturn(0);
    ArrayList<Integer> d1 = createArrayList(3, 5);
    ArrayList<Integer> d2 = createArrayList(0, 20);
    ArrayList<Integer> d3 = createArrayList(0, 15);
    when(playerMock.receiveObject()).thenReturn("0", d1, d2, d3);
    GameHostThread<String> gameThread = createGameHostThread(2);
    gameThread.pickTerritory();
    gameThread.deployUnits();
  }

  private ArrayList<Integer> createArrayList(int a, int b) {
    ArrayList<Integer> array = new ArrayList<Integer>();
    array.add(a);
    array.add(b);
    return array;
  }

  @Test
  public void test_receiveOrderUnits() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    when(playerMock.getPlayerId()).thenReturn(0);
    ArrayList<Integer> deploy = createArrayList(0, 15);
    Order<String> illegalMoveOrder = new MoveOrder<String>("1 0 5");
    Order<String> legalMoveOrder = new MoveOrder<String>("0 1 5");
    Order<String> attackOrder = new AttackOrder<String>("0 3 5");
    Order<String> doneOrder = new DoneOrder<String>();
    when(playerMock.receiveObject()).thenReturn("0", deploy, illegalMoveOrder, legalMoveOrder, attackOrder, doneOrder);
    GameHostThread<String> gameThread = createGameHostThread(2);
    gameThread.pickTerritory();
    gameThread.deployUnits();
    gameThread.receiveOrder();
  }

  @Test
  public void test_checkWinLose()
      throws IOException, ClassNotFoundException, InterruptedException, BrokenBarrierException {
    MockitoAnnotations.initMocks(this);
    when(playerMock.getPlayerStatus()).thenReturn(Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS, 5);
    GameHostThread<String> gameThread = createGameHostThread(2);
    assertEquals(false, gameThread.checkWinLose());
    assertEquals(false, gameThread.checkWinLose());
  }

  // /**
  // * Helper method that creates a new thread to run the test loopback server.
  // * @return a Thread object that the server is running on
  // */
  // private Thread make_test_host_thread_helper(CyclicBarrier barrier) {
  // Thread th = new Thread() {
  // @Override
  // public void run() {
  // try {
  // barrier.await();
  // for(int i=0;i<3;i++){
  // barrier.await();
  // barrier.await();
  // }
  // barrier.await();
  // } catch (Exception e) {
  // }
  // }
  // };
  // return th;
  // }

  // /**
  // * Helper method that creates a new thread to run the test loopback server.
  // * @return a Thread object that the server is running on
  // */
  // private Thread make_test_room_thread_helper(CyclicBarrier barrier) {
  // Thread th = new Thread() {
  // @Override
  // public void run() {
  // try {
  // barrier.await();
  // for(int i=0;i<3;i++){
  // barrier.await();
  // barrier.await();
  // }
  // barrier.await();
  // } catch (Exception e) {
  // }
  // }
  // };
  // return th;
  // }
}
