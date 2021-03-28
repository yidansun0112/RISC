package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GUIPlayerEntityTest {

  /**
   * Test the methods including getFoodResource, consumeFoodResource,
   * getTechResource, consumeTechResource, getTechLevel.
   */
  @Test
  public void test_getter_and_consume_methods() {
    GUIPlayerEntity<String> gp = new GUIPlayerEntity<String>(null, null, 0, "Red", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    assertEquals(Constant.INIT_FOOD_RESOURCE, gp.getFoodResourceAmount());

    gp.foodResource.addResource(10); // add some resource to consume
    assertEquals(10, gp.getFoodResourceAmount());

    gp.consumeFoodResource(2);
    assertEquals(8, gp.getFoodResourceAmount());

    gp.consumeFoodResource(5);
    assertEquals(3, gp.getFoodResourceAmount());

    gp.techResource.addResource(30); // add some resource to consume
    assertEquals(30, gp.getTechResourceAmount());

    gp.consumeTechResource(2);
    assertEquals(28, gp.getTechResourceAmount());

    assertEquals(Constant.INIT_MAX_TECH_LEVEL, gp.getTechLevel());
  }

  /**
   * Test the tryUpgradeTechLevel and canUpTechLevel methods.
   */
  @Test
  public void test_upgrade_tech_methods() {
    GUIPlayerEntity<String> gp = new GUIPlayerEntity<String>(null, null, 0, "Red", 0,
        Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
            
    assertThrows(IllegalStateException.class, () -> gp.upgradeTechLevel());
    
    for (int i = 1; i < Constant.TOTAL_LEVELS; i++) {
      gp.setNeedUpTechLv();
      assertEquals(true, gp.getNeedUpTechLv());

      gp.upgradeTechLevel();
      assertEquals(i + 1, gp.getTechLevel());
      assertEquals(false, gp.getNeedUpTechLv());
    }

    // now this player has reached the hights allowed tech level -
    // Constant.TOTAL_LEVELS
    assertThrows(IllegalStateException.class, () -> gp.upgradeTechLevel());
  }

  @Test
  public void test_harvestAllResource() {
    // TODO: when finished V2 territory and GUIPlayerEntity.harvestAllResource(),
    // back here and test this method!
  }
}
