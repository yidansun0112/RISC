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

public class StartController {
  @FXML
  private Button startBtn;
  @FXML
  private ChoiceBox<String> playerNumBox;
  @FXML
  private Button confirmNumBtn;
  @FXML
  private ChoiceBox<String> mapBox;
  @FXML
  private Button confirmMapBtn;
  private Stage window;
  GUIPlayer player;

  public StartController(Stage window, GUIPlayer player) {
    this.window = window;
    playerNumBox = new ChoiceBox<>();
    mapBox = new ChoiceBox<>();
    this.player=player;
    System.out.println("[DEBUG] Inside Start Controller Constructor");
  }

  @FXML
  public void initialize() {
    playerNumBox.setValue("2");
    ObservableList<String> ChoiceNumber = FXCollections.observableArrayList("2", "3", "4", "5");
    playerNumBox.setItems(ChoiceNumber);
    mapBox.setValue("Map 0");
  }

  @FXML
  public void startGame() throws IOException, ClassNotFoundException{
    PageLoader loader=new PageLoader(window,null);
    loader.showRegLogPage();
  }

  @FXML
  public void selectPlayerNum() throws ClassNotFoundException, IOException {
    String num = playerNumBox.getValue();
    PageLoader loader=new PageLoader(window, player);
    loader.showChooseMapPage();
  }

  @FXML
  public void selectMap() throws ClassNotFoundException, IOException{
    PageLoader loader=new PageLoader(window, player);
    loader.showPickTerritoryPage();
  }

}
