package net.dev.art.lollita;

import net.dev.art.lollita.config.Config;
import net.dev.art.lollita.objects.Bot;

public class Lollita {

    public static Bot bot;
    public static String name, prefix, author, token;
    private static Config config;

    private static void run() {
        config = new Config("assets/config.json");
        name = config.getString("name");
        prefix = config.getString("prefix");
        author = config.getString("author");
        token = config.getString("token");
        config.save();
    }

    public static void main(String[] args) {

    }

}
