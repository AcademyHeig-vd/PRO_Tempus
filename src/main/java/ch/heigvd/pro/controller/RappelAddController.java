/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ProfRegisterController.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Controlleur pour la page principale des rappels
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller;

import java.io.IOException;
import java.sql.SQLException;

import ch.heigvd.pro.model.ModelTableRappel;
import ch.heigvd.pro.Tempus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

            // Update les onglets
            Tempus.updateTab();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode afin de modifier une entrée
     * @throws IOException
     */
    @FXML
    private void modify() throws IOException {
        ModelTableRappel selectedIndex = (ModelTableRappel) table.getSelectionModel().getSelectedItem();

        if(table.getSelectionModel().getSelectedIndex() < 0){
            // Rien n'a été sélectionné
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Aucun rappel n'a été séléctionné !");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        RappelModifyController rappelModifyController = new RappelModifyController();
        rappelModifyController.setRappelAModifier(selectedIndex);
        loader.setController(rappelModifyController);
        loader.setLocation(Tempus.class.getResource("view/rappelModify.fxml"));
        Tempus.getScene().setRoot(loader.load());
    }

    /**
     * Méthode automatiquement appelée lors de l'invocation du FXML, permet de set les données dans la table
     */
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