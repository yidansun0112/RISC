package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.shared.Constant;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * This class handles a GamePlayer for Version1
 * 
 * @param T String for V1
 */
public class V1GamePlayer<T> implements GamePlayer<T> {
  /** The Id of this player */
  private int playerId; // start from 0 (i.e., the first player)
  /** The game client object that connected to the server */
  private GameClient client;
  /** The BufferedReader to read command line inputs from the player */
  private BufferedReader inputReader;
  /** The PrintStream to print message to the command line */
  private PrintStream out;

  /**
   * Constructor of the class, which will initialize the corresponding class
   * fields with the paramters.
   * 
   * @param playerId    the id of this player in the game
   * @param client      the client that does the actual network service
   * @param inputReader the actual BufferedReader object to read the player's
   *                    inputs from the command line
   * @param out         the actual PrintStream that will print message to the
   *                    command line and display to the player
   */
  public V1GamePlayer(int playerId, GameClient client, BufferedReader inputReader, PrintStream out) {
    this.playerId = playerId;
    this.client = client;
    this.inputReader = inputReader;
    this.out = out;
  }

  /**
   * Constructor that takes in a port number and a string of IP address. It
   * creates a game client, and initialize the I/O stream field with corresponding
   * parameters.
   * 
   * This constructor will temporarily set the playerId with value -1. This
   * playerId will get its correct value after joined a game and receive his/her
   * player id from the game server.
   * 
   * @param serverPort the port number of the game server
   * @param serverAddr the IP address of the game server
   * @throws UnknownHostException
   * @throws IOException
   */
  // public V1GamePlayer(int serverPort, String serverAddr, BufferedReader inputReader, PrintStream out)
  //     throws UnknownHostException, IOException {
  //   this(-1, new SocketClient(serverPort, serverAddr), inputReader, out);
  // }

  /**
   * Getter for playerId
   * 
   * @return playerId
   */
  public int getPlayerId() {
    return playerId;
  }

  /**
   * This method receive playerID from server. It sets the playerId field after
   * receiving it, and print the ID to the command line. (This method is called by
   * joinGame() and the player ID will be changed at that time.)
   */
  public void recvID() throws ClassNotFoundException, IOException {
    String strID = (String) client.receiveObject();
    playerId = Integer.parseInt(strID);
    out.println("Your player ID is " + strID);
  }

  /**
   * Let the first player decide how many players he/she want in this game. If the
   * input is invalid due to any one of the following reason: 1. contains
   * non-number character 2. input is a number, but less than allowed minimum
   * number of player in a game, or... 3. ...grater than allowed maximum number of
   * player in a game
   * 
   * it will show the error reason and let the player re-input. If valid, it will
   * send the player in put to the game server.
   * 
   * The method is called if the current player's id is 0, i.e., he/she is the
   * first player that connected to the server.
   * 
   * 
   * @throws IOException
   */
  public void selectPlayerNum() throws IOException {
    out.println("You are the first player in this round, please choose how many players you want in this round.");
    out.println("Player number should be from "+Constant.MIN_PLAYER_NUM+" to "+Constant.MAX_PLAYER_NUM+".");
    out.println("Please make your choice:");
    while(true){
      try {
        String strNum = inputReader.readLine();
        int num;
        try{
          num = Integer.parseInt(strNum);
        }catch(NumberFormatException e){
          out.println(e.getMessage()+" Player number should be digits.");
          out.println("Please do that again!");
          continue;
        }
        if (num < Constant.MIN_PLAYER_NUM || num > Constant.MAX_PLAYER_NUM) {
          throw new IllegalArgumentException("Player number should be from "+Constant.MIN_PLAYER_NUM+" to "+Constant.MAX_PLAYER_NUM+".");
        }
        // Choice is valid, send it to the server
        client.sendObject(strNum);
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
      }
    }
  }

