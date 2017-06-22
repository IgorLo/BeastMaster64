package com.igorlo.Elements;

import java.util.Scanner;

/**
 * Created by 94405 on 21.06.2017.
 */
public class Player extends Character {

    public Player(String name, int strenght, int agility, int intelligence, int health, int maxHealth) {
        super(name, strenght, agility, intelligence, health, maxHealth);
    }

    public static Player createCharacter() {
        System.out.print("Enter your name, traveller: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        int strenght = 10;
        int agility = 10;
        int intelligence = 10;
        int health = 45;
        int maxHealth = 70;

        System.out.println("Player \"" + name + "\" created.");
        System.out.println("Your Stats: " + strenght + "\\" + agility + "\\" + intelligence);
        System.out.println("Your health: " + health + "\\" + maxHealth);

        return new Player(name, strenght, agility, intelligence, health, maxHealth);
    }
}
