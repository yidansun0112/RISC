package edu.duke.ece651.risc.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.duke.ece651.risc.shared.Army;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.Territory;
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
    ArrayList<Territory<String>> territories=player.gameStatus.getGameBoard().getTerritories();
    Territory<String> terr=territories.get(index);
    //owner
    int owner=terr.getOwner();
    if(owner!=-1){
      String strOwner="Player "+Integer.toString(owner);
      terrInfoController.ownerValue.setText(strOwner);
    }else{
      terrInfoController.ownerValue.setText("Not Picked");
    }
    //name
    terrInfoController.nameValue.setText(terr.getName());
    //food
    terrInfoController.foodValue.setText("+"+Integer.toString(terr.getFoodProduction())+"/round");
    //tech
    terrInfoController.techValue.setText("+"+Integer.toString(terr.getTechProduction())+"/round");
    //level0
    Army<String> currDefenderArmy=terr.getCurrDefenderArmy().get(0);
    Army<String> prevDefenderArmy=terr.getPrevDefenderArmy().get(0);
    boolean showCurr=player.gameStatus.getCanShowLatest();
    terrInfoController.lv0Value.setText(getUnitAmount(0, owner, showCurr, currDefenderArmy, prevDefenderArmy));
    terrInfoController.lv1Value.setText(getUnitAmount(1, owner, showCurr, currDefenderArmy, prevDefenderArmy));
    terrInfoController.lv2Value.setText(getUnitAmount(2, owner, showCurr, currDefenderArmy, prevDefenderArmy));
    terrInfoController.lv3Value.setText(getUnitAmount(3, owner, showCurr, currDefenderArmy, prevDefenderArmy));
    terrInfoController.lv4Value.setText(getUnitAmount(4, owner, showCurr, currDefenderArmy, prevDefenderArmy));
    terrInfoController.lv5Value.setText(getUnitAmount(5, owner, showCurr, currDefenderArmy, prevDefenderArmy));
    terrInfoController.lv6Value.setText(getUnitAmount(6, owner, showCurr, currDefenderArmy, prevDefenderArmy));
  }


  public String getUnitAmount(int level,int owner,boolean showCurr,Army<String> currDefenderArmy,Army<String> prevDefenderArmy){
    if(player.playerId==owner){
      return Integer.toString(currDefenderArmy.getUnitAmtByLevel(level));
    }else if(showCurr){
      return Integer.toString(currDefenderArmy.getUnitAmtByLevel(level));
    }else{
      return Integer.toString(prevDefenderArmy.getUnitAmtByLevel(level));
    }
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
  public void linkTwo(){
    setTerrInfo(2);
  }

  @FXML
  public void linkThree(){
    setTerrInfo(3);
  }

  @FXML
  public void linkFour(){
    setTerrInfo(4);
  }

  @FXML
  public void linkFive(){
    setTerrInfo(5);
  }

  @FXML
  public void linkSix(){
    setTerrInfo(6);
  }

  @FXML
  public void linkSeven(){
    setTerrInfo(7);
  }

  @FXML
  public void linkEight(){
    setTerrInfo(8);
  }

  @FXML
  public void linkNine(){
    setTerrInfo(9);
  }

  @FXML
  public void linkTen(){
    setTerrInfo(10);
  }

  @FXML
  public void linkEleven(){
    setTerrInfo(11);
  }

  @FXML
  public void linkTwelve(){
    setTerrInfo(12);
  }

  @FXML
  public void linkThirteen(){
    setTerrInfo(13);
  }

  @FXML
  public void linkFourteen(){
    setTerrInfo(14);
  }

  @FXML
  public void setPlayerInfo(){
    PlayerEntity<String> playerEntity=player.gameStatus.getCurrPlayer();
    playerInfoController.playerValue.setText(Integer.toString(player.playerId));
    int foodResourceAmt=playerEntity.getFoodResourceAmount();
    int techResourceAmt=playerEntity.getTechResourceAmount();
    int techLevel=playerEntity.getTechLevel();
    playerInfoController.foodValue.setText(Integer.toString(foodResourceAmt));
    playerInfoController.techValue.setText(Integer.toString(techResourceAmt));
    playerInfoController.maxValue.setText(Integer.toString(techLevel));
  }

  public void initialize(URL url, ResourceBundle rb) {
      setPlayerInfo();
  } 

}
