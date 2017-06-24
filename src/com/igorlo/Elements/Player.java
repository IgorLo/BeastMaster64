package com.igorlo.Elements;

public class Player extends Character {

    private boolean isTraveled = false;

    public Player(String name, int strenght, int agility, int intelligence) {
        super(name, strenght, agility, intelligence, strenght*6, strenght*6);
    }

    public static Player createCharacter(String name, int strenght, int agility, int intelligence) {

        return new Player(name, strenght, agility, intelligence);
    }

    public boolean isTraveled() {
        return isTraveled;
    }

    public void setTraveled() {
        isTraveled = true;
    }
}
