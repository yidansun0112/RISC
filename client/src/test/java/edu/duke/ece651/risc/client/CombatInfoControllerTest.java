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

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.Territory;
import edu.duke.ece651.risc.shared.V2BoardFactory;
import edu.duke.ece651.risc.shared.V2GameBoard;
import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class CombatInfoControllerTest {
  
  private CombatInfoController cont;
  private LoseChoiceController loseCont;
  private WatchGameController watchCont;
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
    cont = new CombatInfoController(stage, player);
    loseCont = new LoseChoiceController(stage, player);
    watchCont=new WatchGameController(stage, player);
  }

  @Test
  public void test_not_lose(FxRobot robot){
    when(player.receiveObject()).thenReturn(Constant.NOT_LOSE_INFO, gameStatus);
    Platform.runLater(() -> {
      cont.confirm();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_lose_quit(FxRobot robot){
    when(player.receiveObject()).thenReturn(Constant.LOSE_INFO);
    Platform.runLater(() -> {
      cont.confirm();
      //robot.clickOn("#quitBtn");
      loseCont.quit();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_unreachable(FxRobot robot){
    when(player.receiveObject()).thenReturn("unreachable");
    Platform.runLater(() -> {
      cont.confirm();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_lose_watch(FxRobot robot){
    ArrayList<String> combatInfo=new ArrayList<String>();
    combatInfo.add("Player 0 win terr 0.");
    combatInfo.add("Player 1 win terr 1.");
    when(player.receiveObject()).thenReturn(Constant.LOSE_INFO, combatInfo,gameStatus,combatInfo,1);
    Platform.runLater(() -> {
      cont.confirm();
      //robot.clickOn("#watchBtn");
      loseCont.watch();
      watchCont.next();
      watchCont.next();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_somebody_win(FxRobot robot){
    when(player.receiveObject()).thenReturn(1);
    Platform.runLater(() -> {
      cont.confirm();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
