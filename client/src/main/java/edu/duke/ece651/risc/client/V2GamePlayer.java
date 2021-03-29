package edu.duke.ece651.risc.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;

public class V2GamePlayer extends Application{
  private Stage Window;
  private SocketClient client;

  public void showStartView(Stage Window) throws IOException {
    //load the start game page
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Views/StartGame.fxml"));
    loaderStart.setControllerFactory(c->{
      return new StartController(player, Window);
    });
    Scene scene = new Scene(loaderStart.load());
    Window.setScene(scene);
    Window.show();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.Window = primaryStage;
    this.client= new SocketClient(12345,"127.0.0.1");
    Displayable d = new Text();
    player.addDisplayable(d);
    //start the game and receive their own id and store it
    player.ReceiveID();
    //after received, continue to display the start page
    showStartView(player, this.Window);
  }
}
