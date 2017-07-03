package com.igorlo.Files;

import com.igorlo.Elements.Interactable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class InteractablesReader {

    public static List<Interactable> readInteractables(){

        final File fileOfInteractables = new File("src/com/igorlo/resources/Interactibles.txt");

        final List<Interactable> listOfInteractables = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileOfInteractables));
            String line = reader.readLine();
            line = reader.readLine();

            while (line != null){
                final String[] nameAndText = line.split("\\|");
                final String name = nameAndText[0];
                final String text = nameAndText[1];
                final boolean isUniq = Boolean.parseBoolean(nameAndText[2]);
                final Interactable interactable = new Interactable(name, text, isUniq);
                listOfInteractables.add(interactable);
                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfInteractables;

    }
}
