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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterLoginController {
  @FXML
  private Button RegisterBtn;
  @FXML
  private Button LoginBtn;
  @FXML
  private TextField usernameField;
  @FXML
  private TextField passwordField;
  private Stage Window;
  
  public RegisterLoginController(Stage Window) {
    this.Window = Window;
    usernameField=new TextField();
    passwordField=new TextField();
  }

  @FXML
  public void register() throws IOException,ClassNotFoundException{
    String username=usernameField.getText();
    String password=passwordField.getText();
    String type=Constant.VALUE_REQUEST_TYPE_REGISTER;
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(Constant.KEY_REQUEST_TYPE,type);
    jsonObject.put(Constant.KEY_USER_NAME,username);
    jsonObject.put(Constant.KEY_PASSWORD,password);
    SocketClient client=new SocketClient(12345,"127.0.0.1");
    client.sendObject(jsonObject);
  }
}
