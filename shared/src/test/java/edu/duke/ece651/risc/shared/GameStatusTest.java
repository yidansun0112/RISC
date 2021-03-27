package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GameStatusTest {
  @Test
  public void test_constructor_and_getters() {
    PlayerEntity<String> p0 = new TextPlayerEntity<String>(null, null, 1, "Red", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    PlayerEntity<String> p1 = new TextPlayerEntity<String>(null, null, 2, "Azure", 1,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    BoardFactory<String> f = new V1BoardFactory<>();
    Board<String> b = f.makeGameBoard(2);

    List<PlayerEntity<String>> allPlayers = new ArrayList<>();
    allPlayers.add(p0);
    allPlayers.add(p1);

    GameStatus<String> s = new GameStatus<String>(allPlayers, b);

    assertSame(allPlayers, s.getAllPlayers());
    assertSame(b, s.getGameBoard());
  }
}
