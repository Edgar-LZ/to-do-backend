package com.example.todobackend.api.model;

import java.util.ArrayList;

public class PageInfo {
    private ArrayList<ToDo> toDos;
    private int pages;
    private int currentPage;

    public PageInfo(ArrayList<ToDo> allToDos, int currentPage) {
        this.toDos = new ArrayList<ToDo>();

        this.pages = allToDos.size() / 10;
        if(allToDos.size() % 10 != 0) {
            this.pages++;
        }

        int pageStart = (currentPage - 1) * 10;
        int pageFinish = Math.min(allToDos.size(), pageStart + 10);

        for(int i = pageStart; i < pageFinish; i++) {
            toDos.add(allToDos.get(i));
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
