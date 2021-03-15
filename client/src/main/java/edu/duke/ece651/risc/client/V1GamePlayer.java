package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.shared.Constant;

import java.io.*;
import java.net.UnknownHostException;

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
  public V1GamePlayer(int serverPort, String serverAddr, BufferedReader inputReader, PrintStream out)
      throws UnknownHostException, IOException {
    this(-1, new SocketClient(serverPort, serverAddr), inputReader, out);
  }

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
    // TODO: remove magic number here
    out.println("Player number should be from 2 to 5.");
    out.println("Please make your choice:");
    try {
      String strNum = inputReader.readLine();
      if (strNum.length() != 1) { // TODO: pre-assumption: what if in evo2 we allow ten player in a game?
        throw new IllegalArgumentException("Player number should only be one digit.");
      }
      if (!Character.isDigit(strNum.charAt(0))) {
        // TODO: only parseInt would be enough to check the input here, to apply this
        // refactoring, the test code needs to change also
        throw new IllegalArgumentException("Player number should be digit.");
      }
      int num = Integer.parseInt(strNum);
      if (num < Constant.MIN_PLAYER_NUM || num > Constant.MAX_PLAYER_NUM) {
        // TODO: remove magic number here
        throw new IllegalArgumentException("Player number should be from 2 to 5.");
      }
      // Choice is valid, send it to the server
      client.sendObject(strNum);
    } catch (IllegalArgumentException e) {
      out.println("Exception thrown:" + e);
      out.println("Please do that again!");
      selectPlayerNum();
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
    try {
      String strNum = inputReader.readLine();
      if (!allDigits(strNum)) {
        throw new IllegalArgumentException("Map number should be pure number.");
      }
      // NOTE: SEND string to server: send the map index of choosed map
      client.sendObject(strNum);
      // NOTE: RECEIVE string from server: 
      String choiceInfo = (String) client.receiveObject();
      if (choiceInfo != Constant.VALID_MAP_CHOICE_INFO) {
        throw new IllegalArgumentException(choiceInfo);
      }
      out.println(choiceInfo);
    } catch (IllegalArgumentException e) {
      out.println("Exception thrown:" + e);
      out.println("Please do that again!");
      selectGameMap();
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
    try {
      String strNum = inputReader.readLine();
      if (!allDigits(strNum)) {
        throw new IllegalArgumentException("Group number should be pure number.");
      }
      // NOTE: SEND string to server: send the player's input group number as a string
      client.sendObject(strNum);
      // NOTE: RECEIVE string from server: receive whether successfull occupy that
      // group of territory.
      String choiceInfo = (String) client.receiveObject();
      if (choiceInfo != Constant.VALID_MAP_CHOICE_INFO) { // on success choosing, the method will quit from here
        throw new IllegalArgumentException(choiceInfo);
      }
      out.println(choiceInfo);
    } catch (IllegalArgumentException e) {
      out.println("Exception thrown:" + e);
      out.println("Please do that again!");
      pickTerritory(); // tail recursion
    }
  }

  /**
   * Helper function to check whether a string is all digits.
   * 
   * @param strNum
   * @return
   */
  private boolean allDigits(String strNum) {
    for (int i = 0; i < strNum.length(); i++) {
      if (!Character.isDigit(strNum.charAt(i))) {
        return false;
      }
    }
    return true;
  }

}
