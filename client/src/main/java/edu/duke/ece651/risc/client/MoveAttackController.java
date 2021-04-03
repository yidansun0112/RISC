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
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadMap("/ui/map2link.fxml");
  }

  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window,player);
    loader.putMap(rootPane, mapPane);
  }

  @FXML
  public void cancel(){
    PageLoader loader=new PageLoader(window,player);
    loader.showIssueOrderPage();
  }

  @FXML
  public void confirm(){
    AlterBox alterBox=new AlterBox(window, player);
    alterBox.display("orderConfirm", "Back", "Your order is legal");
  }
}
