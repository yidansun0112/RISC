package edu.duke.ece651.risc.shared;

public class BoardTextView extends BoardView<String> {
 
 public BoardTextView(Board<String> display) {
    super(display);
  }

  /*
   * This Funciton will help to make the header of the response.
   */
  public String makeHead(boolean assigned, int idx) {
    String parseLine = "------------------\n";
    if (assigned) {
      return "Player " + idx + ":\n" + parseLine;
    }
    return "Group " + idx + ":\n" + parseLine;
  }

  /*
   * This Function will help to display The whole Board.
   */
  @Override
  public String displayFullBoard() {
    classify();
    StringBuffer sb = new StringBuffer();
    for (int Id : res.keySet()) {
      // If Id < -1, it is an un-assigned territory;
      if (Id < -1) {
        sb.append(makeHead(false, -Id - 2));
      } else {
        sb.append(makeHead(true, Id));
      }
      // What is the display info after choice.
      for (Territory<String> t : res.get(Id)) {
        sb.append(toDisplay.whatIsIn(t, false));
      }
    }
    return sb.toString();
  }

  /*
   * For Different playerId, it the territory belongs to him, we will display it
   * in self_Version. If not, we will display it in Enemy Verion.
   */

  @Override
  public String displayBoardFor(int playerId) {

    classify();
    StringBuffer sb = new StringBuffer();
    for (int Id : res.keySet()) {
      // What is the display info after choice.
      sb.append(makeHead(true, Id));
      for (Territory<String> t : res.get(Id)) {
        sb.append(toDisplay.whatIsIn(t, t.getOwner() == playerId));
      }
    }
    return sb.toString();
    /*
     * StringBuffer sb = new StringBuffer(); for (Territory<String> t :
     * toDisplay.getTerritories()) { sb.append(toDisplay.whatIsIn(t, t.getOwner() ==
     * playerId)); } return sb.toString();
     */
  }

  /*
   * Return a Single Group of Territory.
   */
  @Override
  public String displayGroup(int playerId) {
    classify();
    StringBuffer sb = new StringBuffer();
    for (Territory<String> t : res.get(playerId)) {
      sb.append(toDisplay.whatIsIn(t, true));
    }
    return sb.toString();
  }

  /*
   * @Override public String showTerritoryGroup() { }
   */
}
