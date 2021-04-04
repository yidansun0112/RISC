package edu.duke.ece651.risc.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

public class MapLinkController implements Initializable{
  @FXML
  TerrInfoController terrInfoController;
  @FXML
  PlayerInfoController playerInfoController;
  @FXML
  Hyperlink terr0;
  @FXML
  Hyperlink terr1;
  @FXML
  Hyperlink terr2;
  @FXML
  Hyperlink terr3;
  @FXML
  Hyperlink terr4;
  @FXML
  Hyperlink terr5;
  @FXML
  Hyperlink terr6;
  @FXML
  Hyperlink terr7;
  @FXML
  Hyperlink terr8;
  @FXML
  Hyperlink terr9;
  @FXML
  Hyperlink terr10;
  @FXML
  Hyperlink terr11;
  @FXML
  Hyperlink terr12;
  @FXML
  Hyperlink terr13;
  @FXML
  Hyperlink terr14;
  private GUIPlayer player;

  public MapLinkController(GUIPlayer player){
    this.player=player;
  }

  @FXML
  public void setTerrInfo(int index){
    if(index==0){
      terrInfoController.nameValue.setText("Narnia");
    }
    else{
      terrInfoController.nameValue.setText("Oz");
    }
    terrInfoController.ownerValue.setText("Player 0");
  }

  @FXML
  public void linkZero(){
    setTerrInfo(0);
  }

  @FXML
  public void linkOne(){
    setTerrInfo(1);
  }

  @FXML
  public void setPlayerInfo(){
    playerInfoController.playerValue.setText("1");
    playerInfoController.foodValue.setText("30");
    playerInfoController.techValue.setText("40");
    playerInfoController.maxValue.setText("3");
  }

  public void initialize(URL url, ResourceBundle rb) {
      setPlayerInfo();
  } 

}
