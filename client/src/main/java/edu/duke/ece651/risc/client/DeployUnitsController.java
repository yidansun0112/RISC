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

public class DeployUnitsController implements Initializable{
  // @FXML
  // MapLinkController mapLinkController;
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
  // @FXML
  // Hyperlink terr0;
  // @FXML
  // Hyperlink terr1;
  private Stage window;
  private GUIPlayer player;

  public DeployUnitsController(Stage window,GUIPlayer player) {
    this.window = window;
    this.player=player;
    terrBox=new ChoiceBox<>();
    amountBox=new ChoiceBox<>();
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

  @FXML
  public void deploy(){
    try{
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/chooseMap.fxml"));
    loaderStart.setControllerFactory(c -> {
      return new StartController(window,null);
    });
    Scene scene = new Scene(loaderStart.load());
    window.setScene(scene);
    window.show();
  }catch(Exception e){
    throw new RuntimeException(e);
  }
  }

  public void initialize(URL url, ResourceBundle rb){
    // Button confirmBtn=new Button();
    // confirmBtn.setOnAction(e->deploy());
    // rootPane.getChildren().addAll(mapPane,confirmBtn);
    rootPane.getChildren().add(mapPane);
    AnchorPane.setTopAnchor(mapPane,0.0);
    AnchorPane.setRightAnchor(mapPane,0.0);
    AnchorPane.setLeftAnchor(mapPane,0.0);
    AnchorPane.setBottomAnchor(mapPane,200.0);
    //AnchorPane.setBottomAnchor(confirmBtn,100.0);
  } 
}
