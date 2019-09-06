package net.dev.art.lollita.commands;

import net.dev.art.lollita.managers.API;
import net.dev.art.lollita.objects.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class PingCommands extends Command {
    public PingCommands() {
        super("ping", "get bot ping");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        long time = API.getNow();
        MessageEmbed embed = new EmbedBuilder()
                .setTitle(":ping_pong: Pong!")
                .setColor(getColor(System.currentTimeMillis() - time))
                .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl())
                .setDescription("__**Seu ping: "+(API.getNow() - time)+"ms**__ \n __***Ping do bot: "+event.getJDA().getRestPing().complete()+"ms***__")
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build();
        event.getChannel().sendMessage(embed).queue(message -> {
            message.addReaction("✅").queue();
            message.addReaction("❌").queue();
        });
        return true;
    }

    private Color getColor(long ms){
        if (ms <= 50)
            return Color.CYAN;
        else if (ms >= 50 && ms <= 100)
            return Color.GREEN;
        else if (ms >= 101 && ms <= 175)
            return Color.YELLOW;
        else if (ms >= 176 && ms <= 250)
            return Color.ORANGE;
        else
            return Color.RED;
    }
}
