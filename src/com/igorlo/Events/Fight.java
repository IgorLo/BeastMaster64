package com.igorlo.Events;

import com.igorlo.Elements.Monster;
import com.igorlo.Elements.Player;

public class Fight {

    private final Player player;
    private final Monster monster;

    public Fight(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
    }

    public int startFighting(){
        //if player wins
        return 1;
        //if player runs return 2;
        //if monster wins and player is dead return 0;

        //TODO
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return monster;
    }
}
