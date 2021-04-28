package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ch.heigvd.pro.view.CalendarGridView;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;


public class CalendarPageControler {

    private static final String FXML_SOURCE = "view/calendarPage.fxml";
    private YearMonth currentYearMonth; //variable pour stocker le mois/année courants

    @FXML
    private
    Button previousMonth; //bouton vers mois précédent

    @FXML
    private
    Button toMainMenu; //bouton vers menu principal

    @FXML
    private
    Button nextMonth; //bouton pour mois suivant

    @FXML
    private
    AnchorPane calendarSpace; //espace pour la grille du calendrier

    @FXML
    private
    Text monthYearLabel; //label du mois et année courants


    CalendarPageControler(){}

    public void initialize() throws IOException {
        currentYearMonth = YearMonth.now();
        //ajout du calendrier à la page
        CalendarGridView calendar = new CalendarGridView(currentYearMonth);
        calendarSpace.getChildren().add(calendar.getView());

        //Création des actions des boutons
        //mois précédent
        previousMonth.setOnAction(e -> {
            try {
                addOperationMonths(-1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //mois suivant
        nextMonth.setOnAction(e -> {
            try {
                addOperationMonths(1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //menu principal
        toMainMenu.setOnAction(e -> {
            try {
                switchToPrimary();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //Pour l'affichage du mois et année
        monthYearLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE)
                + " " + currentYearMonth.getYear());
    }

    public static HBox loadFromFXMLDocument() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        CalendarPageControler cpc = new CalendarPageControler();
        loader.setController(cpc);
        loader.setLocation(Tempus.class.getResource(FXML_SOURCE));
        return (HBox) loader.load();
    }

    /**
     * Ajoute un nombre de mois au calendrier en le mettant à jour
     * @param monthsToAdd , le nombre de mois à ajouter
     * @throws IOException
     */
    private void addOperationMonths(int monthsToAdd) throws IOException {
        //nettoie le calendrier
        calendarSpace.getChildren().clear();
        //remet le nouveau mois
        currentYearMonth = monthsToAdd > 0 ?
                currentYearMonth.plusMonths(monthsToAdd) : currentYearMonth.minusMonths(-monthsToAdd);
        monthYearLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE)
                + " " + currentYearMonth.getYear());
        //ajoute la nouvelle grid
        calendarSpace.getChildren().add(new CalendarGridView(currentYearMonth).getView());
    }

    /**
     * passe à la vue primaire (menu principal)
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.setRoot("view/primary");
    }
}
