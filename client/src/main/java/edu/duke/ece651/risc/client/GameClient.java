package edu.duke.ece651.risc.client;

import java.io.IOException;

/**
 * This class handles a basic GameClient interface
 */
public interface GameClient {
  /**
   * Send data to the server
   * 
   * @param o the data that to be sent
   * @throws IOException
   */
  public void sendObject(Object o) throws IOException;

  /**
   * Receive the data sent by the server
   * 
   * @return the data received
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public Object receiveObject() throws ClassNotFoundException, IOException;

  /**
   * Disconnect from the game server
   * 
   * @throws IOException
   */
  public void disconnectServer() throws IOException;
}