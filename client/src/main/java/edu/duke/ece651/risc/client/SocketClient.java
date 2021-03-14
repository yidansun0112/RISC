package edu.duke.ece651.risc.client;
import java.net.Socket;
import java.io.*;

/**
 * This class handles a socket client which implements from GameClient.
 */
public class SocketClient{
  private int hostPort;
  private String hostAddress;
  private Socket clientSocket;

  public SocketClient(int port, String addr){
    hostPort=port;
    hostAddress=addr;
    clientSocket=new Socket(hostAddress,hostPort);
  }

  //question: what does connect server do
  @Override
  public void connectServer(){
    
  }

  @Overrride
  public void disconnectServer(){
    clientSocket.close();
  }
  @Override
  public InputStream getInputStream(){
    return clientSocket.getInputStream();
  }
  @Override
  public OutputStream getOutputStream(){
    return clientSocket.getOutputStream();
  }
}