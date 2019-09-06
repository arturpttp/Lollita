package net.dev.art.lollita.commands.music;

import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.music.GuildMusicManager;
import net.dev.art.lollita.music.PlayerManager;
import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class StopCommand extends Command{

    public StopCommand() {
        super("stop", "stop the current music and queue");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        EmbedManager embed = new EmbedManager();
        if (musicManager.player.getPlayingTrack() == null & musicManager.scheduler.getQueue().size() <= 0){
            embed.errorEmbed("Nenhuma musica tocando e fila vazia").deleteSend(channel, 5);
            return false;
        }

        musicManager.scheduler.getQueue().clear();
        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);

        embed.successEmbed("Parando a musica e limpando a fila").send(event.getChannel());
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
