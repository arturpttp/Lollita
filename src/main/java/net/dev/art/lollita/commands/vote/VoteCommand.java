package net.dev.art.lollita.commands.vote;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Utils;
import net.dev.art.lollita.commands.Command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class VoteCommand extends Command {

    public VoteCommand() {
        super("vote", "make o votation", "vote <v|create|stop|status> <number>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        if (args.length <= 1) {
            getHelpEmbed().send(channel);
            return false;
        }
        String sc = args[1];
        switch(sc) {
            case "v":
                break;
            case "create":
                break;
            case "stop":
                break;
            case "status":
                break;
        }
        return true;
    }
}
