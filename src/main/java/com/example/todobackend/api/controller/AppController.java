package com.example.todobackend.api.controller;

import com.example.todobackend.api.model.PageInfo;
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
    public PageInfo getToDos(@RequestParam(required = false, defaultValue = "1") int page,
                             @RequestParam(required = false,defaultValue = "0") int sortByPriority,
                             @RequestParam(required = false, defaultValue = "0") int sortByDueDate,
                             @RequestParam(required = false, defaultValue = "all") String filterPriority,
                             @RequestParam(required = false, defaultValue = "all") String filterDone,
                             @RequestParam(required = false, defaultValue = "") String filterName) {

        appService.sortByCreationDate();

        if(sortByPriority == 1 || sortByPriority == -1) {
            appService.sortByPriority(-1*sortByPriority);
        }
        if(sortByDueDate == 1 || sortByDueDate == -1) {
            appService.sortByDueDate(-1*sortByDueDate);
        }

        ArrayList <ToDo>  toDos = appService.getToDos(page, filterDone, filterPriority, filterName);

        return new PageInfo(toDos, page);
    }

    @PostMapping("/todos")
    public void addToDo(@RequestParam(required = true) String text,
                        @RequestParam(required = false) String dueDate,
                        @RequestParam(required = true) String priority){
        Date date = null;
        if(! (dueDate==null || dueDate.isEmpty()) ) {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = formatter1.parse(dueDate);
            } catch (ParseException e) {
                date = null;
            }
        }
        appService.addToDo(text, date, priority);
    }

    @PutMapping("/todos/{id}")
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

    @PutMapping("/todos/{id}/undone")
    public void setAsUndone(@PathVariable String id){
        appService.setAsUndone(id);
    }

}
