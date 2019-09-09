package net.dev.art.lollita.commands.all;

import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.MessageManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class EchoCommand extends Command {

    public EchoCommand() {
        super("echo", "make bot echo what you said", "echo (embed) <text>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        if (args.length <= 1) {
            getHelpEmbed().send(channel);
            return false;
        }
        if (args[1].equalsIgnoreCase("embed") || args[1].equalsIgnoreCase("-e") || args[1].equalsIgnoreCase("--embed")) {
          new EmbedManager().setDescription(MessageManager.getMenssage(args, 2)).send(channel);
        } else {
            event.getChannel().sendMessage(MessageManager.getMenssage(args, 1)).queue();
        }
        return true;
    }
}
