package net.dev.art.lollita.commands.admin;

import net.dev.art.lollita.Settings;
import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.EmbedManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class AddRoleCommand extends Command {

    public AddRoleCommand() {
        super("addrole", "add role to a member", "addrole <member> <role>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        EmbedManager embed = new EmbedManager();
        if (args.length <= 2) {
            getHelpEmbed().send(channel);
            return false;
        }
        User user;
        Role role;
        try {
            user = event.getMessage().getMentionedUsers().get(0);
        }catch (IndexOutOfBoundsException e){
            embed.errorEmbed("Mencione um membro valido").deleteSend(event.getChannel(), 5);
            return false;
        }
        try {
            role = event.getMessage().getMentionedRoles().size() > 0 ? event.getMessage().getMentionedRoles().get(0) : event.getGuild().getRolesByName(args[2],true).get(0);
        }catch (IndexOutOfBoundsException e){
            embed.errorEmbed("Cargo invalido").deleteSend(event.getChannel(), 5);
            return false;
        }
        Member member = event.getGuild().getMember(user);
        event.getGuild().addRoleToMember(member,role).queue();
        embed.successEmbed("Cargo adicionado com sucesso").addOutlineField("Cargo: @" + role.getName(), "adicionado para: " + user.getName()).setAuthor(event.getAuthor()).setFooter(user).timestamp().setThumbnail(user.getAvatarUrl()).send(event.getGuild().getTextChannelById(Settings.LOG_CHANNEL));
        embed.successEmbed("Adicionada").deleteSend(event.getChannel(), 5);
        return true;
    }

    @Override
    public CommandCategory category() {
        return CommandCategory.MANAGEMENT;
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.ADMIN;
    }
}
