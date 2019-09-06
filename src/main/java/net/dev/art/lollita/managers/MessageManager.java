package net.dev.art.lollita.managers;

import net.dev.art.lollita.objects.BUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class MessageManager {

    public static String getMenssage(String[] args, int start) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            sb.append(args[i] + " ");
        }
        return sb.toString();
    }

    public static MessageEmbed error(String title, String description) {
        return new EmbedBuilder().setColor(Color.RED).setTitle("Error!").addField(title, description, true).build();
    }

    public static MessageEmbed userInfo(BUser user) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.BLUE);
        builder.addField("Nome", user.getName(), false);
        builder.addField("Ex", user.getXp() + "", false);
        builder.addField("Nivel", user.getLevel().getLevel() + "", false);
        builder.addField("Creditos", user.getCredits() + "", false);
        return builder.build();
    }

    public static void send(String message, TextChannel channel) {
        channel.sendMessage(message).queue();
    }

    public static void sendEmbed(MessageEmbed embed, TextChannel channel) {
        channel.sendMessage(embed).queue();
    }

}
