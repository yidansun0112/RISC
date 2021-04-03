package edu.duke.ece651.risc.client;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class LoseGameController{
    @FXML
    private Button watchBtn;

    @FXML
    private Button quitBtn;

    @FXML
    private TextArea textArea;
    private Stage window;

    GUIPlayer player;
    public LoseGameController(Stage window, GUIPlayer player){
        this.window = window;
        watchBtn = new Button("Watch");
        quitBtn = new Button("Quit");
        this.player = player;
        this.textArea = new TextArea();
        
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
