package edu.duke.ece651.risc.shared;

public class V2UpgradeUnitOrderUnitsChecker<T> extends OrderRuleChecker<T> {
    
    public V2UpgradeUnitOrderUnitsChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        int levelFrom = ((V2UpgradeUnitOrder<T>) order).levelFrom;
        int howMany = ((V2UpgradeUnitOrder<T>) order).howMany;
        int unitsAlreadyHave = board.getTerritories().get(order.getSrcTerritory()).getCurrDefenderArmy().get(0)
                .getUnitAmtByLevel(levelFrom);
        if (unitsAlreadyHave < howMany) {
            return "Sorry, You don't have enough basic unit which you would like to update.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
}
