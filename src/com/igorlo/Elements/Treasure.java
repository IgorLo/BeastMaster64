package com.igorlo.Elements;

import com.igorlo.Utilities;

public class Treasure {

    private final String name;
    private final int money;
    private static final String[] POSSIBLE_NAMES = new String[]{"Дедушкин старый сундук",
            "Покоцаный бронзовый меч", "Поцарапанная бабушкина тарелка"};

    public Treasure(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public static Treasure generate(int treasureness) {
        String name = (String) Utilities.randomElement(POSSIBLE_NAMES);
        final int money = (int) Math.sqrt((2+Math.random()*10)*Math.random()*3);

        return new Treasure(name, money);
    }
}
