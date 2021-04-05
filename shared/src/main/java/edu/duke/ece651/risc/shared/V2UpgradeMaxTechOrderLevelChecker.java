package edu.duke.ece651.risc.shared;

public class V2UpgradeMaxTechOrderLevelChecker<T> extends OrderRuleChecker<T> {
    
    private PlayerEntity<T> player;
    public V2UpgradeMaxTechOrderLevelChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        int techLevel = player.getTechLevel();
        // That means the Max_Level is 6
        if(techLevel >= Constant.TOTAL_LEVELS){
            return "Sorry, You have already on the Max Tech level, cannot upgrade anymore.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        this.player = gs.getCurrPlayer();
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
}
