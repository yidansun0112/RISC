package edu.duke.ece651.risc.shared;
import java.util.*;
public class V1BoardFactory<T> implements BoardFactory<T>{
  protected HashMap<Integer, String> terriName;
  public V1BoardFactory(){
    terriName = new HashMap<Integer,String>();
    initTerriName();
  }
  protected void initTerriName(){
    terriName.put(0,"");
  }
  protected Board<T> make2PlayerBoard(){
    Board<T> b = new V1GameBoard<T>();
  }

  protected Board<T> make3PlayerBoard(){
  }

  protected Board<T> make4PlayerBoard(){
  }

  protected Board<T> make5PlayerBoard(){
  }
  
	@Override
	public Board<T> makeGameBoard(int playerNum) {
		// TODO Auto-generated method stub
		return null;
	}

}






