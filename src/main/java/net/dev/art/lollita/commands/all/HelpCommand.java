package net.dev.art.lollita.commands.all;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Utils;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.CommandManager;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.EventsManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "help of the " + Lollita.name);
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        EmbedManager embed = new EmbedManager().setAuthor(event.getAuthor()).timestamp().setDescription("**__Prefix:__ "+Lollita.prefix+"**").setFooter(event.getJDA().getSelfUser());
        List<Command> list = getAll();
        int max = 50;
        embed.setColor(event.getJDA().getRoles().get(0).getColor());
        for (CommandCategory c : CommandCategory.values()) {
            String commands = "";
            int i = 0;
            for (Command command : list) {
                if (command.category == c) {
                    i++;
                    String virgula = ", ";
                    if (list.indexOf(command) == (list.size() - 1)) virgula = "...";
                    commands += Lollita.prefix + command.invoke + virgula;
                }
            }
            if (i > 0) {
                commands = commands.length() > max ? commands.substring(max, commands.length()-1) + "..." : commands;
                embed.addOutlineField(c.getName(), commands);
            }
        }
        embed.send(channel);
        return true;
    }

    private List<Command> getAll() {
        return new ArrayList<>(CommandManager.COMMANDS.values());
    }

}
