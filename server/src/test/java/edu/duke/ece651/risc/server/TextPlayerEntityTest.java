package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.duke.ece651.risc.shared.Constant;

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

  /** Some values that used in testing  */
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

    TextPlayerEntity tpe = new TextPlayerEntity(oosMock, oisMock, playerId, playerSymbol, ownedGroup, playerStatus);

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

  // @Test
  // public void test_receiveObject() throws ClassNotFoundException, IOException {
  //   MockitoAnnotations.initMocks(this);
  //   String msg = new String("Hi there!"); // a string used in testing, it will "send" by the mocked oosMock object
  //   when(oisMock.readObject()).thenReturn(msg);

  //   TextPlayerEntity tpe = new TextPlayerEntity(oosMock, oisMock, playerId, playerSymbol, ownedGroup, playerStatus);
  //   String receivedMsg = (String) tpe.receiveObject();
  //   // Thought they are actually the same String constant in JVM, but in real environment the server and client may be
  //   // on different computer, so here we need call assertEquals() rather than assertSame()
  //   assertEquals(msg, receivedMsg);
  // }

}
