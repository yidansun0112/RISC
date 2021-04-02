package edu.duke.ece651.risc.client;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class handles an alter box.
 * To use this class, you need to pass current window and player.
 * And then call display method.
 */
public class AlterBox {

  private Stage window;
  private GUIPlayer player;

  public AlterBox(Stage window, GUIPlayer player){
    this.window=window;
    this.player=player;
  }

    public void display(String type, String buttonInfo,String message){
      Stage alterWindow = new Stage();
      alterWindow.initModality(Modality.APPLICATION_MODAL);
      alterWindow.setMinWidth(250);
      Label label = new Label();
      label.setText(message);
      Button button = new Button (buttonInfo);
      //button.setOnAction(e->alterWindow.close());
      chooseActions(button, type,alterWindow);
      VBox layout = new VBox(10);
      layout.getChildren().addAll(label,button);
      Labeled position;
      layout.setAlignment(Pos.CENTER);
      Scene scene = new Scene (layout);
      alterWindow.setScene(scene);
      alterWindow.showAndWait();
  }

  public void chooseActions(Button button, String type,Stage alterWindow){
    switch (type){
      case "orderConfirm":
        button.setOnAction(e->jumpIssueOrderPage(alterWindow));
        return;
      default:
        throw new IllegalArgumentException("We don't have this type button");
      }
  }

  /**
   * Note: Your method need to close the alterWindow at first
   * @param alterwindow
   */
  public void jumpIssueOrderPage(Stage alterWindow){
    alterWindow.close();
    try{
      FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/issueOrder.fxml"));
      loaderStart.setControllerFactory(c -> {
        if(c.equals(IssueOrderController.class)){
          return new IssueOrderController(window,player);
        }
        try{
          return c.getConstructor().newInstance();
        }catch(Exception e){
          throw new RuntimeException(e);
        }
      });
      Scene scene = new Scene(loaderStart.load());
      window.setScene(scene);
      window.show();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  
}
