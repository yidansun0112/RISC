package edu.duke.ece651.risc.client;

import java.io.*;

/**
 * This class handles a basic GameClient interface
 */
public interface GameClient {
  public void connectServer();

  public void disconnectServer() throws IOException;

  public InputStream getInputStream() throws IOException;

  public OutputStream getOutputStream() throws IOException;
}