package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.Order;
import edu.duke.ece651.risc.shared.MoveOrder;
import edu.duke.ece651.risc.shared.AttackOrder;
import edu.duke.ece651.risc.shared.DoneOrder;

import java.io.*;
import java.util.ArrayList;

/**
 * This class handles a GamePlayer for Version1
 * 
 * @param T String for V1
 */
public class V1GamePlayer<T> implements GamePlayer<T> {
  /** The Id of this player */
  private int playerId; // start from 0 (i.e., the first player)
  /** The game client object that connected to the server */
  private GameClient client;
  /** The BufferedReader to read command line inputs from the player */
  private BufferedReader inputReader;
  /** The PrintStream to print message to the command line */
  private PrintStream out;

  /**
   * Constructor of the class, which will initialize the corresponding class
   * fields with the paramters.
   * 
   * @param playerId    the id of this player in the game
   * @param client      the client that does the actual network service
   * @param inputReader the actual BufferedReader object to read the player's
   *                    inputs from the command line
   * @param out         the actual PrintStream that will print message to the
   *                    command line and display to the player
   */
  public V1GamePlayer(int playerId, GameClient client, BufferedReader inputReader, PrintStream out) {
    this.playerId = playerId;
    this.client = client;
    this.inputReader = inputReader;
    this.out = out;
  }

  /**
   * Getter for playerId
   * 
   * @return playerId
   */
  public int getPlayerId() {
    return playerId;
  }

  /**
   * This method receive playerID from server. It sets the playerId field after
   * receiving it, and print the ID to the command line. (This method is called by
   * joinGame() and the player ID will be changed at that time.)
   */
  public void recvID() throws ClassNotFoundException, IOException {
    String strID = (String) client.receiveObject();
    playerId = Integer.parseInt(strID);
    out.println("Your player ID is " + strID);
  }

  /**
   * Let the first player decide how many players he/she want in this game. If the
   * input is invalid due to any one of the following reason: 1. contains
   * non-number character 2. input is a number, but less than allowed minimum
   * number of player in a game, or... 3. ...grater than allowed maximum number of
   * player in a game
   * 
   * it will show the error reason and let the player re-input. If valid, it will
   * send the player in put to the game server.
   * 
   * The method is called if the current player's id is 0, i.e., he/she is the
   * first player that connected to the server.
   * 
   * 
   * @throws IOException
   */
  public void selectPlayerNum() throws IOException {
    out.println("You are the first player in this round, please choose how many players you want in this round.");
    out.println("Player number should be from " + Constant.MIN_PLAYER_NUM + " to " + Constant.MAX_PLAYER_NUM + ".");
    out.println("Please make your choice:");
    while (true) {
      try {
        String strNum = inputReader.readLine().trim();
        int num;
        try {
          num = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
          out.println(e.getMessage() + " Player number should be digits.");
          out.println("Please do that again!");
          continue;
        }
        if (num < Constant.MIN_PLAYER_NUM || num > Constant.MAX_PLAYER_NUM) {
          throw new IllegalArgumentException(
              "Player number should be from " + Constant.MIN_PLAYER_NUM + " to " + Constant.MAX_PLAYER_NUM + ".");
        }
        // Choice is valid, send it to the server
        client.sendObject(strNum);
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
      }
    }
  }

