package edu.duke.ece651.risc.client;

import java.io.IOException;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChooseMapController {
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;
  @FXML
  protected ChoiceBox<String> mapBox;
  @FXML
  private Button confirmMapBtn;
  private Stage window;
  private GUIPlayer player;

  public ChooseMapController(Stage window, GUIPlayer player) {
    this.window = window;
    this.player=player;
    mapBox = new ChoiceBox<>();
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadPureMap();
  }

  @FXML
    public void initialize() {
      PageLoader loader=new PageLoader(window,player);
      loader.putMap(rootPane, mapPane);
      mapBox.setValue("Map 0");
    }

    @FXML
    public void selectMap() {
      String mapChoice=mapBox.getValue();
      player.sendObject(mapChoice);
      PageLoader loader=new PageLoader(window, player);
      System.out.println("to show wait page");
      loader.showWaitPlayerComingPage();
      int playerNum=(int)player.receiveObject();//receive player number, indicate all players arrived
      player.playerNum=playerNum; 
      loader.showPickTerritoryPage();
    }

}
