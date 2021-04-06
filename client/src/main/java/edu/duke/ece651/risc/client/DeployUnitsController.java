package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.Territory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class handles deploy units
 */
public class DeployUnitsController implements Initializable{
  /** AnchorPane to load map */
  @FXML
  AnchorPane mapPane;
  /** AnchorPane to put map and other stuff */
  @FXML
  AnchorPane rootPane;
  /** confirm button */
  @FXML
  private Button confirmBtn;
  /** Choice box to select territory */
  @FXML
  protected ChoiceBox<String> terrBox;
  /** Label to display wait info */
  @FXML
  private Label waitLabel;
  /** Choice box to select unit amount */
  @FXML
  protected ChoiceBox<Integer> amountBox;
  /** window to display */
  private Stage window;
  /** player to handel game logic */
  private GUIPlayer player;

  /**
   * The constructor
   * 
   * Initialize map and choice box.
   * 
   * @param window
   * @param player
   */
  public DeployUnitsController(Stage window,GUIPlayer player) {
    this.window = window;
    this.player=player;
    terrBox=new ChoiceBox<>();
    amountBox=new ChoiceBox<>();
    PageLoader loader=new PageLoader(window, player);
    mapPane=loader.loadMap();
  }

  /**
   * This method got deployment info and send it to server.
   */
  public void sendDeployment(){
    String terrName=terrBox.getValue();
    int terrId=Constant.terrNameToId.get(terrName);
    int amount=amountBox.getValue();
    ArrayList<Integer> deployment = new ArrayList<Integer>();
    deployment.add(terrId);
    deployment.add(amount);
    player.sendObject(deployment);
  }

  /**
   * This method send deploy method to server and receive deployment result from server and display.
   * 
   * If finish deploy, jump to issurOrder page.
   * Else, continue send deployment.
   */
  @FXML
  public void deploy(){
    sendDeployment();
    String deployResult=(String)player.receiveObject();
    AlterBox box=new AlterBox(window, player);
    box.display("stay", "Ok", deployResult);
    Object obj=player.receiveObject();
    if(obj instanceof String){
      waitLabel.setText("Finished! Please wait for other players to deploy!");
      player.gameStatus=(GameStatus<String>)player.receiveObject();
      PageLoader loader=new PageLoader(window, player);
      loader.showIssueOrderPage();
    }else{
      GameStatus<String> status=(GameStatus<String>)obj;
      int amount=status.getGameBoard().getTerritories().get(0).getCurrDefenderArmy().get(0).getUnitAmtByLevel(0);
      player.gameStatus=(GameStatus<String>)obj;
      amount=player.gameStatus.getGameBoard().getTerritories().get(0).getCurrDefenderArmy().get(0).getUnitAmtByLevel(0);
      int remainedUnits=(int)player.receiveObject();
      setAmountBox(remainedUnits);
    }
  }

  /**
   * Put map onto the rootPane.
   * And receive remained units from server to initialize the choice box.
   */
  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window, player);
    loader.putMap(rootPane, mapPane);
    //player.gameStatus=(GameStatus<String>)player.receiveObject();
    int remainedUnits=(int)player.receiveObject();
    setTerrBox();
    setAmountBox(remainedUnits);
  } 

  /**
   * This method set the territory box to only display territories that owned by the player.
   */
  public void setTerrBox(){
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    int ownedGroup=player.gameStatus.getCurrPlayer().getOwnedGroup();
    terrBox.setValue(findTerrName(territories, 3*ownedGroup+0));
    for(int i=0;i<3;i++){
      terrBox.getItems().add(findTerrName(territories, 3*ownedGroup+i));
    }
  }

  /**
   * This method set the amount box to only have numbers from 1 to remained units.
   * @param remainedUnits
   */
  public void setAmountBox(int remainedUnits){
    amountBox.getItems().clear();
    amountBox.setValue(1);
    for(int i=1;i<=remainedUnits;i++){
      amountBox.getItems().add(i);
    }
  }

  /**
   * This method get the territory name specified by the index.
   * 
   * @param territories
   * @param index
   * @return territory name
   */
  public String findTerrName(ArrayList<Territory<String>> territories,int index){
    return territories.get(index).getName();
  }
}
