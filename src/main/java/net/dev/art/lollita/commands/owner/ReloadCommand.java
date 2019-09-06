package net.dev.art.lollita.commands.owner;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.CommandManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("reload", "reload the bot");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        Lollita.reload();
        bot.reload();
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
