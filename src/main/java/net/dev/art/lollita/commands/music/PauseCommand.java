package net.dev.art.lollita.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.music.GuildMusicManager;
import net.dev.art.lollita.music.PlayerManager;
import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PauseCommand extends Command {

    public PauseCommand() {
        super("pause", "pause the current music");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;
        EmbedManager embed = new EmbedManager();

        if (player.getPlayingTrack() == null) {
            embed.errorEmbed("Nenhuma musica tocando").send(event.getChannel());
            return false;
        }

        if (player.isPaused()){
            player.setPaused(false);
            embed.messageEmbed("A musica foi despausada").send(event.getChannel());
        } else {
            player.setPaused(true);
            embed.messageEmbed("A musica foi pausada").send(event.getChannel());
        }
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
