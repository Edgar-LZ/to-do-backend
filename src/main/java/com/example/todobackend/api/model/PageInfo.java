package com.example.todobackend.api.model;

import java.util.ArrayList;

public class PageInfo {
    private ArrayList<ToDo> toDos;
    private int pages;
    private int currentPage;

    public PageInfo(ArrayList<ToDo> toDos, int currentPage) {
        this.toDos = toDos;
        this.pages = toDos.size() / 10;
        if(toDos.size() % 10 != 0) {
            this.pages++;
        }
        this.currentPage = currentPage;
    }

    public ArrayList<ToDo> getToDos() {
        return toDos;
    }
    public int getPages(){
        return pages;
    }
    public int getCurrentPage() {
        return currentPage;
    }
}
