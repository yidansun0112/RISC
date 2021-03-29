package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConstantTest {
  /**
   * Test whether the UP_UNIT_COST map setup with correct key-values
   */
  @Test
  public void test_UP_UNIT_COST() {
    assertEquals(0, Constant.UP_UNIT_COST.get(0));
    assertEquals(3, Constant.UP_UNIT_COST.get(1));
    assertEquals(11, Constant.UP_UNIT_COST.get(2));
    assertEquals(30, Constant.UP_UNIT_COST.get(3));
    assertEquals(55, Constant.UP_UNIT_COST.get(4));
    assertEquals(90, Constant.UP_UNIT_COST.get(5));
    assertEquals(140, Constant.UP_UNIT_COST.get(6));
  }
}
