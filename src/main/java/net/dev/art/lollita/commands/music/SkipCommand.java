package net.dev.art.lollita.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.music.GuildMusicManager;
import net.dev.art.lollita.music.PlayerManager;
import net.dev.art.lollita.music.TrackScheduler;
import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SkipCommand extends Command {

    public SkipCommand() {
        super("skip", "skip the current music");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        TrackScheduler scheduler = musicManager.scheduler;
        AudioPlayer player = musicManager.player;
        EmbedManager embed = new EmbedManager();

        if (player.getPlayingTrack() == null) {
            embed.infoEmbed("Nenhuma musica sendo tocada").deleteSend(event.getChannel(), 5);
            return false;
        }

        AudioTrackInfo atual = player.getPlayingTrack().getInfo();

        scheduler.nextTrack();

        AudioTrackInfo next = player.getPlayingTrack().getInfo();

        embed.successEmbed("pulando a musica atual").addOutlineField("De", atual.title).addOutlineField("Para", next.title).send(event.getChannel());
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
