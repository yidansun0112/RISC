package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.risc.client.GUIPlayer;
import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GameRoomInfo;

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

    // serverThread.interrupt();
    // System.out.println("Now server should be interrupted");

    // dummy_connection();
    // serverThread.join();
    // System.out.println("Now server thread should join");

    // System.out.println("Now we reach here");

    // --- 1. Test register ---
    // 1.1 successful
    String userNameP0 = "Yui Hirasawa";
    String userPasswordP0 = "yui1127";

    JSONObject json0 = new JSONObject();
    json0.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_REGISTER);
    json0.put(Constant.KEY_USER_NAME, userNameP0);
    json0.put(Constant.KEY_PASSWORD, userPasswordP0);
    p0.sendObject(json0.toString());

    Thread.sleep(1000); // let the server handle the request
    assertEquals(Constant.RESULT_SUCCEED_REQEUST, (String) p0.receiveObject());
    // Now check the data in the server to ensure we really succeed in registering
    assertEquals(userNameP0, server.userList.get(0).getUserName());
    assertEquals(userPasswordP0, server.userList.get(0).getPassword());

    // 1.2 on fail
    p0.disconnect(); // we need to reconnect the server to test other situation
    p0.connect();
    p0.sendObject(json0.toString()); // register with a user name which already exist
    Thread.sleep(1000);
    assertEquals(Constant.FAIL_REASON_SAME_USER_NAME, (String) p0.receiveObject());

    // --- 2. Test login ---
    // 2.1 on success
    JSONObject json1 = new JSONObject();
    json1.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_LOGIN);
    json1.put(Constant.KEY_USER_NAME, userNameP0);
    json1.put(Constant.KEY_PASSWORD, userPasswordP0);
    p0.disconnect();
    p0.connect();
    p0.sendObject(json1.toString());
    Thread.sleep(1000);
    assertEquals(Constant.RESULT_SUCCEED_REQEUST, (String) p0.receiveObject());

    // 2.2 on fail
    JSONObject json2 = new JSONObject();
    json2.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_LOGIN);
    json2.put(Constant.KEY_USER_NAME, userNameP0);
    json2.put(Constant.KEY_PASSWORD, "azusa1111");
    p0.disconnect();
    p0.connect();
    p0.sendObject(json2.toString());
    Thread.sleep(1000);
    assertEquals(Constant.FAIL_REASON_WRONG_PASSWORD, (String) p0.receiveObject());

    // --- 3. Test create room
    JSONObject json3 = new JSONObject();
    json3.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_CREATE_ROOM);
    json3.put(Constant.KEY_USER_NAME, userNameP0);
    p0.disconnect();
    p0.connect();
    p0.sendObject(json3.toString());
    p0.sendObject(3); // need to send the total player amount to server to finish this request
    Thread.sleep(1000);

    assertEquals("0", (String) p0.receiveObject()); // the received player id should be string "0"
    assertEquals(1, server.nextRoomId);
    assertEquals(1, server.gameRooms.size()); // now we have 1 game room
    assertEquals(userNameP0, server.gameRooms.get(0).getRoomOwner().getPlayerSymbol());
    assertEquals(0, server.gameRooms.get(0).getRoomOwner().getPlayerId());
    assertEquals(3, server.gameRooms.get(0).getPlayerNum());

    // --- 3. Test get waiting room list ---
    JSONObject json4 = new JSONObject();
    json4.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_GET_WATING_ROOM_LIST);
    json4.put(Constant.KEY_USER_NAME, userNameP0);
    p0.disconnect();
    p0.connect();
    p0.sendObject(json4.toString());
    Thread.sleep(1000);

    ArrayList<GameRoomInfo> receivedList = (ArrayList<GameRoomInfo>) p0.receiveObject();
    assertEquals(1, receivedList.size());
    assertEquals(0, receivedList.get(0).getRoomId());
    assertEquals(userNameP0, receivedList.get(0).getRoomOwnerName());
    assertEquals(3, receivedList.get(0).getTotalPlayers());

    // --- 4. Test join a game room
    String userNameP1 = "Azusa Nakano";
    String userPasswordP1 = "azusa1111";
    GUIPlayer p1 = new GUIPlayer();
    p1.connect();
    // register her first
    JSONObject json5 = new JSONObject();
    json5.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_REGISTER);
    json5.put(Constant.KEY_USER_NAME, userNameP1);
    json5.put(Constant.KEY_PASSWORD, userPasswordP1);
    p1.sendObject(json5.toString());

    Thread.sleep(1000);
    assertEquals(Constant.RESULT_SUCCEED_REQEUST, (String) p1.receiveObject());
    // Now check the data in the server to ensure we really succeed in registering
    assertEquals(userNameP1, server.userList.get(1).getUserName());
    assertEquals(userPasswordP1, server.userList.get(1).getPassword());

    p1.disconnect();
    p1.connect();

    JSONObject json6 = new JSONObject();
    json6.put(Constant.KEY_REQUEST_TYPE, Constant.VALUE_REQUEST_TYPE_JOIN_ROOM);
    json6.put(Constant.KEY_USER_NAME, userNameP1);
    json6.put(Constant.KEY_ROOM_ID_TO_JOIN, "0"); // there should be only one game room with roomId 0 in the server
    p1.sendObject(json6.toString());
    Thread.sleep(1000);

    assertEquals("1", (String) p1.receiveObject()); // receive the player id
    assertEquals(2, server.gameRooms.get(0).players.size());

  }

  @Test
  public void test_handleRegister() {
    // TODO
  }

  @Test
  public void test_handleLogin() {
    // TODO
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
