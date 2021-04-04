package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.Order;
import edu.duke.ece651.risc.shared.V2UpgradeTechLevelOrder;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseBox {
  static boolean isLevel;
  private Stage window;
  private GUIPlayer player;

  public ChooseBox(Stage window, GUIPlayer player){
    this.window=window;
    this.player=player;
  }
  
  public void display(String message) {
    Stage alterWindow = new Stage();
    alterWindow.initModality(Modality.APPLICATION_MODAL);
    alterWindow.setMinWidth(250);
    Label label = new Label();
    label.setText(message);
    Button levelButton = new Button("upgrade Tech level");
    Button unitsButton = new Button("upgrade units");
    Button cancelButton=new Button("Cancel");
    levelButton.setOnAction( e -> {
      alterWindow.close();
      sendUpgradeTechLevel();
     });
    
    unitsButton.setOnAction(e -> {
        alterWindow.close();
        jumpUpgradeUnitsrPage(alterWindow);
    });

    cancelButton.setOnAction(
      e->alterWindow.close()
    );
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, levelButton, unitsButton,cancelButton);
    Scene scene = new Scene(layout);
    alterWindow.setScene(scene);
    alterWindow.showAndWait();
  }

  public void sendUpgradeTechLevel(){
    Order<String> order=new V2UpgradeTechLevelOrder<String>(player.playerId);
    player.sendObject(order);
    String result=(String)player.receiveObject();
    player.gameStatus=(GameStatus<String>)player.receiveObject();
    AlterBox alterBox = new AlterBox(window, player);
    alterBox.display("orderConfirm", "Back", result); 
  }

  public void jumpUpgradeUnitsrPage(Stage alterWindow){
    alterWindow.close();
    PageLoader loader=new PageLoader(window,player);
    loader.showUpgradePage();
  }

}
