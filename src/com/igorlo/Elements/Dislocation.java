package com.igorlo.Elements;

import com.igorlo.Elements.NPCs.Monster;
import com.igorlo.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Dislocation {

    public static final int MAX_DANGERLEVEL = 2;
    private static final DislocationType[] POSSIBLE_TYPES = DislocationType.values();
    private final DislocationType type;
    private Treasure treasure;
    private NotAPlayer npc;
    private final List<Dislocation> variants;
    private final List<Interactible> interactibles;
    private final Dislocation cameFrom;
    private boolean playerLookedAround = false;
    private boolean isDiscovered = false;
    private boolean justArrived = true;
    private boolean isStartingPoint = false;

    public Dislocation(DislocationType type, Treasure treasure,
                       Monster monster, List<Dislocation> variants,
                       List<Interactible> interactibles, Dislocation cameFrom) {
        this.type = type;
        this.treasure = treasure;
        this.variants = variants;
        this.interactibles = interactibles;
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

        List<Interactible> interactibles = new ArrayList<>();
        interactibles.add(Interactible.generate());

        return new Dislocation(type, treasure, monster, variants, interactibles, cameFrom);
    }



    private static DislocationType randomType(){
        final int randomIndex = Utilities.random(0, 2);
        return POSSIBLE_TYPES[randomIndex];
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

    public void setNpc(NotAPlayer npc) {
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

    public NotAPlayer getNpc() {
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

    public enum DislocationType {
        FOREST("Эльфийский Лес",
                "Светлое, красивое и безопасное место.\n" +
                        "Прежде здесь жили эльфы. Возможно вы сможете\n" +
                        "найти здесь что-нибудь полезное.",
                0, 10),

        DESERT("Скорпионья Пустошь",
                "Несмотря на отсутствие углов, опасность\n" +
                        "поджидает вас буквально повсюду. Однако,\n" +
                        "проходившие мимо караваны могли оставить\n" +
                        "здесь что-нибудь ценное.",
                1, 20),

        CAVE("Пещера Огра",
                "Это мрачное место буквально пропитано\n" +
                        "тревогой и страхом. Вам лучше не рисковать\n" +
                        "и поскорее убраться отсюда, пока на вас не\n" +
                        "напал серьезный противник.",
                2, 30);

        private final String name;
        private final String description;
        private final int dangerLevel;
        private final int treasureness;

        DislocationType(String name, String description, int dangerLevel, int treasureness) {
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

    public void playerLookedAround(){
        playerLookedAround = true;
    }

    public boolean isPlayerLookedAround() {
        return playerLookedAround;
    }

    public List<Interactible> getInteractibles() {
        return interactibles;
    }
}
