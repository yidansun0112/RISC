package edu.duke.ece651.risc.server;

import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.Territory;
import edu.duke.ece651.risc.shared.V1BoardFactory;
import static org.junit.jupiter.api.Assertions.*;
public class BattleResolverTest {
  @Test
  public void test_battleresolver() {
    BoardFactory<String> f = new V1BoardFactory();
    Board<String> b = f.makeGameBoard(3);
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);
    b.occupyTerritory(2, 2);
    b.addOwnUnits(1,0);
    b.addEnemyUnits(1,2,0);
    b.addEnemyUnits(1,4,2);
    Resolver<String> resolver = new BattleResolver<String>(new Random(10));
    
    resolver.combineEnemyArmy(b);
    /*Territory<String> t = territory.get(1);
    resolver.combatOnTerritory(t);
    assertEquals(t.getOwner(),2);
    assertEquals(t.getUnitAmount(),3);
    */
    
    resolver.executeAllBattle(b);
    ArrayList<Territory<String>> territory = b.getTerritories();
    assertEquals(territory.get(1).getOwner(),2);
    assertEquals(territory.get(1).getUnitAmount(),3);
  }

}











