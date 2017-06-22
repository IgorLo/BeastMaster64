package com.igorlo;

import com.igorlo.Elements.Dislocation;
import com.igorlo.Elements.Player;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class InteractiveConsole {

    private Player player;
    private Dislocation dislocation;
    private short consoleState = 0;
    private final String[] moves = new String[]{"Отдохнуть", "Искать сокровище", "Осмотреться", "Суицид", "Идти"};
    private final Scanner prey = new Scanner(System.in);

    public InteractiveConsole(Player plr){
        this.player = plr;
        dislocation = Dislocation.generate(0);
        consoleState = 1;
    }

    public void startConversation() {
        say("Тип местности: " + dislocation.getName());
        emptyLine();

        say("Описание: " + dislocation.getDescription());
        emptyLine();
        avalableMoves();
        emptyLine();
        say("Будь аккуратнее, путник. Некоторые твои решения");
        say("могут привести к неприятным последствиям.");
        emptyLine();
        say(String.format("И всё же, что ты собираешься делать, %s?", player.getName()));

        String choose = prey.next();
        emptyLine();
        switch (choose) {
            case "Отдохнуть":
                takeANap();
                break;
            case "Искать сокровище":
               // lookForTreassure();
                break;
            case "Осмотреться":
                //takeALook();
                break;
            case "Суицид":
                suicide();
                break;
            case "Идти":
                if (dislocation.isDiscovered()){}
                    //goTo();
                else
                    say("Пока что ты не знаешь, куда ты можешь пойти");
                    emptyLine();
                    say("Может быть, стоит осмотреться?");
                break;
            default:
                say("И что ты думаешь должно произойти?");
                emptyLine();
                say("Я должен понять то, что ты написал?");
                emptyLine();
                say("Я что по-твоему, нейросеть?");
                emptyLine();
                say(String.format("Попробуй ка ещё раз, %s.", player.getName()));
                break;
        }

        /*
        if (dislocation.getDangerLvl() == 0){
            say("Это место кажется действительно безопасным.");
        } else {
            say("Будь аккуратнее, путник. Некоторые твои решения");
            say("могут привести к неприятным последствиям.");
        }
        */

    }

    private void suicide() {
        say("Ты устал от всей этой суеты.");
        sleep(1200);
        say("Ты больше не знаешь, ради чего сражаться.");
        sleep(1200);
        say("Каждый божий день ты думаешь:");
        sleep(1200);
        say("Во имя чего? Во имя чего я продолжаю идти?");
        sleep(1200);
        say("Найдя спокойное уединённое место ты берёшь свой");
        say("короткий меч и вспарываешь собственный живот.");
        emptyLine();
        emptyLine();
        say("Ты чё, дурак?");
        sleep(1200);
        say("Player " + player.getName() + " suicided. What an idiot.");
        say("GAME OVER");

    }


    private void takeANap() {
        say("Ты нашёл относительно безопасное и спокойное");
        say("место, чтобы прилечь и немного восстановить");
        say("свои силы. Твоё здоровье постепенно растёт,");
        say("а негативные эффекты сходят на нет.");
        emptyLine();
        say(String.format("Спи сладко, %s", player.getName()));

        final int hpHealed =  4 + (int) (Math.random()*6);
        for (int i = 0; i < hpHealed; i++){
            if (player.getHealth() < player.getMaxHealth()){
                player.heal(1);
                say(player.HP());
                sleep(2000);
            }
        }
        emptyLine();
        say("Ты неплохо отдохнул, %USERNAME%, но снова пора в путь.");
    }

    private void emptyLine(){
        sleep(1000);
        System.out.println();
    }

    private void say(String wordOfGod){
        System.out.println(wordOfGod);
    }

    private void avalableMoves(){
        say("Ваши действия:");
        say(moves[1]);
        say(moves[2]);
        say(moves[3]);
        say(moves[4]);
        if (dislocation.isDiscovered()) say(moves[5]);
    }

    private String getMove(int index){
        return moves[index];
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
