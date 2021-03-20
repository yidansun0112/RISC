package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

public class SocketClientTest {
  /**
   * Test the SocketClient sending, receiving and disconnecting
   * 
   * @throws UnknownHostException
   * @throws IOException
   * @throws InterruptedException
   * @throws ClassNotFoundException
   */
  @Test
  public void test_sendOject_receiveObject_disconnectServer()
      throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
    // Start a new TestLoopBackServer in a separate thread
    Thread th = make_test_server_thread_helper();
    th.start();
    Thread.sleep(100); // let the *current* thead wait for a while to let the server setup
                       // this is a bit of hack
    // Now the TestLoopBackServer should start to wait for a connection...
    SocketClient client = new SocketClient(3301, "localhost");
    // Now the client in *this* thread should already connected to the
    // TestLoopBackServer, let's start to run the test cases
    test_SocketClient_helper(client);
    // Join the thread
    th.join();
    // Disconnect to avoid resource leaking
    client.disconnectServer();

    // Now we need to test the orther constructor of SocketClient..
    // Basically the same step
    // This maybe abstract out... But it's too late and I'll go sleep now
    Thread th2 = make_test_server_thread_helper();
    th2.start();
    Thread.sleep(100);
    Socket s = new Socket("localhost", 3301);
    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
    SocketClient client2 = new SocketClient(s, oos, ois);
    test_SocketClient_helper(client2);
    th2.join();
    client2.disconnectServer();
  }

  /**
   * Helper method that takes in the client that ALREADY connected to the
   * TestLoopBackServer, and starts to send-and-receive object
   * 
   * @param client the SocketClient object that ALREADY connected to the
   *               TestLoopBackServer
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private void test_SocketClient_helper(SocketClient client) throws IOException, ClassNotFoundException {
    // Create a Integer with value 4 and
    // send out
    client.sendObject(Integer.valueOf(4));

    TestOrder order = new TestOrder(1, 2, 3);
    // oos.writeObject(order);
    client.sendObject(order);
    TestOrder received = (TestOrder) client.receiveObject();
    assertEquals("(1, 2, 3)", received.toString());

    order = new TestOrder(3, 3, 3);
    client.sendObject(order);
    received = (TestOrder) client.receiveObject();
    assertEquals("(3, 3, 3)", received.toString());

    order = new TestOrder(2, 4, 6);
    client.sendObject(order);
    received = (TestOrder) client.receiveObject();
    assertEquals("(2, 4, 6)", received.toString());

    order = new TestOrder(0, 0, 0);
    client.sendObject(order);
    received = (TestOrder) client.receiveObject();
    assertEquals("(0, 0, 0)", received.toString());
  }

  /**
   * Helper method that creates a new thread to run the test loopback server.
   * 
   * @return a Thread object that the server is running on
   */
  private Thread make_test_server_thread_helper() {
    Thread th = new Thread() {
      @Override
      public void run() {
        try {
          TestLoopBackServer.main(new String[0]);
        } catch (Exception e) {
        }
      }
    };
    return th;
  }
}
