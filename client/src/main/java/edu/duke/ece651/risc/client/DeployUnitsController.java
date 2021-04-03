package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeployUnitsController implements Initializable{
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;
  @FXML
  private Button confirmBtn;
  @FXML
  private ChoiceBox<String> terrBox;
  @FXML
  private ChoiceBox<String> amountBox;
  private Stage window;
  private GUIPlayer player;

  public DeployUnitsController(Stage window,GUIPlayer player) {
    this.window = window;
    this.player=player;
    terrBox=new ChoiceBox<>();
    amountBox=new ChoiceBox<>();
    PageLoader loader=new PageLoader(window, player);
    mapPane=loader.loadMap("/ui/map2link.fxml");
  }

  @FXML
  public void deploy(){
    PageLoader loader=new PageLoader(window, player);
    loader.showIssueOrderPage();
  }

  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window, player);
    loader.putMap(rootPane, mapPane);
  } 
}
