/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ProfRegisterController.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Controlleur pour la page d'ajout d'un professeur
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;

import ch.heigvd.pro.controller.validation.VerifyUserEntry;
import ch.heigvd.pro.model.ModelTableProf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;

public class ProfRegisterController {
    @FXML
    private TextField acronymeField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField mailField;
    @FXML
    private Button submitButton;


    /**
     * Formulaire qui permet d'entrer toutes les informations liées à un cours
     * @throws IOException
     */
    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        if(!inputValid()) return;

        String acronym = acronymeField.getText();
        String name = nomField.getText();
        String firstName = prenomField.getText();
        String mail = mailField.getText();

        boolean ok_request = ModelTableProf.insertProfInDB(acronym, name, firstName, mail);
        if (ok_request)
            showAlert(Alert.AlertType.INFORMATION, owner, "Ajout réussi!",
                    "La nouvelle entrée a été effectuée !", true);
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Ajout échoué",
                    "Erreur lors de l'insertion", true);
        }
    }

    /**
     * Vérifie les entrées utilisateurs
     * @return Retourne false si l'entrée est vide ou invalide
     * @throws IOException
     */
    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        VerifyUserEntry verifyUserEntry = new VerifyUserEntry();

        if (acronymeField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un acronyme", false);
            return false;
        } else if(!verifyUserEntry.verifyEntryAcronym(acronymeField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "L'acronyme n'est pas au bon format (3 lettres majuscules)", false);
            return false;
        }

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
        } else if(!verifyUserEntry.verifyEntryMail(mailField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "Le mail n'est pas valide", false);
            return false;
        }

        return true;
    }

    /**
     * Bouton de validation/annulation qui nous fait revenir à l'onglet précédent
     * @throws IOException
     */
    @FXML
    private void OKButton() throws IOException {
        Tempus.changeTab(3);
    }

    /**
     * Fonction qui affiche une fenêtre d'alerte lors d'une action
     * @param alertType Type d'alerte à afficher
     * @param owner Bouton submit
     * @param title Titre de la fenêtre
     * @param message Message à afficher
     * @param menu True si il est nécessaire de retourner au précédent onglet
     * @throws IOException
     */
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message, boolean
                                  menu) throws IOException {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
        if(menu) Tempus.changeTab(3);
    }
}
