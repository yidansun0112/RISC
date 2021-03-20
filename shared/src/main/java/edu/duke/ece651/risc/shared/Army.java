package edu.duke.ece651.risc.shared;

public class Army<T> {
  private int commanderId;
  private int units;

  /**
   * default constructor for Army, init commanderId and units to -1
   */
  public Army() {
    commanderId = -1;
    units = -1;
  }

  /**
   * Contructor for Army, initilized by parameters passed in
   */
  public Army(int commanderId, int units) {
    this.commanderId = commanderId;
    this.units = units;
  }

  /**
   * Getter for commanderId
   * 
   * @return commanderId
   */
  public int getCommanderId() {
    return commanderId;
  }

  /**
   * Getter for units
   * 
   * @return units
   */
  public int getUnits() {
    return units;
  }

  /**
   * Setter for units
   * 
   * @param units is used to assign units
   */
  public void setUnits(int units) {
    this.units = units;
  }

  /**
   * Setter for commanderId
   * 
   * @param units is used to assign commanderId
   */
  public void setCommanderId(int owner) {
    this.commanderId = owner;
  }

  /**
   * Minus units for an army
   * 
   * @param amout is the units to be minused from the units
   */
  public void minusUnit(int amout) {
    this.units = this.units - amout;
  }
}
