package edu.duke.ece651.risc.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import edu.duke.ece651.risc.client.*;

public class TestGamePlayer implements Runnable {

  protected InputStream in;
  protected PrintStream out;

  @Override
  public void run() {
    try {
      SocketClient client = new SocketClient(12346, "localhost"); // since the testing for evo2 uses 12345 port, here we
                                                                  // change to 12346 to enable parallel testing to speed
                                                                  // up the whole testing process
      BufferedReader input = new BufferedReader(new InputStreamReader(in));
      GamePlayer<String> player = new V1GamePlayer<String>(-1, client, input, out);
      player.initGame();
      player.pickTerritory();
      player.deployUnits();
      player.doPlayPhase();
    } catch (Exception e) {
    }
  }

  /**
   * Constructor that capable for dependency injection. For testing purpose.
   * 
   * @param in  the input stream. Used to read a file when testing
   * @param out the output stream. Used to output to a file when testing
   */
  public TestGamePlayer(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

}
