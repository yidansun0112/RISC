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

/**
 * This class handles from start game\
 * after player clicks on start, it would jump to choose map 
 * or pick territory page
 */
public class StartController {
  @FXML
  private Button startBtn;
  @FXML
  protected ChoiceBox<Integer> playerNumBox;
  @FXML
  private Button confirmNumBtn;
  // @FXML
  // private ChoiceBox<String> mapBox;
  // @FXML
  // private Button confirmMapBtn;
  private Stage window;
  GUIPlayer player;

  /**
   * Constructor
   */
  public StartController(Stage window, GUIPlayer player) {
    this.window = window;
    playerNumBox = new ChoiceBox<>();
    // mapBox = new ChoiceBox<>();
    this.player=player;
  }

  /**
   * make player choose the number of players in the game
   */
  @FXML
  public void initialize() {
    playerNumBox.setValue(2);
    ObservableList<Integer> ChoiceNumber = FXCollections.observableArrayList(2,3, 4, 5);
    playerNumBox.setItems(ChoiceNumber);
    // mapBox.setValue("Map 0");
  }

  /**
   * load the start game page
   */
  @FXML
  public void startGame() {
    PageLoader loader=new PageLoader(window,new GUIPlayer());
    loader.showRegLogPage();
  }

  /**
   * get the number that the player choose and jump to choose map page
   */
  @FXML
  public void selectPlayerNum() {
    int num = playerNumBox.getValue();
    player.playerNum=num;
    player.sendObject(num);
    PageLoader loader=new PageLoader(window, player);
    loader.showChooseMapPage();
  }

}
