package edu.duke.ece651.risc.shared;
import java.util.*;
public class V1GameBoard extends Board<String>{
  ArrayList<Territory<String>> territories;
  ArrayList<ArrayList<Integer>> worldMap;
  public V1GameBoard(ArrayList<Territory<String>> territories, ArrayList<ArrayList<Integer>> worldMap){
    super(territories, worldMap);
   }

   /**
    * This function is used to display the name of the territories and all of its neighbors   
    */   
  protected String whatisInTerritory(String name,int territoryId, Vector<Integer> neigh){
     StringBuilder s = new StringBuilder();
     s.append(name+" ("+territoryId+") ");
     s.append("(next to:");
     String temp = " ";
     for(Integer n : neigh){
       Territory<String> neighTerritory = territories.get(n);
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
	@Override
	public String whatIsIn(Territory<String> territory, boolean isSelf) {
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
	public boolean occupyTerritory(int groupNum, int owner) {
      boolean ifOccupy = true;
      for (int i = groupNum * 3; i < groupNum * 3 + 3; i++) {
        Territory<String> t = territories.get(i);
        if (t.getOwner() == -1) {
          ifOccupy = false;
          t.setOwner(owner);
        }
      }
		return ifOccupy;
	}

	@Override
	public void addUnits(int territoryId, int amount, int playerId) {
    Territory<String> territory = territories.get(territoryId);
    int owner = territory.getOwner();
    if(playerId == owner){
      int curr = territory.getUnitAmount();
      territory.setUnitAmount(amount);
    }else{
      territory.setEnemyUnitAmount()
    }
	}

	@Override
	public void removeUnits(int territory, int amount) {
		// TODO Auto-generated method stub
		
	}

}












