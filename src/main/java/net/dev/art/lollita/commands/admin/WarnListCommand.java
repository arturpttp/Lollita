package net.dev.art.lollita.commands.admin;

import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.UserManager;
import net.dev.art.lollita.objects.BUser;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class WarnListCommand extends Command {
    public WarnListCommand() {
        super("warnslist", "get all warns of a user", "warnslist <user>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        EmbedManager e = new EmbedManager();
        if (args.length <= 1) {
            getHelpEmbed().send(channel);
            return false;
        }else{
            BUser user = null;
            try {
                user = UserManager.get(event.getMessage().getMentionedUsers().get(0).getIdLong());
            } catch (IndexOutOfBoundsException err) {
                e.errorEmbed("Mencione um usuario valido").send(channel);
                return false;
            }
            if (user.warns.size() <= 0){
                e.errorEmbed("Esse usuario não tem nenhuma warn!").send(channel);
                return false;
            }
            e.setColor(Color.RED)
            .setAuthor(event.getAuthor())
            .setFooter(user.getUser())
            .setDescription("⚠ Esse usuario tem: " + user.warns.size() + " warns ⚠")
            .timestamp();
            user.warns.forEach(warn -> {
                e.addOutlineField("**Author: **``" + warn.getAuthor().getUser().getAsTag()+"``", "**Motivo: **__" + warn.getReason() + "__");
            });
        }   e.send(channel);
        return true;
    }

    @Override
    public CommandCategory category() {
        return CommandCategory.MANAGEMENT;
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.ADMIN;
    }
}
