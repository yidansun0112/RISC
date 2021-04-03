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

  public GUIPlayer(GameClient client){
    this.client=client;
  }


  public void sendObject(Object o) throws IOException {
    client.sendObject(o);
  }

  public Object receiveObject() throws ClassNotFoundException, IOException {
    return client.receiveObject();
  }

  public int recvID() throws ClassNotFoundException, IOException{
    String strID = (String) client.receiveObject();
    int playerId = Integer.parseInt(strID);
    return playerId;
  }

  public void connect() throws UnknownHostException,IOException{
    client=new SocketClient(12345,Constant.ipaddress);
  }

  public void disconnect() throws IOException{
    client.disconnectServer();
  }
}
