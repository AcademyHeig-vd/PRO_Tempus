package ch.heigvd.pro.model;


import ch.heigvd.pro.connexion.dbConnexion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelDay {

    private Date date;
    private ObservableList<ModelEvenement> rappels;




    public ModelDay(Date date) throws SQLException, ClassNotFoundException {
        if(date == null)
            throw new IllegalArgumentException("Date can't be null");
        this.date = date;
        this.rappels = FXCollections.observableArrayList();
        this.rappels.addAll(selectRappelPerDay(date));
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


    public static ArrayList<ModelEvenement> selectRappelPerDay(Date day) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> rappels = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(dbConnexion.SELECT_QUERY_REMINDER_PER_DAY);

        preparedStatement.setString(1, day.toString()); //date du debut

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            String test = day.toString();
            rappels.add(new ModelEvenement(rs.getInt("idEvenement"),
                    rs.getString("titre"),
                    rs.getDate("dateEcheance"),
                    rs.getString("heure"),
                    rs.getString("description"),
                    rs.getString("contenu"),
                    rs.getString("lien")));
        }
        return rappels;
    }
}
