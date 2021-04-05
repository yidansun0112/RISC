package edu.duke.ece651.risc.shared;

public class V2UpgradeMaxTechOrderLevelChecker<T> extends OrderRuleChecker<T> {
    
    private PlayerEntity<T> player;
    public V2UpgradeMaxTechOrderLevelChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        // TODO Auto-generated method stub
        int techLevel = player.getTechLevel();
        // That means the Max Max_Level is 6
        if(techLevel >= Constant.TOTAL_LEVELS){
            return "Sorry, You have been to the Max Max_level.";
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