  /**
   * Let the player input a number to choose a map. The input number is the map
   * index of all available maps that suitable for this amount of players.
   * 
   * If the player input is invalid due to one of the following reasons: 1.
   * contains non-number characters 2. is a valid number, but not valid for a map
   * index (decided by the game server)
   * 
   * this method will require the player to re-input, until the server regards
   * this input is valid.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void selectGameMap() throws IOException, ClassNotFoundException {
    // NOTE: RECEIVE string from server: receive the description of all available
    // maps
    String mapChoice = (String) client.receiveObject();
    out.println("Please choose one map among the following maps.");
    out.println(mapChoice);
    out.println("Please type the map number that you would like to choose:");
    while (true) {
      try {
        String strNum = inputReader.readLine().trim();
        int mapIndex = Integer.parseInt(strNum);
        // All maps should have a non-negative indexes
        if (mapIndex < 0) {
          throw new IllegalArgumentException("Cannot choose a map with negative index.");
        }
        // NOTE: SEND string to server: send the map index of choosed map
        client.sendObject(strNum); // if the player input passes all format checking, send it to server
        // NOTE: RECEIVE string from server:
        String choiceInfo = (String) client.receiveObject();
        if (!choiceInfo.equals(Constant.VALID_MAP_CHOICE_INFO)) {
          throw new IllegalArgumentException(choiceInfo);
        }
        out.println(choiceInfo);
        break;
      } catch (NumberFormatException e) {
        out.println(e.getMessage() + " Map number should be pure number.");
        out.println("Please do that again!");
        continue;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
      }
    }
  }

  /**
   * Init the game for the player. Receive player id from server.
   * 
   * If the player is the first player (ID is 0), he/she needs to decide the
   * player number and game map.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public void initGame() throws IOException, ClassNotFoundException {
    recvID();
    if (playerId == 0) {
      selectPlayerNum();
      selectGameMap();
    }
  }

  /**
   * Let the player choose a group of territory. If successfully obtained that
   * group of territory, this method will quit in a manner of tail-recursion.
   * 
   * If the player failed to obtain a group due to one of the following reasons:
   * 1. input contains non-number character 2. this group does not exist (decided
   * by server) 3. other player has already occupied this group (decided by
   * server) it will require the current player to re-input, until successful get
   * a group of territory.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public void pickTerritory() throws IOException, ClassNotFoundException {
    // NOTE: RECEIVE string from server: receive territory group description message
    String mapGroup = (String) client.receiveObject();
    out.println("Please choose one group among the following groups.");
    out.println(mapGroup);
    out.println("Please type the group number that you would like to choose:");
    while (true) {
      try {
        String strNum = inputReader.readLine().trim();
        // DONE: prevent the negative input here!!!
        int groupIndex = Integer.parseInt(strNum);
        if (groupIndex < 0) {
          throw new IllegalArgumentException("Cannot choose a group with negative index.");
        }
        // NOTE: SEND string to server: send the map index of choosed map
        client.sendObject(strNum);
        // NOTE: RECEIVE string from server:
        String choiceInfo = (String) client.receiveObject();
        if (!choiceInfo.equals(Constant.VALID_MAP_CHOICE_INFO)) {
          throw new IllegalArgumentException(choiceInfo);
        }
        out.println(choiceInfo);
        break;
      } catch (NumberFormatException e) {
        out.println(e.getMessage() + " Group number should be pure number.");
        out.println("Please do that again!");
        continue;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
      }
    }
  }

  /**
   * Let the player deploy units on his/her territories.
   * 
   * Player need to type with format like "2,5", which will deploy 5 units on
   * territory 2. Player needs to continue deploying until receive message from
   * server to indicate that deploy finished.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public void deployUnits() throws IOException, ClassNotFoundException {
    out.println("Please deploy units in your territories.");
    String msg = (String) client.receiveObject();
    while (!msg.equals(Constant.FINISH_DEPLOY_INFO)) {
      out.println(msg);
      out.println("Please type in format \"Territory index,Units deployed in this territory\"");
      out.println("For example \"2,5\" will deploy 5 units in territory 2");
      String choice = inputReader.readLine().trim();
      try {
        ArrayList<Integer> deployment = stringParser(choice);
        client.sendObject(deployment);
        String info = (String) client.receiveObject();
        out.println(info);
        msg = (String) client.receiveObject();
      } catch (NumberFormatException e) {
        out.println(e.getMessage() + " Territory/Units should be pure number.");
        out.println("Please do that again!");
        continue;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
        continue;
      }
    }
    out.println(msg);
    out.println("Please be patient to wait other players finish their deployment...");
  }

  /**
   * Helper function to parse deployment from string to arraylist.
   * 
   * This function parse string in front of "," to territory index, and string
   * after "," to unit amount.
   * 
   * @param str string to parse with
   * @throws NumberFormatException when string is not pure number.
   */
  private ArrayList<Integer> stringParser(String str) {
    int comma = str.indexOf(",");
    if (comma == -1) {
      throw new IllegalArgumentException("Cannot find \",\" in your deployment.");
    }
    String strIndex = str.substring(0, comma);
    String strUnits = str.substring(comma + 1);
    int index = Integer.parseInt(strIndex);
    int units = Integer.parseInt(strUnits);
    ArrayList<Integer> deployment = new ArrayList<Integer>();
    deployment.add(index);
    deployment.add(units);
    return deployment;
  }

  /**
   * Let the player issue orders.
   * 
   * Player will continue receive current map from server, and make choice among
   * move, attack and Done and then type content of the order. Player will send
   * the order to server and receive order info to indicate legal or not, and
   * receive another map. If the player choose to done, we will print the final
   * map and return.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void issueOrders() throws IOException, ClassNotFoundException {
    String map = (String) client.receiveObject();
    while (true) {
      // receive map, print
      // print ordermenu
      out.print(map);
      showOrderMenu();
      // get order title (M,A,D), should add into constant
      String choice = inputReader.readLine().toUpperCase().trim();
      try {
        Order<T> order = createOrder(choice);
        client.sendObject(order);
        String info = (String) client.receiveObject();
        map = (String) client.receiveObject();
        out.println(info);
        if (choice.equals("D")) {
          out.print(map);
          break;
        }
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
        continue;
      }
    }
  }

  /**
   * This method helps to print out the order menu.
   */
  public void showOrderMenu() {
    out.println("You are player " + playerId + ", what would you like to do?");
    out.println("(M)ove");
    out.println("(A)ttack");
    out.println("(D)one");
  }

