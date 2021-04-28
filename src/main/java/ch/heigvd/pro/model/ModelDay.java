package ch.heigvd.pro.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ModelDay {

    private Date date;
    private ObservableList<ModelEvenement> rappels;

    //TODO finir la query
    private static final String QUERY_GET_ALL_RAPPEL_OF_DAY =
            "SELECT * " +
                    "FROM pro.Rappel " +
                    "INNER JOIN Evenement " +
                    "   ON Rappel.idEvenement = Evenement.idEvenement " +
                    "WHERE Evenement.date = date";


    public ModelDay(Date date) throws SQLException, ClassNotFoundException {
        this.date = date;
        this.getAllRappelsOfDayFromDB();
    }

    public ModelDay(Date date, ObservableList<ModelEvenement> rappels){
        this.date = date;
        this.rappels = rappels;
    }

    public ModelDay(Date date, List<ModelEvenement> rappels){
        this.date = date;
        this.rappels = FXCollections.observableArrayList();
        this.rappels.addAll(rappels);
    }

    public Date getDate() {
        return date;
    }

    public Date dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ObservableList<ModelEvenement> getRappels() {
        return rappels;
    }

    public void setRappels(List<ModelEvenement> rappels) {
        this.rappels.addAll(rappels);
    }

    public void addRappel(ModelEvenement rappel){
        rappels.add(rappel);
    }

    public void deleteRappel(ModelEvenement rappel) throws SQLException, ClassNotFoundException {
        //Evenement.deleteRappel(rappel.getId());
        rappels.remove(rappel);
    }

    public boolean equals(ModelDay other){
        return date.equals(other.date);
    }



    //TODO ajouter la query
    public void getAllRappelsOfDayFromDB() throws SQLException, ClassNotFoundException {
        rappels.clear();

        //List<Evenement> newRappel = Evenement.initializeAllRappel(date);
        //this.rappels = newRappel;
    }

}
