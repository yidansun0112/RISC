package edu.duke.ece651.risc.shared;

/**
 * This class is used to represent various kinds of resources that a player can
 * have in a game. In evolution 2, there are two kinds of resources, the food
 * resource and the technology resource.
 * 
 * To avoid primitive obsession, I created a class to wrap two kinds of
 * resources, to make the code more flexible. By this I mean, think what if we
 * have R tech resource, SR tech resource and SSR tech resource in evo3?
 * 
 * @since evolution2
 * @author zl246
 */
public class Resource {
  int resourceAmt;

  /**
   * Constructor that initialize field with corresponding parameter.
   * 
   * @param resourceAmt
   */
  public Resource(int resourceAmt) {
    this.resourceAmt = resourceAmt;
  }

  /**
   * @return the resourceAmt
   */
  public int getResourceAmt() {
    return resourceAmt;
  }

  /**
   * @param resourceAmt the resourceAmt to set
   */
  public void setResourceAmt(int resourceAmt) {
    this.resourceAmt = resourceAmt;
  }

  /**
   * Add some amount of resource
   * 
   * @param amt the amonut of resource to add
   */
  public void addResource(int amt) {
    this.resourceAmt += amt;
  }

  /**
   * Consume (remove) some amount of resource
   * 
   * @param amt the amount of resource to consume
   */
  public void consumeResource(int amt) {
    this.resourceAmt -= amt;
  }
}
