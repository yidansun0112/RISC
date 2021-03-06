package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import edu.duke.ece651.risc.shared.*;

public class TestPlayer {
  Socket playerSock;

  public TestPlayer(String ip, int port) throws IOException {
    this.playerSock = new Socket(ip, port);
  }

  /**
   * In this method, the TestLoopBackServer will first receive a number indicating
   * how many objects will be sent by the client. When receive an object, send it
   * back. After receivign all objects, the server close the connetion and quit.
   * 
   * @throws IOException
   */
  public void runAsFirst() throws IOException {
    try {
      try {
        ObjectInputStream recv = new ObjectInputStream(playerSock.getInputStream());
        ObjectOutputStream send = new ObjectOutputStream(playerSock.getOutputStream());
        // recv id
        recv.readObject();
        // select player num
        send.writeObject("2");
        // select game map
        recv.readObject();
        send.writeObject("0");
        recv.readObject();
        // pick territory
        recv.readObject();
        send.writeObject("0");
        recv.readObject();
        // deploy units
        recv.readObject();
        ArrayList<Integer> deploy = new ArrayList<Integer>();
        deploy.add(0);
        deploy.add(15);
        send.writeObject(deploy);
        recv.readObject();
        // first round issue orders
        recv.readObject();
        sendOrder("order", "D", recv, send);
        // recv result
        recv.readObject();
        recv.readObject();
        // second round
        recv.readObject();
        sendOrder("2 1 1", "M", recv, send);
        sendOrder("order", "D", recv, send);
        // recv result
        recv.readObject();
        recv.readObject();
        // third round
        recv.readObject();
        sendOrder("order", "D", recv, send);
        // recv result
        recv.readObject();
        recv.readObject();
        send.writeObject(Constant.TO_QUIT_INFO);
        recv.readObject();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        playerSock.close();
      }
    } catch (IOException e) { // this is to catch the exception thrown by s.close
      e.printStackTrace();
    }
  }

  public void runAsSecond() throws IOException {
    try {
      try {
        ObjectInputStream recv = new ObjectInputStream(playerSock.getInputStream());
        ObjectOutputStream send = new ObjectOutputStream(playerSock.getOutputStream());
        // recv id
        recv.readObject();
        // pick territory
        recv.readObject();
        send.writeObject("1");
        recv.readObject();
        // deploy units
        recv.readObject();
        ArrayList<Integer> deploy = new ArrayList<Integer>();
        deploy.add(3);
        deploy.add(15);
        send.writeObject(deploy);
        recv.readObject();
        // first issue orders
        recv.readObject();
        sendOrder("3 0 15", "A", recv, send);
        sendOrder("order", "D", recv, send);
        // recv result
        recv.readObject();
        recv.readObject();
        // second issue orders
        recv.readObject();
        sendOrder("3 2 1", "A", recv, send);
        sendOrder("0 1 3", "A", recv, send);
        sendOrder("order", "D", recv, send);
        // recv result
        recv.readObject();
        recv.readObject();
        // third issue orders
        recv.readObject();
        sendOrder("2 0 2", "M", recv, send);
        sendOrder("3 0 1", "M", recv, send);
        sendOrder("4 0 2", "M", recv, send);
        sendOrder("5 0 2", "M", recv, send);
        sendOrder("0 1 8", "A", recv, send);
        sendOrder("order", "D", recv, send);
        // recv result
        recv.readObject();
        recv.readObject();
        send.writeObject(Constant.TO_QUIT_INFO);
        recv.readObject();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        playerSock.close();
      }
    } catch (IOException e) { // this is to catch the exception thrown by s.close
      e.printStackTrace();
    }
  }

  public void sendOrder(String order, String type, ObjectInputStream recv, ObjectOutputStream send)
      throws IOException, ClassNotFoundException {
    Order<String> od = new DoneOrder<String>();
    switch (type) {
    case "M":
      od = new MoveOrder<String>(order);
      break;
    case "A":
      od = new AttackOrder<String>(order);
      break;
    case "D":
      od = new DoneOrder<String>();
      break;
    }
    send.writeObject(od);
    recv.readObject();
    recv.readObject();
  }

  public static void main(String[] args) throws IOException {
    TestPlayer testPlayer = new TestPlayer("localhost", 12345);
    if (args[0] == "0") {
      testPlayer.runAsFirst();
    } else {
      testPlayer.runAsSecond();
    }
  }
}
