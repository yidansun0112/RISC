package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.risc.client.GUIPlayer;
import edu.duke.ece651.risc.client.SocketClient;
import edu.duke.ece651.risc.shared.Constant;

public class V2GameServerTest {
  @Test
  public void test_constructor() throws IOException {
    ServerSocket servSock = new ServerSocket(23333);
    V2GameServer server = new V2GameServer(servSock);
    assertNotNull(server.serverSocket);
    assertNotNull(server.gameRooms);
    assertNotNull(server.userList);
    assertNotNull(server.threadPool);
    assertEquals(0, server.nextRoomId);

    servSock.close();
  }

  /**
   * Testing run the server to handle variouse kinds of requests
   * 
   * @throws IOException
   * @throws InterruptedException
   */
  @Test
  public void test_run() throws IOException, InterruptedException {
    ServerSocket serverSock = new ServerSocket(12345);
    V2GameServer server = new V2GameServer(serverSock);
    Thread serverThread = new Thread() {
      @Override
      public void run() {
        try {
          server.run(); // start the server in a seperate thread
        } catch (IOException ignored) {
        }
      }
    };
    // Thread thServer = make_server_thread_helper();

    GUIPlayer p0 = new GUIPlayer();

    serverThread.start();
    Thread.sleep(1000); // give some time to let the server setup

    System.out.println("Now server should be setup");
    p0.connect();
    System.out.println("Now p0 is connected");

    serverThread.interrupt();
    System.out.println("Now server should be interrupted");

    dummy_connection();
    serverThread.join();
    System.out.println("Now server thread should join");

    System.out.println("Now we reach here");

  }

  // /**
  // * Helper function for testing, which will create a thread, then create a
  // * V2GameServer and run it in this thread.
  // *
  // * @return the thread that the server is running
  // * @throws IOException
  // */
  // private Thread make_server_thread_helper() throws IOException {
  // ServerSocket serverSock = new ServerSocket(23333);
  // V2GameServer server = new V2GameServer(serverSock);
  // Thread serverThread = new Thread() {
  // @Override
  // public void run() {
  // try {
  // server.run(); // start the server in a seperate thread
  // } catch (IOException ignored) {
  // }
  // }
  // };
  // return serverThread;
  // }

  private void dummy_connection() {
    try {
      Socket sock = new Socket(Constant.ipaddress, 12345);
      sock.close();
    } catch (IOException ignored) {
    } 
  }
}
