package edu.duke.ece651.risc.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeployUnitsController implements Initializable{
  @FXML
  MapLinkController mapLinkController;
  @FXML
  private Button ConfirmBtn;
  @FXML
  private ChoiceBox<String> terrBox;
  @FXML
  private ChoiceBox<String> amountBox;
  // @FXML
  // Hyperlink terr0;
  // @FXML
  // Hyperlink terr1;
  private Stage Window;

  public DeployUnitsController(Stage Window) {
    this.Window = Window;
    terrBox=new ChoiceBox<>();
    amountBox=new ChoiceBox<>();
  }

  @FXML
  public void deploy(){

  }

  
  public void initialize(URL url, ResourceBundle rb) {
      // mapLinkController.terr0=terr0;
      // mapLinkController.terr1=terr1;
  } 
}
