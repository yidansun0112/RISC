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

/**
 * This class handles for loading different kinds of pages
 */
public class PageLoader {
  private Stage window;
  private GUIPlayer player;

  public PageLoader(Stage window,GUIPlayer player){
    this.window=window;
    this.player=player;
  }

  /**
   * load the start game page
   */
  public void showStartPage() {
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ui/start.fxml"));
    loaderStart.setControllerFactory(c->{
      return new StartController(window,player);
    });
    showPage(loaderStart);
  }

  /**
   * load the register and loggin page
   */
  public void showRegLogPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/registerLogin.fxml"));
    loader.setControllerFactory(c -> {
      return new RegisterLoginController(window,player);
    });
    showPage(loader);
  }

  /**
   * load the choose game page
   */
  public void showChooseGamePage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/chooseGame.fxml"));
    loader.setControllerFactory(c -> {
      return new ChooseGameController(window,player);
    });
    showPage(loader);
  }

  /**
   * load the join room pagg
   * @param roomList
   */
  public void showJoinRoomPage(List<GameRoomInfo> roomList){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/joinRoom.fxml"));
    loader.setControllerFactory(c -> {
      return new JoinRoomController(window,player,roomList);
    });
    showPage(loader);
  }

  /**
   * load the page which allows player to choose to return back to a room
   * @param roomList
   */
  public void showReturnRoomPage(List<GameRoomInfo> roomList){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/returnRoom.fxml"));
    loader.setControllerFactory(c -> {
      return new ReturnRoomController(window,player,roomList);
    });
    showPage(loader);
  }


  /**
   * load the page that indicates player to wait for other players comming
   */
  public void showWaitPlayerComingPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/waitPlayerComing.fxml"));
    loader.setControllerFactory(c -> {
      return new StartController(window,player);
    });
    showPage(loader);
  }

  /**
   * load the choose player number page
   */
  public void showChoosePlayerNum(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/choosePlayerNum.fxml"));
      loader.setControllerFactory(c->{
        return new StartController(window,player);
      });
      showPage(loader);
  }

  /**
   * load the choose map page
   */
  public void showChooseMapPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/chooseMap.fxml"));
    loader.setControllerFactory(c -> {
      return new ChooseMapController(window,player);
    });
    showPage(loader);
  }

  /**
   * load the pick territory page
   */
  public void showPickTerritoryPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/pickTerritory.fxml"));
    loader.setControllerFactory(c -> {
      return new PickTerritoryController(window,player);
    });
    showPage(loader);
  }

  /**
   * load the deploy units page
   */
  public void showDeployUnitsPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/deployUnits.fxml"));
    loader.setControllerFactory(c -> {
      return new DeployUnitsController(window,player);
    });
    showPage(loader);
  }

  /**
   * load the pure map image 
   * @return
   */
  public AnchorPane loadPureMap(){
    String mapResource="/ui/map"+Integer.toString(player.playerNum)+".fxml";
    AnchorPane mapPane;
    FXMLLoader mapLoader = new FXMLLoader(getClass().getResource(mapResource));
    mapLoader.setControllerFactory(c -> {
      // if(c.equals(StartController.class)){
      //   return new StartController(window,player);
      // }
      // try{
      //   return c.getConstructor().newInstance();
      // }catch(Exception e){
      //   throw new RuntimeException(e);
      // }
      return new StartController(window, player);
    });
    try{
      mapPane=mapLoader.load();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
    return mapPane;
  }

  /**
   * load the map for different number of players, which has info links 
   * @return
   */
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


  /**
   * load the issue order page
   */
  public void showIssueOrderPage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/issueOrder.fxml"));
      loader.setControllerFactory(c -> {
        // if(c.equals(IssueOrderController.class)){
        //   return new IssueOrderController(window,player);
        // }
        // try{
        //   return c.getConstructor().newInstance();
        // }catch(Exception e){
        //   throw new RuntimeException(e);
        // }
        return new IssueOrderController(window,player);
      });
    showPage(loader);
  }

  /**
   * set the laoction of the map
   * @param rootPane
   * @param mapPane
   */
  public void putMap(AnchorPane rootPane,AnchorPane mapPane){
    rootPane.getChildren().add(mapPane);
    AnchorPane.setTopAnchor(mapPane,0.0);
    AnchorPane.setRightAnchor(mapPane,0.0);
    AnchorPane.setLeftAnchor(mapPane,0.0);
    AnchorPane.setBottomAnchor(mapPane,200.0);
  }

  /**
   * get the map for different players
   * @param rootPane
   * @param mapPane
   */
  public void putPureMap(AnchorPane rootPane,AnchorPane mapPane){
    rootPane.getChildren().add(mapPane);
    AnchorPane.setTopAnchor(mapPane,0.0);
    AnchorPane.setRightAnchor(mapPane,300.0);
    AnchorPane.setLeftAnchor(mapPane,0.0);
    AnchorPane.setBottomAnchor(mapPane,200.0);
  }

  /**
   * load the move/attack page
   * @param type
   */
  public void showMoveAttack(String type){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/moveAttack.fxml"));
      loader.setControllerFactory(c -> {
        // if(c.equals(MoveAttackController.class)){
        //   return new MoveAttackController(window,player,type);
        // }
        // try{
        //   return c.getConstructor().newInstance();
        // }catch(Exception e){
        //   throw new RuntimeException(e);
        // }
        return new MoveAttackController(window, player, type);
      });
      showPage(loader);
  }

  /**
   * load the upgrade units page
   */
  public void showUpgradePage(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/upgradeUnits.fxml"));
      loader.setControllerFactory(c -> {
        //   if(c.equals(UpgradeUnitsController.class)){
        //   return new UpgradeUnitsController(window,player);
        // }
        // try{
        //   return c.getConstructor().newInstance();
        // }catch(Exception e){
        //   throw new RuntimeException(e);
        // }
        return new UpgradeUnitsController(window, player);
      });
      showPage(loader);
  }

  /**
   * load the page in which player can the combat info after he/she lost
   */
  public void showWatchGame(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/watchGame.fxml"));
      loader.setControllerFactory(c -> {
        //   if(c.equals(WatchGameController.class)){
        //   return new WatchGameController(window,player);
        // }
        // try{
        //   return c.getConstructor().newInstance();
        // }catch(Exception e){
        //   throw new RuntimeException(e);
        // }
        return new WatchGameController(window, player);
      });
    showPage(loader);
  }

  /**
   * load the combat info
   */
  public void showCombatInfo(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/combatInfo.fxml"));
        loader.setControllerFactory(c -> {
            return new CombatInfoController(window,player);
        });
    showPage(loader);
  }

  /**
   * load the page to make player to choose whether quite or not when loses
   */
  public void showLoseChoice(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/loseChoice.fxml"));
        loader.setControllerFactory(c -> {
            return new LoseChoiceController(window,player);
        });
    showPage(loader);
  }

  /**
   * load the scene from each fxml file
   * @param loader
   */
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
