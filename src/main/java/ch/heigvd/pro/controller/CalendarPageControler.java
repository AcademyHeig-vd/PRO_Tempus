/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : CalendarPageControler.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Controlleur pour la page de calendrier
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ch.heigvd.pro.view.CalendarGridView;

import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;


public class CalendarPageControler {

    private static final String FXML_SOURCE = "view/calendarPage.fxml";
    private YearMonth currentYearMonth; //variable pour stocker le mois/année courants


    @FXML
    private
    AnchorPane calendarSpace; //espace pour la grille du calendrier

    @FXML
    private
    Text monthLabel; //label du mois et année courants
    @FXML
    private
    Text yearLabel; //label du mois et année courants
    @FXML
    private
    ImageView nextYear; //label du mois et année courants
    @FXML
    private
    ImageView nextMonth; //label du mois et année courants
    @FXML
    private
    ImageView previousYear; //label du mois et année courants
    @FXML
    private
    ImageView previousMonth; //label du mois et année courants

    /**
     * Constructeur
     */
    CalendarPageControler(){}

    /**
     * Méthode automatiquement appelée lors de l'invocation du FXML, permet de set les données dans la table
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    public void initialize() throws IOException, SQLException, ClassNotFoundException {
        disableAllButtons();
        currentYearMonth = YearMonth.now();
        // Ajout du calendrier à la page
        CalendarGridView calendar = new CalendarGridView(currentYearMonth);
        calendarSpace.getChildren().add(calendar.getView());

        // Création des actions des labels
        // Mois précédent
        previousMonth.setOnMouseClicked(e -> {
            try {
                addOperationMonths(-1);
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        // Mois suivant
        nextMonth.setOnMouseClicked(e -> {
            try {
                addOperationMonths(1);
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        // Année précédente
        previousYear.setOnMouseClicked(e -> {
            try {
                addOperationMonths(-12);
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        // Année suivante
        nextYear.setOnMouseClicked(e -> {
            try {
                addOperationMonths(12);
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        //Pour l'affichage du mois et année
        monthLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE));
        yearLabel.setText(String.valueOf(currentYearMonth.getYear()));
        enableAllButtons();
    }

    /**
     * Méthode chargeant un document FXML
     * @return
     * @throws IOException
     */
    public static HBox loadFromFXMLDocument() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        CalendarPageControler cpc = new CalendarPageControler();
        loader.setController(cpc);
        loader.setLocation(Tempus.class.getResource(FXML_SOURCE));
        return (HBox) loader.load();
    }

    /**
     * Ajoute un nombre de mois au calendrier en le mettant à jour
     * @param monthsToAdd - le nombre de mois à ajouter
     * @throws IOException
     */
    private void addOperationMonths(int monthsToAdd) throws IOException, SQLException, ClassNotFoundException {
        // Nettoie le calendrier
        calendarSpace.getChildren().clear();
        // Remet le nouveau mois
        currentYearMonth = monthsToAdd > 0 ?
                currentYearMonth.plusMonths(monthsToAdd) : currentYearMonth.minusMonths(-monthsToAdd);
        monthLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE));
        yearLabel.setText(String.valueOf(currentYearMonth.getYear()));

        // Ajoute la nouvelle grid
        calendarSpace.getChildren().add(new CalendarGridView(currentYearMonth).getView());
    }

    /**
     * Passe à la vue primaire (menu principal)
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.changeTab(0);
    }

    /**
     * Méthode pour désactiver tous les boutons
     */
    private void disableAllButtons(){
        previousMonth.setDisable(true);
        previousYear.setDisable(true);
        nextMonth.setDisable(true);
        nextYear.setDisable(true);
    }

    /**
     * Méthode pour activer tous les boutons
     */
    private void enableAllButtons(){
        previousMonth.setDisable(false);
        previousYear.setDisable(false);
        nextMonth.setDisable(false);
        nextYear.setDisable(false);
    }
}
