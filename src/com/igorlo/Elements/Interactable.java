package com.igorlo.Elements;

import com.igorlo.Files.InteractablesReader;
import com.igorlo.Utilities;

import java.util.List;

public class Interactable {

    private final String name;
    private final String textOnInteraction;
    private final boolean isUniq;
    private final static List<Interactable> POSSIBLE_INTERACTABLES = InteractablesReader.readInteractables();

    public Interactable(String name, String textOnInteraction, boolean isUniq) {
        this.name = name;
        this.textOnInteraction = textOnInteraction;
        this.isUniq = isUniq;
    }

    public static List<Interactable> allInteractables(){
        return POSSIBLE_INTERACTABLES;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return textOnInteraction;
    }

    public boolean isUniq() {
        return isUniq;
    }

    public static Interactable generate() {
        int randomIndex = Utilities.random(0, POSSIBLE_INTERACTABLES.size() - 1);
        return POSSIBLE_INTERACTABLES.get(randomIndex);
    }
}
