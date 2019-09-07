package net.dev.art.lollita.commands.admin;

import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.API;
import net.dev.art.lollita.managers.EmbedManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear", "clear messagens in channel", "clear <number>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        if (args.length <= 1) {
            getHelpEmbed().send(channel);
            return false;
        }else{
            int number = API.getInt(args[1]);
            try {
                List<Message> messages = event.getChannel().getHistory().retrievePast(number).complete();
                event.getChannel().purgeMessages(messages);
            }catch (IllegalArgumentException e) {
                new EmbedManager().errorEmbed("Apenas numeros de 1 até 100").send(channel);
                return false;
            }
        }
        return true;
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.ADMIN;
    }
}
