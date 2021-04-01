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

public class RegisterLoginController {
  @FXML
  private Button RegisterBtn;
  @FXML
  private Button LoginBtn;
  @FXML
  private Label info;
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
    String userInput=getUserInput(Constant.VALUE_REQUEST_TYPE_REGISTER);
    SocketClient client=new SocketClient(12345,"127.0.0.1");
    client.sendObject(userInput);
    String result=(String)client.receiveObject();
    System.out.println("in register");
    System.out.println(result);
    if(!result.equals(Constant.RESULT_SUCCEED_REQEUST)){
      info.setText(result);
    }else{
      info.setText("You've successfully registered! Please login now!");
      usernameField.setText("");
      passwordField.setText("");
    }
  }

  @FXML
  public void login() throws IOException,ClassNotFoundException{
    String userInput=getUserInput(Constant.VALUE_REQUEST_TYPE_LOGIN);
    SocketClient client=new SocketClient(12345,"127.0.0.1");
    client.sendObject(userInput);
    String result=(String)client.receiveObject();
    System.out.println("in register");
    System.out.println(result);
    if(result.equals(Constant.RESULT_SUCCEED_REQEUST)){
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/choosePlayerNum.fxml"));
      loaderStart.setControllerFactory(c->{
        return new StartController(Window,null);
      });
      Scene scene = new Scene(loaderStart.load());
      Window.setScene(scene);
      Window.show();
    }else{
      info.setText(result);
      usernameField.setText("");
      passwordField.setText("");
    }
  }

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
