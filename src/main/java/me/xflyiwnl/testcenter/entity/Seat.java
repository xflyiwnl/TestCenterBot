package me.xflyiwnl.testcenter.entity;

public class Seat {

    private University university;

    private String date;
    private int freeSeats;

    public Seat(University university, String date, int freeSeats) {
        this.university = university;
        this.date = date;
        this.freeSeats = freeSeats;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }
}
