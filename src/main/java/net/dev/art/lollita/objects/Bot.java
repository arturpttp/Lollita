package net.dev.art.lollita.objects;

import net.dev.art.lollita.Utils;
import net.dev.art.lollita.managers.CommandManager;
import net.dev.art.lollita.managers.UserManager;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

import static net.dv8tion.jda.api.entities.Activity.playing;

public class Bot {

    private String token;
    private JDA jda;
    private JDABuilder builder;
    private UserManager userManager;
    private CommandManager commandManager;

    public Bot(String token) throws LoginException {
        this.token = token;
        this.load();
    }

    private void load() throws LoginException {
        this.builder = new JDABuilder(AccountType.BOT);
        this.builder.setActivity(playing("I'm a loli and my name is carlita"));
        this.builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        this.builder.setToken(this.token);
        this.builder.setAutoReconnect(true);
        this.jda = this.builder.build();
        this.userManager = new UserManager(this);
        this.commandManager = new CommandManager(this);
        Level.setup();
    }

    public void addListener(ListenerAdapter instance) {
        jda.addEventListener(instance);
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

    @NotNull
    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Bot setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
        return this;
    }
}
