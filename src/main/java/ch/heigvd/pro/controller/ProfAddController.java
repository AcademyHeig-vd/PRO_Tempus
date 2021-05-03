package ch.heigvd.pro.controller;

import java.io.IOException;
import java.sql.SQLException;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelTablePeriode;
import ch.heigvd.pro.model.ModelTableProf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    /**
     * Méthode qui permet d'ajouter un cours à la base de donnée, une fenêtre avec un formulaire va s'ouvrir
     * @throws IOException
     */
    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("view/profRegister");
    }

    @FXML
    private void modify() throws IOException {
        ModelTableProf selectedIndex = (ModelTableProf) table.getSelectionModel().getSelectedItem();

        if(table.getSelectionModel().getSelectedIndex() < 0){
            // Rien n'a été sélectionné
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Aucun prof n'a été séléctionné !");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        ProfModifyController profModifyController = new ProfModifyController();
        profModifyController.setProfAModifier(selectedIndex);
        loader.setController(profModifyController);
        loader.setLocation(Tempus.class.getResource("view/profModify.fxml"));
        Tempus.getScene().setRoot(loader.load());
    }

    /**
     * Méthode afin de supprimer une entrée
     */
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