package me.xflyiwnl.testcenter.entity;

public class User {

    private Long id;
    private int from;
    private int to;

    public User() {
    }

    public User(Long id, int from, int to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
