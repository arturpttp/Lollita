package net.dev.art.lollita.managers;

import net.dev.art.lollita.Lollita;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VoteManager {

    public static final String[] EMOTE = {":one:", ":two:", ":three:", ":four:", ":five:", ":six:", ":seven:", ":eight:", ":nine:", ":keycap_ten:"};

    public static HashMap<Guild, Poll> polls = new HashMap<>();

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
                .setFooter("Digite '" + Lollita.prefix + "vote v <number>' para votar!", poll.getCreator(guild).getUser())
                .setColor(Color.cyan);
    }

    public Poll create(String[] args, GuildMessageReceivedEvent event) {
        EmbedManager embed = new EmbedManager();
        if (polls.containsKey(event.getGuild())) {
            embed.errorEmbed("Já existe uma votação em andamento nesse servidor!").send(event.getChannel());
        }
        String argsSTRG = String.join(" ", new ArrayList<>(Arrays.asList(args).subList(2, args.length)));
        List<String> content = Arrays.asList(argsSTRG.split("\\|"));
        String heading = content.get(0);
        List<String> answers = new ArrayList<>(content.subList(1, content.size()));

        Poll poll = new Poll(event.getMember(), heading, answers);
        polls.put(event.getGuild(), poll);

        parse(poll, event.getGuild()).send(event.getChannel());
        return poll;
    }

    public void vote(String[] args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if (!polls.containsKey(event.getGuild())) {
            new EmbedManager().errorEmbed("Não há nenhuma votação ocorrendo para ser votada!").send(channel);
            return;
        }

        Poll poll = polls.get(event.getGuild());

        int vote;
        try {
            vote = Integer.parseInt(args[2]);
            if (vote > poll.answers.size())
                throw new Exception();
        } catch (Exception e) {
            new EmbedManager().errorEmbed("Digite um número valido!").send(channel);
            return;
        }

        if (poll.votes.containsKey(event.getAuthor().getId())) {
            new EmbedManager().errorEmbed("Desculpe, você so pode votar **UMA VEZ**!").send(channel);
            return;
        }

        poll.votes.put(event.getAuthor().getId(), vote);
        polls.replace(event.getGuild(), poll);
        event.getMessage().delete().queue();

    }

    public void stats(GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();

        if (!polls.containsKey(event.getGuild())) {
            new EmbedManager().errorEmbed("Não há nenhuma votação ocorrendo").send(channel);
            return;
        }
        channel.sendMessage(parse(polls.get(event.getGuild()), event.getGuild()).build()).queue();

    }

    public void close(GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if (!polls.containsKey(event.getGuild())) {
            new EmbedManager().errorEmbed("Não há nenhuma votação ocorrendo").send(channel);
            return;
        }

        Guild g = event.getGuild();
        Poll poll = polls.get(g);

        if (!poll.getCreator(g).equals(event.getMember())) {
            new EmbedManager().errorEmbed("Apenas o criador da votação (" + poll.getCreator(g).getAsMention() + ") pode fechar ela!").send(channel);
            return;
        }

        polls.remove(g);
        channel.sendMessage(parse(poll, g).build()).queue();
        new EmbedManager().infoEmbed("Votação encerrada por " + event.getAuthor().getAsMention() + ".").setColor(new Color(0xFF7000)).send(channel);
    }

    public static void savePoll(Guild guild) throws IOException {
        if (!polls.containsKey(guild))
            return;
        String saveFile = "assets/" + guild.getId() + "/vote.dat";
        Poll poll = polls.get(guild);
        FileOutputStream fos = new FileOutputStream(saveFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(poll);
        oos.close();
    }

    public static Poll getPoll(Guild guild) throws IOException, ClassNotFoundException {
        if (polls.containsKey(guild))
            return null;
        String saveFile = "assets/" + guild.getId() + "/vote.dat";
        FileInputStream fis = new FileInputStream(saveFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Poll out = (Poll) ois.readObject();
        ois.close();
        return out;
    }

    public static void loadPolls(JDA jda) {

        jda.getGuilds().forEach(g -> {
            File f = new File("assets/" + g.getId() + "/vote.dat");
            if (f.exists())
                try {
                    polls.put(g, getPoll(g));
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
        });

    }


}
