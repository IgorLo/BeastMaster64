package com.igorlo.Elements;

import com.igorlo.Utilities;

public class Treasure {

    private final String name;
    private final int money;
    private static final String[] POSSIBLE_NAMES = new String[]{"Дедушкина любимая кружка",
            "Покоцаный бронзовый меч", "Поцарапанная бабушкина тарелка", "Лёшин утерянный фиджет-спинер",
            "Диск с модами на Minecraft"};

    public Treasure(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public static Treasure generate(int treasureness) {
        String name = (String) Utilities.randomElement(POSSIBLE_NAMES);
        final int money = (int) Math.sqrt(Utilities.random(10, 200) * treasureness);

        return new Treasure(name, money);
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }
}
