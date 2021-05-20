package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.controller.validation.VerifyUserEntry;
import ch.heigvd.pro.model.ModelTableCours;
import ch.heigvd.pro.model.ModelTableCoursProf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CoursModifyController {
    @FXML
    private TextField titreField;
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private DatePicker dateEcheancePicker;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<ModelTableCoursProf> professeur = new ComboBox<>();
    @FXML
    private Button submitButton;


    ModelTableCours coursToModify;

    ObservableList<ModelTableCoursProf> oblist = FXCollections.observableArrayList();

    private final DateTimeFormatter formatterFrench = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter formatterEnglish = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Formulaire qui permet d'entrer toutes les informations liées à un cours
     * @throws IOException
     */
    @FXML
    public void register() throws IOException, ParseException {

        Window owner = submitButton.getScene().getWindow();

        ModelTableCoursProf coursProf = professeur.getSelectionModel().getSelectedItem();

        if(!inputValid()) return;

        coursToModify.setTitre(titreField.getText());
        coursToModify.setAcronyme(coursProf.getAcronyme());

        coursToModify.setDescription(descriptionField.getText());

        coursToModify.setDateDebut(dateDebutPicker.getValue().format(formatterEnglish));
        coursToModify.setDateEcheance(dateEcheancePicker.getValue().format(formatterEnglish));

        boolean ok_request;
        ok_request= coursToModify.updateFromDB();

        if (ok_request)
            showAlert(Alert.AlertType.INFORMATION, owner, "Modification réussie!",
                    "La moodification a bien été effectuée !", true);
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Modification échouée",
                    "Erreur lors de la modification", true);
        }

    }

    /**
     * Méthode automatiquement appelée lors de l'invocation du FXML, permet d'afficher tous les champs du formulaire
     */
    @FXML
    public void initialize(){
        titreField.setText(coursToModify.getTitre());
        descriptionField.setText(coursToModify.getDescription());

        Locale.setDefault(Locale.FRANCE);
        LocalDate date = LocalDate.parse(coursToModify.getDateDebut(), formatterFrench);
        dateDebutPicker.setShowWeekNumbers(false);
        dateDebutPicker.setEditable(false);
        dateDebutPicker.setValue(date);
        dateDebutPicker.setConverter(new StringConverter<>() {
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

        date = LocalDate.parse(coursToModify.getDateEcheance(), formatterFrench);
        dateEcheancePicker.setShowWeekNumbers(false);
        dateEcheancePicker.setEditable(false);
        dateEcheancePicker.setValue(date);
        dateEcheancePicker.setConverter(new StringConverter<>() {
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

        try {
            oblist.addAll(ModelTableCoursProf.getAllAcronymProf());
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }
        professeur.setItems(oblist);
        professeur.getSelectionModel().select(new ModelTableCoursProf(coursToModify.getAcronyme()));
    }

    /**
     * Vérifie les entrées utilisateurs
     * @return Retourne false si l'entrée est vide ou invalide
     * @throws IOException
     * @throws ParseException
     */
    private boolean inputValid() throws IOException, ParseException {
        Window owner = submitButton.getScene().getWindow();

        ModelTableCoursProf coursProf = professeur.getSelectionModel().getSelectedItem();

        VerifyUserEntry verifyUserEntry = new VerifyUserEntry();

        if (titreField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un titre", false);
            return false;
        }

        if (dateDebutPicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une date de début", false);
            return false;
        }

        if (dateEcheancePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une date d'échéance", false);
            return false;
        }

        if (!verifyUserEntry.verifyDateBeginSmallerDateEnd(dateDebutPicker.getValue().format(formatterFrench),
                dateEcheancePicker.getValue().format(formatterFrench))) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "La date de début doit être plus petite que la d'échéance", false);
            return false;
        }

        if (coursProf == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un professeur", false);
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
        Tempus.changeTab(1);
    }

    public void setLessonToModify(ModelTableCours coursToModify) {
        this.coursToModify = coursToModify;
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
        if(menu) Tempus.changeTab(1);
    }

}
