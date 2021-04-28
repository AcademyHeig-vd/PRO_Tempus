package ch.heigvd.pro.controller;

import java.io.IOException;
import java.sql.SQLException;

import ch.heigvd.pro.model.ModelTablePeriode;
import ch.heigvd.pro.Tempus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class PeriodAddController {

    @FXML
    private TableView<ModelTablePeriode> table;
    @FXML
    private TableColumn<ModelTablePeriode,String>col_nom;
    @FXML
    private TableColumn<ModelTablePeriode,String>col_jour;
    @FXML
    private TableColumn<ModelTablePeriode,String>col_heured;
    @FXML
    private TableColumn<ModelTablePeriode,String>col_heuref;
    @FXML
    private TableColumn<ModelTablePeriode,String>col_salle;

    ObservableList<ModelTablePeriode> oblist = FXCollections.observableArrayList();

    /**
     * Méthode qui permet d'ajouter un cours à la base de donnée, une fenêtre avec un formulaire va s'ouvrir
     * @throws IOException
     */
    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("view/periodeRegister");
    }

    /**
     * Méthode afin de supprimer une entrée
     */
    @FXML
    private void delete(){
        ModelTablePeriode selectedIndex = (ModelTablePeriode) table.getSelectionModel().getSelectedItem();

        try {
            if(table.getSelectionModel().getSelectedIndex() < 0){
                // Rien a été sélectionné
                showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                        "Aucun cours n'a été séléctionné !");
                return;
            }
            //suppression de la db
            selectedIndex.deleteFromDB();

            //suppression locale
            table.getItems().remove(selectedIndex);

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
            oblist.addAll(ModelTablePeriode.getAllPeriodeFromDB());
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }

        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_jour.setCellValueFactory(new PropertyValueFactory<>("jourSemaine"));
        col_heured.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
        col_heuref.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
        col_salle.setCellValueFactory(new PropertyValueFactory<>("salle"));

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