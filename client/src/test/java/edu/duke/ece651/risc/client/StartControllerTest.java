package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import java.io.IOException;
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
public class StartControllerTest {
  private StartController cont;
  private ChooseMapController mapCont;
  @Mock
  private GUIPlayer player;

  @Start
  private void start(Stage stage) {
    MockitoAnnotations.initMocks(this);
    cont=new StartController(stage, player);
    player.playerNum=2;
    mapCont=new ChooseMapController(stage, player);
  }

  @Test
  public void test_startGame() {
    //when(player.receiveObject()).thenReturn("1");
    Platform.runLater(() -> {
      cont.startGame();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_select_player_number() {
    Platform.runLater(() -> {
      cont.playerNumBox.setValue(2);
      cont.selectPlayerNum();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_select_map() {
    when(player.receiveObject()).thenReturn(2);
    mapCont.mapBox.setValue("map 0");
    Platform.runLater(() -> {
      mapCont.selectMap();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
