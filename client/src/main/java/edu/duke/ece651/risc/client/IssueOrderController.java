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

public class IssueOrderController implements Initializable{
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;
  @FXML
  private Button moveBtn;
  @FXML
  private Button attackBtn;
  @FXML
  private Button upgradeBtn;
  @FXML
  private Button doneBtn;
  private Stage window;
  private GUIPlayer player;

  public IssueOrderController(Stage window,GUIPlayer player) {
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
  } 

  @FXML
  public void move(){
    try{
      FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/moveAttack.fxml"));
      loaderStart.setControllerFactory(c -> {
        if(c.equals(MoveAttackController.class)){
          return new MoveAttackController(window,player);
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
  }catch(Exception e){
    throw new RuntimeException(e);
  }
  }

  @FXML
  public void attack(){
    try{
      FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/moveAttack.fxml"));
      loaderStart.setControllerFactory(c -> {
        if(c.equals(MoveAttackController.class)){
          return new MoveAttackController(window,player);
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
  }catch(Exception e){
    throw new RuntimeException(e);
  }
  }

  @FXML
  public void upgrade(){
    ChooseBox chooseBox = new ChooseBox(window, player);
    chooseBox.display("Please choose one to upgrade");
  }

  @FXML
  public void done(){
    
  }
}







