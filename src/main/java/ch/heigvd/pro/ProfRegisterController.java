package ch.heigvd.pro;

import ch.heigvd.pro.Connexion.dbConnexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class ProfRegisterController {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField mailField;
    @FXML
    private Button submitButton;


    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        System.out.println(nomField.getText());
        System.out.println(prenomField.getText());
        System.out.println(mailField.getText());

        if(!inputValid()) return;

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String mail = mailField.getText();

        dbConnexion db = new dbConnexion();
        db.insertRecordProf(nom, prenom, mail);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout réussi!",
                "La nouvelle entrée a été effectuée !", true);
    }

    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        if (nomField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un nom", false);
            return false;
        }
        if (prenomField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un prénom", false);
            return false;
        }
        if (mailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une adresse mail", false);
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
        if(menu) Tempus.setRoot("profAdd");
    }

    @FXML
    private void OKButton() throws IOException {
        Tempus.setRoot("profAdd");
    }

}