package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Random;
import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.Territory;
import edu.duke.ece651.risc.shared.V1BoardFactory;
import java.util.ArrayList;

public class V2BattleResolverTest {
  @Test
  public void test_getBonus() {
    V2BattleResolver<String> resolver=new V2BattleResolver<String>(new Random(10));
    assertEquals(0, resolver.getBonus(0));
    assertEquals(1, resolver.getBonus(1));
    assertEquals(3, resolver.getBonus(2));
    assertEquals(5, resolver.getBonus(3));
    assertEquals(8, resolver.getBonus(4));
    assertEquals(11, resolver.getBonus(5));
    assertEquals(15, resolver.getBonus(6));
    assertEquals(0, resolver.getBonus(7));
  }

  //TODO: retest this when board could add units by level
  @Test
  public void test_battleresolver() {
    BoardFactory<String> f = new V1BoardFactory<String>();
    Board<String> b = f.makeGameBoard(3);
    b.occupyTerritory(0, 0);
    b.occupyTerritory(1, 1);
    b.occupyTerritory(2, 2);
    b.addBasicDefendUnitsTo(1, 4);
    b.addBasicEnemyUnitsTo(1, 2, 0);
    b.addBasicEnemyUnitsTo(1, 4, 2);
    Resolver<String> resolver = new V2BattleResolver<String>(new Random(10));

    resolver.combineEnemyArmy(b);
    /*
     * Territory<String> t = territory.get(1); resolver.combatOnTerritory(t);
     * assertEquals(t.getOwner(),2); assertEquals(t.getUnitAmount(),3);
     */
    ArrayList<String> combatInfo = new ArrayList<>();
    resolver.executeAllBattle(b, combatInfo);
    //resolver.executeAllBattle(b);
    //assertEquals(null, combatInfo.get(0));
    ArrayList<Territory<String>> territory = b.getTerritories();
    assertEquals(0,territory.get(1).getOwner());
  }
}
