package com.example.zad3;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Task {
    private UUID id;
    private String name;
    private Date date;
    private boolean done;
    private Category category;

    public Task(){
        id = UUID.randomUUID();
        date = new Date();
        category = id.getLeastSignificantBits() % 2 == 0 ? Category.HOME : Category.STUDIES;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setCategory(Category category){
        this.category = category;
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
    public Category getCategory() { return this.category; }
    public String getName() {
        return this.name;
    }
}
