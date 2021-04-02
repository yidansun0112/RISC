package edu.duke.ece651.risc.client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseBox {
    static boolean isLevel;
    public static boolean didplay(String title, String message){
      Stage window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle(title);
      window.setMinWidth(250);
      Label label = new Label();
      label.setText(message);
      Button levelButton = new Button ("upgrade level");
      Button unitsButton = new Button ("upgrade units");
      levelButton.setOnAction(e ->{
        isLevel = true;
        window.close();
      });
      
      unitsButton.setOnAction(e ->{
        isLevel = false;
        window.close();
      });
      
      //closeButton.setOnAction(e->window.close());
      VBox layout = new VBox(10);
      layout.getChildren().addAll(label,levelButton, unitsButton);
      //layout.setAlignment(Pos.CENTER);
      Scene scene = new Scene (layout);
      window.setScene(scene);
      window.showAndWait();
      return isLevel;
    }

}
