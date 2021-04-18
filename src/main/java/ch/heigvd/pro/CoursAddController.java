package ch.heigvd.pro;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import ch.heigvd.pro.Connexion.*;
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

    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("coursRegister");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.setRoot("primary");
    }

    @FXML
    private void delete() {
        ModelTableCours selectedIndex = (ModelTableCours) table.getSelectionModel().getSelectedItem();

        try {
            // Connexion a la database
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();

            if(table.getSelectionModel().getSelectedIndex() < 0){
                // Rien n'a été sélectionné
                showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                        "Aucun cours n'a été séléctionnée !");
                return;
            }
            // Suppression application
            table.getItems().remove(selectedIndex);

            // Suppression database
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement("DELETE FROM Evenement where idEvenement = ?");
            stmt.setInt(1, selectedIndex.getIdEvenement());
            stmt.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {

        try {
            dbConnexion db = new dbConnexion();
            Connection conn = db.getConnexion();

            String SQL = "SELECT * FROM pro.Cours INNER JOIN Evenement ON Cours.idEvenement = Evenement.idEvenement INNER JOIN Professeur ON Cours.acronyme = Professeur.acronyme";
            System.out.println("Table name query: \"" + SQL + "\"\n");
            ResultSet rs = conn.createStatement().executeQuery(SQL);

            while(rs.next()){
                oblist.add(new ModelTableCours(rs.getInt("idEvenement"), rs.getString("titre"), rs.getString("dateDebut"), rs.getString("dateEcheance"), rs.getString("description"), rs.getString("acronyme")));
            }
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

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}