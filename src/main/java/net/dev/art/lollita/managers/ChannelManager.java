package net.dev.art.lollita.managers;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class ChannelManager {

    private TextChannel channel;
    private Guild guild;

    public ChannelManager(TextChannel channel) {
        this.channel = channel;
        this.guild = channel.getGuild();
    }

    public TextChannel getChannel() {
        return channel;
    }

    public ChannelManager setChannel(TextChannel channel) {
        this.channel = channel;
        return this;
    }

    public Guild getGuild() {
        return guild;
    }

    public ChannelManager setGuild(Guild guild) {
        this.guild = guild;
        return this;
    }
}
