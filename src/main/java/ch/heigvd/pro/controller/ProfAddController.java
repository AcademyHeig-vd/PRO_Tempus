package ch.heigvd.pro.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelTableProf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import ch.heigvd.pro.Connexion.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ProfAddController {

    @FXML
    private TableView<ModelTableProf> table;
    @FXML
    private TableColumn<ModelTableProf,String>col_acronyme;
    @FXML
    private TableColumn<ModelTableProf,String>col_nom;
    @FXML
    private TableColumn<ModelTableProf,String>col_prenom;
    @FXML
    private TableColumn<ModelTableProf,String>col_mail;


    ObservableList<ModelTableProf> oblist = FXCollections.observableArrayList();

    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("view/profRegister");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.setRoot("view/primary");
    }

    @FXML
    private void delete() {
        ModelTableProf selectedIndex = (ModelTableProf) table.getSelectionModel().getSelectedItem();

        try {
            if(table.getSelectionModel().getSelectedIndex() < 0){
                // Rien n'a été sélectionné
                showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                        "Aucune personne n'a été séléctionnée !");
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
            oblist.addAll(ModelTableProf.selectAllProfFromDB());
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }

        col_acronyme.setCellValueFactory(new PropertyValueFactory<>("acronyme"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));

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