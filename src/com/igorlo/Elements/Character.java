package com.igorlo.Elements;

public class Character {

    //базовые константы:

    //базовый шанс попадания при равной ловкости
    private static final double BASE_HIT_CHANCE = 0.75;
    private static final double BASE_BLOCK_CHANCE = 0.95;


    private final String name;
    private int strenght;
    private int agility;
    private int intelligence;
    private int maxHealth;
    private int curHealth;
    private boolean isDead;

    //Переменные, используемые в бою
    private boolean isBlocked = false;

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

    public void kill(){
        isDead = true;
        curHealth = 0;
    }

    public double getToughness(){ return 2*strenght+agility; }

    public boolean isHealed(){
        if (curHealth == maxHealth) return true;
        return false;
    }

    public void dealDamage(int dmg){
        if (dmg < curHealth)
            curHealth -= dmg;
        else {
            kill();
        }
    }

    public void heal(int hp){
        if (hp > 0){
            if (hp < maxHealth-curHealth) curHealth += hp;
            else curHealth = maxHealth;
        }
    }

    public void block(){
        isBlocked = true;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public short attack(Character character, boolean isStrong){
        double damage = getToughness()*5/character.getToughness();
        damage *= 0.8 + Math.random()/2.5;

        if (isStrong)
            damage *= 1.6;

        if (tryDodge(character, isStrong))
            return 2;

        if (character.isBlocked()) {
            if (Math.random() < BASE_BLOCK_CHANCE) {
                //Жертва смогла заблокировать удар
                character.dealDamage((int) (damage/5));
                return 3;
            } else {
                //Жертва попыталась и не смогла заблокировать удар
                character.dealDamage((int) (damage * 1.3));
                return 4;
            }
        }

        character.dealDamage((int) damage);
        return 1;
    }

    private boolean tryDodge(Character character, boolean isStrong) {
        double hitChance = BASE_HIT_CHANCE;
        double deltaAgi = Math.abs(getAgility() - character.getAgility());

        if (getAgility() > character.getAgility())
            hitChance += (deltaAgi / (100 + deltaAgi)) * 0.25;
        if (getAgility() < character.getAgility())
            hitChance -= (deltaAgi / (100 + deltaAgi)) * 0.6;

        if (isStrong)
            hitChance *= 0.8;

        double result = Math.random();
        if (result > hitChance) return true;
        return false;
    }

}