  /**
   * Let the player input a number to choose a map. The input number is the map index 
   * of all available maps that suitable for this amount of players.
   * 
   * If the player input is invalid due to one of the following reasons:
   * 1. contains non-number characters
   * 2. is a valid number, but not valid for a map index (decided by the game server)
   * 
   * this method will require the player to re-input, until the server regards this input
   * is valid.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void selectGameMap() throws IOException, ClassNotFoundException {
    // NOTE: RECEIVE string from server: receive the description of all available maps
    String mapChoice = (String) client.receiveObject();
    out.println("Please choose one map among the following maps.");
    out.println(mapChoice);
    out.println("Please type the map number that you would like to choose:");
    while(true){
      try {
        String strNum = inputReader.readLine();
        try{
          int num = Integer.parseInt(strNum);
        }catch(NumberFormatException e){
          out.println(e.getMessage()+" Map number should be pure number.");
          out.println("Please do that again!");
          continue;
        }
        // NOTE: SEND string to server: send the map index of choosed map
        client.sendObject(strNum);
        // NOTE: RECEIVE string from server: 
        String choiceInfo = (String) client.receiveObject();
        if (choiceInfo != Constant.VALID_MAP_CHOICE_INFO) {
          throw new IllegalArgumentException(choiceInfo);
        }
        out.println(choiceInfo);
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
      }
    }
  }

  public void initGame() throws IOException, ClassNotFoundException {
    recvID();
    if (playerId == 0) {
      selectPlayerNum();
      selectGameMap();
    }
  }

  /**
   * Let the player choose a group of territory. If successfull obtained that group of territory, 
   * this method will quit in a manner of tail-recursion.
   * 
   * If the player failed to obtain a group due to one of the following reasons:
   * 1. input contains non-number character
   * 2. this group does not exist (decided by server)
   * 3. other player has already occupied this group (decided by server)
   * it will require the current player to re-input, until successful get a group of territory.
   * 
   * Really similar with selectGameMap(), need to abstract out, but I am sleepy
   * now.
   */
  public void pickTerritory() throws IOException, ClassNotFoundException {
    // NOTE: RECEIVE string from server: receive territory group description message
    String mapGroup = (String) client.receiveObject();
    out.println("Please choose one group among the following groups.");
    out.println(mapGroup);
    out.println("Please type the group number that you would like to choose:");
    while(true){
      try {
        String strNum = inputReader.readLine();
        try{
          int num = Integer.parseInt(strNum);
        }catch(NumberFormatException e){
          out.println(e.getMessage()+" Group number should be pure number.");
          out.println("Please do that again!");
          continue;
        }
        // NOTE: SEND string to server: send the map index of choosed map
        client.sendObject(strNum);
        // NOTE: RECEIVE string from server: 
        String choiceInfo = (String) client.receiveObject();
        if (choiceInfo != Constant.VALID_MAP_CHOICE_INFO) {
          throw new IllegalArgumentException(choiceInfo);
        }
        out.println(choiceInfo);
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
      }
    }
  }

  public void deployUnits() throws IOException, ClassNotFoundException{
    out.println("Please deploy units in your territories.");
    String msg=(String)client.receiveObject();
    while(msg!=Constant.FINISH_DEPLOY_INFO){
      out.println(msg);
      out.println("Please type in format \"Territory index,Units deployed in this territory\"");
      out.println("For example \"2,5\" will deploy 5 units in territory 2");
      String choice=inputReader.readLine();
      try{
        ArrayList<Integer> deployment=stringParser(choice);
        client.sendObject(deployment);
        String info=(String)client.receiveObject();
        out.println(info);
        msg=(String)client.receiveObject();
      }catch(NumberFormatException e){
        out.println(e.getMessage()+" Territory/Units should be pure number.");
        out.println("Please do that again!");
        continue;
      }catch(IllegalArgumentException e){
        out.println(e.getMessage());
        out.println("Please do that again!");
        continue;
      }
    }
    out.println(msg);
  }

  private ArrayList<Integer> stringParser(String str){
    int comma=str.indexOf(",");
    if(comma==-1){
      throw new IllegalArgumentException("Cannot find \",\" in your deployment.");
    }
    String strIndex=str.substring(0,comma);
    String strUnits=str.substring(comma+1);
    int index=Integer.parseInt(strIndex);
    int units=Integer.parseInt(strUnits);
    ArrayList<Integer> deployment=new ArrayList<Integer>();
    deployment.add(index);
    deployment.add(units);
    return deployment;
  }

  public void issueOrders() throws IOException, ClassNotFoundException{
    //receive map, print
    //print ordermenu
    //get order title (M,A,D), should add into constant
    //if not correct, continue ask input
    //then ask order content, 
  }

  public void showOrderMenu(){
    out.println("You are player "+playerId+", what would you like to do?");
    out.println("(M)ove");
    out.println("(A)ttack");
    out.println("(D)one");
  }

  
}
