/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : RappelModifyController.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Controlleur pour la page de modification des rappels
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.controller.validation.VerifyUserEntry;
import ch.heigvd.pro.model.ModelTableRappel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ch.heigvd.pro.controller.DayViewDetailedController.testToLoadDailyView;

public class RappelModifyController {
    @FXML
    private TextField titreField;
    @FXML
    private DatePicker datePicker;
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

    ModelTableRappel reminderToModify;

    private final DateTimeFormatter formatterFrench = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter formatterEnglish = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Méthode automatiquement appelée lors de l'invocation du FXML, permet de set les données dans la table
     */
    @FXML
    public void initialize(){
        titreField.setText(reminderToModify.getTitre());
        heureField.setText(reminderToModify.getHeure());
        descriptionField.setText(reminderToModify.getDescription());
        contenuField.setText(reminderToModify.getContenu());
        lienField.setText(reminderToModify.getLien());

        Locale.setDefault(Locale.FRANCE);
        LocalDate date = LocalDate.parse(reminderToModify.getDateEcheance(), formatterFrench);
        datePicker.setShowWeekNumbers(false);
        datePicker.setEditable(false);
        datePicker.setValue(date);
        datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatterFrench.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatterFrench);
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * Formulaire qui permet d'entrer toutes les informations liées à un cours
     * @throws IOException
     */
    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        if(!inputValid()) return;

        reminderToModify.setTitre(titreField.getText());
        reminderToModify.setDateEcheance(datePicker.getValue().format(formatterEnglish));
        reminderToModify.setHeure( heureField.getText());
        reminderToModify.setDescription(descriptionField.getText());
        reminderToModify.setContenu(contenuField.getText());
        reminderToModify.setLien(lienField.getText());

        boolean ok_request;
        try {
            reminderToModify.updateFromDB();
            ok_request = true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            ok_request = false;
        }
        if (ok_request)
            showAlert(Alert.AlertType.INFORMATION, owner, "Modification réussie!",
                    "La moodification a bien été effectuée !", true);
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Modification échouée",
                    "Erreur lors de la modification", true);
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

        if (titreField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un titre", false);
            return false;
        }

        if (datePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une date", false);
            return false;
        }

        if (heureField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une heure", false);
            return false;
        } else if (!verifyUserEntry.verifyEntryHour(heureField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "L'heure n'est pas au bon format (HH:MM)", false);
            return false;
        }

        if (contenuField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un contenu", false);
            return false;
        }

        if (!lienField.getText().isEmpty() && !verifyUserEntry.verifyEntryLink(lienField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "Le lien nest pas valide", false);
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
        if (testToLoadDailyView())
            return;
        Tempus.changeTab(4);
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
        if(menu) {
            if (testToLoadDailyView())
                return;
            Tempus.changeTab(4);
        }
    }

    /**
     * Setter
     */
    public void setReminderToModify(ModelTableRappel reminderToModify) {
        this.reminderToModify = reminderToModify;
    }

}
