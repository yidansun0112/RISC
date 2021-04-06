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
public class IssueOrderControllerTest {

  private IssueOrderController cont;
  private MoveAttackController moveCont;
  private MoveAttackController attackCont;
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
    cont = new IssueOrderController(stage, player);
    moveCont = new MoveAttackController(stage, player, "move");
    attackCont= new MoveAttackController(stage,player,"attack");
  }

  private void setUpLevelBox(int amount){
    moveCont.lv0Box.setValue(amount);
    moveCont.lv1Box.setValue(5);
    moveCont.lv2Box.setValue(amount);
    moveCont.lv3Box.setValue(amount);
    moveCont.lv4Box.setValue(amount);
    moveCont.lv5Box.setValue(amount);
    moveCont.lv6Box.setValue(amount);
    attackCont.lv0Box.setValue(amount);
    attackCont.lv1Box.setValue(amount);
    attackCont.lv2Box.setValue(amount);
    attackCont.lv3Box.setValue(amount);
    attackCont.lv4Box.setValue(amount);
    attackCont.lv5Box.setValue(amount);
    attackCont.lv6Box.setValue(amount);
  }

  @Test
  public void test_move_confirm(FxRobot robot) {
    when(player.receiveObject()).thenReturn("Your Order is Legal!", gameStatus);
    Platform.runLater(() -> {
      cont.move();
      setUpLevelBox(5);
      moveCont.sourceBox.setValue("Narnia");
      moveCont.targetBox.setValue("Oz");
      //robot.clickOn("#confirmBtn");
      moveCont.confirm();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  
  @Test
  public void test_move_cancel(FxRobot robot) {
    // when(player.receiveObject()).thenReturn("Your Order is Legal!");
    Platform.runLater(() -> {
      cont.move();
      //robot.clickOn("#cancelBtn");
      moveCont.cancel();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  
  @Test
  public void test_attack_confirm(FxRobot robot) {
    // when(player.receiveObject()).thenReturn("Your Order is Legal!");
    Platform.runLater(() -> {
      cont.attack();
      setUpLevelBox(0);
      attackCont.sourceBox.setValue("Narnia");
      attackCont.targetBox.setValue("Oz");
      //robot.clickOn("#confirmBtn");
      attackCont.confirm();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_upgrade_cancel(FxRobot robot) {
    // when(player.receiveObject()).thenReturn("Your Order is Legal!");
    Platform.runLater(() -> {
      cont.upgrade();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_done(FxRobot robot) {
    ArrayList<String> combatInfo=new ArrayList<String>();
    combatInfo.add("Player 0 win terr 0.");
    combatInfo.add("Player 1 win terr 1.");
    when(player.receiveObject()).thenReturn("Your Order is Legal!",gameStatus,combatInfo);
    Platform.runLater(() -> {
      cont.done();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_leave(FxRobot robot) {
    Platform.runLater(() -> {
      cont.leave();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
