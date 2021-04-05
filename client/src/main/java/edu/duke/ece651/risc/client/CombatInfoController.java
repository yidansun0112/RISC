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

public class CombatInfoController{

    @FXML
    private Button confirmBtn;

    @FXML
    private ListView<String> listView;

    // @FXML
    // private ObservableList<String> combatInfo;

    private Stage window;

    GUIPlayer player;
    public CombatInfoController(Stage window, GUIPlayer player){
        this.window = window;
        confirmBtn = new Button("Confirm");
        this.player = player;
        //this.combatInfo = FXCollections.observableArrayList();
        this.listView = new ListView<>();
    }

    @FXML
    public void initialize() {
        // The ListView can not be edited by the user.
        this.listView.setEditable(false);
        // ObservableList<String> combatInfo = FXCollections.observableArrayList();
        // this.listView.setItems(combatInfo);
        // //receive combat info
        // String toAdd = "We call for peace!";
        // int i = 0;
        // while(i<20){
        //     combatInfo.add(toAdd);
        //     if(confirmBtn.isPressed()){
        //         break;
        //     }
        //     i++;
        // }
        //ArrayList<String> combatInfo=(ArrayList<String>)player.receiveObject();
        ObservableList<String> combatInfo = FXCollections.observableArrayList((ArrayList<String>)player.receiveObject());
        this.listView.setItems(combatInfo);
    }


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
