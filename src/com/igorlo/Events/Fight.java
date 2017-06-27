package com.igorlo.Events;

import com.igorlo.Elements.NotAPlayer;
import com.igorlo.Elements.Player;

public class Fight {

    private final Player player;
    private final NotAPlayer enemy;

    public Fight(Player player, NotAPlayer enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public int startFighting(){
        //if player wins
        return 1;
        //if player runs return 2;
        //if player avoided the fight return 3;
        //if monster wins and player is dead return 0;
        //TODO
    }

    public Player getPlayer() {
        return player;
    }

    public NotAPlayer getEnemy() {
        return enemy;
    }
}
