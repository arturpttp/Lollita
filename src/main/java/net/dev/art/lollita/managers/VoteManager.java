package net.dev.art.lollita.managers;

import net.dev.art.lollita.Lollita;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VoteManager {

    public static final String[] EMOTE = {":one:", ":two:", ":three:", ":four:", ":five:", ":six:", ":seven:", ":eight:", ":nine:", ":keycap_ten:"};

    private static HashMap<Guild, Poll> polls = new HashMap<>();

    public static class Poll implements Serializable {

        public String creator, heading;
        public List<String> answers;
        private HashMap<String, Integer> votes;

        public Poll(String creator, String heading, List<String> answers) {
            this.creator = creator;
            this.heading = heading;
            this.answers = answers;
            this.votes = new HashMap<>();
        }

        public Poll(Member creator, String heading, List<String> answers) {
            this(creator.getUser().getIdLong() + "", heading, answers);
        }

        public Member getCreator(Guild guild) {
            return guild.getMember(guild.getJDA().getUserById(creator));
        }
    }

    public EmbedManager parse(Poll poll, Guild guild) {
        StringBuilder sb = new StringBuilder();
        final AtomicInteger count = new AtomicInteger();
        poll.answers.forEach(answer -> {
            long votescount = poll.votes.keySet().stream().filter(k -> poll.votes.get(k).equals(count.get() + 1)).count();
            sb.append(EMOTE[count.get()] + "  -  " + answer + "  -  Votos: `" + votescount + "` \n");
            count.addAndGet(1);
        });
        return new EmbedManager().setAuthor(poll.getCreator(guild).getUser())
                .setDescription(":pencil:   " + poll.heading + "\n\n" + sb.toString())
                .setFooter("Digite '" + Lollita.prefix + "vote v <number>' to vote!", poll.getCreator(guild).getUser())
                .setColor(Color.cyan);
    }

    public Poll create(String[] args, GuildMessageReceivedEvent event) {
        EmbedManager embed = new EmbedManager();
        if (polls.containsKey(event.getGuild())) {
            embed.errorEmbed("Já existe uma votação em andamento nesse servidor!").send(event.getChannel());
        }
        String argsSTRG = String.join(" ", new ArrayList<>(Arrays.asList(args).subList(1, args.length)));
        List<String> content = Arrays.asList(argsSTRG.split("\\|"));
        String heading = content.get(0);
        List<String> answers = new ArrayList<>(content.subList(1, content.size()));

        Poll poll = new Poll(event.getMember(), heading, answers);
        polls.put(event.getGuild(), poll);

        parse(poll, event.getGuild()).send(event.getChannel());
        return poll;
    }

}
