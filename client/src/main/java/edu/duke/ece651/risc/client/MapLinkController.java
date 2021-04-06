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

/**
 * This class handles put map , territory info and player info together
 * when player clicks on the info hyperlink of a territory, it would show 
 * the information of that territory
 */
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

  /**
   * The constructor
   * @param player
   */
  public MapLinkController(GUIPlayer player){
    this.player=player;
  }

  /**
   * set up the territory info based on the index of the territory
   */
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


  /**
   * get the amount of different level of units of a territory
   * if the territory belongs to the player, it should get the current defender army value
   * if teh territory belongs to the enemy and the showCurr is false, it would get the previous 
   * defender army
   * @param level
   * @param owner
   * @param showCurr
   * @param currDefenderArmy
   * @param prevDefenderArmy
   * @return
   */
  public String getUnitAmount(int level,int owner,boolean showCurr,Army<String> currDefenderArmy,Army<String> prevDefenderArmy){
    if(player.playerId==owner){
      return Integer.toString(currDefenderArmy.getUnitAmtByLevel(level));
    }else if(showCurr){
      return Integer.toString(currDefenderArmy.getUnitAmtByLevel(level));
    }else{
      return Integer.toString(prevDefenderArmy.getUnitAmtByLevel(level));
    }
  }

  /**
   * set up the info of territory 0 when clicks on the hyperlink
   */
  @FXML
  public void linkZero(){
    setTerrInfo(0);
  }

  /**
   * set up the info of territory 1 when clicks on the hyperlink
   */
  @FXML
  public void linkOne(){
    setTerrInfo(1);
  }

  /**
   * set up the info of territory 2 when clicks on the hyperlink
   */
  @FXML
  public void linkTwo(){
    setTerrInfo(2);
  }

  /**
   * set up the info of territory 3 when clicks on the hyperlink
   */
  @FXML
  public void linkThree(){
    setTerrInfo(3);
  }

  /**
   * set up the info of territory 4 when clicks on the hyperlink
   */
  @FXML
  public void linkFour(){
    setTerrInfo(4);
  }

  /**
   * set up the info of territory 5 when clicks on the hyperlink
   */
  @FXML
  public void linkFive(){
    setTerrInfo(5);
  }

  /**
   * set up the info of territory 6 when clicks on the hyperlink
   */
  @FXML
  public void linkSix(){
    setTerrInfo(6);
  }

  /**
   * set up the info of territory 7 when clicks on the hyperlink
   */
  @FXML
  public void linkSeven(){
    setTerrInfo(7);
  }

  /**
   * set up the info of territory 8 when clicks on the hyperlink
   */
  @FXML
  public void linkEight(){
    setTerrInfo(8);
  }

  /**
   * set up the info of territory 9 when clicks on the hyperlink
   */
  @FXML
  public void linkNine(){
    setTerrInfo(9);
  }

  /**
   * set up the info of territory 10 when clicks on the hyperlink
   */
  @FXML
  public void linkTen(){
    setTerrInfo(10);
  }

  /**
   * set up the info of territory 11 when clicks on the hyperlink
   */
  @FXML
  public void linkEleven(){
    setTerrInfo(11);
  }

  /**
   * set up the info of territory 12 when clicks on the hyperlink
   */
  @FXML
  public void linkTwelve(){
    setTerrInfo(12);
  }

  /**
   * set up the info of territory 13 when clicks on the hyperlink
   */
  @FXML
  public void linkThirteen(){
    setTerrInfo(13);
  }

  /**
   * set up the info of territory 14 when clicks on the hyperlink
   */
  @FXML
  public void linkFourteen(){
    setTerrInfo(14);
  }

  /**
   * set up the player info based on the playerEntity
   */
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

  /**
   * set up player info
   * @param url
   * @param rb
   */
  public void initialize(URL url, ResourceBundle rb) {
      setPlayerInfo();
  } 

}
