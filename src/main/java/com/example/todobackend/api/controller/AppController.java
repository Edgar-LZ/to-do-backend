package com.example.todobackend.api.controller;

import com.example.todobackend.api.model.ToDo;
import com.example.todobackend.service.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class AppController {

    private App appService;

    @Autowired
    public AppController(App appService) {
        this.appService = appService;
    }

    @GetMapping("/todos")
    public ArrayList<ToDo> getToDos(@RequestParam int page) {
        return appService.getToDos(page);
    }

    @PostMapping("/todos")
    public void addToDo(@RequestParam String text, @RequestParam String dueDate, @RequestParam String priority){
        Date date = null;
        if(! (dueDate.isEmpty() || dueDate==null) ) {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = formatter1.parse(dueDate);
            } catch (ParseException e) {
                date = null;
            }
        }
        appService.addToDo(text, date, priority);
    }

    @PostMapping("/todos/{id}")
    public void addToDo(@PathVariable String id, @RequestParam String text, @RequestParam String dueDate, @RequestParam String priority){
        Date date = null;
        if(! (dueDate.isEmpty() || dueDate==null) ) {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = formatter1.parse(dueDate);
            } catch (ParseException e) {
                date = null;
            }
        }
        id = id.replace("%", " ");
        appService.editToDo(id, text, date, priority);
    }
    @PostMapping("/todos/{id}/done")
    public void setAsDone(@PathVariable String id){
        appService.setAsDone(id);
    }

    @PostMapping("/todos/{id}/undone")
    public void setAsUndone(@PathVariable String id){
        appService.setAsUndone(id);
    }

}
