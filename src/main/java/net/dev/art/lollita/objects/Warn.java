package net.dev.art.lollita.objects;

public class Warn {

    private BUser user;
    private BUser author;
    private String reason;

    public Warn(BUser user, BUser author, String reason) {
        this.user = user;
        this.author = author;
        this.reason = reason;
        user.warns.add(this);
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
}
