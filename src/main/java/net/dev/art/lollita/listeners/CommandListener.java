package net.dev.art.lollita.listeners;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.managers.CommandManager;
import net.dev.art.lollita.managers.EventsManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;

public class CommandListener extends EventsManager {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;
        if (event.getMessage().getContentRaw().startsWith(Lollita.prefix))
            CommandManager.handle(event);
    }
}
