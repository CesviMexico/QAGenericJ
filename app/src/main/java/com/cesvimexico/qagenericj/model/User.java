package com.cesvimexico.qagenericj.model;

public class User {
    private int born;
    private String first;
    private String last;

    public User(int born, String first, String last) {
        this.born = born;
        this.first = first;
        this.last = last;
    }

    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
