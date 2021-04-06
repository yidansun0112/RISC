package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GUIPlayerEntity;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.TextPlayerEntity;

public class GameRoomTest {
  @Mock
  TextPlayerEntity<String> player1;
  TextPlayerEntity<String> player2;

  @Test
  public void test_addPlayer_setPlayerNum_getPlayerNum() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    GameRoom<String> room = new TextRoom();
    assertEquals(0, room.getPlayerNum());
    room.addPlayer(player1);
    room.addPlayer(player2);
    /**
     * room.addPlayer( new TextPlayerEntity<String>(null, null, 0, "BasicPlayer",
     * -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS));
     * 
     * room.addPlayer( new TextPlayerEntity<String>(null, null, 0, "BasicPlayer",
     * -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS));
     **/
    room.setPlayerNum(2);
    when(player1.receiveObject()).thenReturn("1");
    room.chooseMap();
    room.incrementUnits();
    // room.updatePlayerStatus();
    // assertEquals(room.checkEnd(),false);
    // room.closeAllStreams();
  }

  @Test
  public void test_getPlayerByName_hasPlayer() {
    String name0 = "lzy";
    String name1 = "yui";
    String name2 = "yoi";
    String name3 = "yuki";
    PlayerEntity<String> roomCreator = new GUIPlayerEntity<String>(null, null, 0, name0, 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    PlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 1, name1, 1,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    PlayerEntity<String> p1 = new GUIPlayerEntity<String>(null, null, 2, name2, 2,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    PlayerEntity<String> p2 = new GUIPlayerEntity<String>(null, null, 3, name3, 3,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    GameRoom<String> room = new V2GameRoom(0, roomCreator);
    room.players.add(p0);
    room.players.add(p1);
    room.players.add(p2);

    // test getPlayerByName
    assertSame(roomCreator, room.getPlayerByName(name0));
    assertSame(p0, room.getPlayerByName(name1));
    assertSame(p1, room.getPlayerByName(name2));
    assertSame(p2, room.getPlayerByName(name3));
    assertNull(room.getPlayerByName("rosmontis"));

    // test hasPlayer
    assertEquals(true, room.hasPlayer(name0));
    assertEquals(true, room.hasPlayer(name1));
    assertEquals(true, room.hasPlayer(name2));
    assertEquals(true, room.hasPlayer(name3));
    assertEquals(false,room.hasPlayer("rosmontis"));
  }

  @Test
  public void test_methods_since_evo2() {
    
  }
}
