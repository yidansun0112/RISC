package edu.duke.ece651.risc.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardTextView extends BoardView<String> {

  public BoardTextView(Board<String> display) {
    super(display);
  }

  /*
   * @param t is the Territory
   * 
   * @param index is the Id of the cooresponding Group or Player
   * 
   * @oaram res is the HashMap to store different kinds of territory. This
   * Function will store territory of different group into the Map.
   */
  protected void classifyHelper(Territory<String> t, int index, HashMap<Integer, ArrayList<Territory<String>>> res) {
    if (!res.containsKey(index)) {
      ArrayList<Territory<String>> temp = new ArrayList<>();
      temp.add(t);
      res.put(index, temp);
    } else {
      res.get(index).add(t);
    }
  }

  /*
   * this will iterate over the TerritoryList to classify different Territories.
   * 
   * @param res is the HashMap to store.
   */
  protected void classify(HashMap<Integer, ArrayList<Territory<String>>> res) {
    res.clear();
    for (Territory<String> t : toDisplay.getTerritories()) {
      // If the Territory is initial
      if (t.getOwner() == -1) {
        int index = -t.getGroup() - 2;
        classifyHelper(t, index, res);
      }
      // If the Territory has been assigned an owner
      else {
        classifyHelper(t, t.getOwner(), res);
      }
    }
  }

  /*
   * This Funciton will help to make the header of the response.
   * 
   * @param assigned to identify if this is a group or player.
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
    HashMap<Integer, ArrayList<Territory<String>>> res = new HashMap<Integer, ArrayList<Territory<String>>>();
    classify(res);
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
    HashMap<Integer, ArrayList<Territory<String>>> res = new HashMap<Integer, ArrayList<Territory<String>>>();
    classify(res);
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
    HashMap<Integer, ArrayList<Territory<String>>> res = new HashMap<Integer, ArrayList<Territory<String>>>();
    classify(res);
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
