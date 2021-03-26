package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class V2ArmyTest {
  /**
   * Test the constructor of class V2Army
   */
  @Test
  public void test_constructor() {
    int commanderId = 2;
    V2Army<String> am = new V2Army<>(commanderId);
    assertEquals(commanderId, am.getCommanderId());
    assertEquals(0, am.getBasicUnits()); // this is an empty army, does not contain any units of any tech level, so the
                                         // expected value here is 0
  }

  /**
   * Test the addUnit method
   */
  @Test
  public void test_addUnit() {
    int commanderId = 2;
    int lv0UnitAmt = 3;
    int lv1UnitAmt = 2;
    int lv2UnitAmt = 1;
    V2Army<String> am = new V2Army<>(commanderId);
    am.addUnit(0, lv0UnitAmt);
    am.addUnit(1, lv1UnitAmt);
    am.addUnit(2, lv2UnitAmt);

    assertEquals(lv0UnitAmt, am.troop.get(0).size());
    assertEquals(lv1UnitAmt, am.troop.get(1).size());
    assertEquals(lv2UnitAmt, am.troop.get(2).size());

    // We also need to be careful of the key-value that the map should not contain
    // at this time
    assertEquals(false, am.troop.containsKey(3));
    assertEquals(false, am.troop.containsKey(4));
    assertEquals(false, am.troop.containsKey(5));
    assertEquals(false, am.troop.containsKey(6));

    // Boundary value analysis (BVA)
    am.addUnit(3, 0);
    assertEquals(0, am.troop.get(3).size());
  }

  /**
   * Test the removeUnit method
   */
  @Test
  public void test_removeUnit() {

    // Here I do not test the invalid case since we will ensure that when we call
    // this method, the parameter MUST be valid.

    int commanderId = 2;
    V2Army<String> am = new V2Army<>(commanderId);
    am.addUnit(0, 3);
    am.addUnit(1, 2);
    am.addUnit(0, 2); // now am has 5 lv0 untis, 2 lv1 units

    // Ensure we successfull added the unit
    assertEquals(5, am.troop.get(0).size());
    assertEquals(2, am.troop.get(1).size());

    // Now remove some units at some level
    am.removeUnit(0, 4); // now am should have 1 lv0 untis, 2 lv1 units
    assertEquals(1, am.troop.get(0).size());

    am.removeUnit(1, 2); // now am should have 1 lv0 untis, 0 lv1 units
    assertEquals(0, am.troop.get(1).size());

    am.addUnit(0, 1); // now am should have 2 lv0 untis, 2 lv1 units
    am.removeUnit(0, 2); // now am should have 0 lv0 untis, 2 lv1 units
    assertEquals(0, am.troop.get(0).size());
  }

  /**
   * Test the getUnitAmtByLevel method
   */
  @Test
  public void test_getUnitAmtByLevel() {
    int commanderId = 2;
    V2Army<String> am = new V2Army<>(commanderId);
    am.addUnit(0, 1);
    am.addUnit(1, 2);
    am.addUnit(2, 3);
    assertEquals(1, am.getUnitAmtByLevel(0));
    assertEquals(2, am.getUnitAmtByLevel(1));
    assertEquals(3, am.getUnitAmtByLevel(2));

    // We need to be careful about other levels
    assertEquals(0, am.getUnitAmtByLevel(3));
    assertEquals(0, am.getUnitAmtByLevel(4));

    am.addUnit(4, 5);
    assertEquals(5, am.getUnitAmtByLevel(4));
    // We need to ensure that at this time level 0, 1, 2 are un-affected
    assertEquals(1, am.getUnitAmtByLevel(0));
    assertEquals(2, am.getUnitAmtByLevel(1));
    assertEquals(3, am.getUnitAmtByLevel(2));
  }

  /**
   * Test the getBasicUnits method. We deprecated it, but since it is new code in
   * evo2, we still need to test it.
   * 
   * All the purpose here is to lower the possibility of a bug as much as
   * possible.
   */
  @Test
  public void test_getBasicUnits() {
    int commanderId = 2;
    V2Army<String> am = new V2Army<>(commanderId);
    am.addUnit(0, 3);
    assertEquals(3, am.getBasicUnits());

    am.addUnit(1, 6);
    assertEquals(3, am.getBasicUnits());

    am.removeUnit(0, 1);
    assertEquals(2, am.getBasicUnits());

    am.removeUnit(1, 1);
    assertEquals(2, am.getBasicUnits());
  }

  /**
   * Test the setBasicUnits method. We deprecated it, but since it is new code in
   * evo2, we still need to test it.
   * 
   * All the purpose here is to lower the possibility of a bug as much as
   * possible.
   */
  @Test
  public void test_setBasicUnits() {
    int commanderId = 2;
    V2Army<String> am = new V2Army<>(commanderId);
    assertEquals(0, am.getBasicUnits());

    am.setBasicUnits(4);
    assertEquals(4, am.getBasicUnits());
    assertEquals(4, am.getUnitAmtByLevel(0));

    am.addUnit(2, 3);
    assertEquals(4, am.getBasicUnits());
    assertEquals(4, am.getUnitAmtByLevel(0));

    am.setBasicUnits(0);
    assertEquals(0, am.getBasicUnits());
    assertEquals(0, am.getUnitAmtByLevel(0));
  }

  /**
   * Test the minusBasicUnits method. We deprecated it, but since it is new code
   * in evo2, we still need to test it.
   * 
   * All the purpose here is to lower the possibility of a bug as much as
   * possible.
   */
  @Test
  public void test_minusBasicUnit() {
    int commanderId = 2;
    V2Army<String> am = new V2Army<>(commanderId);
    am.setBasicUnits(5);
    assertEquals(5, am.getUnitAmtByLevel(0));

    am.minusBasicUnit(3);
    assertEquals(2, am.getUnitAmtByLevel(0));

    am.minusBasicUnit(2);
    assertEquals(0, am.getUnitAmtByLevel(0));

    am.addUnit(3, 4);
    assertEquals(0, am.troop.get(0).size());
  }
}
