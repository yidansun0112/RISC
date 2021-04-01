package edu.duke.ece651.risc.server;

import org.json.JSONObject;

import edu.duke.ece651.risc.shared.Constant;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The game server class used in evolution 2. Since it has a quite different
 * architecture and methods than SocketServer in evo1, we provide this brand new
 * class
 */
public class V2GameServer {
  /** The sevrer socket of this game server */
  ServerSocket serverSocket;
  /**
   * All the sockets that connected to each player on this server. In evolution 1,
   * all the player will in the same game room
   */
  List<Socket> playerSockets;
  /**
   * All the game room on this server. In evo2, we will have multiple game room,
   * and their id may not consistant with their index in evo1's vector. Here we
   * use a thread-safe map, which will be more convenient.
   */
  Map<Integer, GameRoom<String>> gameRooms;

  /** A list that stores all the registered user in the game server */
  List<RISCUser> userList;

  /** A thread pool to handle different kinds of request from multiple players */
  ThreadPoolExecutor threadPool;

  // TODO: a new field that records the mapping between risc user and the room
  // they joined in?

  /**
   * Contructor that takes in a ServerSocket object.
   * 
   * @param serverSocket the server socket object this server will used to listen
   *                     to the incomming connections.
   */
  public V2GameServer(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    this.playerSockets = new Vector<Socket>();
    /**
     * Here we need a thread-safe data sturcture, and ConcurrentHashMap is good at
     * your hand
     */
    this.gameRooms = new ConcurrentHashMap<Integer, GameRoom<String>>();
    /**
     * It is possible that multiple user register at the same time, so here we also
     * need a thread-safe data sturcture.
     */
    this.userList = new Vector<RISCUser>();

    /** Generally we want to keep not too many threads since it will occupy OS's resources */
    this.threadPool = new ThreadPoolExecutor(4, 8, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    // TODO: add some dummy user for the convenience of testing???
  }

  /**
   * This method will run forever to keep the server alive, until the server
   * thread being interrupted or throwing a IOException when accept a connection,
   * which we can't do much about it.
   * 
   * @throws IOException
   */
  public void run() throws IOException {
    // TODO: DEBUG: remove this line when finished all the code, avoid hardcoding
    // system.out
    System.out.println("The server is running now...");
    while (!Thread.currentThread().isInterrupted()) {
      // We need to make sure that the run() method is capable for resolving almost
      // all exceptions to keep the server run forever
      Socket sock = serverSocket.accept();
      threadPool.execute(() -> {
        try {
          handleRequest(sock);
        } catch (IOException | ClassNotFoundException e) {
          // We print the stack trace before try to close the server socket, which is also
          // possible for throwing an exception
          e.printStackTrace();
        }
      });
    }
  }
  
  /**
   * This method will check the type of the request, and call the corresponding
   * specific handler to process the request.
   * 
   * @param sock the newly connected socket. Will be used in reading the request
   *             content and sending the result of the processing
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public void handleRequest(Socket sock) throws ClassNotFoundException, IOException {
    // TODO: to myself: finish this with joinRoom, createRoom two requets soon!

    // Some notes and thoughts here:
    // We may create a playerEntity to reuse the receiveObject() method, but is that
    // a little strange?
    // If a user wants to register, he will not be in any game room for this
    // request. But a playerEntity represents a player who is in a game room.
    //
    // Also, here we *can* create a class for each type of request, but each request
    // has quite different content (except for login and register..), I found that
    // it's hard to find the common field and getter/setters. So the class here is
    // merely a wrap of various fields... I finally adopt the suggestion of Kewei
    // that using a JSON to communicate the request between server and client, since
    // JSON is quite flexible to add/remove contents in code.
    // Here we don't neet to convert a class instance into JSON, so we do not neet
    // to use the Google's GSON package. The org.json should be enough. You can
    // refer to:
    // 1. https://www.baeldung.com/java-org-json (a very good article for beginners)
    // 2. https://stleary.github.io/JSON-java/index.html (official API
    // documentation)

    // Got the short connection for sending-receiving a request, now we receive the
    // JSON string.
    ObjectInputStream oisForJSON = new ObjectInputStream(sock.getInputStream());
    ObjectOutputStream oosForResult = new ObjectOutputStream(sock.getOutputStream());
    String jsonString = (String) oisForJSON.readObject();
    JSONObject requestJSON = new JSONObject(jsonString);

    // Check what type of the request is, then do the appropriate work to process
    // the request.

    String requestType = requestJSON.getString(Constant.KEY_REQUEST_TYPE);
    if (requestType.equals(Constant.VALUE_REQUEST_TYPE_REGISTER)) {
      String result = handleRegister(requestJSON);
      if (result == null) {
        oosForResult.writeObject(Constant.RESULT_SUCCEED_REQEUST);
      } else {
        oosForResult.writeObject(result);
      }
    }

    if (requestType.equals(Constant.VALUE_REQUEST_TYPE_LOGIN)) {
      String result = handleLogin(requestJSON);
      if (result == null) {
        oosForResult.writeObject(Constant.RESULT_SUCCEED_REQEUST);
      } else {
        oosForResult.writeObject(result);
      }
    }
      

    if (requestType.equals(Constant.VALUE_REQUEST_TYPE_CREATE_ROOM)) {
      // handleCreateGameRoom();
    }

  }

  /**
   * This method handles the request of registering.
   * 
   * @param requestJSON the request JSON object that received from client, which
   *                    contains the necessary information to do the registering.
   * @return a string indicating the reason if registering is failed. return null
   *         on success.
   */
  public String handleRegister(JSONObject requestJSON) {
    // First check whether the JSON has the keys we want
    if (!requestJSON.has(Constant.KEY_USER_NAME) || !requestJSON.has(Constant.KEY_PASSWORD)) {
      // Invalid JSON object - with no username or password field
      return Constant.FAIL_REASON_INVALID_JSON;
    }

    String userInputedName = requestJSON.getString(Constant.KEY_USER_NAME);
    // Then check the username is not an empty string -- the client should also
    // prevent this? If it's easy to implement.
    if (userInputedName.equals("")) {
      // Invalid user name
      return Constant.FAIL_REASON_INVALID_USER_NAME;
    }

    // Then check whether there is a same user name in userList
    for (RISCUser user : userList) {
      if (user.getUserName().equals(userInputedName)) {
        return Constant.FAIL_REASON_SAME_USER_NAME;
      }
    }

    // Check pass, now add a new RISCUser into userList
    String userInputedPassword = requestJSON.getString(Constant.KEY_PASSWORD);
    RISCUser newUser = new RISCUser(userInputedName, userInputedPassword);
    
    // Here we need not to synchronize userList, since it is a thread-safe vector
    userList.add(newUser);

    return null;
  }

  /**
   * This method handle the request of login a user.
   * 
   * Note: currently the login has not veru much effect on modifying the userList.
   * It may modify it when we come to switch a game room. This method is due to
   * change.
   * 
   * @param requestJSON the request JSON object that received from client, which
   *                    contains the necessary information to do the registering.
   * @return a string indicating the reason if registering is failed. return null
   *         on success.
   */
  public String handleLogin(JSONObject requestJSON) {
    // First check whether the JSON has the keys we want
    if (!requestJSON.has(Constant.KEY_USER_NAME) || !requestJSON.has(Constant.KEY_PASSWORD)) {
      // Invalid JSON object - with no username or password field
      return Constant.FAIL_REASON_INVALID_JSON;
    }

    String userInputedName = requestJSON.getString(Constant.KEY_USER_NAME);
    // Then check the username is not an empty string -- the client should also
    // prevent this? If it's easy to implement.
    if (userInputedName.equals("")) {
      // Invalid user name
      return Constant.FAIL_REASON_INVALID_USER_NAME;
    }

    // Then check whether this user has registered before
    for (RISCUser user : userList) {
      if (user.getUserName().equals(userInputedName)) {
        return null;
      }
    }

    // If reach here, it means this user has not registered yet.
    return Constant.FAIL_REASON_HAS_NOT_REGISTERED;
  }

  /**
   * This method will create a new game room and let the player who send this
   * request join this newly created game room.
   */
  public void handleCreateGameRoom() {
    // TODO: finish this. The parameter maybe changed
  }

  // /**
  //  * Try out some JSON API behaviors
  //  */
  // public static void main(String[] args) {
  //   JSONObject o = new JSONObject();
  //   o.put("1", "");
  //   o.put("2", " ");
  //   String valueForKey3 = null;
  //   o.put("3", valueForKey3);
  //   System.out.println(o.toString());
  //   System.out.println("<" + o.get("1") + ">");
  //   System.out.println("<" + o.get("2") + ">");
  //   // System.out.println("<" + o.get("3") + ">"); // throw exception here
  // }
}
