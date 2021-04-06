package edu.duke.ece651.risc.client;

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
 * This class handles lose choice.
 */
public class LoseChoiceController {
  /** window to display */
  private Stage window;
  /** player to handle game logic */
  private GUIPlayer player;
  /** quit button */
  @FXML
  private Button quitBtn;
  /** watch button */
  @FXML
  private Button watchBtn;

  /**
   * The constructor
   * @param window
   * @param player
   */
  public LoseChoiceController(Stage window,GUIPlayer player){
    this.window=window;
    this.player=player;
  }

  /**
   * Quit game.
   * Disconnect with server and jump to start page.
   */
  @FXML
  public void quit(){
    player.sendObject(Constant.TO_QUIT_INFO);
    PageLoader loader=new PageLoader(window,player);
    loader.showStartPage();
  }

  /**
   * Watch game.
   * Jump to watch page.
   */
  @FXML
  public void watch(){
    player.sendObject(Constant.TO_WATCH_INFO);
    //jump to watch page
    PageLoader loader=new PageLoader(window, player);
    loader.showWatchGame();
  }
}
