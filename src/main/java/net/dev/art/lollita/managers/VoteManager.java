package net.dev.art.lollita.managers;

import net.dev.art.lollita.Lollita;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VoteManager {

    public static final String[] EMOTE = {":one:", ":two:", ":three:", ":four:", ":five:", ":six:", ":seven:", ":eight:", ":nine:", ":keycap_ten:"};

    public class Poll implements Serializable {

        public String creator, heading;
        public List<String> answers;
        private HashMap<String, Integer> votes;

        public Poll(String creator, String heading, List<String> answers) {
            this.creator = creator;
            this.heading = heading;
            this.answers = answers;
            this.votes = new HashMap<>();
        }

        public Member getCreator(Guild guild) {
            return guild.getMember(guild.getJDA().getUserById(creator));
        }

    }

    public EmbedManager parsePoll(Poll poll, Guild guild) {
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



}
