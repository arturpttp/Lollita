package net.dev.art.lollita.managers;

import net.dev.art.lollita.objects.Bot;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class EventsManager extends ListenerAdapter {

    public static final List<EventsManager> EVENTS = new ArrayList<>();
    public boolean registred = false;

    public Bot bot;

    public void register(Bot bot) {
        this.bot = bot;
        EVENTS.add(this);
        bot.getJda().addEventListener(this);
        registred = true;
    }

}
