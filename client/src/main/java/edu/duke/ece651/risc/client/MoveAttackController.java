package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import edu.duke.ece651.risc.shared.Army;
import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.Order;
import edu.duke.ece651.risc.shared.Territory;
import edu.duke.ece651.risc.shared.V2AttackOrder;
import edu.duke.ece651.risc.shared.V2MoveOrder;
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
  private String type;

  public MoveAttackController(Stage window,GUIPlayer player, String type) {
    this.window = window;
    this.player=player;
    this.type=type;
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadMap();
    this.sourceBox=new ChoiceBox<String>();
    this.targetBox=new ChoiceBox<String>();
    this.lv0Box=new ChoiceBox<Integer>();
    this.lv1Box=new ChoiceBox<Integer>();
    this.lv2Box=new ChoiceBox<Integer>();
    this.lv3Box=new ChoiceBox<Integer>();
    this.lv4Box=new ChoiceBox<Integer>();
    this.lv5Box=new ChoiceBox<Integer>();
    this.lv6Box=new ChoiceBox<Integer>();
  }

  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window,player);
    loader.putMap(rootPane, mapPane);
    if(type=="move"){
      setSelfTerr(sourceBox);
      setSelfTerr(targetBox);
    }else{
      setSelfTerr(sourceBox);
      setEnemyTerr(targetBox);
    }
    String terrName=sourceBox.getValue();
    int terrIndex=Constant.terrNameToId.get(terrName);
    setAllLevelBox(terrIndex);
    sourceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
      int terrId=Constant.terrNameToId.get(newValue);
      setAllLevelBox(terrId);
    } );
  }

  public void setAllLevelBox(int terrId){
      ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
      Territory<String> terr=territories.get(terrId);
      Army<String> currDefenderArmy=terr.getCurrDefenderArmy().get(0);
      setLevelBox(lv0Box, currDefenderArmy.getUnitAmtByLevel(0));
      setLevelBox(lv1Box, currDefenderArmy.getUnitAmtByLevel(1));
      setLevelBox(lv2Box, currDefenderArmy.getUnitAmtByLevel(2));
      setLevelBox(lv3Box, currDefenderArmy.getUnitAmtByLevel(3));
      setLevelBox(lv4Box, currDefenderArmy.getUnitAmtByLevel(4));
      setLevelBox(lv5Box, currDefenderArmy.getUnitAmtByLevel(5));
      setLevelBox(lv6Box, currDefenderArmy.getUnitAmtByLevel(6));
  }

  public void setSelfTerr(ChoiceBox<String> box){
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    for(int i=0;i<territories.size();i++){
      if(territories.get(i).getOwner()==player.playerId){
        box.getItems().add(territories.get(i).getName());
        box.setValue(territories.get(i).getName());
      }
    }
  }

  public void setEnemyTerr(ChoiceBox<String> box){
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    for(int i=0;i<territories.size();i++){
      if(territories.get(i).getOwner()!=player.playerId){
        box.getItems().add(territories.get(i).getName());
        box.setValue(territories.get(i).getName());
      }
    }
  }

  public void setLevelBox(ChoiceBox<Integer> box,int amount){
    box.getItems().clear();
    box.setValue(0);
    for(int i=0;i<=amount;i++){
      box.getItems().add(i);
    }
  }

  @FXML
  public void cancel(){
    PageLoader loader=new PageLoader(window,player);
    loader.showIssueOrderPage();
  }

  @FXML
  public void confirm(){
    int sourceTerr=Constant.terrNameToId.get(sourceBox.getValue());
    int targetTerr=Constant.terrNameToId.get(targetBox.getValue());
    HashMap<Integer,Integer> unitsMap=new HashMap<Integer,Integer>();
    setUnitsMap(unitsMap);
    Order<String> order;
    if(type=="move"){
      order=new V2MoveOrder<String>(sourceTerr, targetTerr, unitsMap);
    }else{
      order=new V2AttackOrder<String>(sourceTerr, targetTerr, unitsMap);
    }
    player.sendObject(order);
    String result=(String)player.receiveObject();
    player.gameStatus=(GameStatus<String>)player.receiveObject();
    AlterBox alterBox=new AlterBox(window, player);
    alterBox.display("orderConfirm", "Back", result);
  }

  public void setUnitsMap(HashMap<Integer,Integer> unitsMap){
    if(lv0Box.getValue()!=0){
      unitsMap.put(0,lv0Box.getValue());
    }
    if(lv1Box.getValue()!=0){
      unitsMap.put(1,lv1Box.getValue());
    }
    if(lv2Box.getValue()!=0){
      unitsMap.put(2,lv2Box.getValue());
    }
    if(lv3Box.getValue()!=0){
      unitsMap.put(3,lv3Box.getValue());
    }
    if(lv4Box.getValue()!=0){
      unitsMap.put(4,lv4Box.getValue());
    }
    if(lv5Box.getValue()!=0){
      unitsMap.put(5,lv5Box.getValue());
    }
    if(lv6Box.getValue()!=0){
      unitsMap.put(6,lv6Box.getValue());
    }
  }
}
