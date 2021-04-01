package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertSame;

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

    GameStatus<String> s0 = new GameStatus<String>(p0, b);
    GameStatus<String> s1 = new GameStatus<String>(p1, b);

    assertSame(p0, s0.getCurrPlayer());
    assertSame(p1, s1.getCurrPlayer());
    assertSame(b, s0.getGameBoard());
    assertSame(b, s1.getGameBoard());
  }
}
