package com.igorlo.Elements;

import com.igorlo.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Dislocation {

    public static final int MAX_DANGERLEVEL = 2;
    private static final DislocationType[] POSSIBLE_TYPES = DislocationType.values();
    private final DislocationType type;
    private final Treasure treasure;
    private final Monster monster;
    private final List<Dislocation> variants;
    private boolean isDiscovered = false;
    private boolean justArrived = true;

    public Dislocation(DislocationType type, Treasure treasure,
                       Monster monster, List<Dislocation> variants) {
        this.type = type;
        this.treasure = treasure;
        this.variants = variants;
        this.monster = monster;
    }

    public static Dislocation generate(int highestDanger) {
        DislocationType type;
        do {type = randomType();}
        while (type.dangerLevel > highestDanger);

        Treasure treasure = Treasure.generate(type.treasureness);
        List<Dislocation> variants = new ArrayList<>();
        Monster monster = Monster.generate(type.dangerLevel);

        return new Dislocation(type, treasure, monster, variants);
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

    public void notForTheFirstTime(){
        justArrived = false;
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

    public void discover(int highestDanger) {
        final int numberOfLocations = Utilities.random(3, 8);
        for (int i = 1; i <= numberOfLocations; i++){
            variants.add(generate(highestDanger));
        }
        isDiscovered = true;
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

}
