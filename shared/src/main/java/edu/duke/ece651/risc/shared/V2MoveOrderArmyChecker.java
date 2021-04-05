package edu.duke.ece651.risc.shared;

import java.util.HashMap;

public class V2MoveOrderArmyChecker<T> extends OrderRuleChecker<T>{

    public V2MoveOrderArmyChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        // TODO Auto-generated method stub
        HashMap<Integer, Integer> toMove = order.getArmyHashMap();
        Territory<T> srcTerr = board.getTerritories().get(order.getSrcTerritory());
        //Territory<T> destTerr = board.getTerritories().get(order.getDestTerritory()); 
        for(int i : toMove.keySet()){
            if(srcTerr.getCurrDefenderArmy().get(0).getUnitAmtByLevel(i) < toMove.get(i)){
                return "Sorry, the level " + i + " army is not enough to move";
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
