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


public class ProfAddController {

    @FXML
    private TableView<ModelTableProf> table;
    @FXML
    private TableColumn<ModelTableProf,String>col_nom;
    @FXML
    private TableColumn<ModelTableProf,String>col_prenom;
    @FXML
    private TableColumn<ModelTableProf,String>col_mail;


    ObservableList<ModelTableProf> oblist = FXCollections.observableArrayList();

    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("profRegister");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.setRoot("primary");
    }

    @FXML
    private void delete() {
        ModelTableProf selectedIndex = (ModelTableProf) table.getSelectionModel().getSelectedItem();

        try {
            // Connexion a la database
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();

            if(table.getSelectionModel().getSelectedIndex() < 0){
                // Rien n'a été sélectionné
                showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                        "Aucune personne n'a été séléctionnée !");
                return;
            }
            // Suppression application
            table.getItems().remove(selectedIndex);

            // Suppression database
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement("DELETE FROM Professeur where idProfesseur = ?");
            stmt.setInt(1, selectedIndex.getIdProfesseur());
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

            String SQL = "SELECT * FROM pro.Professeur";
            System.out.println("Table name query: \"" + SQL + "\"\n");
            ResultSet rs = conn.createStatement().executeQuery(SQL);

            while(rs.next()){
                oblist.add(new ModelTableProf(rs.getInt("idProfesseur"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("mail")));
            }
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }


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