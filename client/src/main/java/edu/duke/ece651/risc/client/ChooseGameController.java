package edu.duke.ece651.risc.client;

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

public class ChooseGameController {
  @FXML
  private Button createBtn;
  @FXML
  private Button joinBtn;
  @FXML
  private Button returnBtn;
  
  private Stage window;
  private GUIPlayer player;

  public ChooseGameController(Stage window,GUIPlayer player){
    this.window=window;
    this.player=player;
  }

  @FXML
  public void createGame() throws IOException,UnknownHostException,ClassNotFoundException{
    player.connect();
    JSONObject jsonObject=new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE,Constant.VALUE_REQUEST_TYPE_CREATE_ROOM);
    jsonObject.put(Constant.KEY_USER_NAME,player.username);
    String request=jsonObject.toString();
    player.sendObject(request);
    String id=(String)player.receiveObject();
    player.playerId=Integer.parseInt(id);
    PageLoader loader=new PageLoader(window,player);
    loader.showChoosePlayerNum();
  }

  @FXML
  public void joinGame(){
    //ask for list
    player.connect();
    JSONObject jsonObject=new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE,Constant.VALUE_REQUEST_TYPE_GET_WATING_ROOM_LIST);
    jsonObject.put(Constant.KEY_USER_NAME,player.username);
    String request=jsonObject.toString();
    player.sendObject(request);
    List<GameRoomInfo> roomList=(List<GameRoomInfo>)player.receiveObject();
    if(roomList.size()==0){
      AlterBox box=new AlterBox(window,player);
      box.display("stay", "Back", "There is no available room right now.");
      player.disconnect();
    }else{
      player.disconnect();
      PageLoader loader=new PageLoader(window,player);
      loader.showJoinRoomPage(roomList);
    }
  }


  @FXML
  public void returnGame(){

  }
}
