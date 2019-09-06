package net.dev.art.lollita.objects;

import net.dev.art.lollita.Utils;
import net.dev.art.lollita.managers.UserManager;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

import static net.dv8tion.jda.api.entities.Activity.playing;

public class Bot {

    public List<ListenerAdapter> listeners = new ArrayList<>();
    private String token;
    private JDA jda;
    private JDABuilder builder;
    private UserManager userManager;

    public Bot(String token) throws LoginException {
        this.token = token;
        this.load();
    }

    private void load() throws LoginException {
        this.builder = new JDABuilder(AccountType.BOT)
                .setActivity(playing("I'm prevented"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setToken(this.token)
                .setAutoReconnect(true);
        this.jda = this.builder.build();
        this.userManager = new UserManager(this);
    }

    public void addListener(ListenerAdapter instance) {
        jda.addEventListener(instance);
        listeners.add(instance);
    }

    public void shutdown() {
        Utils.print("Desligando...");
        getUserManager().saveAll();
        getUserManager().loadAll();
        jda.shutdown();
        System.exit(-1);
    }

    public void reload() {
        getUserManager().saveAll();
        getUserManager().loadAll();
    }

    public String getToken() {
        return token;
    }

    public Bot setToken(String token) {
        this.token = token;
        return this;
    }

    public JDA getJda() {
        return jda;
    }

    public Bot setJda(JDA jda) {
        this.jda = jda;
        return this;
    }

    public JDABuilder getBuilder() {
        return builder;
    }

    public Bot setBuilder(JDABuilder builder) {
        this.builder = builder;
        return this;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public Bot setUserManager(UserManager userManager) {
        this.userManager = userManager;
        return this;
    }

}
