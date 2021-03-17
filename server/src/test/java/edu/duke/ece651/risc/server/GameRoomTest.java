package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.risc.shared.Constant;

public class GameRoomTest {
  @Test
  public void test_addPlayer_setPlayerNum_getPlayerNum() {
    GameRoom<String> room = new GameRoom<String>();
    assertEquals(0, room.getPlayerNum());
    room.addPlayer(
        new TextPlayerEntity<String>(null, null, 0, "BasicPlayer", -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS));

    room.addPlayer(
      new TextPlayerEntity<String>(null, null, 0, "BasicPlayer", -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS));

    
  }
}
