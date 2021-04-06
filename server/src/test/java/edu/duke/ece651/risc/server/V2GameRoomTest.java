package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GUIPlayerEntity;
import edu.duke.ece651.risc.shared.PlayerEntity;

public class V2GameRoomTest {
  @Test
  public void test_V2GameRoomBasic() {
    GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    GUIPlayerEntity<String> p1 = new GUIPlayerEntity<String>(null, null, 1, "kc", 1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    GUIPlayerEntity<String> p2 = new GUIPlayerEntity<String>(null, null, 2, "kc2", 2, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    V2GameRoom room = new V2GameRoom(0, p0);
    assertEquals(0, room.getRoomId());
    room.addPlayer(p1);
    room.addPlayer(p2);
    room.setRoomStatus(Constant.ROOM_STATUS_WAITING_PLAYERS);
    assertEquals(Constant.ROOM_STATUS_WAITING_PLAYERS, room.getRoomStatus());
    try{
      room.chooseMap();
    }catch(Exception e){
      room.updateAllPlayer();
    }
    p2.setPlayerStatus(Constant.SELF_WIN_STATUS);
    assertEquals(2, room.getWinnerId());
  }

}
