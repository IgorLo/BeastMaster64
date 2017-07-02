package com.igorlo;

import com.igorlo.Elements.Interactible;

import java.util.List;
import java.util.Random;

public class Utilities {

    private final static Random random = new Random();

    public static Object randomElement(Object[] array) {
        int randomIndex = random(0, array.length - 1);
        return array[randomIndex];
    }

    public static int random(int min, int max) {
        int num = min + random.nextInt(max + 1 - min);
        return num;
    }

    /*
    public static Object randomElement(List<Object> list) {
        int randomIndex = random(0, list.size() - 1);
        return list.get(randomIndex);
    }
    */
}
