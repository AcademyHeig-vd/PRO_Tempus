package ch.heigvd.pro.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.heigvd.pro.model.ModelTableRappel;
import ch.heigvd.pro.Tempus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import ch.heigvd.pro.Connexion.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class RappelAddController {

    @FXML
    private TableView<ModelTableRappel> table;
    @FXML
    private TableColumn<ModelTableRappel,String>col_titre;
    @FXML
    private TableColumn<ModelTableRappel,String>col_date;
    @FXML
    private TableColumn<ModelTableRappel,String>col_heure;
    @FXML
    private TableColumn<ModelTableRappel,String>col_description;
    @FXML
    private TableColumn<ModelTableRappel,String>col_contenu;
    @FXML
    private TableColumn<ModelTableRappel,String>col_lien;


    ObservableList<ModelTableRappel> oblist = FXCollections.observableArrayList();

    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("view/rappelRegister");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.setRoot("view/primary");
    }

    @FXML
    private void delete() {
        ModelTableRappel selectedIndex = (ModelTableRappel) table.getSelectionModel().getSelectedItem();

        try {
            if(table.getSelectionModel().getSelectedIndex() < 0){
                // Rien n'a été sélectionné
                showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                        "Aucun rappel n'a été séléctionnée !");
                return;
            }
            // Suppression database
            selectedIndex.deleteFromDB();

            // Suppression application
            table.getItems().remove(selectedIndex);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        try {
            oblist.addAll(ModelTableRappel.selectAllRappelFromDB());
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

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}