package net.dev.art.lollita.objects;

import net.dev.art.lollita.managers.CommandManager;
import net.dev.art.lollita.managers.EventsManager;
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

    public Command(String invoke, String help, String usage) {
        this.invoke = invoke;
        this.help = help;
        this.usage = usage;
    }

    public Command(String invoke, String help) {
        this(invoke, help, invoke);
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
        CommandManager.addCommand(this);
        registred = true;
    }

    public static enum  CommandCategory {

        FUNNY("Funny", Color.MAGENTA), ADMIN("Admin", Color.CYAN), MUSiC("Funny", Color.PINK), MINECRAFT("Funny", Color.GREEN), MANAGEMENT("Funny", Color.ORANGE), UTIL("Funny", Color.BLUE);

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
