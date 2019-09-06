package net.dev.art.lollita.objects;

import java.util.HashMap;

public class Level {

    private static HashMap<Integer, Level> levels = new HashMap<>();
    private int neededXp;
    private int level;
    private int credits;

    public Level(int level, int neededXp, int credits) {
        this.neededXp = neededXp;
        this.level = level;
        this.credits = credits;
        Level.insert(this);
    }

    public static Level get(int level) {
        return levels.get(level);
    }

    public static Level insert(Level level) {
        levels.put(level.getLevel(), level);
        return get(level.getLevel());
    }

    public static Level insert(int xp, int neededXp, int credits) {
        return insert(new Level(xp, neededXp, credits));
    }

    public static boolean exists(int level) {
        return levels.containsKey(level);
    }

    public static void remove(int level) {
        levels.remove(level);
    }

    public static void setup() {
        new Level(1, 0, 0);
        new Level(2, 100, 1);
        new Level(3, 200, 2);
        new Level(4, 400, 4);
        new Level(5, 700, 7);
        new Level(6, 1000, 10);
        new Level(7, 1500, 0);
        new Level(8, 1100, 1);
        new Level(9, 1200, 2);
        new Level(10, 1400, 4);
        new Level(11, 1700, 7);
        new Level(12, 2000, 10);
    }

    public int getNeededXp() {
        return neededXp;
    }

    public Level setNeededXp(int neededXp) {
        this.neededXp = neededXp;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public Level setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getCredits() {
        return credits;
    }

    public Level setCredits(int credits) {
        this.credits = credits;
        return this;
    }

}
