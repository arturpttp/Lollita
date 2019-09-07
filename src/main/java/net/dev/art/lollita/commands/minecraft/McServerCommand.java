package net.dev.art.lollita.commands.minecraft;

import net.dev.art.lollita.commands.Command;
import net.dev.art.lollita.objects.JSONReader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

public class McServerCommand extends Command {

    public McServerCommand() {
        super("mcserver", "get the server infos", "mcserver <ip>");
    }

    @Override
    public boolean onCommand(GuildMessageReceivedEvent event, String[] args) {
        if (args.length <= 1){
            getHelpEmbed().send(channel);
            return false;
        }else{
            String ip = "https://api.mcsrvstat.us/2/" + args[1];
            try {
                JSONObject object = JSONReader.readJsonFromUrl(ip);
                String status = object.getBoolean("online") ? "Online" : "Offline";
                EmbedBuilder builder = new EmbedBuilder()
                        .setColor(Color.CYAN)
                        .setThumbnail("https://api.minetools.eu/favicon/" + args[1])
                        .setDescription("**Ip:** ``" + args[1] + "``\n"
                        + "**Status:** ``" + status + "``");
                if (object.getBoolean("online")){
                    JSONObject players = object.getJSONObject("players");
                    int online = players.getInt("online");
                    int max = players.getInt("max");
                    builder.addField("Online","["+online+"/"+max+"]",false);
                }
                event.getChannel().sendMessage(builder.build()).queue();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CommandCategory category() {
        return CommandCategory.MINECRAFT;
    }
}
