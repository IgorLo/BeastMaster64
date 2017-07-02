package com.igorlo.Files;

import com.igorlo.Elements.Interactible;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class InteractiblesReader {

    public static List<Interactible> readInteractibles(){

        final File fileOfInteractibles = new File("src/com/igorlo/resources/Interactibles.txt");

        final List<Interactible> listOfInteractibles = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileOfInteractibles));
            String line = reader.readLine();

            while (line != null){
                final String[] nameAndText = line.split("\\|");
                final String name = nameAndText[0];
                final String text = nameAndText[1];
                final Interactible interactible = new Interactible(name, text);
                listOfInteractibles.add(interactible);
                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfInteractibles;

    }
}
