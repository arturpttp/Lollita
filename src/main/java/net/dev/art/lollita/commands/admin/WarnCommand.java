package net.dev.art.lollita.commands.admin;

import net.dev.art.lollita.Settings;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.MessageManager;
import net.dev.art.lollita.managers.UserManager;
import net.dev.art.lollita.objects.BUser;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class WarnCommand extends Command {

    public WarnCommand() {
        super("warn", "warn a user", "warn <user> (reason)");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        TextChannel lChannel = event.getGuild().getTextChannelById(Settings.LOG_CHANNEL);
        EmbedManager e = new EmbedManager();
        if (args.length <= 1){
            getHelpEmbed().send(channel);
            return false;
        }else{
            BUser user = null;
            try {
                user = UserManager.get(event.getMessage().getMentionedUsers().get(0).getIdLong());
            } catch (IndexOutOfBoundsException err) {
                new EmbedManager().errorEmbed("Mencione um usuario valido").send(channel);
                return false;
            }
            String reason = "";
            boolean motivated = true;
            if (args.length == 2) {
                motivated = false;
                reason = "Não especificado";
            }else{
                motivated = true;
                reason = MessageManager.getMenssage(args, 2);
        }
        if (event.getMessage().getAttachments().size() > 0) {
            e.setImage(event.getMessage().getAttachments().get(0).getUrl());
            reason += "\n"+event.getMessage().getAttachments().get(0).getUrl();
        }
        e.successEmbed("Punição aplicada com sucesso vá até " + lChannel.getAsMention() + " para ver!").send(channel);
        e.setAuthor(event.getAuthor()).setFooter(user.getUser()).setTitle("Punição")
                .setDescription("Usuario: " + user.getUser().getAsMention() + "\n" +
                        "Autor: " + event.getAuthor().getAsMention()/* + "\n" +
                            "Motivo: " + reason*/)
            .addOutlineField("Motivo", reason).setThumbnail(user.getUser().getAvatarUrl()).send(lChannel);
            user.addWarn(event.getAuthor(), reason);

        }
        return true;
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.ADMIN;
    }

    @Override
    public CommandCategory category() {
        return CommandCategory.MANAGEMENT;
    }
}
