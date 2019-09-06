package net.dev.art.lollita.commands.music;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import net.dev.art.lollita.Settings;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.MessageManager;
import net.dev.art.lollita.music.PlayerManager;
import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayCommand extends Command {

    private final YouTube youTube;

    public PlayCommand() {
        super("play", "play any music or video", "play <url|search>");
        YouTube temp = null;

        try {
            temp = new YouTube.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JacksonFactory.getDefaultInstance(),
            null
            )
            .setApplicationName("Prevented bot")
            .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        youTube = temp;
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        EmbedManager embed = new EmbedManager();

        if (!audioManager.isConnected()) {
            embed.infoEmbed("Eu não estou conectado a nenhum canal de voz").send(event.getChannel());
            return false;
        }

        if (args.length == 1){
            embed.errorEmbed("Digite uma pesquisa ou url").send(channel);
            return false;
        }
        TextChannel channel = event.getChannel();
        String input = MessageManager.getMenssage(args, 1);
        if (!isUrl(input)) {
            event.getChannel().sendMessage(embed.infoEmbed("Procurando por: " + input).build()).queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            String ytSearched = searchYoutube(input);

            if (ytSearched == null) {
                event.getChannel().sendMessage(embed.errorEmbed("O youtube não retornou nenhum resultado").build()).queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                return false;
            }
            input = ytSearched;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(event.getChannel(), input);
        return true;
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Nullable
    private String searchYoutube(String input) {
        try {
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey(Settings.YOUTUBE_API_KEY)
                    .execute()
                    .getItems();

            if (!results.isEmpty()) {
                String videoId = results.get(0).getId().getVideoId();
                return "https://www.youtube.com/watch?v=" + videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
