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

/**
 * This class handles for choose game.
 * Player could decide to create a room, join a room or return back to a room.
 */
public class ChooseGameController {
  /** create button, load from fxml */
  @FXML
  private Button createBtn;
  /** join button, load from fxml */
  @FXML
  private Button joinBtn;
  /** return button, load from fxml */
  @FXML
  private Button returnBtn;
  /** window to display on */
  private Stage window;
  /** model, who control the game logic */
  private GUIPlayer player;

  /**
   * The constructor
   * @param window
   * @param player
   */
  public ChooseGameController(Stage window, GUIPlayer player) {
    this.window = window;
    this.player = player;
  }

  /**
   * This method let the player to create a room.
   * 
   * Send a request, and receive player id.
   * Then jump to choose player number page.
   */
  @FXML
  public void createGame() {
    player.connect();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_CREATE_ROOM);
    jsonObject.put(Constant.KEY_USER_NAME, player.username);
    String request = jsonObject.toString();
    player.sendObject(request);
    String id = (String) player.receiveObject();
    player.playerId = Integer.parseInt(id);
    PageLoader loader = new PageLoader(window, player);
    loader.showChoosePlayerNum();
  }


  /**
   * This method let the player to join a room.
   * 
   * Send a request, and receive the room list could join.
   * If the list is empty, the player have to stay on the page.
   * Othersie, jump to join room page.
   */
  @FXML
  public void joinGame() {
    // ask for list
    player.connect();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_GET_WATING_ROOM_LIST);
    jsonObject.put(Constant.KEY_USER_NAME, player.username);
    String request = jsonObject.toString();
    player.sendObject(request);
    List<GameRoomInfo> roomList = (List<GameRoomInfo>) player.receiveObject();
    if (roomList.size() == 0) {
      player.disconnect();
      AlterBox box = new AlterBox(window, player);
      box.display("stay", "Back", "There is no available room right now.");
    } else {
      player.disconnect();
      PageLoader loader = new PageLoader(window, player);
      loader.showJoinRoomPage(roomList);
    }
  }

  /**
   * This method let the player to return to a room.
   * 
   * Send a request, and receive the room list could join.
   * If the list is empty, the player have to stay on the page.
   * Othersie, jump to return room page.
   */
  @FXML
  public void returnGame() {
    // ask for list
    player.connect();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_GET_LEAVING_ROOM_LIST);
    jsonObject.put(Constant.KEY_USER_NAME, player.username);
    String request = jsonObject.toString();
    player.sendObject(request);
    List<GameRoomInfo> roomList = (List<GameRoomInfo>) player.receiveObject();
    if (roomList.size() == 0) {
      player.disconnect();
      AlterBox box = new AlterBox(window, player);
      box.display("stay", "Back", "You don't have any room to return right now.");
    } else {
      player.disconnect();
      PageLoader loader = new PageLoader(window, player);
      loader.showReturnRoomPage(roomList);
    }
  }
}
