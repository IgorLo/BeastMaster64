package com.igorlo.Elements;

public class NotAPlayer extends Character {

    private String description;
    private boolean isAgressive;
    private boolean canConversate;
    private boolean isTreassureGuardian;
    private boolean isFightable;
    private boolean isTradeable;
    private boolean hasQuest;
    private boolean isHidden;
    private boolean isInteractable = true;

    public NotAPlayer(String name, int strenght, int agility,
                      int intelligence, int health, int maxHealth,
                      String description, boolean isAgressive,
                      boolean canConversate, boolean isTreassureGuardian,
                      boolean isFightable, boolean isTradeable,
                      boolean hasQuest, boolean isHidden) {
        super(name, strenght, agility, intelligence, health, maxHealth);
        this.description = description;
        this.isAgressive = isAgressive;
        this.canConversate = canConversate;
        this.isTreassureGuardian = isTreassureGuardian;
        this.isFightable = isFightable;
        this.isTradeable = isTradeable;
        this.hasQuest = hasQuest;
        this.isHidden = isHidden;
    }

    public boolean isAgressive() {
        return isAgressive;
    }

    public boolean canConversate(){
        return canConversate;
    }

    public boolean isTreassureGuardian(){
        return isTreassureGuardian;
    }

    public boolean isFightable() {
        return isFightable;
    }

    public boolean isTradeable() {
        return isTradeable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAgressive(boolean agressive) {
        isAgressive = agressive;
    }

    public boolean hasQuest() {
        return hasQuest;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public void isNotInterractableAnymore(){
        isInteractable = false;
    }
}