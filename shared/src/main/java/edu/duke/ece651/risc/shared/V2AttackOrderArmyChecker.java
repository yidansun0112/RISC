package edu.duke.ece651.risc.shared;

import java.util.HashMap;

public class V2AttackOrderArmyChecker<T> extends OrderRuleChecker<T> {
    
    public V2AttackOrderArmyChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        // TODO Auto-generated method stub
        HashMap<Integer, Integer> armyMap = order.getArmyHashMap();
        // Get the SrcTerritory
        Territory<T> srcTerritory = board.getTerritories().get(order.getSrcTerritory());
        // If Any Level army is not enough.
        for(int i : armyMap.keySet()){
            if(srcTerritory.getCurrDefenderArmy().get(0).getUnitAmtByLevel(i) < armyMap.get(i)){
                return "Sorry, the level " + i + " army is not enough to attack";
            }
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        // TODO Auto-generated method stub
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
}
