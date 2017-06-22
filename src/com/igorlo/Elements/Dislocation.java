package com.igorlo.Elements;

import java.util.ArrayList;
import java.util.List;

public class Dislocation {

    private static final DislocationType[] POSSIBLE_TYPES = DislocationType.values();
    private final DislocationType type;
    private final Treasure treasure;
    private final Monster monster;
    private final List<Dislocation> variants;
    private boolean isDiscovered = false;

    public Dislocation(DislocationType type, Treasure treasure,
                       Monster monster, List<Dislocation> variants) {
        this.type = type;
        this.treasure = treasure;
        this.variants = variants;
        this.monster = monster;
    }

    public static Dislocation generate(int highestDanger) {
        DislocationType type;
        do type = randomType();
        while (type.dangerLevel > highestDanger);

        Treasure treasure = Treasure.generate(type.treasureness);
        List<Dislocation> variants = new ArrayList<>();
        Monster monster = Monster.generate(type.dangerLevel);

        return new Dislocation(type, treasure, monster, variants);
    }

    private static DislocationType randomType(){
        int randomIndex = (int) ((Math.random() * POSSIBLE_TYPES.length) - 1);
        return POSSIBLE_TYPES[randomIndex];
    }

    public String getName(){
        return type.getName();
    }

    public String getDescription(){
        return type.getDescription();
    }

    public int getDangerLvl(){
        return type.dangerLevel;
    }

    public boolean isDiscovered() {
        return isDiscovered;
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
