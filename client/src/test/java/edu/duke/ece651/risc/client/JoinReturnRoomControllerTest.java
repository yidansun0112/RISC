package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.*;

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

import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.GameRoomInfo;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.V2BoardFactory;
import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class JoinReturnRoomControllerTest {

  private JoinRoomController joinCont;
  private ReturnRoomController returnCont;
  @Mock
  private GUIPlayer player;
  @Mock
  private GameStatus<String> gameStatus;
  @Mock
  private PlayerEntity<String> playerEntity;

  @Start
  private void start(Stage stage) {
    MockitoAnnotations.initMocks(this);
    List<GameRoomInfo> roomList=new ArrayList<GameRoomInfo>();
    roomList.add(new GameRoomInfo(1, 3, "a"));
    joinCont=new JoinRoomController(stage, player, roomList);
    returnCont=new ReturnRoomController(stage, player, roomList);
    joinCont.roomBox.setValue(1);
    returnCont.roomBox.setValue(1);
    player.playerNum=2;
    player.playerId=1;
    player.gameStatus=gameStatus;
    when(gameStatus.getCurrPlayer()).thenReturn(playerEntity);
    when(playerEntity.getPlayerId()).thenReturn(0);
    BoardFactory<String> factory=new V2BoardFactory<>();
    Board<String> board=factory.makeGameBoard(2);
    when(gameStatus.getGameBoard()).thenReturn(board);
  }


  @Test
  public void test_join_fail() {
    when(player.receiveObject()).thenReturn("-1");
    Platform.runLater(() -> {
      joinCont.enterRoom();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_join_success() {
    when(player.receiveObject()).thenReturn("1",3);
    Platform.runLater(() -> {
      joinCont.enterRoom();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_return() {
    when(player.receiveObject()).thenReturn(gameStatus);
    Platform.runLater(() -> {
      returnCont.enterRoom();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
