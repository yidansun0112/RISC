package edu.duke.ece651.risc.shared;

public class V2MoveOrderFoodChecker<T> extends OrderRuleChecker<T>{
    
    private int foodResource;
    public V2MoveOrderFoodChecker(OrderRuleChecker<T> next){
        super(next);
        foodResource = -1;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        // TODO Auto-generated method stub
        int srcTerritoryId = order.getSrcTerritory();
        int destTerritoryId = order.getDestTerritory();  
        int pathLength = board.findMinPathDistance(srcTerritoryId, destTerritoryId, playerId);
        if(pathLength * order.getUnitAmount() > this.foodResource ){
            return "Sorry, you don't have enough food resource to move your army.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        // TODO Auto-generated method stub
        foodResource = gs.getCurrPlayer().getFoodResourceAmount();
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
    
}
