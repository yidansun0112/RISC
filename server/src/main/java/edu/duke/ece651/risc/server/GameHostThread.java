package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.shared.*;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;


import java.io.IOException;
import java.lang.Thread;

public class GameHostThread<T> extends Thread{

  private PlayerEntity<T> player;
  private int remainedUnits;
  private Board<T> board;
  private BoardView<T> view;
  private OrderRuleChecker<T> checker;
  private CyclicBarrier barrier;

  public GameHostThread(PlayerEntity<T> player, int units, Board<T> board, BoardView<T> view,OrderRuleChecker<T> checker,CyclicBarrier barrier){
    this.player=player;
    this.remainedUnits=units;
    this.board=board;
    this.view=view;
    this.checker=checker;
    this.barrier=barrier;
  }

  public void pickTerritory() throws IOException, ClassNotFoundException{
    //player side : receive game map, send choice, wait for choice info
    //success break, otherwise, go back

    //server side : send displayboard, receive choice, use occupyterritory,
    //if true, send success
    //if false, send info, and then send showgroup again
    while(true){
      player.sendObject(view.displayFullBoard());
      String choice=(String)player.receiveObject();
      //TODO: occupy should be synchronized
      boolean success=board.occupyTerritory(Integer.parseInt(choice), player.getPlayerId());
      if(success){
        player.sendObject(Constant.VALID_MAP_CHOICE_INFO);
        break;
      }
      else{
        player.sendObject(Constant.INVALID_MAP_CHOICE_INFO);
      }
    }
  }


  public void deployUnits() throws IOException, ClassNotFoundException{
    //player side: receive msg, send deployment (arraylist), receive info
    //continue, until finish deploy

    //servier side: send msg (display group), receive deployment (arraylist), send info (if all done, send finish deploy)
    while(remainedUnits>0){
      player.sendObject(view.displayGroup(player.getPlayerId()));
      ArrayList<Integer> deployment=(ArrayList<Integer>)player.receiveObject();
      int territoryId=deployment.get(0);
      int unitAmount=deployment.get(1);
      //check units
      //>0 continue
      if(remainedUnits-unitAmount>=0){
        boolean result=board.deployUnits(territoryId, unitAmount, player.getPlayerId());
        if(result){
          remainedUnits-=unitAmount;
          player.sendObject(Constant.LEGAL_DEPLOY_INFO);
        }
        else{
          player.sendObject("You are not the owner of this territory!");
        }
      }
      else{
        player.sendObject("You don't have enough units remained!");
      }
    }
    player.sendObject(Constant.FINISH_DEPLOY_INFO);
  }

}