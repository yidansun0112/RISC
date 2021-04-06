package edu.duke.ece651.risc.client;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.risc.shared.Army;
import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.Territory;
import edu.duke.ece651.risc.shared.V2BoardFactory;
import edu.duke.ece651.risc.shared.V2GameBoard;
import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

public class UpgradeUnitsControllerTest {

  private UpgradeUnitsController upc;
  @Mock
  private GUIPlayer player;
  @Mock
  private GameStatus<String> gameStatus;
  @Mock
  private PlayerEntity<String> playerEntity;

  @Start
  private void start(Stage stage) {
    MockitoAnnotations.initMocks(this);
    player.playerNum=2;
    player.playerId=1;
    player.gameStatus=gameStatus;
    when(gameStatus.getCurrPlayer()).thenReturn(playerEntity);
    when(playerEntity.getFoodResourceAmount()).thenReturn(5);
    when(playerEntity.getTechResourceAmount()).thenReturn(5);
    when(playerEntity.getTechLevel()).thenReturn(1);
    BoardFactory<String> factory=new V2BoardFactory<>();
    Board<String> board=factory.makeGameBoard(2);
    ArrayList<Territory<String>> territories=board.getTerritories();
    territories.get(0).setOwner(1);
    when(gameStatus.getGameBoard()).thenReturn(board);
    upc = new UpgradeUnitsController(stage, player);
  }
  
  @Test
  public void test_confirm() {
    when(player.receiveObject()).thenReturn("Your Order is Legal!", gameStatus);
    Platform.runLater(() -> {
      upc.terrBox.setValue("Narnia");
      upc.fromBox.setValue(0);
      upc.toBox.setValue(1);
      upc.amountBox.setValue(1);
      //robot.clickOn("#confirmBtn");
      upc.confirm();
    });
    WaitForAsyncUtils.waitForFxEvents();   
  }

  @Test
  public void test_cancel(FxRobot robot) {
    // when(player.receiveObject()).thenReturn("Your Order is Legal!");
    Platform.runLater(() -> {
      //robot.clickOn("#cancelBtn");
      upc.cancel();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }


}





