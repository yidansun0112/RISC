package edu.duke.ece651.risc.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Initial Author: zl246
 * 
 * Comment:
 * 
 * 1. The V2Army contains a field units, which is inherited from its super class, and 
 * this field is deprecated in evo2. In evo2, this field will always be -1.
 * 
 * 2. Some methods are new in V2Army, and are added in Army class. On the other side, 
 * some mehods are inherited from Army. To satisfy the LSP in both Army and V2Army, I 
 * modified the related methods in both classes, as well as their Javadoc.
 * 
 * 3. On general, the methods inherited from Army are deprecated, and we recommend to use 
 * the new method in V2Army to manipulate the units, e.g., getUnitAmtByLevel, addUnit and 
 * removeUnit. These methods are also added into Army, for the purpose that other code can 
 * see these new methods.
 */

/**
 * This is a class used in evo2 to represent an army, which contains different
 * amount of units at defferent levels.
 */
public class V2Army<T> extends Army<T> {
  /**
   * The key is unit level, the value is an arraylist that contains all the units
   * at this level.
   */
  Map<Integer, List<Unit>> troop; // in evo2, the actual implementation is an ArrayList as value

  /**
   * Constructor for V2Army
   * 
   * @param commanderId the player's id which this army belongs to
   */
  public V2Army(int commanderId) {
    super(commanderId, -1); // call the constructor of super class, but initialize the field
                            // units with -1, since this field will not be used in evo2.
    troop = new HashMap<Integer, List<Unit>>();
  }

  /**
   * This method will return the amount of units at the specified level this army
   * contains.
   * 
   * For example, an army has 4 level-2 units, and call this method with parameter
   * 2, it will return 4.
   * 
   * @param level the level of the units
   * @return the amount of units at this tech level in this army
   */
  @Override
  public int getUnitAmtByLevel(int level) {
    return troop.getOrDefault(level, new ArrayList<Unit>()).size();
  }

  /**
   * This method is used to add some units at a specific level to this army. Same
   * as in evo1, this method can only be called with a valid parameter (which
   * checked by the corresponding rule checkers).
   * 
   * @param level the level of the units which to be added
   * @param amt   the amount of units of this level to add
   */
  @Override
  public void addUnit(int level, int amt) {
    // TODO: we might want to add some validation-checking here, but we can also do
    // this in the rule checker.
    // When we reach that point, we can decide then.
    List<Unit> toAdd = troop.getOrDefault(level, new ArrayList<Unit>());
    for (int i = 0; i < amt; i++) {
      toAdd.add(new Unit());
    }
    troop.put(level, toAdd);
  }

  /**
   * This method is uesd to remove a specific amount of units which all at a
   * specific level out of the army. Same as in evo1, this method can only be
   * called with a valid parameter (which checked by the corresponding rule
   * checkers).
   * 
   * @param level the level of the units to be removed
   * @param amt   the amount of the units to be removed
   */
  @Override
  public void removeUnit(int level, int amt) {
    // We cannot remove units on a level that the army does not contain.
    // we want to fail fast, so here we use get() method rather than getOrDefault().
    // For more info, please refer to the java official doc
    // https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html#getOrDefault-java.lang.Object-V-
    
    // TODO: we might want to add some validation-checking here, but we can also do
    // this in the rule checker.
    // When we reach that point, we can decide then.
    List<Unit> toRemove = troop.get(level);
    for (int i = 0; i < amt; i++) {
      toRemove.remove(toRemove.size() - 1);
    }
  }

  /**
   * This method can return the amount of the level 0 units in this army.
   * 
   * This method is to satifsy liskov substitution, to compatible with the method
   * in its super class which is used in evo1.
   * 
   * Note that this method is DEPRECATED in evo2, we recomend to use addUnit() and
   * removeUnit().
   */
  @Override
  public int getBasicUnits() {
    // We might call this method on a empty army and/or an army that does not
    // contain any level 0 units, in both situations, the amount of basic (i.e.,
    // level 0) units is 0, so we use getOrDefault() here.
    return getUnitAmtByLevel(0);
  }
  
  /**
   * This method will set the level 0 units to the specified amount.
   * 
   * This method is to satifsy liskov substitution, to compatible with the method
   * in its super class which is used in evo1.
   * 
   * Note that this method is DEPRECATED in evo2, we recomend to use addUnit() and
   * removeUnit().
   * 
   * @param units the amount of level 0 units to be set to
   */
  @Override
  public void setBasicUnits(int units) {
    // We might call this method on an empty army, so we need to call getOrDefault()
    // rather than get() to avoid NullPointerException
    int basicUnitAmt = getUnitAmtByLevel(0);
    removeUnit(0, basicUnitAmt);
    addUnit(0, units);
  }

  /**
   * This method will remove specific amount of level 0 units from the army. The
   * amount is given by the parameter.
   * 
   * This method is to satifsy liskov substitution, to compatible with the method
   * in its super class which is used in evo1.
   * 
   * Note that this method is DEPRECATED in evo2, we recomend to use addUnit() and
   * removeUnit().
   * 
   * @param amount the amount of level 0 units to remove
   */
  @Override
  public void minusBasicUnit(int amount) {
    removeUnit(0, amount);
  }

  /**
   * This method will calculate the amount of units in all levels.
   */
  @Override
  public int getTotalUnitAmount(){
    int amount=0;
    for(int i=0;i<=Constant.TOTAL_LEVELS;i++){
      amount+=getUnitAmtByLevel(i);
    }
    return amount;
  }

  /**
   * This method will return the max level of units in this army.
   */
  @Override
  public int getMaxUnitLevel(){
    int maxLevel=0;
    for(int i=0;i<=Constant.TOTAL_LEVELS;i++){
      if(getUnitAmtByLevel(i)>0){
        maxLevel=i;
      }
    }
    return maxLevel;
  }

  /**
   * This method will return the min level of units in this army.
   */
  @Override
  public int getMinUnitLevel(){
    int minLevel=0;
    for(int i=0;i<=Constant.TOTAL_LEVELS;i++){
      if(getUnitAmtByLevel(i)>0){
        minLevel=i;
        break;
      }
    }
    return minLevel;
  }
}
