package net.dev.art.lollita.listeners;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Settings;
import net.dev.art.lollita.Utils;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.CommandManager;
import net.dev.art.lollita.managers.EventsManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;

public class CommandListener extends EventsManager {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;
        if (CommandManager.isCommand(event.getMessage().getContentRaw())) {
            boolean success = CommandManager.handle(event);
            if (success) {
                if (Settings.DEBUG)
                    Utils.log("Commando: " + event.getMessage().getContentRaw() + " foi executado com sucesso por `" + event.getAuthor().getAsTag() + "`!");
            }else {
                if (Settings.DEBUG)
                    Utils.log("Ocorreu uma falha ao tentar executar o commando: " + event.getMessage().getContentRaw() + " -> author -> `" + event.getAuthor().getAsTag() + "`!");
            }
        }
    }
}
