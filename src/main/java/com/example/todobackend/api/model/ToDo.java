package com.example.todobackend.api.model;

import java.util.Date;
import java.util.Random;

public class ToDo {
    final private String id;
    private String text;
    private Date dueDate;
    private boolean done;
    private Date doneDate;
    private String priority;
    final private Date creationDate;

    public ToDo(String text, Date dueDate, String priority) {
        this.text = text;
        this.dueDate = dueDate;
        this.priority = priority;
        this.done = false;

        Random random = new Random();
        this.creationDate = new Date();
        this.id = creationDate.toString() + Math.abs(random.nextInt());
    }

    public void edit(String text, Date dueDate, String priority) {
        this.text = text;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public void  toggleDone() {
        this.done =  !done;
        if(done) {
            this.doneDate = new Date();
        } else {
            this.doneDate = null;
        }
    }
    public String getId() {
        return this.id;
    }
    public String getText() {
        return this.text;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public Date getDoneDate() {
        return doneDate;
    }
    public String getPriority() {
        return priority;
    }
    public Date getCreationDate() {
        return creationDate;
    }

}
