package com.igorlo;

import com.igorlo.Elements.Character;
import com.igorlo.Elements.Dislocation;
import com.igorlo.Elements.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InteractiveConsole {

    private Player player;
    private Dislocation dislocation;
    private short consoleState = 0;
    private final String[] moves = new String[]{"Отдохнуть", "Искать сокровище", "Осмотреться", "Суицид", "Идти"};
    private final Scanner prey = new Scanner(System.in);

    public InteractiveConsole(){
        dislocation = Dislocation.generate(0);
    }

    public void conversate() {

        startNewGame();

        while (consoleState == 1){

            if (dislocation.isJustArrived()) aboutLocation();
            emptyLine();
            avalableMoves();
            emptyLine();

            String choose = prey.next().toLowerCase();
            emptyLine();
            switch (choose) {
                case "отдохнуть":
                    if (player.isHealed()) alreadyFullHealed();
                    else takeANap();
                    break;
                case "искать сокровище":
                    // lookForTreassure();
                    break;
                case "осмотреться":
                    if (!dislocation.isDiscovered()) takeALook();
                    else variantsYouKnow();
                    break;
                case "суицид":
                    suicide();
                    break;
                case "идти":
                    if (dislocation.isDiscovered()) goTo();
                    else notDiscoveredYet();
                    break;
                default:
                    commandUnknown();
                    break;
            }
        }

        if (consoleState == 2){
            say("Вы мертвы.");
            sleep(500);
            emptyLine();
            sleep(500);
            say("На этом ваше путешествие закончилось");
            sleep(500);
            emptyLine();
            sleep(500);
            say("КОНЕЦ ИГРЫ.");
        }


    }

    private void aboutLocation() {
        emptyLine();
        say("Тип местности: " + dislocation.getName());
        emptyLine();
        say("Описание: " + dislocation.getDescription());
        dislocation.notForTheFirstTime();
    }

    private boolean goTo() {
        say("Вы уверены, что хотите отправится в путь?");
        say("скажите \"НЕТ\" чтобы вернуться");
        say("скажите что угодно, чтобы отправится");
        String shouldIGo = prey.next().toLowerCase();
        if (shouldIGo.toLowerCase().equals("нет")) return true;

        emptyLine();
        say("Цифрой выбери дорогу, которой хочешь пойти:");
        variantsYouKnow();
        boolean gone = false;
        boolean isCorrect = true;
        do {
            if (!isCorrect){
                say("Попробуй ввести цифру ещё раз: ");
            }
            isCorrect = true;
            String choose = prey.next();

            if (choose.length() == 0) isCorrect = false;
            for (int i = 0; i < choose.length(); i++){
                if (!java.lang.Character.isDigit(choose.charAt(i))) isCorrect = false;
            }
            int index = -1;
            if (isCorrect) index = Integer.parseInt(choose);
            if (dislocation.numberOfVariants() < index || index < 1) isCorrect = false;
            if (isCorrect) {
                dislocation = dislocation.getVariants().get(index - 1);
                gone = true;
            }
        } while (!gone);

        emptyLine();
        say("Путешествуем...");
        for (int i = 0; i <= 10; i++){
            say("Путь пройден: " + i*10 + "%");
        }
        emptyLine();
        say("Ты прибыл на новую территорию.");
        return true;
    }

    private void alreadyFullHealed() {
        say("Твоё здоровье и так в полном порядке, " + player.getName() + ".");
    }

    private void notDiscoveredYet() {
        say("Пока что ты не знаешь, куда ты можешь пойти");
        emptyLine();
        say("Может быть, стоит осмотреться?");
    }

    private void commandUnknown() {
        say("И что ты думаешь должно произойти?");
        emptyLine();
        say("Я должен понять то, что ты написал?");
        emptyLine();
        say("Я что по-твоему, нейросеть?");
        emptyLine();
        say(String.format("Попробуй ка ещё раз, %s.", player.getName()));
    }

    private void startNewGame() {
        System.out.print("Enter your name, traveller: ");
        String name = prey.next();

        emptyLine();

        int startStrenght = 10;
        int startAgility = 10;
        int startIntelligence = 1;

        System.out.println("Player \"" + name + "\" created.");
        System.out.println("Your Stats: " + startStrenght + " \\ " + startAgility + " \\ " + startIntelligence);
        System.out.println("Your health: " + startStrenght*6 + " \\ " + startStrenght*6);

        player = Player.createCharacter(name, startStrenght, startAgility, startIntelligence);

        emptyLine();
        say("Будь аккуратнее, путник. Некоторые твои решения");
        say("могут привести к неприятным последствиям.");

        consoleState = 1;
    }

    private void takeALook() {
        say("Ты находишь высокое место, чтобы хорошенько");
        say("осмотреть местность.");
        emptyLine();
        say("Кажется, ты видишь несколько троп ведущих к");
        say("соседним территориям...");
        final int newDangerLvl = 2;
        dislocation.discover(newDangerLvl);
        emptyLine();
        say("Да, определённо.");
        sleep(100);
        variantsYouKnow();
    }

    private void variantsYouKnow() {
        say("Вот места, которые ты увидел отсюда:");
        say("*****");
        int count = 0;
        for (Dislocation dis: dislocation.getVariants()) {
            count ++;
            say("(" + count + ") " + dis.getName());
        }
        say("*****");
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
        consoleState = 2;
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
        say(moves[0]);
        say(moves[1]);
        say(moves[2]);
        say(moves[3]);
        if (dislocation.isDiscovered()) say(moves[4]);
    }


    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
