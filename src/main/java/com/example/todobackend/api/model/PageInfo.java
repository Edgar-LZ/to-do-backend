package com.example.todobackend.api.model;

import java.util.ArrayList;

public class PageInfo {
    private ArrayList<ToDo> toDos;
    private int pages;
    private int currentPage;

    private String timeAverage;
    private String lowAverage;
    private String mediumAverage;
    private String highAverage;

    public PageInfo(ArrayList<ToDo> allToDos, int currentPage) {

        this.calculateAverage(allToDos);

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

    private String getTimeString(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long timeHours = totalSeconds / 3600;
        long timeMinutes = (totalSeconds % 3600) / 60;
        long timeSeconds = (totalSeconds % 3600) % 60;

        String hourString = timeHours < 10 ? "0" + timeHours : Long.toString(timeHours);
        String minuteString = timeMinutes < 10 ? "0" + timeMinutes : Long.toString(timeMinutes);
        String secondString = timeSeconds < 10 ? "0" + timeSeconds : Long.toString(timeSeconds);

        return hourString + ":" + minuteString + ":" + secondString + " hours";
    }
    private void calculateAverage(ArrayList<ToDo> allToDos) {

        int count = 0;
        int countLow = 0;
        int countMedium = 0;
        int countHigh = 0;

        long totalSum = 0;
        long lowSum = 0;
        long mediumSum = 0;
        long highSum = 0;

        this.timeAverage = "--:--:-- hours";
        this.lowAverage = "--:--:-- hours";
        this.mediumAverage = "--:--:-- hours";
        this.highAverage = "--:--:-- hours";


        for(int i = 0; i < allToDos.size(); i++) {
            ToDo todo = allToDos.get(i);
            if(allToDos.get(i).getDoneDate() != null) {
                long timeMilliseconds = todo.getDoneDate().getTime() - todo.getCreationDate().getTime();
                count++;
                totalSum += timeMilliseconds;
                if(todo.getPriority().equals("Low")) {
                    lowSum += timeMilliseconds;
                    countLow++;
                } else if (todo.getPriority().equals("Medium")) {
                    mediumSum += timeMilliseconds;
                    countMedium++;
                } else if (todo.getPriority().equals("High")) {
                    highSum += timeMilliseconds;
                    countHigh++;
                }
            }
        }

        if(count != 0) {
            this.timeAverage = getTimeString(totalSum / count);
            if(countLow != 0) {
                this.lowAverage = getTimeString(lowSum / countLow);
            }
            if(countMedium != 0) {
                this.mediumAverage = getTimeString(mediumSum / countMedium);
            }
            if(countHigh != 0) {
                this.highAverage = getTimeString(highSum / countHigh);

            }

        }


    }

    public String getTimeAverage() {
        return this.timeAverage;
    }
    public String getLowAverage() {
        return this.lowAverage;
    }

    public String getMediumAverage(){
        return this.mediumAverage;
    }
    public String getHighAverage() {
        return this.highAverage;
    }

}
