package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.risc.shared.GameRoomInfo;
import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ChooseGameControllerTest {
  private ChooseGameController cont;
  @Mock
  private GUIPlayer player;

  @Start
  private void start(Stage stage) {
    MockitoAnnotations.initMocks(this);
    cont=new ChooseGameController(stage, player);
  }

  @Test
  public void test_createGame(FxRobot robot) {
    when(player.receiveObject()).thenReturn("1");
    Platform.runLater(() -> {
      cont.createGame();
      robot.clickOn("#confirmNumBtn");
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_joinGame_empty(FxRobot robot) {
    List<GameRoomInfo> roomList=new ArrayList<GameRoomInfo>();
    when(player.receiveObject()).thenReturn(roomList);
    robot.robotContext();
    Platform.runLater(() -> {
      cont.joinGame();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_joinGame_not_empty(FxRobot robot) {
    List<GameRoomInfo> roomList=new ArrayList<GameRoomInfo>();
    GameRoomInfo roomInfo=new GameRoomInfo(1,3,"a");
    roomList.add(roomInfo);
    when(player.receiveObject()).thenReturn(roomList);
    Platform.runLater(() -> {
      cont.joinGame();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_returnGame_empty(FxRobot robot) {
    List<GameRoomInfo> roomList=new ArrayList<GameRoomInfo>();
    when(player.receiveObject()).thenReturn(roomList);
    Platform.runLater(() -> {
      cont.returnGame();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_return_Game_not_empty(FxRobot robot) {
    List<GameRoomInfo> roomList=new ArrayList<GameRoomInfo>();
    GameRoomInfo roomInfo=new GameRoomInfo(1,3,"a");
    roomList.add(roomInfo);
    when(player.receiveObject()).thenReturn(roomList);
    Platform.runLater(() -> {
      cont.returnGame();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
