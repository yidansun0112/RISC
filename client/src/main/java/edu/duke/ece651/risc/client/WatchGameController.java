package edu.duke.ece651.risc.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.duke.ece651.risc.shared.GameStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * This class handles for watching game when one player loses.
 * Player would get the newest combat info when player clicks on next
 */
public class WatchGameController implements Initializable{
  /** Listview for display combat INFO, load from fxml */
  @FXML
  private ListView<String> listView;
  /** Pane for display map and resources */
  @FXML
  private  AnchorPane mapPane;

  @FXML
  private  AnchorPane rootPane;
  /** window to  display on*/
  private Stage window;
  /** model, who control the game logic */
  private GUIPlayer player;

  /**
   * Constructor
   * @param window
   * @param player
   */
  public WatchGameController(Stage window,GUIPlayer player){
    this.window=window;
    this.player=player;
    this.listView = new ListView<>();
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadMap();
  }

  /**
   * load the combat info page
   * @param url
   * @param rb
   */
  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window, player);
    loader.putMap(rootPane, mapPane);
    //receive combat info
    this.listView.setEditable(false);
    ObservableList<String> combatInfo = FXCollections.observableArrayList((ArrayList<String>)player.receiveObject());
    this.listView.setItems(combatInfo);
    //then receive gamestatus (game continue) or winner (game end)
    // Object obj=player.receiveObject();
    // if(obj instanceof GameStatus){
    //   player.gameStatus=(GameStatus<String>)obj;
    //   loader.showWatchGame();
    // }else{
    //   int winner=(int)obj;
    //   String message="Game end! Winner is player "+Integer.toString(winner);
    //   AlterBox box=new AlterBox(window, player);
    //   box.display("gameEnd", "Ok", message);
    // }
  } 

  
  /**
   * make player get the newest combat info
   * if the game has end, there would be a box pop up to remind 
   * player to exit
   */
  @FXML
  public void next(){
    Object obj=player.receiveObject();
    PageLoader loader=new PageLoader(window,player);
    if(obj instanceof GameStatus){
      player.gameStatus=(GameStatus<String>)obj;
      loader.showWatchGame();
    }else{
      int winner=(int)obj;
      String message="Game end! Winner is player "+Integer.toString(winner);
      AlterBox box=new AlterBox(window, player);
      box.display("gameEnd", "Ok", message);
    }
  }


}
