package edu.duke.ece651.risc.client;

import java.io.IOException;

import edu.duke.ece651.risc.shared.Constant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class PickTerritoryController {
    @FXML
    private ChoiceBox<String> groupNumBox;
    private Stage window;
    GUIPlayer player;
  
    public PickTerritoryController(Stage window, GUIPlayer player) {
      this.window = window;
      this.player=player;
      groupNumBox = new ChoiceBox<>();
    }

    @FXML
    public void initialize() {
        groupNumBox.setValue("0");
        for(int i=0;i<player.playerNum;i++){
            groupNumBox.getItems().add(Integer.toString(i));
        }
    }

    @FXML
    public void selectGroup() throws ClassNotFoundException, IOException{
        String choice=groupNumBox.getValue();
        player.sendObject(choice);
        String result=(String)player.receiveObject();
        if(result.equals(Constant.VALID_MAP_CHOICE_INFO)){
            AlterBox alterBox=new AlterBox(this.window, this.player);
            alterBox.display("pickConfirm", "OK", "Successfully pick this group territories");
        }else{
            AlterBox alterBox=new AlterBox(this.window, this.player);
            alterBox.display("stay", "Back", "This group has already picked by other player! Please pick another one.");
        }
    }
}