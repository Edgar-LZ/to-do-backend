package com.example.todobackend.api.controller;

import com.example.todobackend.api.model.ToDo;
import com.example.todobackend.service.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
