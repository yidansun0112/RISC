package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.shared.GameRoomInfo;
import edu.duke.ece651.risc.shared.GameStatus;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.json.JSONObject;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameRoomInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * This class handles for returning back to the room the player
 * left before.
 */
public class ReturnRoomController {
  @FXML
  protected ChoiceBox<Integer> roomBox;
  private Stage window;
  private GUIPlayer player;
  private List<GameRoomInfo> roomList;

  /**
   * constructor
   * @param window
   * @param player
   * @param roomList
   */
  public ReturnRoomController(Stage window,GUIPlayer player,List<GameRoomInfo> roomList){
    this.window=window;
    this.player=player;
    this.roomList=roomList;
    roomBox=new ChoiceBox<>();
  }

  /**
   * set the drop box of room list
   */
  @FXML
  public void initialize() {
    roomBox.setValue(roomList.get(0).getRoomId());
    for(int i=0;i<roomList.size();i++){
      roomBox.getItems().add(roomList.get(i).getRoomId());
    }
  }

  /**
   * send server the room player picks, get the game status back from 
   * server and go to issue order page
   */
  @FXML
  public void enterRoom(){
    player.connect();
    JSONObject jsonObject=new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE,Constant.VALUE_REQUEST_TYPE_RETURN_ROOM);
    jsonObject.put(Constant.KEY_USER_NAME,player.username);
    int roomId=roomBox.getValue();
    String strRoomId=Integer.toString(roomId);
    jsonObject.put(Constant.KEY_ROOM_ID_TO_RETURN,strRoomId);
    String request=jsonObject.toString();
    player.sendObject(request);
    player.gameStatus=(GameStatus<String>)player.receiveObject();
    player.playerId=player.gameStatus.getCurrPlayer().getPlayerId();
    player.playerNum=player.gameStatus.getGameBoard().getTerritories().size()/3;
    PageLoader loader=new PageLoader(window,player);
    loader.showIssueOrderPage();
  }
  
}
