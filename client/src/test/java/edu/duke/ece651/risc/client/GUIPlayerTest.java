package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;

import org.testfx.framework.junit5.ApplicationExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class GUIPlayerTest {

  @Mock
  private SocketClient client;
  private GUIPlayer player;

  @Start
  public void start(Stage stage){
    MockitoAnnotations.initMocks(this);
    player = new GUIPlayer(client);
  }

  @Test
  public void test_send() {
    player.sendObject("test");
  }

  @Test
  public void test_receive() throws IOException,ClassNotFoundException{
    when(client.receiveObject()).thenReturn(1,"1");
    player.receiveObject();
    player.recvID();
  }

  @Test
  public void test_disconnect(){
    player.disconnect();
  }

  @Test
  public void test_constructor(){
    GUIPlayer player0=new GUIPlayer();
  }

}
