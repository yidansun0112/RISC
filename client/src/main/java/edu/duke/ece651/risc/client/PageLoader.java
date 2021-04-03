package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PageLoader {
  private Stage window;
  private GUIPlayer player;

  public PageLoader(Stage window,GUIPlayer player){
    this.window=window;
    this.player=player;
  }

  public void showStartPage() {
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/start.fxml"));
    loaderStart.setControllerFactory(c->{
      return new StartController(window,player);
    });
    // Scene scene = new Scene(loaderStart.load());
    // window.setScene(scene);
    // window.show();
    showPage(loaderStart);
  }

  public void showRegLogPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/registerLogin.fxml"));
    loader.setControllerFactory(c -> {
      return new RegisterLoginController(window);
    });
    showPage(loader);
  }

  public void showChooseMapPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/chooseMap.fxml"));
    loader.setControllerFactory(c -> {
      return new StartController(window,player);
    });
    showPage(loader);
  }

  public void showPickTerritoryPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/pickTerritory.fxml"));
    loader.setControllerFactory(c -> {
      return new PickTerritoryController(window,player);
    });
    showPage(loader);
  }

  public void showDeployUnitsPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/deployUnits.fxml"));
    loader.setControllerFactory(c -> {
      if(c.equals(DeployUnitsController.class)){
        return new DeployUnitsController(window,player);
      }
      try{
        return c.getConstructor().newInstance();
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    });
    showPage(loader);
  }

  public AnchorPane loadMap(String resource){
    AnchorPane mapPane;
    FXMLLoader mapLoader = new FXMLLoader(getClass().getResource(resource));
    mapLoader.setControllerFactory(c -> {
      if(c.equals(MapLinkController.class)){
        return new MapLinkController(player);
      }
      try{
        return c.getConstructor().newInstance();
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    });
    try{
      mapPane=mapLoader.load();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
    return mapPane;
  }


  public void showIssueOrderPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/issueOrder.fxml"));
      loader.setControllerFactory(c -> {
        if(c.equals(IssueOrderController.class)){
          return new IssueOrderController(window,player);
        }
        try{
          return c.getConstructor().newInstance();
        }catch(Exception e){
          throw new RuntimeException(e);
        }
      });
    showPage(loader);
  }

  public void putMap(AnchorPane rootPane,AnchorPane mapPane){
    rootPane.getChildren().add(mapPane);
    AnchorPane.setTopAnchor(mapPane,0.0);
    AnchorPane.setRightAnchor(mapPane,0.0);
    AnchorPane.setLeftAnchor(mapPane,0.0);
    AnchorPane.setBottomAnchor(mapPane,200.0);
  }

  public void showMoveAttack(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/moveAttack.fxml"));
      loader.setControllerFactory(c -> {
        if(c.equals(MoveAttackController.class)){
          return new MoveAttackController(window,player);
        }
        try{
          return c.getConstructor().newInstance();
        }catch(Exception e){
          throw new RuntimeException(e);
        }
      });
      showPage(loader);
  }

  public void showUpgradePage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/upgradeUnits.fxml"));
      loader.setControllerFactory(c -> {
          if(c.equals(UpgradeUnitsController.class)){
          return new UpgradeUnitsController(window,player);
        }
        try{
          return c.getConstructor().newInstance();
        }catch(Exception e){
          throw new RuntimeException(e);
        }
      });
      showPage(loader);
  }

  public void showPage(FXMLLoader loader){
    try{
      Scene scene = new Scene(loader.load());
      window.setScene(scene);
      window.show();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
}
