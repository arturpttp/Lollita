package net.dev.art.lollita.commands.music;

import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends Command {

    public LeaveCommand() {
        super("leave", "make bot leave from your channel");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        EmbedManager embed = new EmbedManager();
        if (!audioManager.isConnected()) {
            embed.infoEmbed("Eu não estou conectado a nenhum canal de voz").send(event.getChannel());
            return false;
        }

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(event.getMember())) {
            embed.infoEmbed("Você precisa estar no mesmo canal que eu para usar esse comando").send(event.getChannel());
            return false;
        }

        audioManager.closeAudioConnection();
        embed.successEmbed("Desconectando do seu canal").send(event.getChannel());
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
