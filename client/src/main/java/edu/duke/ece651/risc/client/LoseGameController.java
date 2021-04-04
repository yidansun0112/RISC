package edu.duke.ece651.risc.client;

import java.io.IOException;

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

public class LoseGameController{
    @FXML
    private Button watchBtn;

    @FXML
    private Button quitBtn;

    @FXML
    private ListView<String> listView;

    // @FXML
    // private ObservableList<String> combatInfo;

    private Stage window;

    GUIPlayer player;
    public LoseGameController(Stage window, GUIPlayer player){
        this.window = window;
        watchBtn = new Button("Watch");
        quitBtn = new Button("Quit");
        this.player = player;
        //this.combatInfo = FXCollections.observableArrayList();
        this.listView = new ListView<>();
    }

    @FXML
    public void initialize() {
        // The ListView can not be edited by the user.
        this.listView.setEditable(false);
        ObservableList<String> combatInfo = FXCollections.observableArrayList();
        this.listView.setItems(combatInfo);
        String toAdd = "We call for peace!";
        int i = 0;
        while(i<20){
            combatInfo.add(toAdd);
            if(quitBtn.isPressed() || watchBtn.isPressed()){
                break;
            }
            i++;
        }
    }



    @FXML
    public void watchGame() throws ClassNotFoundException, IOException {
        //Load FXML
        // FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/watchGame.fxml"));
        // loaderStart.setControllerFactory(c -> {
        //     return new LoseGameController(window,player);
        // });
        // textArea.appendText("adksahdahsufdhlshjfakhflkdjhflahdkjahsjlahldfasdljashdkjlfhajhdslfjjah");
        // Scene scene = new Scene(loaderStart.load());
        // window.setScene(scene);
        // window.show();
        //textArea.appendText("adksahdahsufdhlshjfakhflkdjhflahdkjahsjlahldfasdljashdkjlfhajhdslfjjah");
        PageLoader loader=new PageLoader(window,player);
        loader.showWatchGame();
    }

    @FXML
    public void quitGame() throws ClassNotFoundException, IOException{
        PageLoader loader=new PageLoader(window,player);
        loader.showStartPage();
    }
}
