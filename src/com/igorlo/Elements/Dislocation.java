package com.igorlo.Elements;

import com.igorlo.Elements.NPCs.Monster;
import com.igorlo.Files.DislocationTypesReader;
import com.igorlo.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Dislocation {

    private static final double INTERACTIBLE_GENERATION_CHANCE = 0.3;
    private static final int MAX_DANGERLEVEL = 2;

    private static final List<DislocationType> POSSIBLE_TYPES = DislocationTypesReader.readLocations();
    private final DislocationType type;
    private Treasure treasure;
    private NotPlayableCharacter npc;
    private final List<Dislocation> variants;
    private final List<Interactable> interactables;
    private final Dislocation cameFrom;
    private boolean playerLookedAround = false;
    private boolean isDiscovered = false;
    private boolean justArrived = true;
    private boolean isStartingPoint = false;

    public Dislocation(DislocationType type, Treasure treasure,
                       Monster monster, List<Dislocation> variants,
                       List<Interactable> interactables, Dislocation cameFrom) {
        this.type = type;
        this.treasure = treasure;
        this.variants = variants;
        this.interactables = interactables;
        this.npc = monster;
        this.cameFrom = cameFrom;
    }

    public static Dislocation generate(int highestDanger, Dislocation cameFrom) {
        DislocationType type;
        do {type = randomType();}
        while (type.dangerLevel > highestDanger);

        Treasure treasure = Treasure.generate(type.treasureness);
        List<Dislocation> variants = new ArrayList<>();
        Monster monster = Monster.generate(type.dangerLevel);

        List<Interactable> interactables = new ArrayList<>();
        if (Math.random() < INTERACTIBLE_GENERATION_CHANCE)
            interactables.add(Interactable.generate());

        return new Dislocation(type, treasure, monster, variants, interactables, cameFrom);
    }



    private static DislocationType randomType(){
        final int randomIndex = Utilities.random(0, 2);
        return POSSIBLE_TYPES.get(randomIndex);
    }

    public int numberOfVariants(){
        return variants.size();
    }

    public String getName(){
        return type.getName();
    }

    public String getDescription(){
        return type.getDescription();
    }

    public boolean isJustArrived() {
        return justArrived;
    }

    public void setJustArrived() {
        this.justArrived = true;
    }

    public void notForTheFirstTime(){
        justArrived = false;
    }

    public void setNpc(NotPlayableCharacter npc) {
        this.npc = npc;
    }

    public int getDangerLvl(){
        return type.dangerLevel;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public List<Dislocation> getVariants() {
        return variants;
    }

    public Dislocation getCameFrom() {
        return cameFrom;
    }

    public boolean isStartingPoint() {
        return isStartingPoint;
    }

    public void setStartingPoint() {
        isStartingPoint = true;
    }

    public NotPlayableCharacter getNpc() {
        return npc;
    }

    public void defeatNpc(){
        npc.kill();
    }

    public Treasure getTreasure() {
        return treasure;
    }

    public void setTreasure(Treasure treasure) {
        this.treasure = treasure;
    }

    public void discover(int highestDanger, Dislocation cameFrom) {
        final int numberOfLocations = Utilities.random(1, 4);
        for (int i = 1; i <= numberOfLocations; i++){
            variants.add(generate(highestDanger, cameFrom));
        }
        isDiscovered = true;
    }

    public Treasure takeTreassure() {
        Treasure taken = treasure;
        setTreasure(null);
        return taken;
    }

    public void playerLookedAround(){
        playerLookedAround = true;
    }

    public boolean isPlayerLookedAround() {
        return playerLookedAround;
    }

    public List<Interactable> getInteractables() {
        return interactables;
    }



    public static class DislocationType {

        private final String name;
        private final String description;
        private final int dangerLevel;
        private final int treasureness;

        public DislocationType(String name, String description, int dangerLevel, int treasureness) {
            this.name = name;
            this.description = description;
            this.dangerLevel = dangerLevel;
            this.treasureness = treasureness;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getDangerLevel() {
            return dangerLevel;
        }

        public int getTreasureness() {
            return treasureness;
        }
    }

}
