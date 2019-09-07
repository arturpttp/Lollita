package net.dev.art.lollita.commands.admin;

import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.MessageManager;
import net.dev.art.lollita.objects.BUser;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UserinfoCommand extends Command {

    public UserinfoCommand() {
        super("userinfo", "get user infos", "userinfo <user>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        if (args.length <= 1) {
            getHelpEmbed().send(channel);
            return false;
        } else {
            try {
                BUser user = bot.getUserManager().get(event.getMessage().getMentionedUsers().get(0).getIdLong());
                MessageManager.userInfo(user).send(channel);
            } catch (IndexOutOfBoundsException e) {
                new EmbedManager().errorEmbed("Mencione um usuario valido").send(channel);
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
