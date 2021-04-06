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

  /** Stage to be passed to other controllers */
  private Stage window;
  /** GUIPlayer to be passed to other controllers */
  private GUIPlayer player;

  /**
   * The constructor
   * 
   * @param window to pass
   * @param player to pass
   */
  public AlterBox(Stage window, GUIPlayer player){
    this.window=window;
    this.player=player;
  }

  /**
   * This method will display an alert window with the given message, and set the action of the button based on type.
   * 
   * @param type decide how to set button action
   * @param buttonInfo decide the button text
   * @param message the message shown on the window
   */
    public void display(String type, String buttonInfo,String message){
      Stage alterWindow = new Stage();
      alterWindow.initModality(Modality.APPLICATION_MODAL);
      alterWindow.setMinWidth(250);
      Label label = new Label();
      label.setText(message);
      Button button = new Button (buttonInfo);
      chooseActions(button, type,alterWindow);
      VBox layout = new VBox(10);
      layout.getChildren().addAll(label,button);

      layout.setAlignment(Pos.CENTER);
      Scene scene = new Scene (layout);
      alterWindow.setScene(scene);
      alterWindow.showAndWait();
  }

  /**
   * This function set actions on button based on button type
   * 
   * @param button to set
   * @param type decide the action 
   * @param alterWindow window to close
   */
  public void chooseActions(Button button, String type,Stage alterWindow){
    switch (type){
      case "orderConfirm":
        button.setOnAction(e->jumpIssueOrderPage(alterWindow));
        return;
      case "pickConfirm":
        button.setOnAction(e->jumpDeployUnitsPage(alterWindow));
        return;
      case "stay":
        button.setOnAction(e->alterWindow.close());
        return;
      case "backChooseGame":
        button.setOnAction(e->jumpChooseGamePage(alterWindow));
        return;
      case "gameEnd":
        button.setOnAction(e->jumpStartPage(alterWindow));
        return;
      default:
        throw new IllegalArgumentException("We don't have this type button");
      }
  }

  /**
   * Jump to issue order page
   * 
   * @param alterwindow window to close
   */
  public void jumpIssueOrderPage(Stage alterWindow){
    alterWindow.close();
    PageLoader loader=new PageLoader(window, player);
    loader.showIssueOrderPage();
  }

  /**
   * Jump to deploy units page
   * 
   * @param alterWindow window to close
   */
  public void jumpDeployUnitsPage(Stage alterWindow){
    alterWindow.close();
    PageLoader loader=new PageLoader(window,player);
    loader.showDeployUnitsPage();
  }

  /**
   * Jump to choose game page
   * 
   * @param alterWindow window to close
   */
  public void jumpChooseGamePage(Stage alterWindow){
    alterWindow.close();
    PageLoader loader=new PageLoader(window,player);
    loader.showChooseGamePage();
  }

  /**
   * Jump to start page
   * 
   * @param alterWindow
   */
  public void jumpStartPage(Stage alterWindow){
    alterWindow.close();
    PageLoader loader=new PageLoader(window, player);
    loader.showStartPage();
  }
  
}
