package com.igorlo;

import com.igorlo.Elements.Dislocation;
import com.igorlo.Elements.NotAPlayer;
import com.igorlo.Elements.Player;

import java.util.List;
import java.util.Scanner;

public class InteractiveConsole {

    private Player player;
    private Dislocation dislocation;
    private short consoleState = 0;
    private final String[] moves = new String[]{"Отдохнуть", "Исследовать локацию", "Осмотреться", "Путешествовать *№*",
                                                "Персонаж", "Статистика", "Выход", "Взаимодействовать", "Локация"};
    private final Scanner pray = new Scanner(System.in);

    public InteractiveConsole(){
        /*
        for (int i = 0; i < 100; i++){
            say(Monster.generateName());
        }
        */

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

                case "исследовать":
                case "обыскать":
                case "искать":
                    lookForSomething();
                    break;

                case "осмотреться":
                    if (!dislocation.isDiscovered()) takeALook();
                    else variantsYouKnow();
                    break;

                case "суицид":
                case "самоубийство":
                case "покончить":
                    suicide();
                    break;

                case "путешествовать":
                case "идти":
                case "бежать":
                    if (dislocation.isDiscovered()) goTo();
                    else notDiscoveredYet();
                    break;

                case "взаимодействовать":
                    interractWithNpc();
                    break;

                case "персонаж":
                    aboutPlayer();
                    break;

                case "статистика":
                    statistics();
                    break;

                case "выход":
                    consoleState = 3;
                    break;

                case "локация":
                    aboutLocation();
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

    private void aboutPlayer() {
        say("Имя: " + player.getName());
        say("Ваши характеристики: "
                + "сил " + player.getStrenght() + " \\ "
                + "лов " + player.getAgility() + " \\ "
                + "инт " + player.getIntelligence());
        say("Здоровье: " + player.getHealth() + " \\ " + player.getMaxHealth());
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

        emptyLine();

        whatIsMyMoney();
    }

    private void lookForSomething(){

        if (dislocation.getNpc() == null){
            lookForTreassure();
        }

        if (dislocation.getNpc() != null){
            if (!dislocation.getNpc().isTreassureGuardian() || dislocation.getNpc().isDead())
            lookForTreassure();
        }

        if (!dislocation.isPlayerLookedAround()){
            interractWithNpc();
            dislocation.playerLookedAround();
        }
    }

    private void lookForTreassure() {
        emptyLine();
        if (dislocation.getTreasure() != null)
            takeTreassure();
        else
            thereAreNothing();
    }

    private boolean interractWithNpc() {
        if (dislocation.getNpc() == null) return true;

        NotAPlayer NPC = dislocation.getNpc();

        say("***********************");

        if (NPC.isDead()){
            npcIsDead();
            return true;
        }

        say("Вам встретился " + dislocation.getNpc().getName() + ".");
        NPC.setHidden(false);
        emptyLine();
        NPC.getDescription();

        if (NPC.isAgressive()){
            say("Этот персонаж агрессивен и напал на вас!");
            if (NPC.isTreassureGuardian())
                fightMonster();
            else
                fightNpc();
        }

        say("-------Действия:-------");
        if (NPC.isAlive()){
            if (NPC.hasQuest())
                say("Задание");
            if (NPC.isTradeable())
                say("Торговать");
            if (NPC.canConversate())
                say("Говорить");
            if (NPC.isFightable())
                say("Напасть");
        }
        if (NPC.isDead()){
            //say("Обыскать");
        }
        say("Осмотреть");
        say("Уйти");
        say("***********************");

        while (NPC != null){
            switch (pray.next().toLowerCase()) {
                case "уйти":
                    return true;
                case "торговать":
                    //TODO tradeNpc();
                    break;
                case "говорить":
                    //TODO talkNpc();
                    break;
                case "осмотреть":
                    lookAtNpc();
                    break;
                case "напасть":
                    attackNpc();
                    return true;
                case "обыскать":
                    //TODO lootNpc();
                    break;
                case "задание":
                    //TODO questNpc();
                    break;
                default:
                    commandUnknown_Npc();
            }
        }

        return true;
    }

    private void lookAtNpc() {
        if (dislocation.getNpc().isAlive()){
            say("Вы осматриваете персонажа " + dislocation.getNpc().getName());
            say(dislocation.getNpc().getDescription());
        } else {
            say("Это обыкновенный завонявшийся труп.");
            say("Его имя: " + dislocation.getNpc().getName());
        }
    }

    private void npcIsDead() {
        say("Вы нашли труп. Его имя было " + dislocation.getNpc().getName() + ".");
        say("Покойся с миром, " + dislocation.getNpc().getName() + ".");
    }

    private boolean attackNpc() {
        if (dislocation.getNpc().isDead()){
            say("Этот бедолага и так мёртв.");
            return true;
        }
        say_player("Здарова, тварина!");
        say_Npc("Ааааа! Неси палку!");
        say_player("Тiкай с городу");
        say_Npc("АААА ШО ЭТО ТАКОЕ АА!1!");
        say("*Этот персонаж запомнит это*");
        dislocation.getNpc().setAgressive(true);
        fightNpc();
        return true;
    }

    private void commandUnknown_Npc() {
        if (dislocation.getNpc().isAlive())
            say_Npc("Я не понимаю, что ты хочешь от меня, " + player.getName());
        else
            say("Попробуй что-нибудь другое");
    }

    private void fightMonster() {
        Fight fight = new Fight();

        switch (fight.fight()) {
            case 1:
                //Здесь игрок побеждает монстра и получает сокровище
                say("Могучий " + player.getName() + " на изичах победил противника по имени " +
                        dislocation.getNpc().getName() + "!");
                emptyLine();
                player.killedMonster(dislocation.getNpc());
                dislocation.defeatNpc();
                emptyLine();
                lookForTreassure();
                break;
            case 2:
                //Здесь игрок смог убежать от монстра, но не победил
                //его и не получил сокровища
                //Монстр при этом возвращается в исхожное состояние,
                //как будто боя и не было.
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

    private void fightNpc(){
        Fight fight = new Fight();

        switch (fight.fight()) {
            case 1:
                //Здесь игрок побеждает монстра и получает сокровище
                say("Могучий " + player.getName() + " на изичах победил противника по имени " +
                        dislocation.getNpc().getName() + "!");
                emptyLine();
                player.killedMonster(dislocation.getNpc());
                dislocation.defeatNpc();
                emptyLine();
                break;
            case 2:
                //бежал
                break;
            case 3:
                //мирно закончил или вроде того
                break;
            case 0:
                //В данном случае игрок умер в сражении
                //Игра заканчивается
                player.kill();
                consoleState = 2;
                break;
        }
    }

    private void thereAreNothing() {
        say("Вы ничего не нашли.");
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

    private boolean aboutLocation() {
        emptyLine();
        say("Тип местности: " + dislocation.getName());
        emptyLine();
        say("Описание: " + dislocation.getDescription());
        dislocation.notForTheFirstTime();

        if (dislocation.isPlayerLookedAround()) {
            if (dislocation.getNpc() == null){
                emptyLine();
                say("В локации нет никого, кроме вас.");
                return true;
            } else {
                emptyLine();
                whoIsThere();
                return true;
            }
        }

        if (dislocation.getNpc() != null && !dislocation.getNpc().isHidden()){
            emptyLine();
            whoIsThere();
            return true;
        }

        return true;
    }

    private void whoIsThere() {
        say("В локации находится " + dislocation.getNpc().getName() + ".");
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
            sleep(600);
            say("Путь пройден: " + i*10 + "%");
        }
        sleep(200);
        emptyLine();
        say("Ты прибыл на новую территорию.");
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
        System.out.print("Введите имя вашего героя: ");
        String name = pray.next();

        emptyLine();

        int startStrenght = 10;
        int startAgility = 10;
        int startIntelligence = 1;

        player = Player.createCharacter(name, startStrenght, startAgility, startIntelligence);

        say("Игрок создан.");
        emptyLine();
        aboutPlayer();

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
        say("****Ваши действия:****");
        say(moves[0]);
        if (!dislocation.isPlayerLookedAround())
            say(moves[1]);
        say(moves[2]);
        if (dislocation.isDiscovered())
            say(moves[3]);
        if (dislocation.getNpc() != null && !dislocation.getNpc().isHidden())
            say(moves[7] + " (" + dislocation.getNpc().getName() + ")");
        say("------информация------");
        say(moves[4]);
        say(moves[5]);
        say(moves[8]);
        say(moves[6]);

        say("**********************");
    }



    private void emptyLine(){
        sleep(450);
        System.out.println();
    }

    private void say(String wordOfGod){
        for (int i = 0; i < wordOfGod.length(); i++){
            System.out.print(wordOfGod.charAt(i));
            sleep(12);
        }
        System.out.println();
        sleep(50);
    }

    private void say_Npc(String wordOfNpc){
        say(dislocation.getNpc().getName() + ": " + wordOfNpc);
    }

    private void say_player(String wordOfPlayer){
        say(player.getName() + ": " + wordOfPlayer);
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public class Fight {

        public int fight(){
            //if player wins
            return 1;
            //if player runs return 2;
            //if player avoided the fight return 3;
            //if monster wins and player is dead return 0;
            //TODO
        }

    }
}
