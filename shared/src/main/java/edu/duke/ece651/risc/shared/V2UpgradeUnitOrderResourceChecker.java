package edu.duke.ece651.risc.shared;

public class V2UpgradeUnitOrderResourceChecker<T> extends OrderRuleChecker<T> {
    
    private PlayerEntity<T> player;
    public V2UpgradeUnitOrderResourceChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        // TODO Auto-generated method stub
        int levelFrom = ((V2UpgradeUnitOrder)order).levelFrom;
        int howMany = ((V2UpgradeUnitOrder)order).howMany;
        int resource = this.player.getTechResourceAmount();
        int require = Constant.UP_UNIT_COST.get(levelFrom);
        if(require * howMany > resource){
            return "Sorry, you don't have enough Tech resouce to update your units.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        // TODO Auto-generated method stub
        this.player = gs.getCurrPlayer();
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
}
