package edu.duke.ece651.risc.client;

import java.io.*;
import java.net.UnknownHostException;

/**
 * This class handles a GamePlayer for Version1
 * 
 * @param T String for V1
 */
public class V1GamePlayer<T> implements GamePlayer<T> {
  /** The Id of this player */
  private int playerId;
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

  public void joinGame() {

  }
}