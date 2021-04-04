package edu.duke.ece651.risc.client;

import java.io.IOException;

public class GUIPlayer {
  private GameClient client;

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
}
