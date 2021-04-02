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

public class MoveAttackController implements Initializable{
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;
  @FXML
  private Button cancelBtn;
  @FXML
  private Button confirmBtn;
  @FXML
  private ChoiceBox<String> sourceBox;
  @FXML
  private ChoiceBox<String> targetBox;
  @FXML
  private ChoiceBox<Integer> lv0Box;
  @FXML
  private ChoiceBox<Integer> lv1Box;
  @FXML
  private ChoiceBox<Integer> lv2Box;
  @FXML
  private ChoiceBox<Integer> lv3Box;
  @FXML
  private ChoiceBox<Integer> lv4Box;
  @FXML
  private ChoiceBox<Integer> lv5Box;
  @FXML
  private ChoiceBox<Integer> lv6Box;
  private Stage window;
  private GUIPlayer player;

  public MoveAttackController(Stage window,GUIPlayer player) {
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
  public void cancel(){
    try{
      FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/issueOrder.fxml"));
      loaderStart.setControllerFactory(c -> {
        if(c.equals(IssueOrderController.class)){
          return new IssueOrderController(window,player);
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
  public void confirm(){
    AlterBox alterBox=new AlterBox(window, player);
    alterBox.display("orderConfirm", "Back", "Your order is legal");
  }
}
