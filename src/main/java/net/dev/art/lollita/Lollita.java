package net.dev.art.lollita;

import net.dev.art.lollita.config.Config;
import net.dev.art.lollita.managers.EventsManager;
import net.dev.art.lollita.objects.Bot;
import net.dev.art.lollita.objects.Command;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.time.Instant;

public class Lollita {

    public static Bot bot;
    public static String name, prefix, owner, token, storage_path;
    private static Config config;
    private static Bot lollita;

    private static void run() {
        config = new Config("assets/config.json");
        name = config.getString("name");
        prefix = config.getString("prefix");
        owner = config.getString("owner");
        token = config.getString("token");
        storage_path = config.getString("storage_path");
        config.save();
        try {
            lollita = new Bot(token);
            loadClasses();
        } catch (LoginException | IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void loadClasses() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (Class clz : Utils.getClasses("net.dev.art.lollita")) {
            if (EventsManager.class.isAssignableFrom(clz) && clz != EventsManager.class && clz != Command.class) {
                EventsManager eventsManager = (EventsManager) clz.newInstance();
                if (!eventsManager.registred)
                    eventsManager.register(lollita);
            }else if (Command.class.isAssignableFrom(clz) && clz != Command.class) {
                Command command = (Command) clz.newInstance();
                if (!command.registred)
                    command.register(lollita);
            }
        }
    }

    public static void main(String[] args) {
        run();
    }

}
