package com.igorlo;

public class Utilities {

    public static Object randomElement(Object[] array){
        int randomIndex = (int) (Math.random()*array.length);
        return array[randomIndex];
    }

}
