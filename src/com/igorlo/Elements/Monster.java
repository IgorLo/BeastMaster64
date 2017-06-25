package com.igorlo.Elements;

import com.igorlo.Utilities;

public class Monster extends Character {

    public Monster(String name, int strenght, int agility, int intelligence, int health, int maxHealth) {
        super(name, strenght, agility, intelligence, health, maxHealth);
    }

    public static Monster generate(int dangerLevel) {
        if (dangerLevel == 0) return null;
        final int health = Utilities.random(5, 10)*dangerLevel;
        return new Monster("MONSTER",
                Utilities.random(1, 5) * dangerLevel,
                Utilities.random(1, 5) * dangerLevel,
                Utilities.random(1, 5) * dangerLevel,
                health, health);
    }
}
