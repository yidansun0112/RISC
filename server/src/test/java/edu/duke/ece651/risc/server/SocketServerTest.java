package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.Test;

public class SocketServerTest {
  @Test
  public void test_connectPlayer_connectAll() throws IOException {
    ServerSocket serverSock = new ServerSocket(33333);
    SocketServer<String> socketServer = new SocketServer<String>(serverSock, "BasicPlayer");
  }
}
