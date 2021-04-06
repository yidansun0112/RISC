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

/**
 * This handles for picking territories for each player
 * Each would pick a group of territories
 */
public class PickTerritoryController {
    @FXML
    AnchorPane mapPane;
    @FXML
    AnchorPane rootPane;
    @FXML
    protected ChoiceBox<String> groupNumBox;
    @FXML
    private Label waitLabel;
    private Stage window;
    GUIPlayer player;
  
    /**
     * Construtor
     * @param window
     * @param player
     */
    public PickTerritoryController(Stage window, GUIPlayer player) {
      this.window = window;
      this.player=player;
      groupNumBox = new ChoiceBox<>();
      PageLoader loader=new PageLoader(window,player);
      mapPane=loader.loadPureMap();
    }

    /**
     * load the map and set up the drop box for choosing map
     */
    @FXML
    public void initialize() {
        PageLoader loader=new PageLoader(window,player);
        loader.putPureMap(rootPane, mapPane);
        groupNumBox.setValue("0");
        for(int i=0;i<player.playerNum;i++){
            groupNumBox.getItems().add(Integer.toString(i));
        }
    }

    /**
     * Make each player select a group and send the choice to the server
     * If the group has been picked by other players, it would remind the player to pick 
     * another one.
     * If the group hasn't been picked yet, the player would get this group of territories
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @FXML
    public void selectGroup() throws ClassNotFoundException, IOException{
        String choice=groupNumBox.getValue();
        player.sendObject(choice);
        String result=(String)player.receiveObject();
        if(result.equals(Constant.VALID_MAP_CHOICE_INFO)){
            waitLabel.setText("Please wait for other players finish picking.");
            //receive gamestatus first, then load mapLinkPage, otherwise, NullPointerException
            player.gameStatus=(GameStatus<String>)player.receiveObject();
            AlterBox alterBox=new AlterBox(this.window, this.player);
            alterBox.display("pickConfirm", "OK", "Successfully pick this group territories");
        }else{
            AlterBox alterBox=new AlterBox(this.window, this.player);
            alterBox.display("stay", "Back", "This group has already picked by other player! Please pick another one.");
        }
    }
}
