package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ResourceTest {
  @Test
  public void test_resource_all_methods() {
    Resource r = new Resource(5);
    assertEquals(5, r.getResourceAmt());

    r.addResource(3);
    assertEquals(8, r.getResourceAmt());

    r.consumeResource(1);
    assertEquals(7, r.getResourceAmt());

    r.setResourceAmt(0);
    assertEquals(0, r.getResourceAmt());
  }
}
