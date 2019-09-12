package net.dev.art.lollita;

import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.config.Config;
import net.dev.art.lollita.languages.Language;
import net.dev.art.lollita.managers.CommandManager;
import net.dev.art.lollita.managers.EventsManager;
import net.dev.art.lollita.objects.Bot;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Lollita {

    public static String name, prefix, owner, token, storage_path;
    private static Config config;
    private static Bot lollita;

    public static void config() {
        config = new Config("assets/config.json");
        name = config.getString("name");
        prefix = config.getString("prefix");
        owner = config.getString("owner");
        token = config.getString("token");
        storage_path = config.getString("storage_path");
        config.save();
    }

    public static void loadLanguages() {
        File file = new File(Language.path);
        if (file.exists() && file.listFiles().length > 0) {
            for (File f : file.listFiles()) {
                Language l = new Language(f.getName());
            }
        }
    }

    public static void reload() {
        config();
        CommandManager.unregisterAll();
        EventsManager.unregisterAll();
        try {
            loadClasses();
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        lollita.reload();
    }

    public static void run() {
        config();
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
                if (!eventsManager.registred) {
                    eventsManager.register(lollita);
                }
            } else if (Command.class.isAssignableFrom(clz) && clz != Command.class) {
                Command command = (Command) clz.newInstance();
                if (!command.registred)
                    command.register(lollita);
            }
        }
    }
}
