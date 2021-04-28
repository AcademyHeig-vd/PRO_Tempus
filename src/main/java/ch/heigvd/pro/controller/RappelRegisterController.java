package ch.heigvd.pro.controller;

import ch.heigvd.pro.connexion.dbConnexion;
import ch.heigvd.pro.Tempus;

import ch.heigvd.pro.model.ModelTableRappel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;

public class RappelRegisterController {
    @FXML
    private TextField titreField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField heureField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField contenuField;
    @FXML
    private TextField lienField;
    @FXML
    private Button submitButton;


    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        if(!inputValid()) return;

        String titre = titreField.getText();
        String date = dateField.getText();
        String heure = heureField.getText();
        String description = descriptionField.getText();
        String contenu = contenuField.getText();
        String lien = lienField.getText();

        dbConnexion db = new dbConnexion();

        //TODO : modifier emplacement cette fonction après merge avec Lev
        int idEvenement = db.insertRecordEvenement(titre, date, date, description);

        boolean ok_request = ModelTableRappel.insertRecordRappel(idEvenement, contenu, lien, heure);
        if (ok_request)
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout réussi!",
                    "La nouvelle entrée a été effectuée !", true);
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Ajout échoué",
                    "Erreur lors de l'insertion", true);
        }
    }

    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        if (titreField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un titre", false);
            return false;
        }
        if (dateField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une date", false);
            return false;
        }
        if (heureField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une heure", false);
            return false;
        }
        if (contenuField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un contenu", false);
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
