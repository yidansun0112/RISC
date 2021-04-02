package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.util.Random;

import edu.duke.ece651.risc.shared.AttackOrderConsistencyChecker;
import edu.duke.ece651.risc.shared.AttackOrderEffectChecker;
import edu.duke.ece651.risc.shared.AttackOrderPathChecker;
import edu.duke.ece651.risc.shared.BoardFactory;
import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.MoveOrderConsistencyChecker;
import edu.duke.ece651.risc.shared.MoveOrderEffectChecker;
import edu.duke.ece651.risc.shared.MoveOrderPathChecker;
import edu.duke.ece651.risc.shared.OrderRuleChecker;
import edu.duke.ece651.risc.shared.PlayerEntity;
import edu.duke.ece651.risc.shared.V1BoardFactory;

/**
 * README: in evo1, we start let the first player to choose map is when all
 * players are in this room. Then after choosing the map, we immediately call
 * the playGame(). Now we need a new method called addPlayer() and check whether
 * we have enough player to start chooseMap and playGame in this method in
 * evo2!!!
 */

public class V2GameRoom extends GameRoom<String> {

  /** The id of this room in the sevrer */
  int roomId; // in evo2, we have multiple rooms now, so we need an id to name a room

  /** 
   * The status of this game room, will be one of the following:
   * (a) waiting for players
   * (b) running the game
   * (c) game is finished
   */
  int roomStatus;

  /** rule checker for upgrade unit order */
  OrderRuleChecker<String> upgradeUnitChecker;

  /** rule checker for upgrade max technology level order */
  OrderRuleChecker<String> upgradeTechLevelChecker;

  /**
   * Constructor that initailizes fields with corresponding parameters. Note that
   * the board is initialized after the first player has decided the map, and the
   * view may not be used in evo2.
   * 
   * @param roomId
   * @param roomCreator
   */
  public V2GameRoom(int roomId, PlayerEntity<String> roomCreator) {
    // TODO check this contructor again when most of other code are finished!!!
    super(0, Constant.TOTAL_UNITS, roomCreator);
    this.roomId = roomId;
    this.moveChecker = makeMoveOrderRuleCheckerChain();
    this.attackChecker = makeAttackOrderRuleCheckerChain();
    this.upgradeUnitChecker = makeUpgradeUnitOrderRuleCheckerChain();
    this.upgradeTechLevelChecker = makeUpgradeTechLevelOrderRuleCheckerChain();
    this.resolver = makeBattleResolver();

  }

  // --- Below is the helper method that make the evo2 Battle Resolver --- //
  public static Resolver<String> makeBattleResolver() {
    return new BattleResolver<String>(new Random(Constant.randomSeed));
  }

  // --- Below are the helper methods that make order rule checker chains --- //

  public static OrderRuleChecker<String> makeMoveOrderRuleCheckerChain() {
    // TODO: replace the checker into version 2 checker when they are finished!!!
    return new MoveOrderConsistencyChecker<String>(
        new MoveOrderPathChecker<String>(new MoveOrderEffectChecker<String>(null)));
  }

  public static OrderRuleChecker<String> makeAttackOrderRuleCheckerChain() {
    // TODO: replace the checker into version 2 checker when they are finished!!!
    return new AttackOrderConsistencyChecker<String>(
        new AttackOrderPathChecker<String>(new AttackOrderEffectChecker<String>(null)));
  }

  public static OrderRuleChecker<String> makeUpgradeUnitOrderRuleCheckerChain() {
    // TODO: finish this when checkers are finished!!!
    return null;
  }

  public static OrderRuleChecker<String> makeUpgradeTechLevelOrderRuleCheckerChain() {
    // TODO: finish this when checkers are finished!!!
    return null;
  }


  /**
   * This method will add a new player who wants to join in this game room. This method will first checker 
   */
  
  public void addPlayerAndCheckToPlay(PlayerEntity<String> newPlayer) {

  }


  /**
   * This method will let the first player (who create this game room) select the
   * map. This method should be called only after all players have in the room.
   */
  @Override
  public void chooseMap() throws IOException, ClassNotFoundException {
    PlayerEntity<String> firstPlayer = players.get(0);
    // TODO: replace V1BoardFactory with V2BoardFactory later!
    BoardFactory<String> factory = new V1BoardFactory<String>();
    gameBoard = factory.makeGameBoard(playerNum);
    gameBoard.updateAllPrevDefender();

    // In evo 2 we make the map choice be type of int
    String choice = (String) firstPlayer.receiveObject(); // not used in evolution 2, since there's only one map
    // firstPlayer.sendObject(Constant.VALID_MAP_CHOICE_INFO);
  }

  /**
   * @return the roomStatus
   */
  @Override
  public int getRoomStatus() {
    return roomStatus;
  }

  /**
   * @param roomStatus the roomStatus to set
   */
  @Override
  public void setRoomStatus(int roomStatus) {
    this.roomStatus = roomStatus;
  }

}
