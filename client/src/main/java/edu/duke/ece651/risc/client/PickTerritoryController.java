package edu.duke.ece651.risc.client;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class PickTerritoryController {
    @FXML
    private Button startBtn;
    @FXML
    private ChoiceBox<String> groupNumBox;
    @FXML
    private Button confirmNumBtn;
    @FXML
    private ChoiceBox<String> mapBox;
    @FXML
    private Button confirmMapBtn;
    private Stage window;
    GUIPlayer player;
  
    public PickTerritoryController(Stage window, GUIPlayer player) {
      this.window = window;
      groupNumBox = new ChoiceBox<>();
      //mapBox = new ChoiceBox<>();
      this.player=player;
      System.out.println("[DEBUG] Inside Start Controller Constructor");
    }

    @FXML
    public void initialize() {
        groupNumBox.setValue("0");
        ObservableList<String> ChoiceNumber = FXCollections.observableArrayList("0", "1", "2", "3");
        groupNumBox.setItems(ChoiceNumber);
        //mapBox.setValue("Group 0");
    }

    @FXML
    public void selectGroup() throws ClassNotFoundException, IOException{
        AlterBox alterBox=new AlterBox(this.window, this.player);
        alterBox.display("pickConfirm", "OK", "Successfully pick this group territories");
    }
}