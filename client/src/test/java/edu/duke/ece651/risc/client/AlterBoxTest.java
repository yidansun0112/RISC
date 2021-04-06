package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
public class AlterBoxTest {

  private AlterBox box;
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
    box=new AlterBox(stage,player);
    alterWindow = new Stage();
  }

  @Test
  public void test_order_confirm() {
    Platform.runLater(() -> {
      box.display("orderConfirm", "ok", "ok");
    });
    WaitForAsyncUtils.waitForFxEvents(); 
  }

  @Test
  public void test_jump_issue() {
    Platform.runLater(() -> {
      box.jumpIssueOrderPage(alterWindow);
    });
    WaitForAsyncUtils.waitForFxEvents(); 
  }

  @Test
  public void test_pick_confirm() {
    when(player.receiveObject()).thenReturn(1);
    Platform.runLater(() -> {
      box.display("pickConfirm", "ok", "ok");
    });
    WaitForAsyncUtils.waitForFxEvents();  
  }

  @Test
  public void test_jump_deploy() {
    when(player.receiveObject()).thenReturn(1);
    Platform.runLater(() -> {
      box.jumpDeployUnitsPage(alterWindow); 
    });
    WaitForAsyncUtils.waitForFxEvents();  
  }

  @Test
  public void test_stay() {
    Platform.runLater(() -> {
      box.display("stay", "ok", "ok");
    });
    WaitForAsyncUtils.waitForFxEvents();  
  }

  @Test
  public void test_back_chooseGame() {
    Platform.runLater(() -> {
      box.display("backChooseGame", "ok", "ok");
    });
    WaitForAsyncUtils.waitForFxEvents();  
  }

  @Test
  public void test_jump_chooseGame(){
    Platform.runLater(() -> {
      box.jumpChooseGamePage(alterWindow);
    });
    WaitForAsyncUtils.waitForFxEvents();  
  }

  @Test
  public void test_gameend() {
    Platform.runLater(() -> {
      box.display("gameEnd", "ok", "ok");
    });
    WaitForAsyncUtils.waitForFxEvents();  
  }

  @Test
  public void test_jump_start() {
    Platform.runLater(() -> {
      box.jumpStartPage(alterWindow);
    });
    WaitForAsyncUtils.waitForFxEvents();  
  }

  @Test
  public void test_throw() {
    Platform.runLater(() -> {
      assertThrows(IllegalArgumentException.class, ()->box.display("notsupport","ok","ok"));
    });
    WaitForAsyncUtils.waitForFxEvents();  
  }

}
