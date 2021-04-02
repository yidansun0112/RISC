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
  private Button startBtn;
  @FXML
  private ChoiceBox<String> playerNumBox;
  @FXML
  private Button confirmNumBtn;
  @FXML
  private ChoiceBox<String> mapBox;
  @FXML
  private Button confirmMapBtn;
  private Stage window;
  GUIPlayer player;

  public StartController(Stage window, GUIPlayer player) {
    this.window = window;
    playerNumBox = new ChoiceBox<>();
    mapBox = new ChoiceBox<>();
    this.player=player;
    System.out.println("[DEBUG] Inside Start Controller Constructor");
  }

  @FXML
  public void initialize() {
    playerNumBox.setValue("2");
    ObservableList<String> ChoiceNumber = FXCollections.observableArrayList("2", "3", "4", "5");
    playerNumBox.setItems(ChoiceNumber);
    mapBox.setValue("Map 0");
  }

  @FXML
  public void startGame() throws IOException, ClassNotFoundException{
    // player=new GUIPlayer(new SocketClient(12345,"127.0.0.1"));
    // int playerId = player.recvID();
    // if (playerId == 0) {
    //   FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/registerLogin.fxml"));
    //   loaderStart.setControllerFactory(c -> {
    //     return new RegisterLoginController(Window);
    //   });
    //   Scene scene = new Scene(loaderStart.load());
    //   Window.setScene(scene);
    //   Window.show();
    // }
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/registerLogin.fxml"));
    loaderStart.setControllerFactory(c -> {
      return new RegisterLoginController(window);
    });
    Scene scene = new Scene(loaderStart.load());
    window.setScene(scene);
    window.show();
  }

  @FXML
  public void selectPlayerNum() throws ClassNotFoundException, IOException {
    String num = playerNumBox.getValue();
    //player.sendObject(num);
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/chooseMap.fxml"));
    loaderStart.setControllerFactory(c -> {
      return new StartController(window,player);
    });
    Scene scene = new Scene(loaderStart.load());
    window.setScene(scene);
    window.show();
  }

  @FXML
  public void selectMap() throws ClassNotFoundException, IOException{
    //String mapinfo=(String)player.receiveObject();
    //System.out.println(mapinfo);
    //player.sendObject(0);
    //player.receiveObject();
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/deployUnits.fxml"));
    loaderStart.setControllerFactory(c -> {
      if(c.equals(DeployUnitsController.class)){
        return new DeployUnitsController(window);
      }
      try{
        return c.getConstructor().newInstance();
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    });
    Scene scene = new Scene(loaderStart.load());
    window.setScene(scene);
    window.show();
  }

}
