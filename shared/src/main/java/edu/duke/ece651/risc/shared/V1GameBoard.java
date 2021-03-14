package edu.duke.ece651.risc.shared;
import java.util.*;
public class V1GameBoard<T> extends Board<T>{
  ArrayList<Territory<T>> territories;
  int[][] worldMap;
  public V1GameBoard(ArrayList<Territory<T>> territories, int[][] worldMap){
    super(territories, worldMap);
  }

   /**
    * This function is used to display the name of the territories and all of its neighbors
    * @return String in the format like Narnia(0) (next to: Oz, Mordor, Roshar)  
    */   
  protected String whatisInTerritory(String name,int territoryId, Vector<Integer> neigh){
     StringBuilder s = new StringBuilder();
     s.append(name+"("+territoryId+") ");
     s.append("(next to:");
     String temp = " ";
     for(Integer n : neigh){
       Territory<T> neighTerritory = territories.get(n);
       String neighName = neighTerritory.getName();
       s.append(temp);
       s.append(neighName);
       temp = ", ";
     }
     s.append(")");
     return "";
   }
   
   /**
    * This function is used to display amount of each types of units in the territory 
    * @return String is either in the format like: 10 units in or just a ""   
    */
  protected String unitsInfo(HashMap<String, Integer> infoMap){
    Iterable<String> keySet = infoMap.keySet();
    StringBuilder s = new StringBuilder();
    String temp = "";
    boolean addIn = false;
    for(String key: keySet){
      s.append(temp);
      int amount = infoMap.get(key);
      if(amount != -1){
         s.append(amount + " " + key);
         temp = ",";
         addIn = true;
      }     
    }
    if(addIn){
      s.append(" in ");
    }
    return s.toString();
  }

  /**
   * Display the info of one territory
   * @return String in the format like: 3 units in Roshar (next to: Hogwarts, Scadrial, Elantris) 
   * or Roshar (next to: Hogwarts, Scadrial, Elantris)  
   */  
  @Override
  public String whatIsIn(Territory<T> territory, boolean isSelf) {
    HashMap<String, Integer> infoMap = new HashMap<>();
    infoMap=territory.getDisplayInfo(isSelf);
    Vector<Integer> neigh = new Vector<>();
    neigh = territory.getNeigh();
    String name = territory.getName();
    int territoryId = territory.getId();
    StringBuilder s = new StringBuilder();
    s.append(unitsInfo(infoMap));
    s.append(whatisInTerritory(name, territoryId, neigh));
    s.append("\n");
  	return s.toString();
  }
  
  @Override
  public synchronized boolean  occupyTerritory(int groupNum, int owner) {
      boolean ifOccupy = true;
      for (int i = groupNum * 3; i < groupNum * 3 + 3; i++) {
        Territory<T> t = territories.get(i);
        if (t.getOwner() == -1) {
          ifOccupy = false;
          t.setOwner(owner);
          t.initCurrDefender(owner);
        }
      }
  	return ifOccupy;
  }

  /**
   * Add certain amount of units in a player's territory for a player
   
   */  
  @Override
  public void addOwnUnits(int territoryId, int amount) {
    Territory<T> territory = territories.get(territoryId);
      int curr = territory.getUnitAmount();
      territory.setUnitAmount(amount+curr);
  }

  /**
   * Add an enemy army in a territory 
   
   */ 
  @Override
	public void addEnemyUnits(int territoryId, int amount, int playerId) {
    Territory<T> territory = territories.get(territoryId);
		territory.addEnemy(playerId, amount);
	}
  
  /**
   * remove certain amount of units in one territory for a player
   * can only remove units from his/her owner territory   
   */
  @Override
  public void removeUnits(int territoryId, int amount) {
    Territory<T> territory = territories.get(territoryId);
    int curr = territory.getUnitAmount();
    territory.setUnitAmount(curr-amount);
  	
  }
  
    /**
     * This function is used to update every Territory's previous defender army after one round done    
     */
	@Override
	public void updateAllPrevDefender() {
		for(Territory<T> t: territories){
      t.updatePrevDefender();
    }
	}

	

}












