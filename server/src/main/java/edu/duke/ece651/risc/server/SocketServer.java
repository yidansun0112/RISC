package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.Constant;

public class SocketServer<T> implements GameServer<T> {
  /** The symbol used to represent all players */
  T symbol; // in evolution 1, this is a string type.
  /** The sevrer socket of this game server */
  protected ServerSocket serverSocket;
  /**
   * All the sockets that connected to each player on this server. In evolution 1,
   * all the player will in the same game room
   */
  protected Vector<Socket> playerSockets;
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
    this.rooms.add(new GameRoom<T>());
  }

  /**
   * Responsible for starting listening to the connect attemption, accept it, and
   * create the PlayerEntity object.
   * 
   * @throws IOException
   */
  public void connectPlayer() throws IOException {
    Socket firstSock = serverSocket.accept();
    PlayerEntity<T> firstPlayer = new TextPlayerEntity<T>(new ObjectOutputStream(firstSock.getOutputStream()),
        new ObjectInputStream(firstSock.getInputStream()), 0, symbol, -1, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);

    // add the first player of this room into the list of player field
    rooms.get(rooms.size() - 1).addPlayer(firstPlayer);

  }

}
