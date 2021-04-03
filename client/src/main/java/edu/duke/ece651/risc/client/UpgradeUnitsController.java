package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UpgradeUnitsController implements Initializable{
  // @FXML
  // MapLinkController mapLinkController;
  @FXML
  AnchorPane mapPane;
  @FXML
  AnchorPane rootPane;
  @FXML
  private Button confirmBtn;
  @FXML
  private ChoiceBox<String> terrBox;
  @FXML
  private ChoiceBox<String> fromBox;
  @FXML
  private ChoiceBox<String> toBox;
  @FXML
  private ChoiceBox<String> amountBox;
  // @FXML
  // Hyperlink terr0;
  // @FXML
  // Hyperlink terr1;
  private Stage window;
  private GUIPlayer player;

  public UpgradeUnitsController(Stage window,GUIPlayer player) {
    this.window = window;
    this.player=player;
    terrBox=new ChoiceBox<>();
    fromBox=new ChoiceBox<>();
    toBox=new ChoiceBox<>();
    amountBox=new ChoiceBox<>();
    // FXMLLoader mapLoader = new FXMLLoader(getClass().getResource("/ui/map2link.fxml"));
    // mapLoader.setControllerFactory(c -> {
    //   if(c.equals(MapLinkController.class)){
    //     return new MapLinkController(player);
    //   }
    //   try{
    //     return c.getConstructor().newInstance();
    //   }catch(Exception e){
    //     throw new RuntimeException(e);
    //   }
    // });
    // try{
    //   mapPane=mapLoader.load();
    // }catch(Exception e){
    //   throw new RuntimeException(e);
    // }
    PageLoader loader=new PageLoader(window,player);
    mapPane=loader.loadMap("/ui/map2link.fxml");
  }

  @FXML
  public void confirm(){
    // pop up a alert box, saying if the order is legal
    //back button of the window would go back to issue order scene whether legal or illegal
    AlterBox alterBox=new AlterBox(window, player);
    alterBox.display("orderConfirm", "Back", "Your order is legal");
  }

  @FXML
  public void cancel(){
    PageLoader loader=new PageLoader(window,player);
    loader.showIssueOrderPage();
  }

  public void initialize(URL url, ResourceBundle rb){
    PageLoader loader=new PageLoader(window,player);
    loader.putMap(rootPane, mapPane);
  } 
}

