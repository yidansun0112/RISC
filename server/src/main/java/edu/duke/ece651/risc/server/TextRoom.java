package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import edu.duke.ece651.risc.shared.*;
public class TextRoom extends GameRoom<String>{
  

  public TextRoom(){
    this(0, Constant.TOTAL_UNITS, new Vector<PlayerEntity<String>>(), null, null);
  }

  public TextRoom(int playerNum, int totalUnits, List<PlayerEntity<String>> players, Board<String> gameBoard, BoardView<String> view) {
    super(playerNum,totalUnits,players,gameBoard,view);
  }

  @Override
  public void chooseMap() throws IOException, ClassNotFoundException{
    PlayerEntity<String> firstPlayer=players.get(0);
    BoardFactory<String> factory=new V1BoardFactory<String>();
    gameBoard=factory.makeGameBoard(playerNum);
    gameBoard.updateAllPrevDefender();
    view=new BoardTextView(gameBoard);
    String msg=view.displayFullBoard()+"We only have one map now, please type any number to continue.";
    firstPlayer.sendObject(msg);
    String choice=(String)firstPlayer.receiveObject(); // NOTE: not used in evolution1, since there's only one map
    firstPlayer.sendObject(Constant.VALID_MAP_CHOICE_INFO);
  }

}







