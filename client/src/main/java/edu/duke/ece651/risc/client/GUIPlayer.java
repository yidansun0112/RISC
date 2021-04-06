package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.UnknownHostException;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameStatus;

/**
 * This class serves as game model.
 * It will send to and receive from server.
 * And contains other useful information of the game.
 */
public class GUIPlayer {
  /** GameClient that servers communication */
  private GameClient client;
  /** player number to decide which map to load */
  protected int playerNum;
  /** username, used when join/return room */
  protected String username;
  /** game status, contains current player entity and gameboard */
  protected GameStatus<String> gameStatus;
  /** player id to display */
  protected int playerId;


  /**
   * Constructor
   */
  public GUIPlayer(){
    playerId=-1;
  }

  /**
   * Constructor
   * @param client
   */
  public GUIPlayer(GameClient client){
    this.client=client;
  }


  /**
   * This method send object to server.
   * @param o
   */
  public void sendObject(Object o) {
    try{
      client.sendObject(o);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  /**
   * This method receive object from server.
   * @return
   */
  public Object receiveObject(){
    try{
      return client.receiveObject();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  /**
   * This method receive player id from server.
   * @return
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public int recvID() throws ClassNotFoundException, IOException{
    String strID = (String) client.receiveObject();
    int playerId = Integer.parseInt(strID);
    return playerId;
  }

  /**
   * This method let the player re-connect to server.
   */
  public void connect() {
    try{
      client=new SocketClient(12345,Constant.ipaddress);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  /**
   * This method let the player disconnect with server.
   */
  public void disconnect(){
    try{
      client.disconnectServer();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
}
