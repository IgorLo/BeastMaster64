package com.igorlo;

import com.igorlo.Elements.Dislocation;
import com.igorlo.Elements.Monster;
import com.igorlo.Elements.Player;
import com.igorlo.Events.Fight;

import java.util.List;
import java.util.Scanner;

public class InteractiveConsole {

    private Player player;
    private Dislocation dislocation;
    private short consoleState = 0;
    private final String[] moves = new String[]{"Отдохнуть", "Исследовать локацию", "Осмотреться", "Путешествовать *№*"};
    private final Scanner pray = new Scanner(System.in);

    public InteractiveConsole(){
        dislocation = Dislocation.generate(0, null);
        dislocation.setStartingPoint();
    }

    public void conversate() {

        startNewGame();

        while (consoleState == 1){

            if (dislocation.isJustArrived()) aboutLocation();
            emptyLine();
            avalableMoves();
            emptyLine();

            String choose = pray.next().toLowerCase();
            emptyLine();
            switch (choose) {

                case "отдохнуть":
                    if (player.isHealed()) alreadyFullHealed();
                    else takeANap();
                    break;

                case "исследовать": case "обыскать": case "искать":
                    lookForSomething();
                    break;

                case "осмотреться":
                    if (!dislocation.isDiscovered()) takeALook();
                    else variantsYouKnow();
                    break;

                case "суицид": case "самоубийство": case "покончить":
                    suicide();
                    break;

                case "путешествовать": case "идти": case "бежать":
                    if (dislocation.isDiscovered()) goTo();
                    else notDiscoveredYet();
                    break;

                default:
                    commandUnknown();
                    break;
            }
        }

        sleep(1200);
        emptyLine();
        emptyLine();

        if (consoleState == 2){
            say("Вы мертвы.");
            sleep(500);
            emptyLine();
            sleep(500);
            say("На этом ваше путешествие закончилось");
            emptyLine();
            statistics();
            emptyLine();
            say("КОНЕЦ ИГРЫ.");
        }


    }

    private void statistics() {
        say("Статистика игрока по имени " + player.getName());
        emptyLine();

        List<String> treassures = player.getTakenTreassures();
        say("Сокровищ справедливо присвоено: " + treassures.size());
        say("Среди которых были:");
        for (String str: treassures) {
            say(str + ",");
        }
        emptyLine();

        List<String> monsters = player.getDefeatedMonsters();
        say("Противников повержено: " + monsters.size());
        say("Среди которых были:");
        for (String str: monsters) {
            say(str + ",");
        }

        whatIsMyMoney();
    }

    private void lookForSomething(){
        interractWithNpc();

        boolean ableToGrabDatTreassure = true;
        if (dislocation.getNpc() instanceof Monster && dislocation.getNpc().isAlive())
            ableToGrabDatTreassure = false;

        if (ableToGrabDatTreassure) lookForTreassure();
    }

    private void lookForTreassure() {
        emptyLine();
        if (dislocation.getTreasure() != null)
            takeTreassure();
        else
            thereAreNoTreassure();
    }

    private boolean interractWithNpc() {

        if (dislocation.getNpc().isDead()){
            say("Вы нашли труп. Его имя было " + dislocation.getNpc().getName() + ".");
            say("Покойся с миром, " + dislocation.getNpc().getName() + ".");
            return true;
        }

        if (dislocation.getNpc() instanceof Monster) {
            fightNpc();
            return true;
        }

        return true;

    }

    private void fightNpc() {
        Fight encounter = new Fight(player, dislocation.getNpc());

        switch (encounter.startFighting()) {
            case 1:
                //Здесь игрок побеждает монстра и получает сокровище
                say("Могучий " + player.getName() + " на изичах победил противника по имени " +
                        dislocation.getNpc().getName() + "!");
                emptyLine();
                player = encounter.getPlayer();
                player.killedMonster(dislocation.getNpc());
                dislocation.defeatNpc();
                break;
            case 2:
                //Здесь игрок смог убежать от монстра, но не победил
                //его и не получил сокровища
                //Монстр при этом возвращается в исхожное состояние,
                //как будто боя и не было.
                player = encounter.getPlayer();
                break;
            case 3:
                //В этом случае игрок смог избежать боя и либо обошёл монстра,
                //либо разрешил конфликт мирным путём
                break;
            case 0:
                //В данном случае игрок умер в сражении с монстром
                //Игра заканчивается
                player.kill();
                consoleState = 2;
        }
    }

