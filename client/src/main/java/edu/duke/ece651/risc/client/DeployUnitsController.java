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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeployUnitsController implements Initializable{
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;
  @FXML
  private Button confirmBtn;
  @FXML
  private ChoiceBox<String> terrBox;
  @FXML
  private ChoiceBox<Integer> amountBox;
  private Stage window;
  private GUIPlayer player;

  public DeployUnitsController(Stage window,GUIPlayer player) {
    this.window = window;
    this.player=player;
    terrBox=new ChoiceBox<>();
    amountBox=new ChoiceBox<>();
    PageLoader loader=new PageLoader(window, player);
    mapPane=loader.loadMap("/ui/map2link.fxml");
  }

  public void sendDeployment(){
    String terrName=terrBox.getValue();
    int terrId=Constant.terrNameToId.get(terrName);
    int amount=amountBox.getValue();
    ArrayList<Integer> deployment = new ArrayList<Integer>();
    deployment.add(terrId);
    deployment.add(amount);
    player.sendObject(deployment);
  }

  @FXML
  public void deploy(){
    sendDeployment();
    String deployResult=(String)player.receiveObject();
    AlterBox box=new AlterBox(window, player);
    box.display("stay", "Ok", deployResult);
    Object obj=player.receiveObject();
    if(obj instanceof String){
      PageLoader loader=new PageLoader(window, player);
      loader.showIssueOrderPage();
    }else{
      player.gameStatus=(GameStatus)obj;
      int remainedUnits=(int)player.receiveObject();
      setAmountBox(remainedUnits);
    }
  }

  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window, player);
    loader.putMap(rootPane, mapPane);
    player.gameStatus=(GameStatus)player.receiveObject();
    int remainedUnits=(int)player.receiveObject();
    setTerrBox();
    setAmountBox(remainedUnits);
  } 

  public void setTerrBox(){
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    int ownedGroup=player.gameStatus.getCurrPlayer().getOwnedGroup();
    terrBox.setValue(findTerrName(territories, 3*ownedGroup+0));
    for(int i=0;i<3;i++){
      terrBox.getItems().add(findTerrName(territories, 3*ownedGroup+i));
    }
  }

  public void setAmountBox(int remainedUnits){
    amountBox.getItems().clear();
    amountBox.setValue(1);
    for(int i=1;i<=remainedUnits;i++){
      amountBox.getItems().add(i);
    }
  }

  public String findTerrName(ArrayList<Territory<String>> territories,int index){
    return territories.get(index).getName();
  }
}
