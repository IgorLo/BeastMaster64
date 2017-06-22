package com.igorlo;

import com.igorlo.Elements.Player;

public class MainGame {

    public static void main(String[] args) {

        Player player = Player.createCharacter();
        System.out.println();

        InteractiveConsole Game = new InteractiveConsole(player);
        Game.startConversation();

    }





}
