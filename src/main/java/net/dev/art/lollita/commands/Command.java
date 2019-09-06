package net.dev.art.lollita.commands;

import net.dev.art.lollita.managers.CommandManager;
import net.dev.art.lollita.managers.EventsManager;
import net.dev.art.lollita.objects.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public abstract class Command extends EventsManager {

    public String invoke, help, usage;
    public String[] args;
    public CommandCategory category;
    public CommandPermission permission;
    public GuildMessageReceivedEvent event;
    public Guild guild;
    public TextChannel channel;
    public Bot bot;
    public boolean registred = false;
    public Command(String invoke, String help) {
        this(invoke, help, invoke);
    }

    public Command(String invoke, String help, String usage) {
        this.invoke = invoke;
        this.help = help;
        this.usage = usage;
        if (!CommandManager.all.contains(this)) {
            CommandManager.all.add(this);
        }
        CommandManager.addCommand(this);
    }

    public abstract boolean onCommand(GuildMessageReceivedEvent event, String[] args);

    public CommandPermission permission() {
        return CommandPermission.ALL;
    }

    public CommandCategory category() {
        return CommandCategory.UTIL;
    }

    public void register(Bot bot) {
        this.bot = bot;
        registred = true;
    }

    public static enum  CommandCategory {

        FUNNY("Funny", Color.MAGENTA), ADMIN("Admin", Color.CYAN), MUSIC("Music", Color.PINK), MINECRAFT("Minecraft", Color.GREEN), MANAGEMENT("Management", Color.ORANGE), UTIL("Utilities", Color.BLUE);

        private String name;
        private Color color;

        CommandCategory(String name, Color color) {
            this.name = name;
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public String getName() {
            return name;
        }
    }

    public static enum CommandPermission {

        ALL, PRIVATE, ADMIN, DJ,  OWNER;

    }
}
