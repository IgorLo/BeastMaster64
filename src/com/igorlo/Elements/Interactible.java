package com.igorlo.Elements;

import com.igorlo.Files.InteractiblesReader;
import com.igorlo.Utilities;

import java.util.List;

public class Interactible {

    private final String name;
    private final String textOnInteraction;
    private final static List<Interactible> POSSIBLE_INTERACTIBLES = InteractiblesReader.readInteractibles();

    public Interactible(String name, String textOnInteraction) {
        this.name = name;
        this.textOnInteraction = textOnInteraction;
    }

    public static List<Interactible> allInteractibles(){
        return POSSIBLE_INTERACTIBLES;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return textOnInteraction;
    }

    public static Interactible generate() {
        int randomIndex = Utilities.random(0, POSSIBLE_INTERACTIBLES.size() - 1);
        return POSSIBLE_INTERACTIBLES.get(randomIndex);
    }
}
