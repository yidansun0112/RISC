package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.shared.Constant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import org.junit.jupiter.api.Test;
import java.net.UnknownHostException;

public class V1GamePlayerTest {
  @Mock
  private SocketClient clientMock;

  private V1GamePlayer<String> createV1GamePlayer(String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    return new V1GamePlayer<String>(1, clientMock, input, output);
  }

  @Test
  public void test_recvID() throws ClassNotFoundException, IOException, UnknownHostException {
    MockitoAnnotations.initMocks(this);
    when(clientMock.receiveObject()).thenReturn("1");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player = createV1GamePlayer(" ", bytes);
    player.recvID();
    assertEquals(1, player.getPlayerId());
    assertEquals("Your player ID is 1\n", bytes.toString());
  }

  @Test
  public void test_selectPlayerNum() throws IOException {
    MockitoAnnotations.initMocks(this);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player = createV1GamePlayer("a\n1\n6\n4\n", bytes);
    String prompt = "You are the first player in this round, please choose how many players you want in this round.\nPlayer number should be from 2 to 5.\nPlease make your choice:\n";
    String exceptDigit = "For input string: \"a\" Player number should be digits.\n" + "Please do that again!\n";
    String exceptRange = "Player number should be from 2 to 5.\n" + "Please do that again!\n";
    String total = prompt + exceptDigit + exceptRange + exceptRange;
    player.selectPlayerNum();
    assertEquals(total, bytes.toString());
  }

  @Test
  public void test_selectGameMap() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    String mapChoices = "Map 0: HarryPort\nMap 1: The Rings\n";
    when(clientMock.receiveObject()).thenReturn(mapChoices, "Invalid choice", Constant.VALID_MAP_CHOICE_INFO);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player = createV1GamePlayer("a\n2\n1\n", bytes);
    String p1 = "Please choose one map among the following maps.\n";
    String p2 = "Please type the map number that you would like to choose:\n";
    String prompt = p1 + mapChoices + "\n" + p2;
    String exceptNotNum = "For input string: \"a\" Map number should be pure number.\n" + "Please do that again!\n";
    String exceptInvalid = "Invalid choice\n" + "Please do that again!\n";
    String successInfo = Constant.VALID_MAP_CHOICE_INFO + "\n";
    String total = prompt + exceptNotNum + exceptInvalid + successInfo;
    player.selectGameMap();
    assertEquals(total, bytes.toString());
  }

  @Test
  public void test_initGame() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    String mapChoices = "Map 0: HarryPort\nMap 1: The Rings\n";
    when(clientMock.receiveObject()).thenReturn("0", mapChoices, Constant.VALID_MAP_CHOICE_INFO, "1");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player0 = createV1GamePlayer("4\n1\n", bytes);
    V1GamePlayer<String> player1 = createV1GamePlayer(" ", bytes);
    player0.initGame();
    player1.initGame();
    String prompt1 = "You are the first player in this round, please choose how many players you want in this round.\nPlayer number should be from 2 to 5.\nPlease make your choice:\n";
    String p1 = "Please choose one map among the following maps.\n";
    String p2 = "Please type the map number that you would like to choose:\n";
    String prompt2 = p1 + mapChoices + "\n" + p2;
    String successInfo = Constant.VALID_MAP_CHOICE_INFO + "\n";
    String id0 = "Your player ID is 0\n";
    String id1 = "Your player ID is 1\n";
    String total = id0 + prompt1 + prompt2 + successInfo + id1;
    assertEquals(total, bytes.toString());
  }

  @Test
  public void test_pickTerritory() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    String groupChoices = "Group 0: Hogwarts\nGroup 1: Oz\n";
    when(clientMock.receiveObject()).thenReturn(groupChoices, "Invalid choice", Constant.VALID_MAP_CHOICE_INFO);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player = createV1GamePlayer("a\n2\n1\n", bytes);
    String p1 = "Please choose one group among the following groups.\n";
    String p2 = "Please type the group number that you would like to choose:\n";
    String prompt = p1 + groupChoices + "\n" + p2;
    String exceptNotNum = "For input string: \"a\" Group number should be pure number.\n" + "Please do that again!\n";
    String exceptInvalid = "Invalid choice\n" + "Please do that again!\n";
    String successInfo = Constant.VALID_MAP_CHOICE_INFO + "\n";
    String total = prompt + exceptNotNum + exceptInvalid + successInfo;
    player.pickTerritory();
    assertEquals(total, bytes.toString());
  }

  @Test
  public void test_deployUnits() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    String territoryInfo = "Territory 0: Hogwarts\nTerritory 1: Oz\n";
    when(clientMock.receiveObject()).thenReturn(territoryInfo, "succeed", territoryInfo, "succeed",
        Constant.FINISH_DEPLOY_INFO);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player = createV1GamePlayer("1 5\n,5\n1,\na,5\n1,a\n0,5\n1,5\n", bytes);
    String prompt = "Please deploy units in your territories.\n";
    String p1 = "Please type in format \"Territory index,Units deployed in this territory\"\n";
    String p2 = "For example \"2,5\" will deploy 5 units in territory 2\n";
    String doChoice = territoryInfo + "\n" + p1 + p2;
    String exceptComma = "Cannot find \",\" in your deployment.\n" + "Please do that again!\n";
    String exceptNotNumA = "For input string: \"a\" Territory/Units should be pure number.\n"
        + "Please do that again!\n";
    String exceptNotNumNull = "For input string: \"\" Territory/Units should be pure number.\n"
        + "Please do that again!\n";
    String total = prompt + doChoice + exceptComma + doChoice + exceptNotNumNull + doChoice + exceptNotNumNull
        + doChoice + exceptNotNumA + doChoice + exceptNotNumA + doChoice + "succeed\n" + doChoice + "succeed\n"
        + Constant.FINISH_DEPLOY_INFO + "\n";
    player.deployUnits();
    assertEquals(total, bytes.toString());
  }

  @Test
  public void test_issueOrders() throws IOException, ClassNotFoundException {
    MockitoAnnotations.initMocks(this);
    String map = "Territory 0: Hogwarts\nTerritory 1: Oz\n";
    when(clientMock.receiveObject()).thenReturn(map, Constant.LEGAL_ORDER_INFO, map, Constant.LEGAL_ORDER_INFO, map,
        Constant.LEGAL_ORDER_INFO, map);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player = createV1GamePlayer("F\nM\n1 3 5\nA\n2 4 6\nD\n", bytes);
    String ordermenu = "You are player 1, what would you like to do?\n" + "(M)ove\n" + "(A)ttack\n" + "(D)one\n";
    String exceptInfo = "Choices should be among M, A, D\nPlease do that again!\n";
    String info = Constant.LEGAL_ORDER_INFO + "\n";
    String promptAttack = "Please type the content of your attack order.\n"
        + "Format: \"Source_territory_index Destination_territory_index units_to_attack\n"
        + "For example: \"1 3 5\" will move 5 units from territory 1 to attack territory 3\n";
    String promptMove = "Please type the content of your move order.\n"
        + "Format: \"Source_territory_index Destination_territory_index units_to_move\n"
        + "For example: \"1 3 5\" will move 5 units from territory 1 to territory 3\n";
    String total = map + ordermenu + exceptInfo + map + ordermenu + promptMove + info + map + ordermenu + promptAttack
        + info + map + ordermenu + info + map;
    player.issueOrders();
    assertEquals(total, bytes.toString());
  }
}
