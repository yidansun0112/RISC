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

  /** Indicate a player successfully chose a map */
  public static final String VALID_MAP_CHOICE_INFO = "Choice Succeed!";
  /** Indicate a player successfully chose a map */
  public static final String INVALID_MAP_CHOICE_INFO = "Invalid Choice, please choose a valid one!";
  /** Indicate the order issued by a player is legal */
  public static final String LEGAL_DEPLOY_INFO = "The Order Is Legal!";
  /** Indicate the player is not the owner of this territory */
  public static final String NOT_OWNER_INFO = "You are not the owner of this territory!";
  /** Indicate the named territory does not exist */
  public static final String NO_SUCH_TERRITORY_INFO = "This territory does not exist!";
  /** Indicate the player does not have enough units */
  public static final String INSUFFICIENT_UNIT_INFO = "You don't have enough units remained!";
  /** Indicate a player has finished the deployment */
  public static final String FINISH_DEPLOY_INFO = "Deploy Finished!";
  /** Indicate the order issued by a player is illegal */
  public static final String ILLEGAL_ORDER_INFO = "The Order Is Illegal!";
  /** Indicate the order issued by a player is legal */
  public static final String LEGAL_ORDER_INFO = "The Order Is Legal!";
  /** Indicate the player has issued all his/her order */
  public static final String DONE_ORDER_INFO = "I'm Done.";
  /** Indicate a player has not lost the game yet */
  public static final String NOT_LOSE_INFO = "You Have Not Lost Yet!";
  /** Indicate a player has lost the game, but the game has no one win yet */
  public static final String LOSE_INFO = "You Losed!";
  /** Indicate a player wins the game */
  public static final String WIN_INFO = "You Win!";
  /** Indicate the game has a winner now and is end */
  public static final String GAME_END_INFO = "The Game Is End!";
  /** Indicate the game is still going */
  public static final String GAME_CONTINUE_INFO = "The Game Is Continuing!";
  /** Indicate the player will watch the game */
  public static final String TO_WATCH_INFO = "I will watch the game!";
  /** Indicate the player will quit the game */
  public static final String TO_QUIT_INFO = "I will quit the game!";
  /** Indicate the server has confirmed this player's choice */
  public static final String CONFIRM_INFO = "Confirmed!";

  // --- Below are the configurations for a game --- //

  /** The maximun number of player allowed (inclusive) in a game room */
  public static final int MAX_PLAYER_NUM = 5;
  /** The minimum number of player allowed (inclusive) in a game room */
  public static final int MIN_PLAYER_NUM = 2;
  /** The total units to be deployed for each player */
  public static final int TOTAL_UNITS = 15;
  /** The side number of the dice */
  public static final int DICE_SIDE = 20;

  /** Values that indicate different status of a player */
  public static final int SELF_NOT_LOSE_NO_ONE_WIN_STATUS = 0; // the player has not lost, but not win neither, the game
  // keeps going
  public static final int SELF_LOSE_NO_ONE_WIN_STATUS = 1; // the player lose the game, but no one win, the game keeps
                                                           // going
  public static final int SELF_WIN_STATUS = 2; // the player is the winner, the game will be end
  public static final int SELF_LOSE_OTHER_WIN_STATUS = 3; // the player lose the game, and some one win, the game will
                                                          // be end

  /** Indicate the total level numbers */                                                      
  public static final int TOTAL_LEVELS = 6;
}
