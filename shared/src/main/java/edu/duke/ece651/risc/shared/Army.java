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
   * Setter for commanderId
   * 
   * @param units is used to assign commanderId
   */
  public void setCommanderId(int owner) {
    this.commanderId = owner;
  }

  /**
   * This method will only return the amount of "basic" units in this army. Add
   * this method for LSP satisfacation as well as other code can see this method.
   * 
   * @param level not used in evo1
   * @return the amount of units in this army
   */
  public int getUnitAmtByLevel(int level) {
    return this.units;
  }

  /**
   * This method will add some units to the army
   * 
   * @param level unused in evo1
   * @param amt   the amount of units to be added
   */
  public void addUnit(int level, int amt) {
    this.units += amt;
  }

  /**
   * This method will remove some units from the army
   * 
   * @param level unused in evo1
   * @param amt   the amount of units to remove
   */
  public void removeUnit(int level, int amt) {
    this.units -= amt;
  }

  /**
   * Get the number of (basic) units in this army.
   * 
   * Note: change the doc of this method to satisfy LSP
   * 
   * @return units
   */
  public int getBasicUnits() {
    return getUnitAmtByLevel(0);
  }

  /**
   * Set the units to the amount specific by the parameter.
   * 
   * In evo1, units do not have a attribute of level, to satisfy the Liskov
   * Substitution Principle, we regard all units are "basic units" with level 0.
   * 
   * @param units is used to assign units
   */
  public void setBasicUnits(int units) {
    this.units = 0;
    addUnit(0, units);
  }

  /**
   * Minus units for an army.
   * 
   * In evo1, units do not have a attribute of level, to satisfy the Liskov
   * Substitution Principle, we regard all units are "basic units" with level 0.
   * 
   * @param amount is the units to be minused from the units
   */
  public void minusBasicUnit(int amount) {
    // this.units = this.units - amount;
    removeUnit(0, amount);
  }
}
