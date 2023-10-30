package com.example.zad3;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID id;
    private String name;
    private Date date;
    private boolean done;

    public Task(){
        id = UUID.randomUUID();
        date = new Date();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDone(boolean done){
        this.done = done;
    }

    public boolean isDone(){
        return this.done;
    }

    public Date getDate(){
        return this.date;
    }
    public UUID getId() { return this.id; }

    public String getName() {
        return this.name;
    }
}
