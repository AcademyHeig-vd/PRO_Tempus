package ch.heigvd.pro;

import ch.heigvd.pro.Connexion.dbConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PeriodeRegisterController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField jourField;
    @FXML
    private TextField heureDebutField;
    @FXML
    private TextField heureFinField;
    @FXML
    private TextField salleField;
    @FXML
    private Button submitButton;
    @FXML
    private ComboBox cours;

    ObservableList oblist = FXCollections.observableArrayList();


    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        System.out.println(nomField.getText());
        System.out.println(jourField.getText());
        System.out.println(heureDebutField.getText());
        System.out.println(heureFinField.getText());
        System.out.println(salleField.getText());

        if(!inputValid()) return;

        String nom = nomField.getText();
        String jour = jourField.getText();
        String heureDebut = heureDebutField.getText();
        String heureFin = heureFinField.getText();
        String salle = salleField.getText();

        // Connexion a la database
        dbConnexion db = new dbConnexion();
        db.insertRecordCours(1,nom, jour, heureDebut, heureFin, salle);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout réussi!",
                "La nouvelle entrée a été effectuée !", true);
    }

    @FXML
    public void initialize(){
        // Ajout d'une liste déroulante avec les différents cours
        try {
            dbConnexion db = new dbConnexion();
            Connection conn = db.getConnexion();

            String SQL = "SELECT titre FROM pro.Evenement";
            System.out.println("Table name query: \"" + SQL + "\"\n");

            ResultSet rs = conn.createStatement().executeQuery(SQL);

            while(rs.next()){
                oblist.add(rs.getString("titre"));
            }
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }

        cours.setItems(oblist);
    }

    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        if (nomField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le nom de la période", false);
            return false;
        }
        if (jourField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le jour de la semaine", false);
            return false;
        }
        if (heureDebutField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez l'heure de début", false);
            return false;
        }
        if (heureFinField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez l'heure de fin", false);
            return false;
        }
        if (salleField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le numéro de la salle", false);
            return false;
        }
        return true;
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message, boolean
                                  menu) throws IOException {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
        if(menu) Tempus.setRoot("periodeAdd");
    }

    @FXML
    private void OKButton() throws IOException {
        Tempus.setRoot("periodeAdd");
    }


}