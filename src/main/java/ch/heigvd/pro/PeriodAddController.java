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
import javafx.stage.Window;


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

    @FXML
    private void newEntry() throws IOException {
        Tempus.setRoot("periodeRegister");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.setRoot("primary");
    }

    @FXML
    private void delete(){
        ModelTablePeriode selectedIndex = (ModelTablePeriode) table.getSelectionModel().getSelectedItem();

        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();

            if(table.getSelectionModel().getSelectedIndex() < 0){
                // Rien a été sélectionné
                showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                        "Aucun cours n'a été séléctionné !");
                return;
            }

            table.getItems().remove(selectedIndex);

            PreparedStatement stmt = null;
            stmt = connection.prepareStatement("DELETE FROM Periode where idPeriode = ?");
            stmt.setInt(1, selectedIndex.getId());
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

            String SQL = "SELECT * FROM pro.Periode INNER JOIN Evenement ON Periode.idCours = Evenement.idEvenement";
            System.out.println("Table name query: \"" + SQL + "\"\n");

            ResultSet rs = conn.createStatement().executeQuery(SQL);

            while(rs.next()){
                oblist.add(new ModelTablePeriode(rs.getInt("idPeriode"), rs.getString("titre"), rs.getString("jourSemaine"),
                        rs.getString("heureDebut"), rs.getString("heureFin"),
                        rs.getString("salle")));
            }
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

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}