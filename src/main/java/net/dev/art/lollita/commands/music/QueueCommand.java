package net.dev.art.lollita.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.music.GuildMusicManager;
import net.dev.art.lollita.music.PlayerManager;
import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends Command {

    public QueueCommand() {
        super("queue", "get the bot queue");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
        EmbedManager embed = new EmbedManager();

        if (queue.isEmpty()) {
            embed.errorEmbed("A fila está vazia").send(event.getChannel());
            return false;
        }

        int trackCount = Math.min(queue.size(), 20);
        List<AudioTrack> tracks = new ArrayList<>(queue);
        EmbedBuilder builder = new EmbedBuilder().setColor(Color.BLUE)
                .setTitle("Fila atual (Total: " + queue.size() + ")");

        for (int i = 0; i < trackCount; i++) {
            AudioTrack track = tracks.get(i);
            AudioTrackInfo info = track.getInfo();
            builder.appendDescription(String.format(
                    "%s - %s\n",
                    info.title,
                    info.author
            ));
        }
        channel.sendMessage(builder.build()).queue();
        return true;
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.DJ;
    }

    @Override
    public CommandCategory category() {
        return CommandCategory.MUSIC;
    }
}
