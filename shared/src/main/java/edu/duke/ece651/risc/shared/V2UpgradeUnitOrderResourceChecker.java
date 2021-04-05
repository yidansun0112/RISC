package edu.duke.ece651.risc.shared;

public class V2UpgradeUnitOrderResourceChecker<T> extends OrderRuleChecker<T> {

    private PlayerEntity<T> player;

    public V2UpgradeUnitOrderResourceChecker(OrderRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        V2UpgradeUnitOrder<T> thisOrder = ((V2UpgradeUnitOrder<T>) order);
        int levelFrom = thisOrder.levelFrom;
        int levelTo = thisOrder.levelTo;
        int howMany = ((V2UpgradeUnitOrder<T>) order).howMany;
        int requiredResource = (Constant.UP_UNIT_COST.get(levelTo) - Constant.UP_UNIT_COST.get(levelFrom)) * howMany;

        int resourceCurrentHave = this.player.getTechResourceAmount();
        if (requiredResource > resourceCurrentHave) {
            return "Sorry, you don't have enough Tech resouce to update your units.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        this.player = gs.getCurrPlayer();
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
}
