package com.igorlo.Elements;

public class Character {

    private final String name;
    private int strenght;
    private int agility;
    private int intelligence;
    private int maxHealth;
    private int curHealth;
    private boolean isDead;

    public Character(String name, int strenght, int agility, int intelligence, int health, int maxHealth) {
        this.name = name;
        this.strenght = strenght;
        this.agility = agility;
        this.intelligence = intelligence;
        this.curHealth = health;
        this.maxHealth = maxHealth;
        this.isDead = false;
    }

    public String getName() {
        return name;
    }

    public int getStrenght() {
        return strenght;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getHealth() {
        return curHealth;
    }

    public String HP(){ return curHealth+"\\"+maxHealth; }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isAlive() {
        return !isDead;
    }

    public int getToughness(){ return strenght+agility; }

    public void dealDamage(int dmg){
        if (dmg < curHealth)
            curHealth -= dmg;
        else {
            curHealth = 0;
            isDead = true;
        }
    }

    public void heal(int hp){
        if (hp > 0){
            if (hp < maxHealth-curHealth) curHealth += hp;
            else curHealth = maxHealth;
        }
    }

    public void attack(Character character){
        int damage = getToughness()/5;
        if (damage > 0)
            character.dealDamage(damage);
    }

}
