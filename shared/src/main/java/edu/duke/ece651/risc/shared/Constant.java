package edu.duke.ece651.risc.shared;

/**
 * This class is a set of message content that the server and client agree with,
 * as a protocol to indicate the game flow.
 * 
 * The server can send these message to the player, and display the message to
 * the command line (or GUI, etc.) to the player.
 */
public class Constant {

  // --- Below are the messages that used by the server and the client --- //

  /** Indicate a player has finished the deployment */
  public static final String FINISH_DEPLOY_INFO = "Deploy Finished";
  /** Indicate the order issued by a player is illegal */
  public static final String ILLEGAL_ORDER_INFO = "The Order Is Illegal";
  /** Indicate the order issued by a player is legal */
  public static final String LEGAL_ORDER_INFO = "The Order Is Legal";
  /** Indicate a player has not lost the game yet */
  public static final String NOT_LOSE_INFO = "You Have Not Lost Yet";
  /** Indicate a player has lost the game, but the game has no one win yet */
  public static final String LOSE_INFO = "You Losed";
  /** Indicate a player wins the game */
  public static final String WIN_INFO = "You Win";
  /** Indicate the game has a winner now and is end */
  public static final String GAME_END_INFO = "The Game Is End";

  // --- Below are the configuration for a game --- //

  /** The maximun number of player allowed (inclusive) in a game room */
  public static final int MAX_PLAYER_NUM = 5;
  /** The minimum number of player allowed (inclusive) in a game room */
  public static final int MIN_PLAYER_NUM = 2;
}
