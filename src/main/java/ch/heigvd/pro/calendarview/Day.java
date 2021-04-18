package ch.heigvd.pro.calendarview;

import ch.heigvd.pro.Connexion.dbConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day {
    Date date;
    List<Rappel> rappels;

    public Day(Date date, List<Rappel> rappels){
        this.date = date;
        this.rappels = rappels;
    }

    public void addRappel(Rappel rappel){
        rappels.add(rappel);
    }

    public void deleteRappel(Rappel rappel) throws SQLException, ClassNotFoundException {
        Rappel.deleteRappel(rappel.getId());
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
        List<Rappel> newRappel = Rappel.initializeAllRappel(date);
        this.rappels = newRappel;
    }

}
