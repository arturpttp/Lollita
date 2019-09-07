package net.dev.art.lollita.objects;

import org.json.JSONObject;

public class Warn {

    private BUser user;
    private BUser author;

    @Override
    public String toString() {
        return "Warn{" +
                "user=" + user +
                ", author=" + author +
                ", reason='" + reason + '\'' +
                '}';
    }

    private String reason;

    public Warn(BUser user, BUser author, String reason) {
        this.user = user;
        this.author = author;
        this.reason = reason;
    }

    public BUser getUser() {
        return user;
    }

    public Warn setUser(BUser user) {
        this.user = user;
        return this;
    }

    public BUser getAuthor() {
        return author;
    }

    public Warn setAuthor(BUser author) {
        this.author = author;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public Warn setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("user", user.getId());
        obj.put("author", author.getId());
        obj.put("reason", reason);
        return obj;
    }

}
