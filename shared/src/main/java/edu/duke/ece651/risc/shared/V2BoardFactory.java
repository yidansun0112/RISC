package edu.duke.ece651.risc.shared;

import java.util.*;

public class V2BoardFactory<T> extends BoardFactory<T> {

    public V2BoardFactory() {
        terriName = new HashMap<>();
        initTerriName();
    }

    @Override
    protected Board<T> make2PlayerBoard() {
        ArrayList<Territory<T>> territories = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            territories.add(new V2Territory<T>(i, terriName.get(i), i / 3, worldMap2[i], Constant.TERRITORY_SIZE,
                    Constant.TERRITORY_FOOD_PRODUCTIVITY, Constant.TERRITORY_TECH_PRODUCTIVITY));

        }
        Board<T> b = new V2GameBoard<>(territories, worldMap2);
        return b;
    }

    @Override
    protected Board<T> make3PlayerBoard() {
        ArrayList<Territory<T>> territories = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            territories.add(new V2Territory<T>(i, terriName.get(i), i / 3, worldMap3[i], Constant.TERRITORY_SIZE,
                    Constant.TERRITORY_FOOD_PRODUCTIVITY, Constant.TERRITORY_TECH_PRODUCTIVITY));

        }
        Board<T> b = new V2GameBoard<>(territories, worldMap3);
        return b;
    }

    @Override
    protected Board<T> make4PlayerBoard() {
        ArrayList<Territory<T>> territories = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            territories.add(new V2Territory<T>(i, terriName.get(i), i / 3, worldMap4[i], Constant.TERRITORY_SIZE,
                    Constant.TERRITORY_FOOD_PRODUCTIVITY, Constant.TERRITORY_TECH_PRODUCTIVITY));

        }
        Board<T> b = new V2GameBoard<>(territories, worldMap4);
        return b;
    }

    @Override
    protected Board<T> make5PlayerBoard() {
        ArrayList<Territory<T>> territories = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            territories.add(new V2Territory<T>(i, terriName.get(i), i / 3, worldMap5[i], Constant.TERRITORY_SIZE,
                    Constant.TERRITORY_FOOD_PRODUCTIVITY, Constant.TERRITORY_TECH_PRODUCTIVITY));

        }
        Board<T> b = new V2GameBoard<>(territories, worldMap5);
        return b;
    }

}
