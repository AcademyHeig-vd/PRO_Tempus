package ch.heigvd.pro.calendarview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Day {
    Date date;
    List<Object> rappels;

    public Day(Date date, List<Object> rappels){
        this.date = date;
        this.rappels = rappels;
    }

    public Day(){
        this(new Date(), new ArrayList<Object>());
    }

    public void addRappel(Object rappel){
        rappels.add(rappel);
    }

    public void deleteRappel(Object rappel){
        rappels.remove(rappel);
    }

    public boolean equals(Day other){
        return date.equals(other.date);
    }

    public int getDay(){
        return date.getDay();
    }

}