  /**
   * This method lets the player to type in the content of moveorder.
   * 
   * Player should type with format like "1 3 5", which will move 5 units from
   * territory 1 to territory 3.
   * 
   * @return MoveOrder<T>
   * @throws IllegalArgumentException when order format is wrong
   * @throws IOException
   */
  public Order<T> createMoveOrder() throws IOException {
    out.println("Please type the content of your move order.");
    out.println("Format: \"Source_territory_index Destination_territory_index units_to_move");
    out.println("For example: \"1 3 5\" will move 5 units from territory 1 to territory 3");
    String str = inputReader.readLine().trim();
    MoveOrder<T> moveOrder = new MoveOrder<T>(str);
    return moveOrder;
  }

  /**
   * This method lets the player to type in the content of attack.
   * 
   * Player should type with format like "1 3 5", which will move 5 units from
   * territory 1 to attack territory 3.
   * 
   * @return AttackOrder<T>
   * @throws IllegalArgumentException when order format is wrong
   * @throws IOException
   */
  public Order<T> createAttackOrder() throws IOException {
    out.println("Please type the content of your attack order.");
    out.println("Format: \"Source_territory_index Destination_territory_index units_to_attack");
    out.println("For example: \"1 3 5\" will move 5 units from territory 1 to attack territory 3");
    String str = inputReader.readLine().trim();
    AttackOrder<T> attackOrder = new AttackOrder<T>(str);
    return attackOrder;
  }

  /**
   * This method lets the player to create done order.
   * 
   * @return DoneOrder<T>
   */
  public Order<T> createDoneOrder() {
    DoneOrder<T> doneOrder = new DoneOrder<T>();
    return doneOrder;
  }

  /**
   * This method helps to create order based on the choice.
   * 
   * "M" : will create a MoveOrder "A" : will create an AttackOrder "D" : will
   * create a DoneOrder others: will throw IllegalArgumentException
   * 
   * @param choice the order type player choose to make
   * @return Order<T>
   * @throws IOException
   */
  public Order<T> createOrder(String choice) throws IOException {
    switch (choice) {
    case "M":
      return createMoveOrder();
    case "A":
      return createAttackOrder();
    case "D":
      return createDoneOrder();
    default:
      throw new IllegalArgumentException("Choices should be among M, A, D");
    }
  }

  /**
   * This method let player to play the game.
   * 
   * Player need to issue orders at first. Then player will wait to receive combat
   * result from server. And receive the current game result. If the player wins
   * or somebody wins, the game will end. If the player lose, he/she need to
   * choose whether to watch the game or quit. If the player does not lose, he/she
   * need to issue order again.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public void doPlayPhase() throws IOException, ClassNotFoundException {
    while (true) {
      issueOrders();
      String combatInfo = (String) client.receiveObject();
      out.println(combatInfo);
      String result = (String) client.receiveObject();
      switch (result) {
      case Constant.WIN_INFO:
        out.println(result);
        doEndPhase();
        return;
      case Constant.GAME_END_INFO:
        out.println(result);
        doEndPhase();
        return;
      case Constant.LOSE_INFO:
        out.println(result);
        loseChoice();
        return;
      default:
        continue;
      }
    }
  }

  /**
   * This method lets the player to choose whether to watch the game or quit after
   * losing the game.
   * 
   * Player need to type the choice, W for watch and Q for quit. (Case
   * insensitive) Send this choice to server and wait confirm.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void loseChoice() throws IOException, ClassNotFoundException {
    out.println("Do you want to watch the game or quit?");
    out.println("W for watch, Q for quit");
    out.println("Please make your choice:");
    while (true) {
      String choice = inputReader.readLine().trim().toUpperCase();
      try {
        switch (choice) {
        case "W":
          doWatchPhase();
          break;
        case "Q":
          doEndPhase();
          break;
        default:
          throw new IllegalArgumentException("Choice should be W or Q");
        }
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        out.println("Please do that again!");
        continue;
      }
    }
  }

  /**
   * This method lets the player do watch phase.
   * 
   * Player will send watch decision to server and wait confirm information. Then
   * player will wait to receive combat result and game info in every round. If
   * game info is GAME_END_INFO, the player need to end the game.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void doWatchPhase() throws IOException, ClassNotFoundException {
    client.sendObject(Constant.TO_WATCH_INFO);
    String confirmInfo = (String) client.receiveObject();
    out.println(confirmInfo);
    while (true) {
      String result = (String) client.receiveObject();
      out.println(result);
      String gameInfo = (String) client.receiveObject();
      out.println(gameInfo);
      if (gameInfo.equals(Constant.GAME_END_INFO)) {
        doEndPhase();
        break;
      }
    }
  }

  /**
   * This method lets the player to end the game.
   * 
   * Player need to send TO_QUIT_INFO to server at first and receive confirm info
   * from server. Then the player will disconnet with the server and game end.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void doEndPhase() throws IOException, ClassNotFoundException {
    client.sendObject(Constant.TO_QUIT_INFO);
    String confirmInfo = (String) client.receiveObject();
    out.println(confirmInfo);
    client.disconnectServer();
  }
}
