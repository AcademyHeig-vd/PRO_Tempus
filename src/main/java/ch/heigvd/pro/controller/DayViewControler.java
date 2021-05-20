/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : DayViewControler.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Controlleur pour la page de vue par jour des rappels
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller;

import ch.heigvd.pro.model.ModelEvenement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelDay;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class DayViewControler {

    private static final String FXML_SOURCE = "view/dayView.fxml";

    private Date date; //la date du jour
    @FXML
    Text dateLabel; //pour contenir le jour du mois (numéro)
    @FXML
    TableView<ModelEvenement> evenementTable; //la table contenant les rappels
    @FXML
    TableColumn<ModelEvenement, String> nameEvenement; //le nom de l'évènement




    public DayViewControler (){
    }

    /**
     * Getter et setter
     */
    public void setDate(Date date){
        this.date = date;    }

    public Date getDate(){
        return date;
    }

    public void setEvenementTable(ObservableList<ModelEvenement> events){
        if(events.size() != 0)
            this.evenementTable.setItems(events);
    }

    /**
     * Initialise la vue du jour
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        ModelDay modelDay = new ModelDay(this.date);
        // Set des valeur du jours
        evenementTable.setItems(modelDay.getRappels());
        dateLabel.setText(Integer.toString(modelDay.getDate().toLocalDate().getDayOfMonth()));
        nameEvenement.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));

        evenementTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                switchToDetailedView();
            }
        });
        nameEvenement.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
    }

    /**
     * Méthode de vue utilisée par FXML
     * @throws IOException
     */
    @FXML
    public void viewDetailed() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        DayViewDetailedController dvc = new DayViewDetailedController();
        dvc.setDate(date);
        loader.setController(dvc);
        loader.setLocation(Tempus.class.getResource("view/dayViewDetailed.fxml"));
        Tempus.getScene().setRoot(loader.load());
    }

    /**
     * Charge le fxml relatif à ce controleur
     * @param date la date à laquelle fixer le jour
     * @return Un anchorPane contenant le jour
     * @throws IOException
     */
    public static AnchorPane loadFromFXML(Date date, List<ModelEvenement> eventOfTheDay) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        DayViewControler dvc = new DayViewControler();
        dvc.setDate(date);
        dvc.setEvenementTable(FXCollections.observableList(eventOfTheDay));
        loader.setController(dvc);
        loader.setLocation(Tempus.class.getResource(FXML_SOURCE));

        return (AnchorPane) loader.load();
    }

    public void switchToDetailedView(){
        System.out.println("go to detailed view");
    }

}
