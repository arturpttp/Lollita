package net.dev.art.lollita.managers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class EmbedManager {

    private EmbedBuilder builder;

    public EmbedManager() {
        this.builder = new EmbedBuilder();
        setColor(Color.WHITE);
    }

    public EmbedManager defaultEmbed(Color color) {
        builder.setColor(color);
        return this;
    }

    public EmbedManager messageEmbed(String message) {
        setDescription(message);
        return this;
    }

    public EmbedManager errorEmbed(String error, String fix) {
        defaultEmbed(Color.RED);
        setTitle("Erro:");
        addInlineField(error, fix);
        return this;
    }

    public EmbedManager errorEmbed(String error) {
        defaultEmbed(Color.RED);
        setTitle("Erro:");
        setDescription(error);
        return this;
    }

    public EmbedManager infoEmbed(String info, String fix) {
        defaultEmbed(Color.cyan);
        setTitle("Informação:");
        addInlineField(info, fix);
        return this;
    }

    public EmbedManager infoEmbed(String info) {
        defaultEmbed(Color.cyan);
        setTitle("Informação:");
        setDescription(info);
        return this;
    }

    public EmbedManager successEmbed(String info, String fix) {
        defaultEmbed(Color.GREEN);
        setTitle("Sucesso:");
        addInlineField(info, fix);
        return this;
    }

    public EmbedManager successEmbed(String info) {
        defaultEmbed(Color.GREEN);
        setTitle("Sucesso:");
        setDescription(info);
        return this;
    }

    public EmbedManager defaultEmbed() {
        builder.setColor(Color.WHITE);
        return this;
    }

    public EmbedManager setColor(Color color) {
        builder.setColor(color);
        return this;
    }

    public EmbedManager setImage(String url) {
        builder.setImage(url);
        return this;
    }

    public EmbedManager addInlineField(String title, String description) {
        builder.addField(title, description, true);
        return this;
    }

    public EmbedManager addOutlineField(String title, String description) {
        builder.addField(title, description, false);
        return this;
    }

    public EmbedManager addField(String title, String description, boolean inline) {
        builder.addField(title, description, inline);
        return this;
    }

    public EmbedManager timestamp() {
        builder.setTimestamp(Instant.now());
        return this;
    }

    public EmbedManager setTitle(String title) {
        builder.setTitle(title);
        return this;
    }

    public EmbedManager setDescription(String description) {
        builder.setDescription(description);
        return this;
    }

    public EmbedManager setThumbnail(String url) {
        builder.setThumbnail(url);
        return this;
    }

    public EmbedManager setFooter(User user) {
        builder.setFooter(user.getName(), user.getAvatarUrl());
        return this;
    }

    public EmbedManager setFooter(String text, User user) {
        builder.setFooter(text, user.getAvatarUrl());
        return this;
    }

    public EmbedManager setAuthor(User user) {
        builder.setAuthor(user.getName(), user.getAvatarUrl(), user.getAvatarUrl());
        return this;
    }

    public MessageEmbed build() {
        return this.builder.build();
    }

    public EmbedBuilder getBuilder() {
        return builder;
    }

    public EmbedManager send(TextChannel channel) {
        channel.sendMessage(build()).queue();
        return this;
    }

    public EmbedManager deleteSend(TextChannel channel, long seconds) {
        channel.sendMessage(build()).queue(message -> message.delete().queueAfter(seconds, TimeUnit.SECONDS));
        return this;
    }

}