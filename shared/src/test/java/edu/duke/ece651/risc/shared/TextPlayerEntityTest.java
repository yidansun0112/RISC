package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TextPlayerEntityTest {

  /**
   * Mocked ObjectInputStream that used in the constructor of TextPlayerEntity
   */
  @Mock
  ObjectInputStream oisMock;

  /**
   * Mocked ObjectInputStream that used in testing setter of TextPlayerEntity
   */
  @Mock
  ObjectInputStream oisAnotherMock;

  /**
   * Mocked ObjectOutputStream that used in the constructor of TextPlayerEntity
   */
  @Mock
  ObjectOutputStream oosMock;

  /**
   * Mocked ObjectOutputStream that used in testing setter of TextPlayerEntity
   */
  @Mock
  ObjectOutputStream oosAnthoerMock;

  @Mock
  Socket sockMock;

  /** Some values that used in testing */
  int playerId = -1;
  int anotherPlayerId = -1;

  String playerSymbol = "BasicPlayer";
  String anotherPlayerSymbol = "AlsoBasicPlayer";

  int ownedGroup = -1;
  int anotherOwnedGroup = 0;

  int playerStatus = Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS;
  int anotherPlayerStatus = Constant.SELF_LOSE_NO_ONE_WIN_STATUS;

  /**
   * Test the constructors, getters and setters
   * 
   * @throws IOException
   */
  @Test
  public void test_TextPlayerEntity_constructor_getter_setters() throws IOException {
    MockitoAnnotations.initMocks(this);

    TextPlayerEntity<String> tpe = new TextPlayerEntity<String>(oosMock, oisMock, playerId, playerSymbol, ownedGroup, playerStatus);

    // Test getters and setters

    assertEquals(playerId, tpe.getPlayerId());
    tpe.setPlayerId(anotherPlayerId);
    assertEquals(anotherPlayerId, tpe.getPlayerId());
    // in evo1 we generally don't need to call this method, bu we still need to test
    // it.
    assertEquals(playerSymbol, tpe.getPlayerSymbol());
    tpe.setPlayerSymbol(anotherPlayerSymbol);
    assertEquals(anotherPlayerSymbol, tpe.getPlayerSymbol());

    assertEquals(ownedGroup, tpe.getOwnedGroup());
    tpe.setOwnedGroup(anotherOwnedGroup);
    assertEquals(anotherOwnedGroup, tpe.getOwnedGroup());

    assertEquals(playerStatus, tpe.getPlayerStatus());
    tpe.setPlayerStatus(anotherPlayerStatus);
    assertEquals(anotherPlayerStatus, tpe.getPlayerStatus());

    assertSame(oisMock, tpe.getFromPlayer());
    assertSame(oosMock, tpe.getToPlayer());

    tpe.setFromPlayer(oisAnotherMock);
    tpe.setToPlayer(oosAnthoerMock);
    assertSame(oisAnotherMock, tpe.getFromPlayer());
    assertSame(oosAnthoerMock, tpe.getToPlayer());
  }

  @Test
  /**
   * Test sendObject() method and receiveObject() method
   * 
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws InterruptedException
   */
  public void test_sendObject_receiveObject() throws ClassNotFoundException, IOException, InterruptedException {

    // Start a new TestLoopBackServer in a separate thread
    Thread th = make_test_server_thread_helper();
    th.start();
    Thread.sleep(100); // let the *current* thead wait for a while to let the server setup
                       // this is a bit of hack

    Socket socket = new Socket("localhost", 3301);

    TextPlayerEntity<String> tpe = new TextPlayerEntity<String>(new ObjectOutputStream(socket.getOutputStream()),
        new ObjectInputStream(socket.getInputStream()), playerId, playerSymbol, ownedGroup, playerStatus);

    String msg = new String("Hi there!"); // a string used in testing, it will "send" by the mocked oosMock object

    // First we need to inform test loop back server how many msg we want to send
    // and receive
    tpe.sendObject(Integer.valueOf(1));

    tpe.sendObject(msg);
    String receivedMsg = (String) tpe.receiveObject();
    // Thought they are actually the same String constant in JVM, but in real
    // environment the server and client may be
    // on different computer, so here we need call assertEquals() rather than
    // assertSame()
    assertEquals(msg, receivedMsg);

    th.join();
    socket.close();
  }

  /**
   * Helper method that creates a new thread to run the test loopback server.
   * 
   * @return a Thread object that the server is running on
   */
  private Thread make_test_server_thread_helper() {
    Thread th = new Thread() {
      @Override
      public void run() {
        try {
          TestLoopBackServer.main(new String[0]);
        } catch (Exception e) {
        }
      }
    };
    return th;
  }

  /**
   * Testing the dummy implementation methods that are acutally used in evo2 but
   * presented in evo1 code
   */
  @Test
  public void test_dummy_methods() {
    TextPlayerEntity<String> p = new TextPlayerEntity<String>(null, null, 0, "Red", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

      p.harvestAllResource(); // this method has no actual action, no need to assert here

      assertNull(p.getFoodResource());

      p.consumeFoodResource(0); // this method has no actual action, no need to assert here

      assertNull(p.getTechResource());

      p.consumeTechResource(0);

      assertEquals(0, p.getTechLevel());

      p.upgradeTechLevel();
      // assertEquals(false, p.canUpTechLevel());

      p.setNeedUpTechLv();
      assertEquals(false, p.getNeedUpTechLv());
  }
}
