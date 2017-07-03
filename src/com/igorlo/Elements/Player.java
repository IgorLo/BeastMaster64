package com.igorlo.Elements;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character {

    private boolean isTraveled = false;
    private int money = 0;

    private List<String> takenTreassures = new ArrayList<>();
    private List<String> defeatedMonsters = new ArrayList<>();

    public Player(String name, int strenght, int agility, int intelligence) {
        super(name, strenght, agility, intelligence, strenght*6, strenght*6);
    }

    public static Player createCharacter(String name, int strenght, int agility, int intelligence) {

        return new Player(name, strenght, agility, intelligence);
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount){
        money += amount;
    }

    public void subMoney(int amount){
        money -= amount;
    }

    public boolean pay(int amount){
        if (money >= amount){
            subMoney(amount);
            return true;
        }
        return false;
    }

    public boolean isTraveled() {
        return isTraveled;
    }

    public void setTraveled() {
        isTraveled = true;
    }

    public void giveTreassure(Treasure treasure) {
        takenTreassures.add(treasure.getName());
        addMoney(treasure.getMoney());
    }

    public void killedMonster(NotPlayableCharacter monster){
        defeatedMonsters.add(monster.getName());
    }

    public List<String> getTakenTreassures() {
        return takenTreassures;
    }

    public List<String> getDefeatedMonsters() {
        return defeatedMonsters;
    }
}
