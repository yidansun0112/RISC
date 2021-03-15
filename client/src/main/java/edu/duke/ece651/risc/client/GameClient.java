package edu.duke.ece651.risc.client;

import java.io.IOException;

/**
 * This class handles a basic GameClient interface
 */
public interface GameClient {
  public void sendObject(Object o) throws IOException;

  public Object receiveObject() throws ClassNotFoundException, IOException;

  public void disconnectServer() throws IOException;
}