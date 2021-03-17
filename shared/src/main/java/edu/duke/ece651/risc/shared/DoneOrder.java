package edu.duke.ece651.risc.shared;

public class DoneOrder<T> implements Order<T> {
  private String msg;

  public DoneOrder(){
    msg="I'm Done.";
  }

  @Override
  public boolean execute(Board<T> board){return false;}

  /*
   * This is the SrcTerritory getter Function.
   */
  @Override
  public int getSrcTerritory(){return -1;}

  /*
   * This is the DestTerritory getter Function.
   */
  @Override
  public int getDestTerritory(){return -1;}

  /*
   * This is the unitAmount getter Function.
   */
  @Override
  public int getUnitAmount(){return -1;}
}