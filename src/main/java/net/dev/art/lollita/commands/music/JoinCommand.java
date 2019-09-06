package net.dev.art.lollita.commands.music;

import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.commands.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends Command {

    public JoinCommand() {
        super("join", "make bot join in your voice channel");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        EmbedManager embed = new EmbedManager();

        if (audioManager.isConnected()) {
            embed.errorEmbed("Já estou conectado em um canaç de voz").send(event.getChannel());
            return false;
        }

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            embed.errorEmbed("Entre em um canal de voz antes").send(event.getChannel());
            return false;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            embed.errorEmbed("Estou sem a permissão de entrar em " + voiceChannel.getName()).send(event.getChannel());
            return false;
        }

        audioManager.openAudioConnection(voiceChannel);
        embed.successEmbed("Entrado no seu canal de voz").send(event.getChannel());
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
