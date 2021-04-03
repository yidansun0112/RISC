package edu.duke.ece651.risc.server;

import org.json.JSONObject;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GUIPlayerEntity;
import edu.duke.ece651.risc.shared.GameRoomInfo;
import edu.duke.ece651.risc.shared.PlayerEntity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
  // TODO: decide do we still need this field in evo2 later!!!
  List<Socket> playerSockets;

  /**
   * All the game room on this server. In evo2, we will have multiple game rooms,
   * and their id may not consistant with their index in evo1's vector. What's
   * more, we need to use a room id to remove a game room if its game is end. It
   * would be cubersome if we still use a vector to store all rooms. Here we use a
   * thread-safe map, which will be more convenient.
   */
  Map<Integer, GameRoom<String>> gameRooms;

  /** The room id for the next created room */
  int nextRoomId;

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

    this.nextRoomId = 0;
    /**
     * It is possible that multiple user register at the same time, so here we also
     * need a thread-safe data sturcture.
     */
    this.userList = new Vector<RISCUser>();

    /**
     * Generally we want to keep not too many threads since it will occupy OS's
     * resources
     */
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
          // We need to print the stack trace before we try to close the server socket,
          // since closing a server socket may also
          // possible for throwing an new exception
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
    System.out.println(requestType);
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

    // The user wants to create a new game room and join in
    if (requestType.equals(Constant.VALUE_REQUEST_TYPE_CREATE_ROOM)) {
      // handleCreateGameRoom();
      // Now we can sure this user want to start to play a game, we create a
      // PlayerEntity here
      String playerName = requestJSON.getString(Constant.KEY_USER_NAME); // we need the GUI client send a json including
                                                                         // the request type and username
                                                                         // Note: the client should also need to check
                                                                         // whether the username is an empty string when
                                                                         // register/login, otherwise here will throw an
                                                                         // exception
      System.out.println(playerName);
      // PlayerEntity<String> roomCreator = new GUIPlayerEntity<String>(new ObjectOutputStream(sock.getOutputStream()),
      //     new ObjectInputStream(sock.getInputStream()), 0, playerName, -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
      PlayerEntity<String> roomCreator = new GUIPlayerEntity<String>(oosForResult,
          oisForJSON, 0, playerName, -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
      System.out.println("after create player entity");
          handleCreateGameRoom(roomCreator);
    }

    if (requestType.equals(Constant.VALUE_REQUEST_TYPE_GET_WATING_ROOM_LIST)) {
      oosForResult.writeObject(getWaitingRoomList()); // we directly send the list here.
    }

    // The user wants to join an existing game room which is waiting for the rest of
    // players come in
    if (requestType.equals(Constant.VALUE_REQUEST_TYPE_JOIN_ROOM)) {
      String playerName = requestJSON.getString(Constant.KEY_USER_NAME);
      int roomIdToJoin = Integer.parseInt(requestJSON.getString(Constant.KEY_ROOM_ID_TO_JOIN).trim());
      PlayerEntity<String> followingPlayer = new GUIPlayerEntity<String>(new ObjectOutputStream(sock.getOutputStream()),
          new ObjectInputStream(sock.getInputStream()), 0, playerName, -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
      handleJoinGameRoom(followingPlayer, roomIdToJoin);
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
        if (user.getPassword().equals(requestJSON.getString(Constant.KEY_PASSWORD))) {
          return null;
        } else {
          return Constant.FAIL_REASON_WRONG_PASSWORD;
        }
      }
    }

    // If reach here, it means this user has not registered yet.
    return Constant.FAIL_REASON_HAS_NOT_REGISTERED;
  }

  /**
   * This method will create a new game room and let the player who send this
   * request join this newly created game room.
   * 
   * @apiNote This method is synchronized: although the ConcurrentHashMap itself
   *          is thead-safe, but here the operation on this map is not atomic,
   *          i.e., we (a) get the size of the map, and then (b) add a new room
   *          instance into this map. The operation (a) and (b) itself is atomic
   *          and thread-safe, but the combination of these two are not. This is a
   *          pitfall in Java that when you manipulate some thread-safe data
   *          structure, you still need to be careful that whether your operations
   *          are atomic as a whole.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public synchronized void handleCreateGameRoom(PlayerEntity<String> roomCreator)
      throws ClassNotFoundException, IOException {

    // TODO update the mapping between user(player) and rooms here, do this after
    // decide how to store this map relationship!

    // Basically same with evo1, we create a room, add this player into this room,
    // set this room's playerNum field based on the content in this request JSON
    System.out.println("in hancle create game room");
    int idForTheNewRoom = nextRoomId;
    this.nextRoomId++;
    System.out.println("before create room");
    GameRoom<String> newRoom = new V2GameRoom(idForTheNewRoom, roomCreator);
    System.out.println("after create room");
    gameRooms.put(idForTheNewRoom, newRoom);

    // We need to send the player id to this player. Since he/she is the creator of
    // a new game room, player id will be 0 (zero)
    // NOTE: SEND String to client - player id
    roomCreator.setPlayerId(0);
    System.out.println("before send");
    roomCreator.sendObject(new String("0"));
    System.out.println("after send");

    // Now receive the user decision about how many players should in this room
    int totalPlayer = (int) roomCreator.receiveObject();
    newRoom.setPlayerNum(totalPlayer);
  }

  /**
   * This method handle the request of join a game room, which will try to add the
   * player into the game room. If the room is just full of player, the player
   * will receive the player id as a string "-1". This is sent inside the
   * addPlayerAndCheckToPlay() method.
   * 
   * Note that the client need to hold the long connection if on successful join,
   * or give up the connection on failed join.
   * 
   * @param followingPlayer the player entity who wants to join an existing room
   *                        which is waiting for the rest of players
   */
  public void handleJoinGameRoom(PlayerEntity<String> followingPlayer, int roomIdToJoin) {
    GameRoom<String> roomWantToJoin = gameRooms.get(roomIdToJoin);
    roomWantToJoin.addPlayerAndCheckToPlay(followingPlayer);
  }

  // public void handleGetWaitingRoomList() {
  // }

  /**
   * We are using iterator to remove key-value pairs, the iterator may not be
   * thread safe? Synchronize this method for now.
   * 
   * @return a list contains all the rooms that are waiting for the rest of
   *         players
   */
  protected synchronized List<GameRoomInfo> getWaitingRoomList() {
    // TODO: check whether there is concurrency bug here when I am awake.. Currently
    // I am sleepy to do this. And also check whether the synchronize is necessary
    // First we need to remove the rooms which the games in them have finished to
    // save some memory
    for (Map.Entry<Integer, GameRoom<String>> e : gameRooms.entrySet()) {
      if (e.getValue().getRoomStatus() == Constant.ROOM_STATUS_GAME_FINISHED) {
        gameRooms.remove(e.getKey());
      }
    }

    // Then build the list of GameRoomInfo of rooms that are waiting for the rest of
    // players
    List<GameRoomInfo> ans = new ArrayList<GameRoomInfo>();
    for (Map.Entry<Integer, GameRoom<String>> e : gameRooms.entrySet()) {
      if (e.getValue().getRoomStatus() == Constant.ROOM_STATUS_WAITING_PLAYERS) {
        GameRoom<String> roomToAdd = e.getValue();
        ans.add(new GameRoomInfo(roomToAdd.getRoomId(), roomToAdd.getPlayerNum(),
            roomToAdd.getRoomOwner().getPlayerSymbol()));
      }
    }

    return ans;
  }

  // /**
  // * Try out some JSON API behaviors
  // */
  // public static void main(String[] args) {
  // JSONObject o = new JSONObject();
  // o.put("1", "");
  // o.put("2", " ");
  // String valueForKey3 = null;
  // o.put("3", valueForKey3);
  // System.out.println(o.toString());
  // System.out.println("<" + o.get("1") + ">");
  // System.out.println("<" + o.get("2") + ">");
  // // System.out.println("<" + o.get("3") + ">"); // throw exception here
  // }
}
