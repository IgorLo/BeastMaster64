package com.igorlo;

import java.util.Random;

public class Utilities {

    private final static Random random = new Random();

    public static Object randomElement(Object[] array) {
        int randomIndex = (int) (Math.random()*array.length);
        return array[randomIndex];
    }

    public static int random(int min, int max) {
        int num = min + random.nextInt(max + 1 - min);
        return num;
    }

}
