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


public class RappelAddController {

    @FXML
    private TableView<ModelTableRappel> table;
    @FXML
    private TableColumn<ModelTableRappel,String>col_contenu;
    @FXML
    private TableColumn<ModelTableRappel,String>col_lien;
    @FXML
    private TableColumn<ModelTableRappel,String>col_heure;


    ObservableList<ModelTableRappel> oblist = FXCollections.observableArrayList();

    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("rappelRegister");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.setRoot("primary");
    }

    @FXML
    private void delete() {
        ModelTableRappel selectedIndex = (ModelTableRappel) table.getSelectionModel().getSelectedItem();

        try {
            // Connexion a la database
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();

            if(table.getSelectionModel().getSelectedIndex() < 0){
                // Rien n'a été sélectionné
                showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                        "Aucun rappel n'a été séléctionnée !");
                return;
            }
            // Suppression application
            table.getItems().remove(selectedIndex);

            // Suppression database
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement("DELETE FROM Rappel where idEvenement = ?");
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

            String SQL = "SELECT * FROM pro.Rappel";
            System.out.println("Table name query: \"" + SQL + "\"\n");
            ResultSet rs = conn.createStatement().executeQuery(SQL);

            while(rs.next()){
                oblist.add(new ModelTableRappel(rs.getInt("idEvenement"), rs.getString("contenu"), rs.getString("lien"), rs.getString("heure")));
            }
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }


        col_contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        col_lien.setCellValueFactory(new PropertyValueFactory<>("lien"));
        col_heure.setCellValueFactory(new PropertyValueFactory<>("heure"));

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