    private void thereAreNoTreassure() {
        say("Вы не нашли сокровищ.");
    }

    private void takeTreassure() {
        say("Перед вами старый, грязный сундук. Вы медленно");
        say("его открываете и достаёте содержимое.");
        emptyLine();
        say("Хм...");
        emptyLine();
        say("Что же это?..");
        emptyLine();
        say("Вы оттираете грязь и обнаруживаете перед собой...");
        sleep(500);
        emptyLine();
        sleep(500);
        say("Вау!!!1!");
        emptyLine();
        say("Вы обнаружили: " + dislocation.getTreasure().getName());
        sleep(800);
        say("Ценность: " + dislocation.getTreasure().getMoney());
        emptyLine();
        say("Вы забираете сокровище себе.");
        player.giveTreassure(dislocation.takeTreassure());
        whatIsMyMoney();
    }

    private void whatIsMyMoney() {
        say("В кошельке: " + player.getMoney());
    }

    private void aboutLocation() {
        emptyLine();
        say("Тип местности: " + dislocation.getName());
        emptyLine();
        say("Описание: " + dislocation.getDescription());
        dislocation.notForTheFirstTime();
    }

    private boolean goTo() {
        boolean gone = false;
        boolean isCorrect = true;
        do {
            if (!isCorrect){
                say("Номер локации указан некорректно. Попробуй ещё раз: ");
            }
            isCorrect = true;
            String choose = pray.next();

            for (int i = 0; i < choose.length(); i++){
                if (!java.lang.Character.isDigit(choose.charAt(i))) isCorrect = false;
            }
            int index = -1;
            if (isCorrect) index = Integer.parseInt(choose);
            if (dislocation.numberOfVariants() < index || index < 1) isCorrect = false;
            if (!dislocation.isStartingPoint() && choose.equals("0")) {
                travelTo(dislocation.getCameFrom());
                return true;
            }
            if (isCorrect) {
                travelTo(dislocation.getVariants().get(index - 1));
                if (!player.isTraveled()) player.setTraveled();
                gone = true;
            }
        } while (!gone);

        return true;
    }

    private void travelTo(Dislocation newLocation){
        dislocation = newLocation;
        dislocation.setJustArrived();
        emptyLine();
        say("Путешествуем...");
        for (int i = 1; i <= 10; i++){
            sleep(1400);
            say("Путь пройден: " + i*10 + "%");
        }
        sleep(200);
        emptyLine();
        say("Ты прибыл на новую территорию.");
    };

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
        String name = pray.next();

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
        dislocation.discover(newDangerLvl, dislocation);
        emptyLine();
        say("Да, определённо.");
        sleep(100);
        variantsYouKnow();
    }

    private void variantsYouKnow() {
        say("Вот места, куда ты можешь отправиться:");
        say("*****");
        int count = 0;
        if (!dislocation.isStartingPoint()) {
            say("(0) В предыдущую местность (" + dislocation.getCameFrom().getName() + ")");
            say("-----");
        }
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
        consoleState = 2;
    }

    private void takeANap() {
        say("Ты нашёл относительно безопасное и спокойное");
        say("место, чтобы прилечь и немного восстановить");
        say("свои силы. Твоё здоровье постепенно растёт,");
        say("а негативные эффекты сходят на нет.");
        emptyLine();
        say(String.format("Спи сладко, %s.", player.getName()));

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

    private void avalableMoves(){
        say("Ваши действия:");
        say(moves[0]);
        say(moves[1]);
        say(moves[2]);
        if (dislocation.isDiscovered()) say(moves[3]);
    }



    private void emptyLine(){
        sleep(1000);
        System.out.println();
    }

    private void say(String wordOfGod){
        for (int i = 0; i < wordOfGod.length(); i++){
            System.out.print(wordOfGod.charAt(i));
            sleep(20);
        }
        System.out.println();
        sleep(50);
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
