package net.dev.art.lollita.commands;

import net.dev.art.lollita.objects.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class PingCommands extends Command {
    public PingCommands(String invoke, String help) {
        super("ping", "get bot ping");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        channel.sendMessage("aaa").queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
        return false;
    }
}
