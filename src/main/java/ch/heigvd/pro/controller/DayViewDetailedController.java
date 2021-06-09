/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : DayViewDetailedController.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Controlleur pour la page de vue par jour détaillée des rappels
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelEvenement;
import ch.heigvd.pro.model.ModelTableRappel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;


public class DayViewDetailedController {

    @FXML
    private TableView<ModelEvenement> table;
    @FXML
    private TableColumn<ModelEvenement,String>col_titre;
    @FXML
    private TableColumn<ModelEvenement,String>col_date;
    @FXML
    private TableColumn<ModelEvenement,String>col_heure;
    @FXML
    private TableColumn<ModelEvenement,String>col_description;
    @FXML
    private TableColumn<ModelEvenement,String>col_contenu;
    @FXML
    private TableColumn<ModelEvenement,String>col_lien;
    @FXML
    private TableColumn<ModelEvenement,String>col_type;

    @FXML
    Text titleLabel;
    public static Date returnDate;
    private Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    ObservableList<ModelEvenement> oblist = FXCollections.observableArrayList();

    /**
     * Méthode qui permet d'ajouter un cours à la base de donnée, une fenêtre avec un formulaire va s'ouvrir
     * @throws IOException
     */
    @FXML
    private void newEntry() throws IOException {
        returnDate = date;
        Tempus.setRoot("view/rappelRegister");
    }

    /**
     * Fonction appelée pour une modification d'élément
     * @throws IOException
     */
    @FXML
    private void modify() throws IOException {

        ModelEvenement selectedIndex = (ModelEvenement) table.getSelectionModel().getSelectedItem();

        if(table.getSelectionModel().getSelectedIndex() < 0){
            // Rien n'a été sélectionné
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Aucun rappel n'a été séléctionné !");
            return;
        }
        if(!selectedIndex.isRappel()) {
            showAlert(Alert.AlertType.WARNING, "Modification impossible",
                    "Vous ne pouvez pas modifier une période !");
            return;
        }
        // permet le retour si besoin
        returnDate = date;

        ModelTableRappel modelTableRappel = new ModelTableRappel(selectedIndex.getId(),
                selectedIndex.getTitle(), selectedIndex.getDateEnd().toString(), selectedIndex.getHour(),
                selectedIndex.getDescription(), selectedIndex.getContent(), selectedIndex.getLink());

        FXMLLoader loader = new FXMLLoader();
        RappelModifyController rappelModifyController = new RappelModifyController();
        rappelModifyController.setReminderToModify(modelTableRappel);
        loader.setController(rappelModifyController);
        loader.setLocation(Tempus.class.getResource("view/rappelModify.fxml"));
        Tempus.getScene().setRoot(loader.load());

    }

    /**
     * Retour à la vue par jour
     * @return
     * @throws IOException
     */
    public static boolean testToLoadDailyView() throws IOException {
        if (returnDate != null){
            FXMLLoader loader = new FXMLLoader();
            DayViewDetailedController dvc = new DayViewDetailedController();
            dvc.setDate(new Date(returnDate.getTime()));
            returnDate = null;
            loader.setController(dvc);
            loader.setLocation(Tempus.class.getResource("view/dayViewDetailed.fxml"));
            Tempus.getScene().setRoot(loader.load());
            return true;
        }
        return false;
    }

    /**
     * Retour au calendrier
     * @throws IOException
     */
    @FXML
    private void returnToCalendar() throws IOException {
        Tempus.changeTab(5);
    }

    /**
     * Méthode afin de supprimer une entrée
     */
    @FXML
    private void delete() {
        ModelEvenement selectedIndex = (ModelEvenement) table.getSelectionModel().getSelectedItem();

        try {
            if(table.getSelectionModel().getSelectedIndex() < 0){
                // Rien n'a été sélectionné
                showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                        "Aucun rappel n'a été séléctionnée !");
                return;
            } // On crée un objet temporaire pour le supprimer si c'est un rappel
            if (selectedIndex.isRappel())
                new ModelTableRappel(selectedIndex.getId(), null, null, null, null, null, null).deleteFromDB();
            else {//dans l'autre cas, c'est une période TODO : décider si on supprime une période par la vue par jour, vu qu'elle n'est pas crée
                showAlert(Alert.AlertType.WARNING, "Suppression impossible",
                        "Une période ne peut pas être supprimée !");
                // new ModelTablePeriode(selectedIndex.getId(),null,null,null,null,null).deleteFromDB();
                return;
            }
            table.getItems().remove(selectedIndex);
            // Update les onglets
            Tempus.updateTab();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode automatiquement appelée lors de l'invocation du FXML, permet de set les données dans la table
     */
    @FXML
    private void initialize() {
        String[] dateSplit = date.toString().split("-");
        titleLabel.setText("Rappels et cours du\n" + dateSplit[2] + "." + dateSplit[1] + "." + dateSplit[0]);
        try {
            oblist.addAll(ModelEvenement.getAllEventPerDay(date));
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }

        col_titre.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        col_heure.setCellValueFactory(new PropertyValueFactory<>("hour"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_contenu.setCellValueFactory(new PropertyValueFactory<>("content"));
        col_lien.setCellValueFactory(new PropertyValueFactory<>("link"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("typeEvent"));

        table.setItems(oblist);
    }

    /**
     * Méthode qui permet d'afficher une alerte
     * @param alertType Le type d'alerte (fenêtre)
     * @param title Titre de la fenêtre d'alerte
     * @param message Message à afficher dans la fenêtre
     */
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Setter
     */
    public void setDate(Date date) {
        this.date = date;
    }

}