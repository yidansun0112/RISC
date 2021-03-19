package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameRoomTest {
  @Mock
  TextPlayerEntity<String> player1;
  TextPlayerEntity<String> player2;
  @Test
  public void test_addPlayer_setPlayerNum_getPlayerNum() throws IOException, ClassNotFoundException{
    MockitoAnnotations.initMocks(this);
    GameRoom<String> room = new TextRoom();
    assertEquals(0, room.getPlayerNum());
    room.addPlayer(player1);
    room.addPlayer(player2);
    /**room.addPlayer(
        new TextPlayerEntity<String>(null, null, 0, "BasicPlayer", -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS));

    room.addPlayer(
      new TextPlayerEntity<String>(null, null, 0, "BasicPlayer", -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS));
    **/
    room.setPlayerNum(2);
    when(player1.receiveObject()).thenReturn("1");
    room.chooseMap();
    room.incrementUnits();
    //room.updatePlayerStatus();
    //assertEquals(room.checkEnd(),false);
    //room.closeAllStreams();
  }
  

}










