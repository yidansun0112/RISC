package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RISCUserTest {
  @Test
  public void test_RISCUser() {
    RISCUser r = new RISCUser("kc", "k");
    assertEquals("kc", r.getUserName());
    assertEquals("k", r.getPassword());
    r.setUserName("k");
    r.setPassword("kc");
    assertEquals("k", r.getUserName());
    assertEquals("kc", r.getPassword());
  }

}
