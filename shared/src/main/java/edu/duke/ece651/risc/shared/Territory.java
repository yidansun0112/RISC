package edu.duke.ece651.risc.shared;

import java.util.*;

public interface Territory<T>{
  public HashMap<String,Integer> getDisplayInfo(boolean isSelf);

  public void setOwner(int owner);

  public int getOwner();

  public void setUnitAmount(int amount);

  public int getUnitAmount();

  public int getId();
  public String getName();
  public int getGroup();

  public Vector<Integer> getNeigh();

  public Vector<Army<T>> getCurrDefenderArmy();
  public Vector<Army<T>> getEnemyArmy();
  public void addEnemy(int playerId, int amount);
  public void updatePrevDefender();

  public void initCurrDefender(int owner);

}













