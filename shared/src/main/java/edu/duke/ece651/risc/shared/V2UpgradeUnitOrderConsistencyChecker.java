package edu.duke.ece651.risc.shared;

public class V2UpgradeUnitOrderConsistencyChecker<T> extends OrderRuleChecker<T> {
    
    public V2UpgradeUnitOrderConsistencyChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        Territory<T> srcTerritory = board.getTerritories().get(order.getSrcTerritory());
        if(srcTerritory.getOwner() != playerId){
            return "Sorry, You are not allowed to Update other's Unit.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
    
}
