package edu.duke.ece651.risc.shared;
import java.util.*;
public class V1BoardFactory<T> implements BoardFactory<T>{
  protected HashMap<Integer, String> terriName;
  public V1BoardFactory(){
    terriName = new HashMap<Integer,String>();
    initTerriName();
  }
  protected void initTerriName(){
    terriName.put(0,"Narnia");
    terriName.put(1,"Midkemia");
    terriName.put(2,"Oz");
    terriName.put(3,"Elantris");
    terriName.put(4,"Roshar");
    terriName.put(5,"Scadrial");
    terriName.put(6,"Gondor");
    terriName.put(7,"Mordor");
    terriName.put(8,"Hogwarts");
    terriName.put(9,"Narnia");
    terriName.put(10,"Britt");
    terriName.put(12,"Calibre");
    terriName.put(13,"Elk");
    terriName.put(14,"Floyd");
    
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






