package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.duke.ece651.risc.shared.Army;
import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.Order;
import edu.duke.ece651.risc.shared.Territory;
import edu.duke.ece651.risc.shared.V2UpgradeUnitOrder;
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

/**
 * This class handles for upgrading units tech level
 */
public class UpgradeUnitsController implements Initializable{
  // @FXML
  // MapLinkController mapLinkController;
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;
  @FXML
  private Button confirmBtn;
  @FXML
  protected ChoiceBox<String> terrBox;
  @FXML
  protected ChoiceBox<Integer> fromBox;
  @FXML
  protected ChoiceBox<Integer> toBox;
  @FXML
  protected ChoiceBox<Integer> amountBox;
  // @FXML
  // Hyperlink terr0;
  // @FXML
  // Hyperlink terr1;
  private Stage window;
  private GUIPlayer player;

  /**
   * Constructor
   * @param window
   * @param player
   */
  public UpgradeUnitsController(Stage window,GUIPlayer player) {
    this.window = window;
    this.player=player;
    terrBox=new ChoiceBox<>();
    fromBox=new ChoiceBox<>();
    toBox=new ChoiceBox<>();
    amountBox=new ChoiceBox<>();
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadMap();
  }

  /**
   * get thr the territory, from level, to level and amount the player selects
   * make a order based on the information get and send it to the server
   * get the response froom server, and return back to issue order page
   */
  @FXML
  public void confirm(){
    // pop up a alert box, saying if the order is legal
    //back button of the window would go back to issue order scene whether legal or illegal
    int terrId=Constant.terrNameToId.get(terrBox.getValue());
    int fromLevel=fromBox.getValue();
    int toLevel=toBox.getValue();
    int amount=amountBox.getValue();
    Order<String> order=new V2UpgradeUnitOrder<String>(terrId, fromLevel, toLevel, amount);
    player.sendObject(order);
    String result=(String)player.receiveObject();
    player.gameStatus=(GameStatus<String>)player.receiveObject();
    AlterBox alterBox=new AlterBox(window, player);
    alterBox.display("orderConfirm", "Back", result);
  }

  /**
   * return back to issue order page
   */
  @FXML
  public void cancel(){
    PageLoader loader=new PageLoader(window,player);
    loader.showIssueOrderPage();
  }

  /**
   * load the page for upgrade units tech level and make the drop box 
   * of territory, from level, to level, amount change dynamically.
   * to level would change based on from level, amount would change based on from level and amount
   * @param url
   * @param rb
   */
  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window,player);
    loader.putMap(rootPane, mapPane);
    setSelfTerr(terrBox);
    setLevelAmount(fromBox,0,Constant.TOTAL_LEVELS-1);
    setLevelAmount(toBox, 1,Constant.TOTAL_LEVELS);
    //setLevelAmount(amountBox, 0, 0);
    int defaultTerrId=Constant.terrNameToId.get(terrBox.getValue());
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    Territory<String> defaultTerr=territories.get(defaultTerrId);
    setLevelAmount(amountBox, 0, defaultTerr.getCurrDefenderArmy().get(0).getBasicUnits());
    terrBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
      int terrId=Constant.terrNameToId.get(newValue);
      //ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
      Territory<String> terr=territories.get(terrId);
      Army<String> currDefenderArmy=terr.getCurrDefenderArmy().get(0);
      setLevelAmount(amountBox, 0, currDefenderArmy.getUnitAmtByLevel(fromBox.getValue()));
    } );
    fromBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
      setLevelAmount(toBox, newValue+1, Constant.TOTAL_LEVELS);
      int terrId=Constant.terrNameToId.get(terrBox.getValue());
      //ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
      Territory<String> terr=territories.get(terrId);
      Army<String> currDefenderArmy=terr.getCurrDefenderArmy().get(0);
      setLevelAmount(amountBox, 0, currDefenderArmy.getUnitAmtByLevel(newValue));
    } );
  } 

  /**
   * This set up the drop box to show the territories that belongs to the player
   * @param box
   */
  public void setSelfTerr(ChoiceBox<String> box){
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    for(int i=0;i<territories.size();i++){
      if(territories.get(i).getOwner()==player.playerId){
        box.getItems().add(territories.get(i).getName());
        box.setValue(territories.get(i).getName());
      }
    }
  }

  /**
   * This set up the drop box for the units amount
   * @param box
   * @param fromAmount
   * @param toAmount
   */
  public void setLevelAmount(ChoiceBox<Integer> box,int fromAmount,int toAmount){
    box.getItems().clear();
    box.setValue(fromAmount);
    for(int i=fromAmount;i<=toAmount;i++){
      box.getItems().add(i);
    }
  }

}

