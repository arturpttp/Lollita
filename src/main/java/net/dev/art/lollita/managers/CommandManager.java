package net.dev.art.lollita.managers;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Settings;
import net.dev.art.lollita.Utils;
import net.dev.art.lollita.objects.Bot;
import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class CommandManager {

    public static final Map<String, Command> COMMANDS = new HashMap<>();
    public static final Map<String, Command> LABELS = new HashMap<>();
    public static final List<Command> all = new ArrayList<>();
    public Bot bot;
    private static CommandManager instance;

    public CommandManager(Bot bot) {
        this.bot = bot;
        instance = this;
    }

    public static void unregisterAll() {
        COMMANDS.values().forEach(Command::unregister);
        COMMANDS.clear();
        all.clear();
    }

    public static List<Command> getAll() {
        return all;
    }

    public static void addCommand(Command command) {
        COMMANDS.put(command.invoke, command);
    }

    public static Command getCommand(String invoke) {
        Command command = null;
        command = LABELS.getOrDefault(invoke, null);
        if (command == null)
            command = COMMANDS.getOrDefault(invoke, null);
        return command;
    }

    public static boolean isCommand(String message) {
        if (!message.startsWith(Lollita.prefix)) return false;
        String msg = message.replace(Lollita.prefix, "");
        msg = msg.contains(" ") ? msg.split(" ")[0] : msg;
        return LABELS.containsKey(msg) || COMMANDS.containsKey(msg);
    }

    public static boolean handle(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Lollita.prefix), "").split("\\s+");
        String invoke = message;
        invoke = invoke.contains(" ") ? invoke.split(" ")[0] : invoke;
        invoke = invoke.replace(Lollita.prefix, "");
        if(COMMANDS.containsKey(invoke)) {
            Command command = getCommand(invoke);
            final String[] arguments = event.getMessage().getContentRaw().replaceFirst(
                    "(?i)" + Pattern.quote(Lollita.prefix), "").split("\\s+");
            if (command.permission() == Command.CommandPermission.OWNER && event.getAuthor().getIdLong() != Long.parseLong(Lollita.owner)) {
                event.getChannel().sendMessage(Settings.NON_PERMISSION_MESSAGE).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return false;
            }else if (command.permission() == Command.CommandPermission.PRIVATE && event.getChannel().getType() != ChannelType.PRIVATE){
                event.getChannel().sendMessage(Settings.ONLY_PRIVATE__MESSAGE).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return false;
            }else if (command.permission() == Command.CommandPermission.ADMIN && !event.getMember().getRoles().contains(event.getGuild().getRoleById(Settings.ADMIN_ROLE))) {
                event.getChannel().sendMessage("Apenas para administradores").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return false;
            }else if (command.permission() == Command.CommandPermission.DJ && !event.getMember().getRoles().contains(event.getGuild().getRoleById(Settings.DJ_ROLE))) {
                event.getChannel().sendMessage("Apenas os DJs podem usar isso").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return false;
            }
            if (Settings.DELETE_COMMAND & event.getMessage() != null)
                try {
                    event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
                }catch (Exception e){

                }
            command.bot = instance.bot;
            command.args = arguments;
            command.event = event;
            command.channel = event.getChannel();
            command.guild = event.getGuild();
            command.permission = command.permission();
            command.category = command.category();
            return command.onCommand(event, arguments);
        }
        return false;
    }

}
