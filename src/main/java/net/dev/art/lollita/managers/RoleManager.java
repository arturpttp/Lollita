package net.dev.art.lollita.managers;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public class RoleManager {

    public static Role getRole(String name, Guild guild) {
        return guild.getRolesByName(name, true).get(0);
    }

    public static void addRole(User user, Role role, Guild guild) {
        guild.addRoleToMember(guild.getMember(user), role).queue();
    }

    public static void addRole(Member member, Role role, Guild guild) {
        addRole(member.getUser(), role, guild);
    }

    public static void removeRole(User user, Role role, Guild guild) {
        guild.removeRoleFromMember(guild.getMember(user), role).queue();
    }

    public static void removeRole(Member member, Role role, Guild guild) {
        removeRole(member.getUser(), role, guild);
    }

}
