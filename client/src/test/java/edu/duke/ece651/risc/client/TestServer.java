package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import edu.duke.ece651.risc.shared.Constant;

/**
 * This is a simple loop-back server that receive an object sent by client and
 * send this object back to the client. This class is only for the testing
 * purposes.
 * 
 * To test the socket connection, we need two thread, one plays the role of
 * client, one plays the role of server.
 * 
 * Note: some of the code in this class is adopt from Drew's GitLab repo:
 * factorserver.
 */
public class TestServer {
  ServerSocket serverSock;

  public TestServer(int port) throws IOException {
    this.serverSock = new ServerSocket(port);
  }

  /**
   * This method will block and wait for a connection.
   * 
   * @return a new {@code Socket} object of this connection, or {@code null} on
   *         exception.
   */
  public Socket acceptOrNull() {
    try {
      return serverSock.accept();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * In this method, the TestLoopBackServer will first receive a number indicating
   * how many objects will be sent by the client. When receive an object, send it
   * back. After receivign all objects, the server close the connetion and quit.
   * 
   * @throws IOException
   */
  public void run() throws IOException {
    final Socket s = acceptOrNull();
    if (s == null) {
      throw new IOException();
    }
    try {
      try {

        ObjectInputStream recv = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream send = new ObjectOutputStream(s.getOutputStream());
        // send id
        send.writeObject("1");
        // pick territory
        send.writeObject("This is map.");
        recv.readObject();
        send.writeObject(Constant.VALID_MAP_CHOICE_INFO);
        // deploy units
        send.writeObject(Constant.FINISH_DEPLOY_INFO);
        // issue Orders
        send.writeObject("This is map.");
        recv.readObject();
        send.writeObject(Constant.LEGAL_ORDER_INFO);
        send.writeObject("This is map.");
        // send result
        send.writeObject("This is combat info");
        send.writeObject(Constant.GAME_END_INFO);
        // do end phase
        recv.readObject();
        send.writeObject(Constant.CONFIRM_INFO);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        s.close();
        serverSock.close();
      }
    } catch (IOException e) { // this is to catch the exception thrown by s.close
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    TestServer testSerevr = new TestServer(12345);
    testSerevr.run();
  }
}
