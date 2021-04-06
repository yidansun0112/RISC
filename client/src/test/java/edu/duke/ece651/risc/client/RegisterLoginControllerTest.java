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

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameRoomInfo;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class RegisterLoginControllerTest {

  private RegisterLoginController cont;
  @Mock
  private GUIPlayer player;

  @Start
  private void start(Stage stage) {
    MockitoAnnotations.initMocks(this);
    cont=new RegisterLoginController(stage,player);
    cont.usernameField.setText("a");
    cont.passwordField.setText("a");
    cont.info=new Label();
  }

  @Test
  public void test_register_success() {
    when(player.receiveObject()).thenReturn(Constant.RESULT_SUCCEED_REQEUST);
    Platform.runLater(() -> {
      cont.register();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_register_fail() {
    when(player.receiveObject()).thenReturn("fail");
    Platform.runLater(() -> {
      cont.register();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_login_success() {
    when(player.receiveObject()).thenReturn(Constant.RESULT_SUCCEED_REQEUST);
    Platform.runLater(() -> {
      cont.login();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_login_fail() {
    when(player.receiveObject()).thenReturn("fail");
    Platform.runLater(() -> {
      cont.login();
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
