package net.dev.art.lollita.managers;

import net.dv8tion.jda.api.entities.Guild;

public class GuildManager {

    private Guild guild;

    public GuildManager(Guild guild) {
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }

    public GuildManager setGuild(Guild guild) {
        this.guild = guild;
        return this;
    }
}
