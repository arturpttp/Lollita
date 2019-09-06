package net.dev.art.lollita.listeners;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Settings;
import net.dev.art.lollita.managers.EventsManager;
import net.dev.art.lollita.objects.BUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UsersListener extends EventsManager {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        event.getJDA().getUsers().forEach(user -> {
            if (!bot.getUserManager().exists(user.getIdLong())) {
                bot.getUserManager().create(user);
            }
        });
        bot.getUserManager().loadAll();
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        User user = event.getUser();
        if (!bot.getUserManager().exists(user.getIdLong())) {
            bot.getUserManager().create(user);
        }
        event.getGuild().getTextChannelById(Settings.WELCOME_CHANNEL).sendMessage(bot.getUserManager().getJoinEmbed(event.getUser(), event.getGuild())).queue();
    }

    @Override
    public void onGuildMemberLeave(@Nonnull GuildMemberLeaveEvent event) {
        User user = event.getUser();
        if (!bot.getUserManager().exists(user.getIdLong())) {
            bot.getUserManager().delete(user);
        }
        event.getGuild().getTextChannelById(Settings.WELCOME_CHANNEL).sendMessage(bot.getUserManager().getLeaveEmbed(event.getUser(), event.getGuild())).queue();
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        User author = event.getAuthor();
        if (!bot.getUserManager().exists(author.getIdLong())) {
            bot.getUserManager().delete(author);
        }
        BUser user = bot.getUserManager().get(event.getAuthor().getIdLong());
        if (!event.getMessage().getContentRaw().startsWith(Lollita.prefix)) {
            int xp = 5 + new Random().nextInt(15);
            user.addXp(xp);
            if (user.upLevel()) {
                EmbedBuilder builder = new EmbedBuilder()
                        .setThumbnail(user.getUser().getAvatarUrl())
                        .setTitle("Parabens! você upou de nivel!")
                        .setAuthor(user.getUser().getName(), user.getUser().getAvatarUrl(), user.getUser().getAvatarUrl())
                        .addField("Novo nivel", "você atingiu o nivel " + user.getLevel().getLevel(), false);
                event.getChannel().sendMessage(builder.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS
                ));
            }
        }
    }

}
