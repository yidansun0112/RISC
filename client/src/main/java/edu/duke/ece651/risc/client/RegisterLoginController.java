package edu.duke.ece651.risc.client;

import java.io.IOException;
import edu.duke.ece651.risc.shared.Constant;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class handles registering and logging of the player
 * player would first register, then use the username and password 
 * to loggin the game
 */
public class RegisterLoginController {
  @FXML
  private Button registerBtn;
  @FXML
  private Button loginBtn;
  @FXML
  protected Label info;
  @FXML
  protected TextField usernameField;
  @FXML
  protected TextField passwordField;
  private Stage window;
  private GUIPlayer player;
  
  /**
   * The constructor
   * @param window
   * @param player
   */
  public RegisterLoginController(Stage window, GUIPlayer player) {
    this.window = window;
    this.player=player;
    usernameField=new TextField();
    passwordField=new TextField();
  }

  /**
   * let the user type in the name and password to register
   */
  @FXML
  public void register() {
    String userInput=getUserInput(Constant.VALUE_REQUEST_TYPE_REGISTER);
    player.connect();
    player.sendObject(userInput);
    String result=(String)player.receiveObject();
    if(!result.equals(Constant.RESULT_SUCCEED_REQEUST)){
      info.setText(result);
    }else{
      info.setText("You've successfully registered! Please login now!");
      usernameField.setText("");
      passwordField.setText("");
    }
    player.disconnect();
  }

  /**
   * let the user type in the password and username to login. 
   * If succeed, go to choose game page
   * if not, stay in the login page
   */
  @FXML
  public void login() {
    String userInput=getUserInput(Constant.VALUE_REQUEST_TYPE_LOGIN);
    // SocketClient client=new SocketClient(12345,Constant.ipaddress);
    // client.sendObject(userInput);
    // String result=(String)client.receiveObject();
    player.connect();
    player.sendObject(userInput);
    String result=(String)player.receiveObject();
    player.disconnect();
    //GUIPlayer player=new GUIPlayer(null);
    if(result.equals(Constant.RESULT_SUCCEED_REQEUST)){
      player.username=usernameField.getText();
      PageLoader loader=new PageLoader(window,player);
      loader.showChooseGamePage();
    }else{
      info.setText(result);
      usernameField.setText("");
      passwordField.setText("");
    }
  }

  /**
   * get the username and password that the player has typed in
   * @param type
   * @return
   */
  private String getUserInput(String type){
    String username=usernameField.getText();
    String password=passwordField.getText();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE,type);
    jsonObject.put(Constant.KEY_USER_NAME,username);
    jsonObject.put(Constant.KEY_PASSWORD,password);
    String userInput=jsonObject.toString();
    return userInput;
  }

}
