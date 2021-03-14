package edu.duke.ece651.risc.client;

import java.io.*;
import java.net.UnknownHostException;

/**
 * This class handles a GamePlayer for Version1
 * 
 * @param T String for V1
 */
public class V1GamePlayer<T> implements GamePlayer<T>{
  private int playerId;
  private GameClient client;
  private InputStream in;
  private OutputStream out;

  public V1GamePlayer(int port,String addr) throws UnknownHostException, IOException{
    playerId=-1;
    //
    client=new SocketClient(port,addr);
    in=client.getInputStream();
    out=client.getOutputStream();
  }

  public void joinGame(){
    
  }
}