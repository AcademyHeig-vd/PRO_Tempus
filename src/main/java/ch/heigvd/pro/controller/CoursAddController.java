package ch.heigvd.pro.controller;

import java.io.IOException;
import java.sql.SQLException;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelTableCours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class CoursAddController {
    @FXML
    private TableView<ModelTableCours> table;
    @FXML
    private TableColumn<ModelTableCours,String>col_titre;
    @FXML
    private TableColumn<ModelTableCours,String>col_dateDebut;
    @FXML
    private TableColumn<ModelTableCours,String>col_dateEcheance;
    @FXML
    private TableColumn<ModelTableCours,String>col_description;
    @FXML
    private TableColumn<ModelTableCours,String>col_professeur;

    ObservableList<ModelTableCours> oblist = FXCollections.observableArrayList();

    /**
     * Méthode qui permet d'ajouter un cours à la base de donnée, une fenêtre avec un formulaire va s'ouvrir
     * @throws IOException
     */
    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("view/coursRegister");
    }

    /**
     * Méthode afin de supprimer une entrée
     */
    @FXML
    private void delete() {
        ModelTableCours selectedIndex = (ModelTableCours) table.getSelectionModel().getSelectedItem();

        if(table.getSelectionModel().getSelectedIndex() < 0){
            // Rien n'a été sélectionné
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Aucun cours n'a été séléctionnée !");
            return;
        }
        try {
            // Suppression base de donnée
            selectedIndex.deleteFromDB();

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
            oblist.addAll(ModelTableCours.getAllCoursFromDB());
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }

        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        col_dateEcheance.setCellValueFactory(new PropertyValueFactory<>("dateEcheance"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_professeur.setCellValueFactory(new PropertyValueFactory<>("acronyme"));

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

}