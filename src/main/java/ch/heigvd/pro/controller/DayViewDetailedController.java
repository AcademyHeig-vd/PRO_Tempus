package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelEvenement;
import ch.heigvd.pro.model.ModelTablePeriode;
import ch.heigvd.pro.model.ModelTableRappel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    ObservableList<ModelEvenement> oblist = FXCollections.observableArrayList();

    /**
     * Méthode qui permet d'ajouter un cours à la base de donnée, une fenêtre avec un formulaire va s'ouvrir
     * @throws IOException
     */
    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("view/rappelRegister");
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
            } //on crée un objet temporaire pour le supprimer si c'est un rappel
            if (selectedIndex.isRappel())
                new ModelTableRappel(selectedIndex.getId(), null, null, null, null, null, null).deleteFromDB();
            else //dans l'autre cas, c'est une période TODO : décider si on supprime une période par la vue par jour, vu qu'elle n'est pas crée
                showAlert(Alert.AlertType.WARNING, "Suppression impossible",
                        "Une période ne peut pas être supprimée !");
                // new ModelTablePeriode(selectedIndex.getId(),null,null,null,null,null).deleteFromDB();

            // Suppression application
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
        try {
            oblist.addAll(ModelEvenement.getAllEvenementPerDay(date));
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }


        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("dateEcheance"));
        col_heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        col_lien.setCellValueFactory(new PropertyValueFactory<>("lien"));

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

    public void setDate(Date date) {
        this.date = date;
    }

}