package net.dev.art.lollita.managers;

import net.dev.art.lollita.Lollita;
import net.dev.art.lollita.Settings;
import net.dev.art.lollita.config.Config;
import net.dev.art.lollita.objects.BUser;
import net.dev.art.lollita.objects.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class UserManager {

    public static HashMap<Long, BUser> users = new HashMap<Long, BUser>();
    public final String[] join_messages = {"Quem é vivo sempre aparece né [member]!", "Bem vindo [member]", "Wow um novo membro chamado [member]"};
    public final String[] leave_messages = {"Oh... O membro [member] foi em bora!", "Tchau [member]"};
    private Bot bot;

    public UserManager(Bot bot) {
        this.bot = bot;
    }

    public static BUser create(User user) {
        BUser bUser = new BUser(user.getName(), user.getIdLong(), 0L, user);
        users.put(user.getIdLong(), bUser);
        return bUser;
}

    public static BUser get(Long id) {
        return users.get(id);
    }

    public static void delete(User user) {
        users.remove(user.getIdLong());
    }

    public static void delete(Long id) {
        users.remove(id);
    }

    public boolean exists(Long id) {
        return get(id) != null && users.containsKey(id);
    }

    public List<BUser> getAll() {
        List<BUser> usersList = new ArrayList<>();
        users.values().forEach(u -> usersList.add(u));
        return usersList;
    }

    public Bot getBot() {
        return bot;
    }

    public UserManager setBot(Bot bot) {
        this.bot = bot;
        return this;
    }

    public MessageEmbed getJoinEmbed(User user, Guild guild) {
        EmbedBuilder builder = new EmbedBuilder()
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.GREEN)
                .setTitle("Entrada")
                .setAuthor(user.getName(), user.getAvatarUrl(), user.getEffectiveAvatarUrl());
        String msg = join_messages[new Random().nextInt(join_messages.length)].replace("[member]", user.getAsMention());
        builder.setDescription(msg);
        builder.setFooter(guild.getName(), bot.getJda().getSelfUser().getAvatarUrl());
        MessageEmbed embed = builder.build();
        return embed;
    }

    public MessageEmbed getLeaveEmbed(User user, Guild guild) {
        EmbedBuilder builder = new EmbedBuilder()
                .setThumbnail(user.getAvatarUrl());
        builder.setColor(Color.RED);
        builder.setTitle("Saida");
        builder.setAuthor(user.getName(), user.getAvatarUrl(), user.getEffectiveAvatarUrl());
        String msg = leave_messages[new Random().nextInt(leave_messages.length)].replace("[member]", user.getAsMention());
        builder.setDescription(msg);
        builder.setFooter(guild.getName(), bot.getJda().getSelfUser().getAvatarUrl());
        MessageEmbed embed = builder.build();
        return embed;
    }

    public void saveAll() {
        Config config = new Config(Lollita.storage_path+"users.json");
        users.values().forEach(user -> {
            user.toJson(config);
        });
        config.save();
    }

    public void loadAll() {
        Config config = new Config(Lollita.storage_path+"users.json");
        List<String> toDelete = new ArrayList<>();
        for (String key : config.getJSONObject().keySet()) {
            JSONObject section = config.getJSON(key);
            boolean delete = BUser.fromJson(section, bot, key);
            if (!delete) {
                toDelete.add(key);
            }
        }
        if (toDelete.size() > 0)
            toDelete.forEach(config::remove);
    }

    public static boolean isAdmin(Member member, Guild guild) {
        return member.hasPermission(Permission.ADMINISTRATOR) || member.getRoles().contains(guild.getRoleById(Settings.ADMIN_ROLE));
    }

    public static boolean isAdmin(User user, Guild guild) {
        Member member = guild.getMember(user);
        return isAdmin(member, guild);
    }

}
