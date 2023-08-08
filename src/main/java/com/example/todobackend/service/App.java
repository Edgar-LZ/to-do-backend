package com.example.todobackend.service;

import com.example.todobackend.api.model.ToDo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service
public class App {
    private ArrayList <ToDo> toDos;
    private HashMap<String, ToDo> toDoMap;
    private final int pageSize;

    App(){
        toDoMap = new HashMap<String, ToDo>();
        toDos =new ArrayList<ToDo>();
        pageSize = 10;

        this.addToDo("Hola", null, "high");
        this.addToDo("Mundo", null, "high");
        this.addToDo("fin", null, "high");

    }

    public void addToDo(String text, Date dueDate, String priority) {
        ToDo todo = new ToDo(text, dueDate, priority);
        toDoMap.put(todo.getId(), todo);
        toDos.add(todo);
    }
    public void editToDo(String id, String text, Date dueDate, String priority) {
        ToDo todo = toDoMap.get(id);
        todo.edit(text, dueDate, priority);
    }

    public void removeToDo(String id) {
        ToDo todo;
        todo = toDoMap.remove(id);
        if(todo != null) {
            int index;
            for(index = 0; index < toDos.size(); index++ ) {
                if(toDos.get(index).getId().equals(id)) {
                    break;
                }
            }
            toDos.remove(index);
        }
    }

    public void toggleDone(String id) {
        ToDo todo = toDoMap.get(id);
        todo.toggleDone();
    }
    public ArrayList<ToDo> getToDos(int page) {
        ArrayList<ToDo> pageList = new ArrayList<>();
        if (page > 1 && page * pageSize > toDos.size()) {
            return null;
        }
        int pageStart = (page - 1) * 10;
        int pageFinish = Math.min(toDos.size(), pageStart + 10);
        for(int i = pageStart; i < pageFinish; i++) {
            pageList.add(toDos.get(i));
        }

        return pageList;
    }
}
