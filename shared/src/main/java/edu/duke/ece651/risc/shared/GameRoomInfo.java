package edu.duke.ece651.risc.shared;

import java.io.Serializable;

/**
 * This class wrap some information of a game room (e.g., room id, room owner
 * name, num of total players), which is used to display rooms in GUI when
 * player wants to join an existing room.
 * 
 * @since evolution 2
 */
public class GameRoomInfo implements Serializable {
  /**
   * Fields required by Serializable
   */
  private static final long serialVersionUID = 7L;

  int roomId;
  int totalPlayers;
  String roomOwnerName;

  /**
   * Constructor that initialize fields with corresponding parameters.
   * 
   * @param roomId        the room id of the room which this class to represent to
   * @param totalPlayers  the number of players should be in the room, which this
   *                      class to represent to
   * @param roomOwnerName the name of the room owner of the room which this class
   *                      to represent to
   */
  public GameRoomInfo(int roomId, int totalPlayers, String roomOwnerName) {
    this.roomId = roomId;
    this.totalPlayers = totalPlayers;
    this.roomOwnerName = roomOwnerName;
  }

  /**
   * @return the roomId
   */
  public int getRoomId() {
    return roomId;
  }

  /**
   * @return the totalPlayers
   */
  public int getTotalPlayers() {
    return totalPlayers;
  }

  /**
   * @return the roomOwnerName
   */
  public String getRoomOwnerName() {
    return roomOwnerName;
  }

}
