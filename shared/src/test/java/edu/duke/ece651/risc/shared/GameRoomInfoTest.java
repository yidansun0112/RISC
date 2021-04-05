package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GameRoomInfoTest {
  @Test
  public void test_GameRoomInfo() {
    GameRoomInfo gi = new GameRoomInfo(0, 2, "kk");
    assertEquals(gi.getRoomId(),0);
    assertEquals(gi.getTotalPlayers(),2);
    assertEquals(gi.getRoomOwnerName(),"kk");
    
  }
}











