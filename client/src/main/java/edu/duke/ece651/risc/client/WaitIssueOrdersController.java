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

public class WaitController implements Initializable{
// @FXML
  // MapLinkController mapLinkController;
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;

  private Stage window;
  private GUIPlayer player;

  public WaitController(Stage window,GUIPlayer player)  {
    this.window = window;
    this.player=player;
    FXMLLoader mapLoader = new FXMLLoader(getClass().getResource("/ui/map2link.fxml"));
    mapLoader.setControllerFactory(c -> {
      if(c.equals(MapLinkController.class)){
        return new MapLinkController(player);
      }
      try{
        return c.getConstructor().newInstance();
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    });
    try{
      mapPane=mapLoader.load();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }


  public void initialize(URL url, ResourceBundle rb){
    
    rootPane.getChildren().add(mapPane);
    AnchorPane.setTopAnchor(mapPane,0.0);
    AnchorPane.setRightAnchor(mapPane,0.0);
    AnchorPane.setLeftAnchor(mapPane,0.0);
    AnchorPane.setBottomAnchor(mapPane,200.0);
    //AnchorPane.setBottomAnchor(confirmBtn,100.0);
  } 
}
