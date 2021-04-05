package edu.duke.ece651.risc.shared;

public class V2UpgradeMaxTechOrderResourceChecker<T> extends OrderRuleChecker<T> {
    
    private PlayerEntity<T> player;
    public V2UpgradeMaxTechOrderResourceChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        int techLevel = player.getTechLevel();
        int techResource = player.getTechResourceAmount();
        int require = Constant.UP_TECH_LEVEL_COST.get(techLevel);
        if(techResource < require){
            return "Sorry, You don't have enough technology resource to update your Max Tech Level.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        this.player = gs.getCurrPlayer();
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
}
