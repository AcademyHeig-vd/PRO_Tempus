package ch.heigvd.pro.controller;

import ch.heigvd.pro.Connexion.dbConnexion;
import ch.heigvd.pro.Tempus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

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

        System.out.println(titreField.getText());
        System.out.println(dateField.getText());
        System.out.println(heureField.getText());
        System.out.println(descriptionField.getText());
        System.out.println(contenuField.getText());
        System.out.println(lienField.getText());


        if(!inputValid()) return;

        String titre = titreField.getText();
        String date = dateField.getText();
        String heure = heureField.getText();
        String description = descriptionField.getText();
        String contenu = contenuField.getText();
        String lien = lienField.getText();

        dbConnexion db = new dbConnexion();

        int idEvenement = db.insertRecordEvenement(titre, date, date, description);
        db.insertRecordRappel(idEvenement, contenu, lien, heure);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout réussi!",
                "La nouvelle entrée a été effectuée !", true);
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
        if(menu) Tempus.setRoot("view/rappelAdd");
    }

    @FXML
    private void OKButton() throws IOException {
        Tempus.setRoot("view/rappelAdd");
    }

}
