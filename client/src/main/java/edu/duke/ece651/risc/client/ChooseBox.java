package edu.duke.ece651.risc.client;

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
    levelButton.setOnAction( e -> {
      alterWindow.close();
      AlterBox alterBox = new AlterBox(window, player);
      //need more oprations to check is the order is legal
      alterBox.display("orderConfirm", "Back", "Your order is legal");
     });
    
    unitsButton.setOnAction(e -> {
        alterWindow.close();
        jumpUpgradeUnitsrPage(alterWindow);
    });

    // closeButton.setOnAction(e->window.close());
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, levelButton, unitsButton);
    // layout.setAlignment(Pos.CENTER);
    Scene scene = new Scene(layout);
    alterWindow.setScene(scene);
    alterWindow.showAndWait();
  }

  public void jumpUpgradeUnitsrPage(Stage alterWindow){
    alterWindow.close();
    // try{
    //   FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/upgradeUnits.fxml"));
    //   loaderStart.setControllerFactory(c -> {
    //       if(c.equals(UpgradeUnitsController.class)){
    //       return new UpgradeUnitsController(window,player);
    //     }
    //     try{
    //       return c.getConstructor().newInstance();
    //     }catch(Exception e){
    //       throw new RuntimeException(e);
    //     }
    //   });
    //   Scene scene = new Scene(loaderStart.load());
    //   window.setScene(scene);
    //   window.show();
    // }catch(Exception e){
    //   throw new RuntimeException(e);
    // }
    PageLoader loader=new PageLoader(window,player);
    loader.showUpgradePage();
  }

}
