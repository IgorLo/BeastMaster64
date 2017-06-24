package com.igorlo.Elements;

import java.util.Scanner;

/**
 * Created by 94405 on 21.06.2017.
 */
public class Player extends Character {

    public Player(String name, int strenght, int agility, int intelligence) {
        super(name, strenght, agility, intelligence, strenght*6, strenght*6);
    }

    public static Player createCharacter(String name, int strenght, int agility, int intelligence) {

        return new Player(name, strenght, agility, intelligence);
    }
}
