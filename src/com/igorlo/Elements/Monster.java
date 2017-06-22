package com.igorlo.Elements;

public class Monster extends Character {

    public Monster(String name, int strenght, int agility, int intelligence, int health, int maxHealth) {
        super(name, strenght, agility, intelligence, health, maxHealth);
    }

    public static Monster generate(int dangerLevel) {
        if (dangerLevel == 0) return null;
        final int health = (int) Math.random()*5*dangerLevel;
        return new Monster("MONSTER",
                (int) Math.random()*2*dangerLevel,
                (int) Math.random()*2*dangerLevel,
                (int) Math.random()*2*dangerLevel,
                health, health);
    }
}
