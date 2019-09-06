package net.dev.art.lollita.managers;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Settings;
import net.dev.art.lollita.objects.Bot;
import net.dev.art.lollita.objects.Command;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class CommandManager {

    public static final Map<String, Command> COMMANDS = new HashMap<>();

    public Bot bot;
    private static CommandManager instance;

    public CommandManager(Bot bot) {
        this.bot = bot;
        instance = this;
    }

    public static void addCommand(Command command) {
        COMMANDS.put(command.invoke, command);
    }

    public static Command getCommand(String invoke) {
        return COMMANDS.get(invoke);
    }

    public static void handle(GuildMessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Lollita.prefix), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if(COMMANDS.containsKey(invoke)) {
            Command command = getCommand(invoke);
            final String[] arguments = event.getMessage().getContentRaw().replaceFirst(
                    "(?i)" + Pattern.quote(Lollita.prefix), "").split("\\s+");
            /**final List<String> argsm = Arrays.asList(split).subList(1, split.length);
             String[] args = new String[argsm.size() - 1];
             argsm.subList(1, argsm.size()).toArray(args);*/
            if (command.permission() == Command.CommandPermission.OWNER && event.getAuthor().getIdLong() != Long.parseLong(Lollita.owner)) {
                event.getChannel().sendMessage(Settings.NON_PERMISSION_MESSAGE).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return;
            }else if (command.permission() == Command.CommandPermission.PRIVATE && event.getChannel().getType() != ChannelType.PRIVATE){
                event.getChannel().sendMessage(Settings.ONLY_PRIVATE__MESSAGE).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return;
            }else if (command.permission() == Command.CommandPermission.ADMIN && !event.getMember().getRoles().contains(event.getGuild().getRoleById(Settings.ADMIN_ROLE))) {
                event.getChannel().sendMessage("Apenas para administradores").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return;
            }else if (command.permission() == Command.CommandPermission.DJ && !event.getMember().getRoles().contains(event.getGuild().getRoleById(Settings.DJ_ROLE))) {
                event.getChannel().sendMessage("Apenas os DJs podem usar isso").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return;
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
            command.onCommand(event, arguments);
        }
    }

}
