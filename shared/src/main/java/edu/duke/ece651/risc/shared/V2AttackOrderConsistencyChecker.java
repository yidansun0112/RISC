package edu.duke.ece651.risc.shared;

public class V2AttackOrderConsistencyChecker<T> extends OrderRuleChecker<T>{

    public V2AttackOrderConsistencyChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        // TODO Auto-generated method stub
        int srcTerritoryId = order.getSrcTerritory();
        int destTerritoryId = order.getDestTerritory();
        Territory<T> srcTerritory = board.getTerritories().get(srcTerritoryId);
        Territory<T> destTerritory = board.getTerritories().get(destTerritoryId);
        if(srcTerritory.getOwner() == destTerritory.getOwner()){
            return "Sorry, you are not allowed to attack your self.";
        }
        if(srcTerritory.getOwner() != playerId){
            return "Sorry, you must attack from your own territory.";
        }
        int[][] map = board.getWorldMap();
        if(map[srcTerritoryId][destTerritoryId] == 0){
            return "Sorry, the two territory is not adjacent.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        // TODO Auto-generated method stub
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
    
    
}
