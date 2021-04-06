package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import edu.duke.ece651.risc.shared.GameRoomInfo;
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
    showPage(loaderStart);
  }

  public void showRegLogPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/registerLogin.fxml"));
    loader.setControllerFactory(c -> {
      return new RegisterLoginController(window,player);
    });
    showPage(loader);
  }

  public void showChooseGamePage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/chooseGame.fxml"));
    loader.setControllerFactory(c -> {
      return new ChooseGameController(window,player);
    });
    showPage(loader);
  }

  public void showJoinRoomPage(List<GameRoomInfo> roomList){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/joinRoom.fxml"));
    loader.setControllerFactory(c -> {
      return new JoinRoomController(window,player,roomList);
    });
    showPage(loader);
  }

  public void showReturnRoomPage(List<GameRoomInfo> roomList){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/returnRoom.fxml"));
    loader.setControllerFactory(c -> {
      return new ReturnRoomController(window,player,roomList);
    });
    showPage(loader);
  }

  public void showWaitPlayerComingPage(){
    System.out.println("in show wait page");
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/waitPlayerComing.fxml"));
    loader.setControllerFactory(c -> {
      return new StartController(window,player);
    });
    showPage(loader);
  }

  public void showChoosePlayerNum(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/choosePlayerNum.fxml"));
      loader.setControllerFactory(c->{
        return new StartController(window,player);
      });
      showPage(loader);
  }

  public void showChooseMapPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/chooseMap.fxml"));
    loader.setControllerFactory(c -> {
      return new ChooseMapController(window,player);
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


  public AnchorPane loadPureMap(){
    String mapResource="/ui/map"+Integer.toString(player.playerNum)+".fxml";
    AnchorPane mapPane;
    FXMLLoader mapLoader = new FXMLLoader(getClass().getResource(mapResource));
    mapLoader.setControllerFactory(c -> {
      if(c.equals(StartController.class)){
        return new StartController(window,player);
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

  public AnchorPane loadMap(){
    String mapResource="/ui/map"+Integer.toString(player.playerNum)+"link.fxml";
    AnchorPane mapPane;
    FXMLLoader mapLoader = new FXMLLoader(getClass().getResource(mapResource));
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

  public void showMoveAttack(String type){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/moveAttack.fxml"));
      loader.setControllerFactory(c -> {
        if(c.equals(MoveAttackController.class)){
          return new MoveAttackController(window,player,type);
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

  public void showWatchGame(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/watchGame.fxml"));
      loader.setControllerFactory(c -> {
          if(c.equals(WatchGameController.class)){
          return new WatchGameController(window,player);
        }
        try{
          return c.getConstructor().newInstance();
        }catch(Exception e){
          throw new RuntimeException(e);
        }
      });
    showPage(loader);
  }

  public void showCombatInfo(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/combatInfo.fxml"));
        loader.setControllerFactory(c -> {
            return new CombatInfoController(window,player);
        });
    showPage(loader);
  }

  public void showLoseChoice(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/loseChoice.fxml"));
        loader.setControllerFactory(c -> {
            return new LoseChoiceController(window,player);
        });
    showPage(loader);
  }

  public void showPage(FXMLLoader loader){
    try{
      System.out.println("to show page");
      Scene scene = new Scene(loader.load());
      window.setScene(scene);
      window.show();
      System.out.println("page showed");
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
}
