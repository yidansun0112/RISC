package edu.duke.ece651.risc.client;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;

/**
 * This class handles a socket client which implements from GameClient.
 */
public class SocketClient implements GameClient {
  private int hostPort;
  private String hostAddress;
  private Socket clientSocket;

  public SocketClient(int port, String addr) throws UnknownHostException, IOException {
    hostPort = port;
    hostAddress = addr;
    clientSocket = new Socket(hostAddress, hostPort);
  }

  // question: what does connect server do
  @Override
  public void connectServer() {

  }

  @Override
  public void disconnectServer() throws IOException {
    clientSocket.close();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return clientSocket.getInputStream();
  }

  @Override
  public OutputStream getOutputStream() throws IOException {
    return clientSocket.getOutputStream();
  }
}