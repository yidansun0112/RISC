package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;

import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.Constant;

public class SocketServer<T> implements GameServer<T> {
  /** The symbol used to represent all players */
  protected T symbol; // in evolution 1, this is a string type.
  /** The sevrer socket of this game server */
  protected ServerSocket serverSocket;
  /**
   * All the sockets that connected to each player on this server. In evolution 1,
   * all the player will in the same game room
   */
  protected List<Socket> playerSockets;
  /**
   * All the game room on this server. In evoliution 1, there will be only one
   * server
   */
  protected Vector<GameRoom<T>> rooms;
  // protected BoardFactory<String> boardFactory;

  /**
   * Constructor that takes in a server socket. It will also initialize
   * playerSockets with a empty vector, and initialze an empty room in field
   * {@code rooms}.
   * 
   * Note: This constructor is used in evolution 1.
   * 
   * @param serverSocket
   */
  public SocketServer(ServerSocket serverSocket, T symbol) {
    this.serverSocket = serverSocket;
    this.playerSockets = new Vector<Socket>();
    this.rooms = new Vector<GameRoom<T>>();
  }

  /**
   * Responsible for starting listening to the connect attemption, accept it, and
   * create the PlayerEntity object.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void connectPlayer(GameRoom<T> room) throws IOException, ClassNotFoundException {
    Socket firstSock = serverSocket.accept();
    // Got a player, create a game room for him/her. A socket server at least has
    // one game room in evolution 
    rooms.add(room);
    // Before picking territory group, no one owns any territory group, so we pass -1 here
    PlayerEntity<T> firstPlayer = new TextPlayerEntity<T>(new ObjectOutputStream(firstSock.getOutputStream()),
        new ObjectInputStream(firstSock.getInputStream()), 0, symbol, -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    // add the first player of this room into the list of player field
    room.addPlayer(firstPlayer);

    // NOTE: SEND to client the player ID
    firstPlayer.sendObject(new String("0")); // for the first player, his/her id is 0
    // NOTE: RECEIVE the number of player message from the client
    String playerNumMsg = (String) firstPlayer.receiveObject();
    int playerNum = Integer.parseInt(playerNumMsg);
    // Update the room playerNum field
    room.setPlayerNum(playerNum);
  }

  /**
   * According to the first player's choice on how many players will in this game,
   * wait for the rest of the player get connected to the game server.
   * 
   * Every time a following player get connected, this method will get the socket
   * of this connection and add it into the playerSockets, create a new
   * PlayerEntity and add this new entity to the game room, and then send the
   * player ID to the player.
   * 
   * Afther this method finishing, all the players for this game should be all in
   * the same game room.
   * 
   * @throws IOException
   */
  public void connectAll() throws IOException {
    GameRoom<T> currRoom = rooms.firstElement();
    for (int i = 0; i < rooms.elementAt(0).getPlayerNum() - 1; i++) {
      Socket playerSock = serverSocket.accept();
      playerSockets.add(playerSock);
      PlayerEntity<T> newPlayer = new TextPlayerEntity<T>(new ObjectOutputStream(playerSock.getOutputStream()),
          new ObjectInputStream(playerSock.getInputStream()), i + 1, symbol, -1,
          Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
      currRoom.addPlayer(newPlayer);
      // NOTE: SEND String: player id
      newPlayer.sendObject(String.valueOf(i + 1));
    }
  }
  
  public void runServer(GameRoom<T> r) throws IOException, ClassNotFoundException, InterruptedException,BrokenBarrierException {
    connectPlayer(r);
    connectAll();
    GameRoom<T> room=rooms.get(0);
    room.chooseMap();
    room.playGame();
  }
}
