package edu.duke.ece651.risc.shared;

public class V2AttackOrderFoodChecker<T> extends OrderRuleChecker<T> {
    
    public int foodResource;
    public V2AttackOrderFoodChecker(OrderRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, Board<T> board) {
        if(order.getUnitAmount() > this.foodResource){
            return "Sorry, you don't have enough food to attack.";
        }
        return null;
    }

    @Override
    protected String checkMyRule(int playerId, Order<T> order, GameStatus<T> gs) {
        this.foodResource = gs.getCurrPlayer().getFoodResourceAmount();
        return checkMyRule(playerId, order, gs.getGameBoard());
    }
}
