package com.example.zad3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskStorage {
    private static TaskStorage instance = new TaskStorage();
    private final List<Task> tasks = new ArrayList<>();


    public static TaskStorage getInstance(){
        return instance;
    }

    private TaskStorage(){
        for(int i = 0; i < 150; i++){
            Task t = new Task();
            t.setName("Nazwa zadania nr " + Integer.toString(i));
            t.setDone(i % 3 == 0);
            t.setCategory((i+1)%3 == 0? Category.STUDIES : Category.HOME);

            tasks.add(t);
        }
    }
    public Task getTask(UUID taskId){
        return tasks.stream().filter(x -> x.getId().equals(taskId)).findFirst().orElse(null);
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public List<Task> getTasks(){
        return tasks;
    }
}
