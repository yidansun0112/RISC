package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.duke.ece651.risc.shared.Territory;
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
    mapPane=loader.loadMap("/ui/map2link.fxml");
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
  }

  public void setSelfTerr(ChoiceBox<String> box){
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    int ownedGroup=player.gameStatus.getCurrPlayer().getOwnedGroup();
    box.setValue(findTerrName(territories, 3*ownedGroup+0));
    for(int i=0;i<3;i++){
      box.getItems().add(findTerrName(territories, 3*ownedGroup+i));
    }
  }

  public void setEnemyTerr(ChoiceBox<String> box){
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    int ownedGroup=player.gameStatus.getCurrPlayer().getOwnedGroup();
    ArrayList<Integer> groupList=new ArrayList<Integer>();
    for(int i=0;i<player.playerNum;i++){
      if(i!=ownedGroup){
        groupList.add(i);
      }
    }
    box.setValue(findTerrName(territories, 3*groupList.get(0)+0));
    for(int i=0;i<groupList.size();i++){
      for(int j=0;j<3;j++){
        box.getItems().add(findTerrName(territories, 3*groupList.get(i)+j));
      }
    }
  }

  public String findTerrName(ArrayList<Territory<String>> territories,int index){
    return territories.get(index).getName();
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
