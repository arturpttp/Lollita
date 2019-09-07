package net.dev.art.lollita.objects;

import net.dev.art.lollita.Utils;
import net.dev.art.lollita.config.Config;
import net.dev.art.lollita.managers.API;
import net.dev.art.lollita.managers.UserManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BUser {

    private String name;
    private long id, xp;
    private User user;
    private Level level;
    private int credits;
    public List<Warn> warns = new ArrayList<>();

    public BUser(String name, long id, long xp, User user) {
        this.name = name;
        this.id = id;
        this.xp = xp;
        this.user = user;
        this.level = Level.get(1);
        this.credits = 0;
        UserManager.users.put(id, this);
    }

    public BUser(String name, long id, long xp, User user, Level level, int credits) {
        this.name = name;
        this.id = id;
        this.xp = xp;
        this.user = user;
        this.level = level;
        this.credits = credits;
        UserManager.users.put(id, this);
    }

    public Warn addWarn(BUser author, String reason) {
        Warn w = new Warn(this, author, reason);
        warns.add(w);
        return w;
    }

    public Warn addWarn(User author, String reason) {
        return addWarn(UserManager.get(author.getIdLong()), reason);
    }

    public BUser addXp(long qnt) {
        this.xp += qnt;
        return this;
    }

    public BUser addCredits(int qnt) {
        this.credits += qnt;
        return this;
    }

    public BUser removeCredits(int qnt) {
        this.credits -= qnt;
        return this;
    }

    public boolean upLevel() {
        if (Level.exists(level.getLevel() + 1)) {
            Level l = Level.get(level.getLevel() + 1);
            while (xp >= l.getNeededXp()) {
                if (xp >= l.getNeededXp()) {
                    removeXp(l.getNeededXp());
                    this.level = l;
                    addCredits(this.level.getCredits());
                    return true;
                }
            }
        }
        return false;
    }

    public BUser removeXp(long qnt) {
        this.xp -= qnt;
        return this;
    }

    public String getName() {
        return name;
    }

    public BUser setName(String name) {
        this.name = name;
        return this;
    }

    public long getId() {
        return id;
    }

    public BUser setId(long id) {
        this.id = id;
        return this;
    }

    public long getXp() {
        return xp;
    }

    public BUser setXp(long xp) {
        this.xp = xp;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BUser setUser(User user) {
        this.user = user;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    public BUser setLevel(Level level) {
        this.level = level;
        return this;
    }

    public int getCredits() {
        return credits;
    }

    public BUser setCredits(int credits) {
        this.credits = credits;
        return this;
    }

    public JSONObject toJson(Config config) {
        try {
            config.remove(id + "");
            config.set(id + "", new JSONObject());
        }catch (Exception e) {
            config.set(id + "", new JSONObject());
        }
        /*JSONObject warnsObj = new JSONObject();
        int i = 1;
        for (Warn warn : warns) {
            JSONObject w = new JSONObject();
            w.put("user", warn.getUser().getId());
            w.put("author", warn.getAuthor().getId());
            w.put("reason", warn.getReason());
            warnsObj.put(i+"", w);
            i++;
        }*/
        JSONArray arr = new JSONArray();
        warns.forEach(warn -> arr.put(warn.toJson()));
        JSONObject obj = config.getSection(id + "")
                .put("name", name)
                .put("xp", xp)
                .put("level", level.getLevel())
                .put("credits", credits)
                .put("warns", arr);
        return obj;
    }

    public static BUser fromJson(JSONObject obj, Bot bot, String id) {
        String name = obj.getString("name");
        long xp = obj.getLong("xp");
        Level level = Level.get(obj.getInt("level"));
        int credits = obj.getInt("credits");
        User user = null;
        for (Guild guild : bot.getJda().getGuilds()) {
            if ((user = guild.getMemberById(id).getUser()) == null) {
                break;
            }
        }
        BUser bUser =  new BUser(name, Long.parseLong(id), xp, user, level, credits);
        JSONArray warnsArray = obj.getJSONArray("warns");
        if (warnsArray.length() > 0)
            for (int i = 0; i < warnsArray.length(); i++) {
                JSONObject j = warnsArray.getJSONObject(i);
                Warn w = bUser.addWarn(UserManager.get(j.getLong("author")), j.getString("reason"));
            }
        return bUser;
    }

}
