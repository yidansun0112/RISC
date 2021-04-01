package edu.duke.ece651.risc.server;

/**
 * This class is to represent a registered user of the RISC game. The game
 * server will used the infomation in this class to authentificate a user who
 * tries login or join a game.
 */
public class RISCUser {
  /** The name of this user */
  String userName;
  /** The password of this user */
  String password;

  /**
   * Constructor that initializes the fields with corresponding parameters.
   * 
   * @param userName
   * @param password
   */
  public RISCUser(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

}
