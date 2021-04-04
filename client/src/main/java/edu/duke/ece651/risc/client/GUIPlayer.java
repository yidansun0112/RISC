package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.UnknownHostException;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameStatus;

public class GUIPlayer {
  private GameClient client;
  protected int playerNum;
  protected String username;
  protected GameStatus<String> gameStatus;
  protected int playerId;

  public GUIPlayer(GameClient client){
    this.client=client;
  }


  public void sendObject(Object o) {
    try{
      client.sendObject(o);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  public Object receiveObject(){
    try{
      return client.receiveObject();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  public int recvID() throws ClassNotFoundException, IOException{
    String strID = (String) client.receiveObject();
    int playerId = Integer.parseInt(strID);
    return playerId;
  }

  public void connect() {
    try{
      client=new SocketClient(12345,Constant.ipaddress);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  public void disconnect(){
    try{
      client.disconnectServer();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
}
