package com.example.todobackend.api.controller;

import com.example.todobackend.api.model.PageInfo;
import com.example.todobackend.api.model.ToDo;
import com.example.todobackend.service.App;
import com.example.todobackend.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @CrossOrigin(origins = "http://localhost:8080", methods = {RequestMethod.GET} )
    public ResponseEntity< PageInfo> getToDos(@RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false,defaultValue = "0") int sortByPriority,
                                              @RequestParam(required = false, defaultValue = "0") int sortByDueDate,
                                              @RequestParam(required = false, defaultValue = "All") String filterPriority,
                                              @RequestParam(required = false, defaultValue = "All") String filterDone,
                                              @RequestParam(required = false, defaultValue = "") String filterName) {

        appService.sortByCreationDate();

        if(sortByPriority == 1 || sortByPriority == -1) {
            appService.sortByPriority(-1*sortByPriority);
        }
        if(sortByDueDate == 1 || sortByDueDate == -1) {
            appService.sortByDueDate(-1*sortByDueDate);
        }

        ArrayList <ToDo>  toDos = appService.getToDos(page, filterDone, filterPriority, filterName);

        return new ResponseEntity<PageInfo>(new PageInfo(toDos, page), HttpStatus.OK);
    }

    @PostMapping("/todos")
    @CrossOrigin(origins = "http://localhost:8080", methods = {RequestMethod.POST})
    public ResponseEntity<ResponseHandler> addToDo(@RequestParam(required = true) String text,
                                                   @RequestParam(required = false) String dueDate,
                                                   @RequestParam(required = true) String priority){
        if( text.length() <1 || text.length() > 120) {
            return new ResponseEntity<>(new ResponseHandler(406,
                    "To do length must be between 0 and 120 characters."),
                    HttpStatus.NOT_ACCEPTABLE);
        }
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
        return new ResponseEntity<>(new ResponseHandler(201f,
                "Created!"),
                HttpStatus.CREATED);
    }

    @PutMapping("/todos/{id}")
    @CrossOrigin(origins = "http://localhost:8080", methods = {RequestMethod.PUT})
    public ResponseEntity<ResponseHandler> editToDo(@PathVariable String id, @RequestParam String text, @RequestParam String dueDate, @RequestParam String priority){
        Date date = null;
        if( text.length() <1 || text.length() > 120) {
            return new ResponseEntity<>(new ResponseHandler(406,
                    "To do length must be between 0 and 120 characters."),
                    HttpStatus.NOT_ACCEPTABLE);
        }
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
        return new ResponseEntity<>(new ResponseHandler(201,
                "Edited!"),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/todos/{id}")
    @CrossOrigin(origins = "http://localhost:8080", methods = {RequestMethod.DELETE})
    public void removeToDo(@PathVariable String id) {
        appService.removeToDo(id);
    }


    @PostMapping("/todos/{id}/done")
    @CrossOrigin(origins = "http://localhost:8080", methods = {RequestMethod.POST})
    public void setAsDone(@PathVariable String id){
        appService.setAsDone(id);
    }

    @PutMapping("/todos/{id}/undone")
    @CrossOrigin(origins = "http://localhost:8080", methods = {RequestMethod.PUT})
    public void setAsUndone(@PathVariable String id){
        appService.setAsUndone(id);
    }

}
