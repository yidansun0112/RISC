package edu.duke.ece651.risc.client;

import java.io.IOException;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChooseMapController {
  /** AnchorPane to load map */
  @FXML
  AnchorPane mapPane;
  /** AnchorPane to load other material and the mapPane */
  @FXML
  AnchorPane rootPane;
  /** Choice box to select map */
  @FXML
  protected ChoiceBox<String> mapBox;
  /** button to confirm */
  @FXML
  private Button confirmMapBtn;
  /** window to display */
  private Stage window;
  /** player to handle game logic */
  private GUIPlayer player;

  /**
   * The constructor
   * 
   * Initialize the choice box and map.
   * 
   * @param window
   * @param player
   */
  public ChooseMapController(Stage window, GUIPlayer player) {
    this.window = window;
    this.player=player;
    mapBox = new ChoiceBox<>();
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadPureMap();
  }

  /**
   * Put map onto the root pane.
   */
  @FXML
    public void initialize() {
      PageLoader loader=new PageLoader(window,player);
      loader.putPureMap(rootPane, mapPane);
      mapBox.setValue("Map 0");
    }

    /**
     * This method let the player to select map.
     * Then send the choice to server and receive player number from server to decide how to load map.
     */
    @FXML
    public void selectMap() {
      String mapChoice=mapBox.getValue();
      player.sendObject(mapChoice);
      PageLoader loader=new PageLoader(window, player);
      loader.showWaitPlayerComingPage();
      int playerNum=(int)player.receiveObject();//receive player number, indicate all players arrived
      player.playerNum=playerNum; 
      loader.showPickTerritoryPage();
    }

}
