package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.util.ArrayList;

import edu.duke.ece651.risc.shared.Constant;
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
 * This class handles displaying combat info.
 */
public class CombatInfoController{
    /** Confirm button, load from fxml */
    @FXML
    private Button confirmBtn;
    /** list view to show combat info. */
    @FXML
    private ListView<String> listView;
    /** window to display */
    private Stage window;
    /** player to handle game logic */
    GUIPlayer player;

    /**
     * The constructor
     * 
     * Initialize the listview.
     * 
     * @param window
     * @param player
     */
    public CombatInfoController(Stage window, GUIPlayer player){
        this.window = window;
        confirmBtn = new Button("Confirm");
        this.player = player;
        this.listView = new ListView<>();
    }

    /**
     * Receive combat info from server and display.
     */
    @FXML
    public void initialize() {
        // The ListView can not be edited by the user.
        this.listView.setEditable(false);
        ObservableList<String> combatInfo = FXCollections.observableArrayList((ArrayList<String>)player.receiveObject());
        this.listView.setItems(combatInfo);
    }

    /**
     * This method receive game result from server.
     * If is a string, means game not end, go to losechoice or issueorder.
     * If is an int, means game end, then go to start page.
     */
    @FXML
    public void confirm(){
        Object obj=player.receiveObject();
        if(obj instanceof String){
            String status=(String)obj;
            if(status.equals(Constant.NOT_LOSE_INFO)){
                //go to issueOrder
                player.gameStatus=(GameStatus<String>)player.receiveObject();
                PageLoader loader=new PageLoader(window,player);
                loader.showIssueOrderPage();
            }else if(status.equals(Constant.LOSE_INFO)){
                //go to lose choice page
                //TODO: dont have lose choice and watch game page now
                //TODO: think watch game logic
                PageLoader loader=new PageLoader(window,player);
                loader.showLoseChoice();
            }
        }else{
            //somebody wins, game end
            //alterbox show who wins, then back to start page
            int winner=(int)obj;
            String message="Game end! Winner is player "+Integer.toString(winner);
            AlterBox box=new AlterBox(window, player);
            box.display("gameEnd", "Ok", message);
        }
    }
}
