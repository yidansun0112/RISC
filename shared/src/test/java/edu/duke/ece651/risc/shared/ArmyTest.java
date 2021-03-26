package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArmyTest {
  @Test
  public void test_army() {
    Army<String> a = new Army<String>(0, 2);
    a.minusBasicUnit(1);
    assertEquals(a.getBasicUnits(), 1);
    assertEquals(1, a.getTotalUnitAmount());
    assertEquals(0, a.getMaxUnitLevel());
    assertEquals(0, a.getMinUnitLevel());
  }
}
