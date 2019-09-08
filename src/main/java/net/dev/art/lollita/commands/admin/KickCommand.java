package net.dev.art.lollita.commands.admin;

import net.dev.art.lollita.Settings;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.MessageManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class KickCommand extends Command {
    public KickCommand() {
        super("kick", "kick a user", "kick <user>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        TextChannel lChannel = event.getGuild().getTextChannelById(Settings.LOG_CHANNEL);
        if (args.length <= 1) {
            getHelpEmbed().send(channel);
            return false;
        }
        EmbedManager e = new EmbedManager();
        User user = event.getMessage().getMentionedUsers().get(0);
        String reason = "";
        if (args.length == 2) {
            reason = "Não especificado";
        }else{
            reason = MessageManager.getMenssage(args, 2);
        }
        if (event.getMessage().getAttachments().size() > 0) {
            e.setImage(event.getMessage().getAttachments().get(0).getUrl());
            String start = reason.length() == 0 ? "" : "\n";
            reason += start+event.getMessage().getAttachments().get(0).getUrl();
        }
        e.setAuthor(event.getAuthor()).setFooter("Expulção", user).setTitle("Expulso")
                .setDescription("Usuario: " + user.getAsMention() + "\n" +
                        "Autor: " + event.getAuthor().getAsMention())
                .addOutlineField("Motivo", reason).setThumbnail(user.getAvatarUrl());
        try{
            event.getGuild().kick(event.getGuild().getMember(user), reason).queue();
        }catch (HierarchyException ex) {
            e.clear().errorEmbed("Não posso expulsar alguem com cargo maior ou igual ao meu!").send(channel);
            return false;
        }
        e.send(lChannel);
        try {
            e.setTitle("Banido de " + event.getGuild().getName());
            e.send(user.openPrivateChannel().complete());
        }catch (Exception err) {
            e.clear().infoEmbed("❌ " + user.getName() + " está com o canal privado desabilitado").send(channel);
        }
        e.clear().successEmbed("Punição aplicada com sucesso vá até " + lChannel.getAsMention() + " para ver!").send(channel);
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
