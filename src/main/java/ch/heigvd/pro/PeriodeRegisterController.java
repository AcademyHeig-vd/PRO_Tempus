package ch.heigvd.pro;

import ch.heigvd.pro.Connexion.dbConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PeriodeRegisterController {


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
    private ComboBox<ModelTableEvenement> cours = new ComboBox<>();

    ObservableList<ModelTableEvenement> oblist = FXCollections.observableArrayList();


    @FXML
    public void register(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        Window owner = submitButton.getScene().getWindow();
        ModelTableEvenement coursEvenement = cours.getSelectionModel().getSelectedItem();;

        if(!inputValid()) return;

        System.out.println(coursEvenement.getIdEvenement());
        System.out.println(coursEvenement.getTitre());
        System.out.println(jourField.getText());
        System.out.println(heureDebutField.getText());
        System.out.println(heureFinField.getText());
        System.out.println(salleField.getText());

        int id = coursEvenement.getIdEvenement();
        String jour = jourField.getText();
        String heureDebut = heureDebutField.getText();
        String heureFin = heureFinField.getText();
        String salle = salleField.getText();

        // Connexion a la database
        dbConnexion db = new dbConnexion();
        db.insertRecordPeriode(id, jour, heureDebut, heureFin, salle);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout réussi!",
                "La nouvelle entrée a été effectuée !", true);
    }

    @FXML
    public void initialize(){
        // Ajout d'une liste déroulante avec les différents cours
        try {
            dbConnexion db = new dbConnexion();
            Connection conn = db.getConnexion();

            // Ajouter une condition qui vérifie que titre appartient à cours
            String SQL = "SELECT * FROM pro.Evenement";
            System.out.println("Table name query: \"" + SQL + "\"\n");

            ResultSet rs = conn.createStatement().executeQuery(SQL);

            while(rs.next()){
                oblist.add(new ModelTableEvenement(rs.getInt("idEvenement"), rs.getString("titre")));
            }
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }

        cours.setItems(oblist);
    }

    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        ModelTableEvenement coursEvenement = cours.getSelectionModel().getSelectedItem();

       if (coursEvenement == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le nom du cours", false);
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