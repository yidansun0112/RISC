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

/**
 * This class handles issue order.
 */
public class IssueOrderController implements Initializable{
  /** AnchorPane to load map */
  @FXML
  AnchorPane mapPane;
  /** AnchorPane to put map and other stuff */
  @FXML
  AnchorPane rootPane;
  /** move button */
  @FXML
  private Button moveBtn;
  /** attack button */
  @FXML
  private Button attackBtn;
  /** upgrade button */
  @FXML
  private Button upgradeBtn;
  /** done button */
  @FXML
  private Button doneBtn;
  /** window to display */
  private Stage window;
  /** player to handle game logic */
  private GUIPlayer player;

  /**
   * The constructor
   * @param window
   * @param player
   */
  public IssueOrderController(Stage window,GUIPlayer player) {
    this.window = window;
    this.player=player;
    PageLoader loader=new PageLoader(window, player);
    mapPane=loader.loadMap();
  }


  /**
   * Initialize map
   */
  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window, player);
    loader.putMap(rootPane, mapPane);
  } 

  /**
   * load move page
   */
  @FXML
  public void move(){
    PageLoader loader=new PageLoader(window,player);
    loader.showMoveAttack("move");
  }

  /**
   * load attack page
   */
  @FXML
  public void attack(){
    PageLoader loader=new PageLoader(window,player);
    loader.showMoveAttack("attack");
  }

  /**
   * Show choose box to select upgrade tech level, units or cancel
   */
  @FXML
  public void upgrade(){
    ChooseBox chooseBox = new ChooseBox(window, player);
    chooseBox.display("Please choose one to upgrade");
  }

  /**
   * Done, then jump to combat info page.
   */
  @FXML
  public void done(){
    Order<String> order=new DoneOrder<String>();
    player.sendObject(order);
    String result=(String)player.receiveObject();
    player.gameStatus=(GameStatus<String>)player.receiveObject();
    PageLoader loader=new PageLoader(window,player);
    loader.showCombatInfo();
  }

  /**
   * Leave, then jump to choose room page.
   */
  @FXML
  public void leave(){
    player.disconnect();
    PageLoader loader=new PageLoader(window,player);
    loader.showChooseGamePage();
  }

}







