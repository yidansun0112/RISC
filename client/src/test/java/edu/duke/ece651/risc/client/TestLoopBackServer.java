package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
public class TestLoopBackServer {
  ServerSocket serverSock;

  public TestLoopBackServer(int port) throws IOException {
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

        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        // Receive the amount of object need to loop-back
        Integer num = (Integer) ois.readObject(); // may throws ClassNotFoundException
        for (int i = 0; i < num.intValue(); i++) {
          TestOrder order = (TestOrder) ois.readObject();
          oos.writeObject(order);
        }
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
    TestLoopBackServer testSerevr = new TestLoopBackServer(3301);
    testSerevr.run();
  }
}
