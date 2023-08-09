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
    private int pages;

    App(){
        this.toDoMap = new HashMap<String, ToDo>();
        this.toDos =new ArrayList<ToDo>();
        this.pageSize = 10;
        this.pages = 1;

        this.addToDo("Example to do", null, "High");


    }

    public void addToDo(String text, Date dueDate, String priority) {
        ToDo todo = new ToDo(text, dueDate, priority);
        toDoMap.put(todo.getId(), todo);
        toDos.add(todo);
    }
    public void editToDo(String id, String text, Date dueDate, String priority) {
        ToDo todo = toDoMap.get(id);
        if(todo != null) {
            todo.edit(text, dueDate, priority);
        }
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

    public void setAsDone(String id) {
        ToDo todo = toDoMap.get(id);
        if(todo != null) {
            todo.setAsDone();
        }
    }
    public void setAsUndone(String id) {
        ToDo todo = toDoMap.get(id);
        if(todo != null) {
            todo.setAsUndone();
        }
    }
    public ArrayList<ToDo> getToDos(int page, String filterDone, String filterPriority, String filterName) {
        ArrayList<ToDo> pageList = new ArrayList<>();
        ArrayList<ToDo> filtered;



        if(filterDone.equals("all") && filterPriority.equals("all") && filterName.isEmpty()) {
            filtered = this.toDos;
        } else {
            filtered = new ArrayList<ToDo>();
            for (ToDo todo : this.toDos) {
                boolean add = true;
                if (!filterDone.equals("all") &&
                        !(filterDone.equals("done") && todo.getDone()) &&
                        !(filterDone.equals("undone") && !todo.getDone())) {
                    add = false;
                }

                if (!filterPriority.equals("all") && !filterPriority.equals(todo.getPriority())) {
                    add = false;
                }
                if (!filterName.isEmpty() && !todo.getText().toLowerCase().contains(filterName.toLowerCase())) {

                    add = false;
                }
                if (add) {
                    filtered.add(todo);
                }

            }
        }
        if (page > 1 && ( (page - 1) * pageSize + filtered.size() % pageSize ) > filtered.size()) {
            return null;
        }
        int pageStart = (page - 1) * 10;
        int pageFinish = Math.min(filtered.size(), pageStart + 10);
        for(int i = pageStart; i < pageFinish; i++) {
            pageList.add(filtered.get(i));
        }

        return pageList;
    }
    public void sortByPriority(int order) { // order == 1 not decreasing , -1 not increasing
        toDos.sort((first, second) -> first.compareTo(second)*order);
    }

    public void sortByDueDate(int order) {
        toDos.sort((first, second) -> {
            if (first.getDueDate() == null && second.getDueDate() != null) {
                return 0;
            } else if (first.getDueDate() == null) {
                return - 1 * order;
            } else if (second.getDueDate() == null) {
                return 1 * order;
            }
            return -1*first.getDueDate().compareTo(second.getDueDate()) * order;
        });
    }

    public void sortByCreationDate() {
        toDos.sort((first, second) -> {
            if(first.getCreationDate() ==null && second.getCreationDate()!= null) {
                return 0;
            } else if(first.getCreationDate() == null){
                return 1;
            } else if(second.getCreationDate() == null) {
                return -1;
            }
            return first.getCreationDate().compareTo(second.getCreationDate());
        });
    }

    public int getPages() {
        this.pages = toDos.size() / pageSize;
        if(toDos.size() % pageSize != 0) pages++;
        return this.pages;
    }


}
