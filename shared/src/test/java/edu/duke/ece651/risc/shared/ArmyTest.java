package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArmyTest {
  @Test
  public void test_army() {
    Army<String> a = new Army<String>(0,2);
    a.minusUnit(1);
    assertEquals(a.getUnits(),1);
  }
}
