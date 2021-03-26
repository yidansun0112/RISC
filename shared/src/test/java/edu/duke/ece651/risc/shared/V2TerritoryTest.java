package edu.duke.ece651.risc.shared;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public class V2TerritoryTest {
  @Test
  public void test_V2Territory() {
    V2Territory<String> test = new V2Territory<>(0, "test", 0, new int[] { 1, 1, 1, 1, 1, 1 }, -1);
    HashMap<Integer, Integer> army = new HashMap<>();
    
    for(int i = 0 ; i <= 6 ; i++){
      army.put(i , i);
    }

    test.addUnitAmount(army);

    assertEquals(3, test.getUnitAmountV2().get(3));

    test.removeUnitAmount(army);
    
    assertEquals(0, test.getUnitAmountV2().get(3));


    test.addEnemy(0, army);
    test.addEnemy(1, army);
    test.addEnemy(0, army);
    assertEquals(4, test.getEnemyArmy().get(0).getUnitAmtByLevel(2));
    assertEquals(2, test.getEnemyArmy().get(1).getUnitAmtByLevel(2));
  }

}











