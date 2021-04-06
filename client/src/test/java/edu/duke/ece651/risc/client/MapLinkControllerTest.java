package edu.duke.ece651.risc.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.risc.shared.Army;
import edu.duke.ece651.risc.shared.Board;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.GameStatus;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.Territory;
import edu.duke.ece651.risc.shared.V2BoardFactory;
import edu.duke.ece651.risc.shared.V2GameBoard;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class MapLinkControllerTest {
  @Mock
  private GUIPlayer player;
  @Mock
  private GameStatus<String> gameStatus;
  @Mock
  private PlayerEntity<String> playerEntity;
  private MapLinkController mapCont;
  private TerrInfoController terrCont;

  @Start
  private void start(Stage stage) {
    MockitoAnnotations.initMocks(this);
    player.playerNum=5;
    player.playerId=1;
    player.gameStatus=gameStatus;
    when(gameStatus.getCurrPlayer()).thenReturn(playerEntity);
    when(playerEntity.getFoodResourceAmount()).thenReturn(5);
    when(playerEntity.getTechResourceAmount()).thenReturn(5);
    when(playerEntity.getTechLevel()).thenReturn(1);
    BoardFactory<String> factory=new V2BoardFactory<>();
    Board<String> board=factory.makeGameBoard(5);
    ArrayList<Territory<String>> territories=board.getTerritories();
    territories.get(0).setOwner(1);
    when(gameStatus.getGameBoard()).thenReturn(board);
    terrCont = new TerrInfoController();
    terrCont.ownerValue=new Label();
    terrCont.nameValue=new Label();
    terrCont.foodValue=new Label();
    terrCont.techValue=new Label();
    terrCont.lv0Value=new Label();
    terrCont.lv1Value=new Label();
    terrCont.lv2Value=new Label();
    terrCont.lv3Value=new Label();
    terrCont.lv4Value=new Label();
    terrCont.lv5Value=new Label();
    terrCont.lv6Value=new Label();
    mapCont= new MapLinkController(player);
    mapCont.terrInfoController=terrCont;
  }

  @Test
  public void test_hyperLink(FxRobot robot) {
    //when(player.receiveObject()).thenReturn("Your Order is Legal!", gameStatus);
    Platform.runLater(() -> {
      mapCont.linkZero();
      mapCont.linkOne();
      mapCont.linkTwo();
      mapCont.linkThree();
      mapCont.linkFour();
      mapCont.linkFive();
      mapCont.linkSix();
      mapCont.linkSeven();
      mapCont.linkEight();
      mapCont.linkNine();
      mapCont.linkTen();
      mapCont.linkEleven();
      mapCont.linkTwelve();
      mapCont.linkThirteen();
      mapCont.linkFourteen();
    });
    WaitForAsyncUtils.waitForFxEvents();   
  }

  @Test
  public void test_show_true(FxRobot robot) {
    //when(player.receiveObject()).thenReturn("Your Order is Legal!", gameStatus);
    Platform.runLater(() -> {
      when(gameStatus.getCanShowLatest()).thenReturn(true);
      mapCont.linkZero();
      mapCont.linkOne();
    });
    WaitForAsyncUtils.waitForFxEvents();   
  }

}
