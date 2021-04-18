package ch.heigvd.pro.TempusObjects;

import java.sql.*;
import java.util.List;

public class Day {
    Date date;
    List<Evenement> rappels;

    public Day(Date date, List<Evenement> rappels){
        this.date = date;
        this.rappels = rappels;
    }

    public void addRappel(Evenement rappel){
        rappels.add(rappel);
    }

    public void deleteRappel(Evenement rappel) throws SQLException, ClassNotFoundException {
        Evenement.deleteRappel(rappel.getId());
        rappels.remove(rappel);
    }

    public boolean equals(Day other){
        return date.equals(other.date);
    }

    public int getDay(){
        return date.getDay();
    }

    public void getAllRappelsOfDay() throws SQLException, ClassNotFoundException {
        rappels.clear();
        List<Evenement> newRappel = Evenement.initializeAllRappel(date);
        this.rappels = newRappel;
    }

}
