package edu.duke.ece651.risc.client;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.*;

/**
 * This class that does the actual network services, which are
 * connecting/disconnecting to/from the server, and send/receive message to/from
 * server. It handles a socket client which implements from GameClient.
 */
public class SocketClient implements GameClient {
  /** The socket object that will be connected to the game server */
  protected Socket clientSocket;

  /** The stream that is used to read object from game server. */
  protected ObjectOutputStream oos; // in evolution 1, this method may not be used.
  /** The stream that is used to send object to game server */
  protected ObjectInputStream ois;

  /**
   * Constructor that for Dependency Injection. It will initialize the
   * corresponding class fields using these parameters. Currently this constructor
   * is mainly for testing purposes.
   * 
   * @param socket a Socket object
   * @param oos    an ObjectOutputStream object
   * @param ois    an ObjectInputStream object
   */
  public SocketClient(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
    this.clientSocket = socket;
    this.oos = oos;
    this.ois = ois;
  }

  /**
   * The constructor of {@code SocketClient}. It creates a socket object and
   * immediately connect to the server. After established the connection, four i/o
   * streams for sending/receiving string and obejct will be created from the
   * socket.
   * 
   * @param port the port number listened by the game server
   * @param addr the IP address of the game server
   * @throws UnknownHostException if {@code GameClient} is created before the game
   *                              server starts to run, or an invalid host name is
   *                              given, etc.
   * @throws IOException
   */
  public SocketClient(int port, String addr) throws UnknownHostException, IOException {
    // Try to connect to the server
    clientSocket = new Socket(addr, port);

    // Initialize all the streams
    this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
    this.ois = new ObjectInputStream(clientSocket.getInputStream());
  }

  /**
   * Send an object to the game server via ObjectOutputStream. This method is used
   * to send self-defined object as well as simple String messages.
   * 
   * @param o
   * @throws IOException
   */
  @Override
  public void sendObject(Object o) throws IOException {
    oos.writeObject(o);
  }

  /**
   * Receive an Object from the game server via ObjectInputStream. This method is
   * used to receive self-defined object as well as simple String messages.
   * 
   * @return the Object recieved
   * @throws ClassNotFoundException
   * @throws IOException
   */
  @Override
  public Object receiveObject() throws ClassNotFoundException, IOException {
    return ois.readObject();
  }

  /**
   * Close the socket to disconnect from the server.
   * 
   * @throws IOException
   */
  @Override
  public void disconnectServer() throws IOException {
    clientSocket.close();
  }
}