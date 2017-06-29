package com.igorlo.Elements.NPCs;

import com.igorlo.Elements.NotAPlayer;
import com.igorlo.Utilities;

public class Monster extends NotAPlayer {

    private static final String[] FIRSTNAME_FIRSTHALF = new String[]{"Ветро", "Древо", "Пещеро", "Стале", "Пиво",
                                                                    "Велико", "Старо", "Дубо", "Камне", "Чиче", "Михо",
                                                                    "Евгено", "Данило", "Максимо", "Де", "Само"};

    private static final String[] FIRSTNAME_SECONDHALF = new String[]{"крыл", "руб", "лаз", "вар", "ус",
                                                                    "борец", "верец", "щит", "лом", "ил", "рез"};

    private static final String[] SECOND_NAME = new String[]{"Древний", "Славный", "Обжористый", "Непробиваемый",
                                                            "Щуплый", "Пустоголовый", "Прекрасный", "Дерзкий",
                                                            "Трусливый", "Седой", "Лысый", "Бородач", "Первобытный",
                                                            "Ильин", "Ерёменко", "Косенков", "Чикулаев", "Простаков",
                                                            "Жванецкий", "Навальный", "Вульт"};

    private static final String[] AFTER_NAME = new String[]{"I", "II", "III", "VI", "из рода Ланистеров", "(особенный)",
                                                            "Отчисленный", "(дырявый)", "умоляющий не есть его", "сын Марии",
                                                            "святой"};

    public Monster(String name, int strenght, int agility, int intelligence, int health, int maxHealth, String description) {
        super(name, strenght, agility, intelligence, health, maxHealth, description,
                true,
                false,
                true,
                true,
                false,
                false,
                true);
    }

    public static Monster generate(int dangerLevel) {
        if (dangerLevel == 0) return null;
        final int health = Utilities.random(5, 10)*dangerLevel;
        String name = generateName();
        String description = generateDescription(name);
        return new Monster(name,
                Utilities.random(1, 5) * dangerLevel,
                Utilities.random(1, 5) * dangerLevel,
                Utilities.random(1, 5) * dangerLevel,
                health, health,
                description);
    }

    private static String generateDescription(String name) {
        return "Самый обыкновенный " + name;
    }

    public static String generateName() {
        final String firstName = Utilities.randomElement(FIRSTNAME_FIRSTHALF) + "" + Utilities.randomElement(FIRSTNAME_SECONDHALF);
        final String secondName = (String) Utilities.randomElement(SECOND_NAME);
        final String afterName = (String) Utilities.randomElement(AFTER_NAME);
        if (Utilities.random(0, 100) > 80) return (firstName + " " + secondName + " " + afterName);
        return (firstName + " " + secondName);
    }
}
