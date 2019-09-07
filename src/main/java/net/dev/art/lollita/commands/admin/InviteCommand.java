package net.dev.art.lollita.commands.admin;

import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.MessageManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class InviteCommand extends Command {

    public InviteCommand() {
        super("invite", "create or send invites", "invite <create|send> (user)");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        Invite inv = event.getChannel().createInvite().setTemporary(true).setUnique(true).complete();
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("create")) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.LIGHT_GRAY);
                builder.addField("Link do convite", inv.getUrl(), true);
                MessageManager.sendEmbed(builder.build(), event.getChannel());
            } else if (args[1].equalsIgnoreCase("send")) {
                if (args.length >= 3){
                    User user = event.getMessage().getMentionedUsers().get(0);
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.LIGHT_GRAY);
                    builder.addField("Link do convite para o server: "+event.getGuild().getName(), inv.getUrl(), true);
                    user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(builder.build()).queue());
                }else{
                    MessageManager.sendEmbed(MessageManager.error("Argumentos insuficientes", "Mencione um usuario ou digite o id do mesmo"), event.getChannel());
                }
            }
        }else{
            getHelpEmbed().send(channel);
        }
        return true;
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.ADMIN;
    }
}
