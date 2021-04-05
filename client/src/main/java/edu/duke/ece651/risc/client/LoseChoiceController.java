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

public class LoseChoiceController {
  private Stage window;
  private GUIPlayer player;
  @FXML
  private Button quitBtn;
  @FXML
  private Button watchBtn;

  public LoseChoiceController(Stage window,GUIPlayer player){
    this.window=window;
    this.player=player;
  }

  @FXML
  public void quit(){
    player.sendObject(Constant.TO_QUIT_INFO);
    PageLoader loader=new PageLoader(window,player);
    loader.showStartPage();
  }

  @FXML
  public void watch(){
    player.sendObject(Constant.TO_WATCH_INFO);
    //jump to watch page
    PageLoader loader=new PageLoader(window, player);
    loader.showWatchGame();
  }
}
