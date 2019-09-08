package net.dev.art.lollita.listeners;

import net.dev.art.lollita.Utils;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.managers.EventsManager;
import net.dev.art.lollita.managers.MessageManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessageListener extends EventsManager {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
    }
}
