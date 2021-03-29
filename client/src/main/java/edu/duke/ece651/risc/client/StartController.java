package edu.duke.ece651.risc.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class StartController {
  @FXML private Button Start;
  private Stage Window;
  public StartController( Stage Window){
        this.Window = Window;
        System.out.println("[DEBUG] Inside Start Controller Constructor");
  }

  @FXML
  public void startGame() throws IOException, ClassNotFoundException{
    SocketClient client=new SocketClient(12345,"127.0.0.1");
    int playerId=recvID(client);
  }

  /**
   * This method receive playerID from server. It sets the playerId field after
   * receiving it, and print the ID to the command line. (This method is called by
   * joinGame() and the player ID will be changed at that time.)
   */
  public int recvID(SocketClient client) throws ClassNotFoundException, IOException {
    String strID = (String) client.receiveObject();
    int playerId = Integer.parseInt(strID);
    return playerId;
  }

}








