package net.dev.art.lollita.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.music.GuildMusicManager;
import net.dev.art.lollita.music.PlayerManager;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.objects.YoutubeAPI;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static net.dev.art.lollita.managers.MessageManager.*;

public class VolumeCommand extends Command {

    public VolumeCommand() {
        super("volume", "change the music volume", "volume <newvolume>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        if (args.length > 1) {
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            musicManager.player.setVolume(Integer.parseInt(args[1]));
            try {
                AudioTrackInfo info = musicManager.player.getPlayingTrack().getInfo();
                EmbedManager manager = new EmbedManager().defaultEmbed();
                manager.setAuthor(event.getAuthor());
                manager.setThumbnail(YoutubeAPI.getThumbnail(info.uri.split("v=")[1]));
                manager.setDescription("O volume foi redefinido para: " + args[1]);
                manager.setFooter(event.getJDA().getSelfUser());
                event.getChannel().sendMessage(manager.build()).queue();
            }catch (NullPointerException e){
                sendEmbed(error("Algum erro ocorreu", "Nenhuma musica está tocando"), event.getChannel());
                return false;
            }
        }else{
            getHelpEmbed().send(channel);
            return false;
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
