package net.dev.art.lollita.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.music.GuildMusicManager;
import net.dev.art.lollita.music.PlayerManager;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.objects.YoutubeAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class PlayingCommand extends Command {

    public PlayingCommand() {
        super("playing", "get playing music");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;
        EmbedManager embed = new EmbedManager();

        if (player.getPlayingTrack() == null) {
            embed.errorEmbed("Nenhuma musica tocando").send(event.getChannel());
            return false;
        }

        AudioTrackInfo info = player.getPlayingTrack().getInfo();

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Musica atual")
                .setImage(YoutubeAPI.getThumbnail(info.uri.split("v=")[1]))
                .setDescription(String.format(
                        "**Tocando** [%s](%s)\n%s %s - %s",
                        info.title,
                        info.uri,
                        player.isPaused() ? "⏸" : "▶",
                        formatTime(player.getPlayingTrack().getPosition()),
                        formatTime(player.getPlayingTrack().getDuration())
                ));

        channel.sendMessage(builder.build()).queue();
        return true;
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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
