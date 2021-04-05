package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.shared.GameRoomInfo;
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


public class JoinRoomController {
  @FXML
  private ChoiceBox<Integer> roomBox;
  private Stage window;
  private GUIPlayer player;
  private List<GameRoomInfo> roomList;

  public JoinRoomController(Stage window,GUIPlayer player,List<GameRoomInfo> roomList){
    this.window=window;
    this.player=player;
    this.roomList=roomList;
    roomBox=new ChoiceBox<>();
  }

  @FXML
  public void initialize() {
    roomBox.setValue(roomList.get(0).getRoomId());
    for(int i=0;i<roomList.size();i++){
      roomBox.getItems().add(roomList.get(i).getRoomId());
    }
  }

  @FXML
  public void enterRoom(){
    player.connect();
    JSONObject jsonObject=new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE,Constant.VALUE_REQUEST_TYPE_JOIN_ROOM);
    jsonObject.put(Constant.KEY_USER_NAME,player.username);
    int roomId=roomBox.getValue();
    String strRoomId=Integer.toString(roomId);
    jsonObject.put(Constant.KEY_ROOM_ID_TO_JOIN,strRoomId);
    String request=jsonObject.toString();
    player.sendObject(request);
    String strId=(String)player.receiveObject();
    int playerId=Integer.parseInt(strId);
    if(playerId==-1){
      AlterBox box=new AlterBox(window,player);
      box.display("backChooseGame","Back","Sorry, this room has been chosen!");
      player.disconnect();
    }else{
      PageLoader loader=new PageLoader(window,player);
      loader.showWaitPlayerComingPage();
      int playerNum=(int)player.receiveObject();
      player.playerNum=playerNum;
      player.playerId=playerId;
      loader.showPickTerritoryPage();
    }
  }
  
}