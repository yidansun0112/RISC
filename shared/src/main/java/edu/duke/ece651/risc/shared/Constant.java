package edu.duke.ece651.risc.shared;

import java.util.HashMap;

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
  /** Indicate the initial max tech level at the beginning of the game */
  public static final int INIT_MAX_TECH_LEVEL = 1; // note it starts at 1 (pdf2 7 a.)

  /**
   * Indicate the initial amount of food resource that each player can have at the
   * beginning
   */
  public static final int INIT_FOOD_RESOURCE = 0;

  /**
   * Indicate the initial amount of technology resource that each player can have
   * at the beginning
   */
  public static final int INIT_TECH_RESOURCE = 0;

  /**
   * Store the cost of technology resource of upgrading some amount of units. Key
   * is the level, value is the cost. Same with that in pdf 7.(e).
   */
  public static final HashMap<Integer, Integer> UP_UNIT_COST;
  // Here we use a static block to initialize the map. If we can use Java9, there
  // is a factory can do this job, which is nicer.
  static {
    UP_UNIT_COST = new HashMap<Integer, Integer>();
    UP_UNIT_COST.put(0, 0);
    UP_UNIT_COST.put(1, 3);
    UP_UNIT_COST.put(2, 11);
    UP_UNIT_COST.put(3, 30);
    UP_UNIT_COST.put(4, 55);
    UP_UNIT_COST.put(5, 90);
    UP_UNIT_COST.put(6, 140);
  }

  // --- Below are JSON Key-Values as the Request protocol of the RISC Game --- //

  /** The key that represents the type of the request */
  public static final String KEY_REQUEST_TYPE = "RequestType";

  /** The request type that will register a new user */
  public static final String VALUE_REQUEST_TYPE_REGISTER = "Register";

  /** The request type that will login a user */
  public static final String VALUE_REQUEST_TYPE_LOGIN = "Login";

  /** The request type that will create a room */
  public static final String VALUE_REQUEST_TYPE_CREATE_ROOM = "CreateRoom";

  /** The request type that will join a room that waiting for other players */
  public static final String VALUE_REQUEST_TYPE_JOIN_ROOM = "JoinRoom";

  /** The request json key which indicates a username field */
  public static final String KEY_USER_NAME = "UserName";

  /** The request json key which indicates a password field */
  public static final String KEY_PASSWORD = "Password";

  /**
   * The request json key which indicates the total number of players in a game
   * room
   */
  public static final String KEY_NUM_PLAYER = "PlayerNum";

  public static final String RESULT_SUCCEED_REQEUST = "Request Succeed.";

  /** Some possible reasons of a failed register/login */
  public static final String FAIL_REASON_INVALID_JSON = "The data from client is invalid in format.";
  public static final String FAIL_REASON_INVALID_USER_NAME = "Your user name is invalid.";
  public static final String FAIL_REASON_SAME_USER_NAME = "Your user name already exists. Please choose a different one.";
  public static final String FAIL_REASON_HAS_NOT_REGISTERED = "You need to register first.";
  public static final String FAIL_REASON_WRONG_PASSWORD = "Your password is incorrect.";

  // --- The status of a game room in evolution 2 --- //

  /**
   * The game room is just created, or is waiting for the resrt of players come in
   */
  public static final int ROOM_STATUS_WAITING_PLAYERS = 0;
 
  /**
   * When all players come in the game room, the status will immediately be this
   * value, and will keep on this value during hte process of picking territory,
   * deploying unit, receving orders and combating in each turn, until there is a
   * winner in this room.
   * 
   * If a player leave this room, the status will still be this value since the
   * game has not finished (no one wins yet).
   */
  public static final int ROOM_STATUS_RUNNING_GAME = 1;

  /**
   * If a winner appears, the status of a room will immediately be this value, and
   * this room can be removed by the server at some time.
   */
  public static final int ROOM_STATUS_GAME_FINISHED = 2;

  /**
   * Public random seed that used in battle resolver for dependency injection in
   * testing
   */
  public static final int randomSeed = 42;
}
