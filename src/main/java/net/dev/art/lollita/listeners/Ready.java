package net.dev.art.lollita.listeners;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Utils;
import net.dev.art.lollita.managers.CommandManager;
import net.dev.art.lollita.managers.EventsManager;
import net.dev.art.lollita.objects.Bot;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;

import javax.annotation.Nonnull;

public class Ready extends EventsManager {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        Utils.print("====================================");
        Utils.print("Iniciando em " + event.getGuildTotalCount() + " servidores");
        Utils.print("Com " + event.getJDA().getUsers().size() + " usuarios");
        Utils.print("====================================");
        if (!CommandManager.COMMANDS.isEmpty())
            CommandManager.COMMANDS.entrySet().forEach(e -> Utils.print("Comando " + Lollita.prefix + e.getKey()));
    }

}
