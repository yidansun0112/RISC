package edu.duke.ece651.risc.client;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.Territory;
import edu.duke.ece651.risc.shared.V2BoardFactory;
import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ChooseBoxTest {
  private ChooseBox box;
  private Stage alterWindow;
  @Mock
  private GUIPlayer player;
  @Mock
  private GameStatus<String> gameStatus;
  @Mock
  private PlayerEntity<String> playerEntity;
  @Test

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
    box=new ChooseBox(stage,player);
    alterWindow = new Stage();
  }

  @Test
  public void test_display(){
    Platform.runLater(() -> {
      box.display("message");
    });
    WaitForAsyncUtils.waitForFxEvents(); 
  }

  @Test
  public void test_set_upgrade(){
    when(player.receiveObject()).thenReturn("success",gameStatus);
    Platform.runLater(() -> {
      box.sendUpgradeTechLevel();
    });
    WaitForAsyncUtils.waitForFxEvents(); 
  }

  @Test
  public void test_jump_upgrade(){
    //when(player.receiveObject()).thenReturn("success",gameStatus);
    Platform.runLater(() -> {
      box.jumpUpgradeUnitsrPage(alterWindow);;
    });
    WaitForAsyncUtils.waitForFxEvents(); 
  }
  
}
