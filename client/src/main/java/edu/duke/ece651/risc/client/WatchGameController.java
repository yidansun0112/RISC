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

public class WatchGameController implements Initializable{
  @FXML
  private ListView<String> listView;
  @FXML
  private  AnchorPane mapPane;
  @FXML
  private  AnchorPane rootPane;

  private Stage window;
  private GUIPlayer player;

  public WatchGameController(Stage window,GUIPlayer player){
    this.window=window;
    this.player=player;
    this.listView = new ListView<>();
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadMap();
  }

  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window, player);
    loader.putMap(rootPane, mapPane);
    //receive combat info
    this.listView.setEditable(false);
    ObservableList<String> combatInfo = FXCollections.observableArrayList((ArrayList<String>)player.receiveObject());
    this.listView.setItems(combatInfo);
    //then receive gamestatus (game continue) or winner (game end)
    Object obj=player.receiveObject();
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
