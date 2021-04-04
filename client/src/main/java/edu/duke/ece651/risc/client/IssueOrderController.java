package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.duke.ece651.risc.shared.DoneOrder;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.Order;
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
    PageLoader loader=new PageLoader(window, player);
    mapPane=loader.loadMap("/ui/map2link.fxml");
  }


  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window, player);
    loader.putMap(rootPane, mapPane);
  } 

  @FXML
  public void move(){
    PageLoader loader=new PageLoader(window,player);
    loader.showMoveAttack("move");
  }

  @FXML
  public void attack(){
    PageLoader loader=new PageLoader(window,player);
    loader.showMoveAttack("attack");
  }

  @FXML
  public void upgrade(){
    ChooseBox chooseBox = new ChooseBox(window, player);
    chooseBox.display("Please choose one to upgrade");
  }

  @FXML
  public void done(){
    Order<String> order=new DoneOrder<String>();
    player.sendObject(order);
    String result=(String)player.receiveObject();
    player.gameStatus=(GameStatus<String>)player.receiveObject();
  }
}







