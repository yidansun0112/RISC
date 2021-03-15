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

  private V1GamePlayer<String> createV1GamePlayer(String inputData, OutputStream bytes){
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    return new V1GamePlayer<String>(-1,clientMock,input,output);
  }

  @Test
  public void test_recvID() throws ClassNotFoundException,IOException,UnknownHostException{
    MockitoAnnotations.initMocks(this);
    when(clientMock.receiveObject()).thenReturn("1");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player=createV1GamePlayer(" ", bytes);
    player.recvID();
    assertEquals(1,player.getPlayerId());
    assertEquals("Your player ID is 1\n",bytes.toString());
  }

  @Test
  public void test_selectPlayerNum() throws IOException{
    MockitoAnnotations.initMocks(this);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player=createV1GamePlayer("11\na\n1\n6\n4\n", bytes);
    // TODO: player doesn't need to know the type of exception, only know why his input is wrong would be enough
    String prompt="You are the first player in this round, please choose how many players you want in this round.\nPlayer number should be from 2 to 5.\nPlease make your choice:\n";
    String exceptLen="Exception thrown:java.lang.IllegalArgumentException: "+"Player number should only be one digit.\n"+"Please do that again!\n";
    String exceptDigit="Exception thrown:java.lang.IllegalArgumentException: "+"Player number should be digit.\n"+"Please do that again!\n";
    String exceptRange="Exception thrown:java.lang.IllegalArgumentException: "+"Player number should be from 2 to 5.\n"+"Please do that again!\n";
    String total=prompt+exceptLen+prompt+exceptDigit+prompt+exceptRange+prompt+exceptRange+prompt;
    player.selectPlayerNum();
    assertEquals(total,bytes.toString());
  }

  @Test
  public void test_selectGameMap() throws IOException,ClassNotFoundException{
    MockitoAnnotations.initMocks(this);
    String mapChoices="Map 0: HarryPort\nMap 1: The Rings\n";
    when(clientMock.receiveObject()).thenReturn(mapChoices,mapChoices,"Invalid choice",mapChoices,Constant.VALID_MAP_CHOICE_INFO);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player=createV1GamePlayer("a\n2\n1\n", bytes);
    String p1="Please choose one map among the following maps.\n";
    String p2="Please type the map number that you would like to choose:\n";
    String prompt=p1+mapChoices+"\n"+p2;
    String exceptNotNum="Exception thrown:java.lang.IllegalArgumentException: "+"Map number should be pure number.\n"+"Please do that again!\n";
    String exceptInvalid="Exception thrown:java.lang.IllegalArgumentException: "+"Invalid choice\n"+"Please do that again!\n";
    String successInfo=Constant.VALID_MAP_CHOICE_INFO + "\n";
    String total=prompt+exceptNotNum+prompt+exceptInvalid+prompt+successInfo;
    player.selectGameMap();
    assertEquals(total,bytes.toString());
  }

  @Test
  public void test_initGame() throws IOException,ClassNotFoundException{
    MockitoAnnotations.initMocks(this);
    String mapChoices="Map 0: HarryPort\nMap 1: The Rings\n";
    when(clientMock.receiveObject()).thenReturn("0",mapChoices,Constant.VALID_MAP_CHOICE_INFO,"1");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player0=createV1GamePlayer("4\n1\n", bytes);
    V1GamePlayer<String> player1=createV1GamePlayer(" ", bytes);
    player0.initGame();
    player1.initGame();
    String prompt1="You are the first player in this round, please choose how many players you want in this round.\nPlayer number should be from 2 to 5.\nPlease make your choice:\n";
    String p1="Please choose one map among the following maps.\n";
    String p2="Please type the map number that you would like to choose:\n";
    String prompt2=p1+mapChoices+"\n"+p2;
    String successInfo=Constant.VALID_MAP_CHOICE_INFO + "\n";
    String id0="Your player ID is 0\n";
    String id1="Your player ID is 1\n";
    String total=id0+prompt1+prompt2+successInfo+id1;
    assertEquals(total,bytes.toString());
  }

  @Test
  public void test_pickTerritory() throws IOException,ClassNotFoundException{
    MockitoAnnotations.initMocks(this);
    String groupChoices="Group 0: Hogwarts\nGroup 1: Oz\n";
    when(clientMock.receiveObject()).thenReturn(groupChoices,groupChoices,"Invalid choice",groupChoices,Constant.VALID_MAP_CHOICE_INFO);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V1GamePlayer<String> player=createV1GamePlayer("a\n2\n1\n", bytes);
    String p1="Please choose one group among the following groups.\n";
    String p2="Please type the group number that you would like to choose:\n";
    String prompt=p1+groupChoices+"\n"+p2;
    String exceptNotNum="Exception thrown:java.lang.IllegalArgumentException: "+"Group number should be pure number.\n"+"Please do that again!\n";
    String exceptInvalid="Exception thrown:java.lang.IllegalArgumentException: "+"Invalid choice\n"+"Please do that again!\n";
    String successInfo=Constant.VALID_MAP_CHOICE_INFO + "\n";
    String total=prompt+exceptNotNum+prompt+exceptInvalid+prompt+successInfo;
    player.pickTerritory();
    assertEquals(total,bytes.toString());
  }
}










