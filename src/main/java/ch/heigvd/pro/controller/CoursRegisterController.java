package ch.heigvd.pro.controller;

import ch.heigvd.pro.Connexion.dbConnexion;
import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelTableCours;
import ch.heigvd.pro.model.ModelTableCoursProf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class CoursRegisterController {
    @FXML
    private TextField titreField;
    @FXML
    private TextField dateDebutField;
    @FXML
    private TextField dateEcheanceField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<ModelTableCoursProf> professeur = new ComboBox<>();
    @FXML
    private Button submitButton;

    ObservableList<ModelTableCoursProf> oblist = FXCollections.observableArrayList();

    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        ModelTableCoursProf coursProf = professeur.getSelectionModel().getSelectedItem();

        if(!inputValid()) return;

        String acronyme = coursProf.getAcronyme();
        String titre = titreField.getText();
        String dateDebut = dateDebutField.getText();
        String dateEcheance = dateEcheanceField.getText();
        String description = descriptionField.getText();

        dbConnexion db = new dbConnexion();
        //TODO : à déplacer quand merge avec Lev
        int idEvenement = db.insertRecordEvenement(titre, dateDebut, dateEcheance, description);
        //db.insertRecordCours(idEvenement, acronyme);
        boolean ok_request = ModelTableCours.insertCoursInDB(idEvenement, acronyme);
        if (ok_request)
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout réussi!",
                    "La nouvelle entrée a été effectuée !", true);
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Ajout échoué",
                    "Erreur lors de l'insertion", true);
        }
    }

    @FXML
    public void initialize(){
        // Ajout d'une liste déroulante avec les différents professeurs
        try {
            oblist.addAll(ModelTableCoursProf.getAllAcronymProf());
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }
        professeur.setItems(oblist);
    }

    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        ModelTableCoursProf coursProf = professeur.getSelectionModel().getSelectedItem();

        if (titreField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un titre", false);
            return false;
        }
        if (dateDebutField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une date de début", false);
            return false;
        }
        if (dateEcheanceField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une date d'échéance", false);
            return false;
        }
        if (coursProf == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un professeur", false);
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
        if(menu) Tempus.setRoot("view/primary");
    }

    @FXML
    private void OKButton() throws IOException {
        Tempus.setRoot("view/primary");
    }

}
