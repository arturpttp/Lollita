package net.dev.art.lollita.commands.vote;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Utils;
import net.dev.art.lollita.commands.Command;

import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.StringManager;
import net.dev.art.lollita.managers.VoteManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.IOException;

public class VoteCommand extends Command {

    public VoteCommand() {
        super("vote", "make o votation", "vote <v|create|stop|status> <number>");
    }

    public VoteManager vote = new VoteManager();

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        if (args.length <= 1) {
            getHelpEmbed().send(channel);
            return false;
        }
        String sc = args[1];
        if (StringManager.contains(args, "\\|")) {
            new EmbedManager().errorEmbed("User `|` para separar cada item na votação!").send(channel);
            return false;
        }
        switch(sc) {
            case "vote":
            case "v":
                vote.vote(args, event);
                break;
            case "create":
                vote.create(args, event);
                break;
            case "close":
            case "stop":
                vote.close(event);
                break;
            case "status":
            case "stats":
                vote.stats(event);
                break;
        }


        VoteManager.polls.forEach((g, poll) -> {

            File path = new File("assets/" + g.getId() + "/");
            if (!path.exists())
                path.mkdirs();

            try {
                VoteManager.savePoll(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return true;
    }
}
