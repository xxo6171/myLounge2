package com.example.mylounge2;

public class list {
    String startTime;
    String endTime;
    String date;

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getStartTime(){
        return startTime;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getEndTime(){
        return endTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public list( String date, String startTime, String endTime){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }


}
