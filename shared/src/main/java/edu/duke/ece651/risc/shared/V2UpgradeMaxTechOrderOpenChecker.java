package edu.duke.ece651.risc.shared;

public class V2UpgradeMaxTechOrderOpenChecker<T> extends OrderRuleChecker<T> {

    private PlayerEntity<T> player;
    public V2UpgradeMaxTechOrderOpenChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        // TODO Auto-generated method stub
        if(this.player.getNeedUpTechLv()){
            return "Sorry, you have updated your Max Tech Level in this round.";
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
