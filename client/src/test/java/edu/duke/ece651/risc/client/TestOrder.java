package edu.duke.ece651.risc.client;

import java.io.Serializable;

/**
 * This class is only for testing, and cannot be used in real game code.
 */
public class TestOrder implements Serializable {
  /**
   * required by Serializable
   */
  private static final long serialVersionUID = 1L;

  int intA;
  int intB;
  int intC;

  /**
   * Constructor that initializes the class fields with corresponding parameters.
   * 
   * @param intA value for intA
   * @param intB value for intB
   * @param intC value for intC
   */
  public TestOrder(int intA, int intB, int intC) {
    this.intA = intA;
    this.intB = intB;
    this.intC = intC;
  }

  /**
   * 
   * @return
   */
  @Override
  public String toString() {
    return "(" + intA + ", " + intB + ", " + intC + ")";
  }
}
