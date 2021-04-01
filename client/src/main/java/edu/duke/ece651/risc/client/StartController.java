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
  private ChoiceBox<String> PlayerNumBox;
  @FXML
  private Button ConfirmNumBtn;
  @FXML
  private ChoiceBox<String> MapBox;
  @FXML
  private Button ConfirmMapBtn;
  private Stage Window;
  GUIPlayer player;

  public StartController(Stage Window, GUIPlayer player) {
    this.Window = Window;
    PlayerNumBox = new ChoiceBox<>();
    MapBox = new ChoiceBox<>();
    this.player=player;
    System.out.println("[DEBUG] Inside Start Controller Constructor");
  }

  @FXML
  public void initialize() {
    PlayerNumBox.setValue("2");
    ObservableList<String> ChoiceNumber = FXCollections.observableArrayList("2", "3", "4", "5");
    PlayerNumBox.setItems(ChoiceNumber);
    MapBox.setValue("Map 0");
  }

  @FXML
  public void startGame() throws IOException, ClassNotFoundException{
    player=new GUIPlayer(new SocketClient(12345,"127.0.0.1"));
    int playerId = player.recvID();
    if (playerId == 0) {
      FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/choosePlayerNum.fxml"));
      loaderStart.setControllerFactory(c -> {
        return new StartController(Window,player);
      });
      Scene scene = new Scene(loaderStart.load());
      Window.setScene(scene);
      Window.show();
    }
  }

  @FXML
  public void selectPlayerNum() throws ClassNotFoundException, IOException {
    String num = PlayerNumBox.getValue();
    player.sendObject(num);
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/chooseMap.fxml"));
    loaderStart.setControllerFactory(c -> {
      return new StartController(Window,player);
    });
    Scene scene = new Scene(loaderStart.load());
    Window.setScene(scene);
    Window.show();
  }

  @FXML
  public void selectMap() throws ClassNotFoundException, IOException {
    //String mapinfo=(String)player.receiveObject();
    //System.out.println(mapinfo);
    player.sendObject(0);
    player.receiveObject();
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/choosePlayerNum.fxml"));
    loaderStart.setControllerFactory(c -> {
      return new StartController(Window,player);
    });
    Scene scene = new Scene(loaderStart.load());
    Window.setScene(scene);
    Window.show();
  }

}
