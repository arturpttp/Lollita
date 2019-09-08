package net.dev.art.lollita.commands.admin;

import net.dev.art.lollita.Settings;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.ChannelManager;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.GuildManager;
import net.dev.art.lollita.managers.MessageManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class UnbanCommand extends Command {

    public UnbanCommand() {
        super("unban", "unban a banned user", "unban <user>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        EmbedManager e = new EmbedManager();
        GuildManager gManager = new GuildManager(event.getGuild());
        ChannelManager lManager = gManager.getChannel(event.getGuild().getTextChannelById(Settings.LOG_CHANNEL));
        TextChannel lChannel = lManager.getChannel();
        if (args.length <= 1) {
            getHelpEmbed().send(channel);
            return false;
        }
        final User[] users = {null};
        event.getGuild().retrieveBanList().queue((bans) -> {
            List<User> goodUsers = bans.stream().filter((ban) -> isCorrectUser(ban, event.getMessage().getMentionedUsers().get(0).getName()))
                    .map(Guild.Ban::getUser).collect(Collectors.toList());

            if (goodUsers.isEmpty()) {
                event.getChannel().sendMessage("This user is not banned").queue();
                    return;
            }
            final User target = goodUsers.get(0);
        });
        User user = users[0];
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
        e.setAuthor(event.getAuthor()).setFooter("Desmanimento", user).setTitle("Desmanido")
                .setDescription("Usuario: " + user.getAsMention() + "\n" +
                        "Autor: " + event.getAuthor().getAsMention())
                .addOutlineField("Motivo", reason).setThumbnail(user.getAvatarUrl());
        e.send(lChannel);
        try {
            e.setTitle("Expulso de " + event.getGuild().getName());
            e.send(user.openPrivateChannel().complete());
        }catch (Exception err) {
            e.clear().infoEmbed("❌ " + user.getName() + " está com o canal privado desabilitado").send(channel);
        }
        e.clear().successEmbed("Desbanimento efetuado com sucesso vá até " + lChannel.getAsMention() + " para ver!").send(channel);
        return true;
    }

    private boolean isCorrectUser(Guild.Ban ban, String arg) {
        User bannedUser = ban.getUser();

        return bannedUser.getName().equalsIgnoreCase(arg) || bannedUser.getId().equals(arg)
                || String.format("%#s", bannedUser).equalsIgnoreCase(arg);
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
