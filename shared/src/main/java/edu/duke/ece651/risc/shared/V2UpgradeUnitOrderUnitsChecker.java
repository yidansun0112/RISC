package edu.duke.ece651.risc.shared;

public class V2UpgradeUnitOrderUnitsChecker<T> extends OrderRuleChecker<T> {
    
    public V2UpgradeUnitOrderUnitsChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        // TODO Auto-generated method stub
        int levelFrom = ((V2UpgradeUnitOrder)order).levelFrom;
        int howMany = ((V2UpgradeUnitOrder)order).howMany;
        int armyLevelFrom = board.getTerritories().get(order.getSrcTerritory()).getCurrDefenderArmy().get(0).getUnitAmtByLevel(levelFrom);
        if(armyLevelFrom > howMany){
            return "Sorry, You don't have enough basic unit which you would like to update.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        // TODO Auto-generated method stub
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
}
