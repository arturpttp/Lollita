package net.dev.art.lollita.commands.owner;

import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ShutdownCommand extends Command {

    public ShutdownCommand() {
        super("shutdown", "shutdown the bot");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        event.getMessage().delete().queue();
        bot.shutdown();
        return true;
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.OWNER;
    }

    @Override
    public CommandCategory category() {
        return CommandCategory.MANAGEMENT;
    }
}
