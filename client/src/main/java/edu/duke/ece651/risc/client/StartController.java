package edu.duke.ece651.risc.client;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class StartController {
  @FXML
  private Button Start;
  @FXML
  private ChoiceBox<Integer> PlayerNumBox;
  @FXML
  private Button ConfirmNumBtn;
  private Stage Window;
  SocketClient client;

  public StartController(Stage Window) {
    this.Window = Window;
    PlayerNumBox = new ChoiceBox<>();
    System.out.println("[DEBUG] Inside Start Controller Constructor");
  }

  @FXML
  public void initialize() {
    PlayerNumBox.setValue(2);
    ObservableList<Integer> ChoiceNumber = FXCollections.observableArrayList(2, 3, 4, 5);
    PlayerNumBox.setItems(ChoiceNumber);
  }

  @FXML
  public void startGame() throws IOException, ClassNotFoundException {
    client = new SocketClient(12345, "127.0.0.1");
    int playerId = recvID();
    if (playerId == 0) {
      FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/choosePlayerNum.fxml"));
      loaderStart.setControllerFactory(c -> {
        return new StartController(Window);
      });
      Scene scene = new Scene(loaderStart.load());

      Window.setScene(scene);
      Window.show();
    }
  }

  /**
   * This method receive playerID from server. It sets the playerId field after
   * receiving it, and print the ID to the command line. (This method is called by
   * joinGame() and the player ID will be changed at that time.)
   */
  public int recvID() throws ClassNotFoundException, IOException {
    String strID = (String) client.receiveObject();
    int playerId = Integer.parseInt(strID);
    return playerId;
  }

  @FXML
  public void selectPlayerNum() throws ClassNotFoundException, IOException {
    int num = PlayerNumBox.getValue();
    client.sendObject(num);
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/choosePlayerNum.fxml"));
    loaderStart.setControllerFactory(c -> {
      return new StartController(Window);
    });
    Scene scene = new Scene(loaderStart.load());

    Window.setScene(scene);
    Window.show();
  }
}
