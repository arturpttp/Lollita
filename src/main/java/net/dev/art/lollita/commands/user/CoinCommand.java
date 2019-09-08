package net.dev.art.lollita.commands.user;

import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.managers.EmbedManager;
import net.dev.art.lollita.objects.BUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Map;

public class CoinCommand extends Command {

    public CoinCommand() {
        super("coin", "manage a user coins", "coin <member> <set|add|remove> <qnt>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        EmbedManager embed = new EmbedManager();
        if (args.length <= 3) {
            getHelpEmbed().send(channel);
            return false;
        }else{
            User user;
            try {
                user = event.getMessage().getMentionedUsers().get(0);
            }catch (IndexOutOfBoundsException e){
                embed.errorEmbed("Mencione um membro valido").deleteSend(event.getChannel(), 5);
                return false;
            }
            String sc = args[2];
            if (args[3].equalsIgnoreCase("nan")) {
                embed.errorEmbed("Digite um numero valido").deleteSend(event.getChannel(), 5);
                return false;
            }
            int qnt = 0;
            try {
                qnt = Integer.parseInt(args[3]);
            }catch (NumberFormatException e){
                embed.errorEmbed("Digite um numero valido").deleteSend(event.getChannel(), 5);
                return false;
            }
            BUser bUser = bot.getUserManager().get(user.getIdLong());
            switch (sc) {
                case "set":
                    bUser.setCredits(qnt);
                    embed.successEmbed("Você alterou o dinheiro de " + user.getName() + " para " + bUser.getCredits()).deleteSend(event.getChannel(), 5);
                    break;
                case "add":
                    bUser.addCredits(qnt);
                    embed.successEmbed("Você alterou o dinheiro de " + user.getName() + " para " + bUser.getCredits()).deleteSend(event.getChannel(), 5);
                    break;
                case "remove":
                    bUser.removeCredits(qnt);
                    embed.successEmbed("Você alterou o dinheiro de " + user.getName() + " para " + bUser.getCredits()).deleteSend(event.getChannel(), 5);
                    break;
            }
        }
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
