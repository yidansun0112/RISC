package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WaitIssueOrdersController implements Initializable{
// @FXML
  // MapLinkController mapLinkController;
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;

  private Stage window;
  private GUIPlayer player;

  public WaitIssueOrdersController(Stage window,GUIPlayer player)  {
    this.window = window;
    this.player=player;
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadMap("/ui/map2link.fxml");
  }


  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window,player);
    loader.putMap(rootPane, mapPane);
  } 
}
