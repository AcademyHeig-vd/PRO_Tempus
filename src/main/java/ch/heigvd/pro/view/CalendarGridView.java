package ch.heigvd.pro.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ch.heigvd.pro.controller.DayViewControler;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarGridView {

    private VBox view;
    private ArrayList<AnchorPane> allCalendarDays = new ArrayList<>(35);
    private YearMonth currentYearMonth;

    /**
     * Constructor
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public CalendarGridView(YearMonth yearMonth) throws IOException {
        currentYearMonth = yearMonth;

        // Création du tableau du calendrier
        GridPane calendar = new GridPane();
        AnchorPane.setBottomAnchor(calendar, 0.0);
        AnchorPane.setTopAnchor(calendar, 0.0);
        AnchorPane.setLeftAnchor(calendar, 0.0);
        AnchorPane.setRightAnchor(calendar, 0.0);
        calendar.setHgap(20);
        calendar.setVgap(20);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                //Insertion des noms des jours
                AnchorPane ap = new AnchorPane();
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }

        //Rempli le calendrier
        populateCalendar(yearMonth);
        // crée la vue du calendrier
        view = new VBox(calendar);
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) throws IOException {
        // recupere la date de début du calendrier (mois année
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        Month currentMonth = yearMonth.getMonth();
        // recule jusqu'à dimanche pour commencer
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        //Les string des jours pour les labels
        Text[] dayNames = new Text[]{ new Text("Dimanche"), new Text("Lundi"), new Text("Mardi"),
                new Text("Mercredi"), new Text("Jeudi"), new Text("Vendredi"),
                new Text("Samedi") };
        int i = 0;
        // Rempli le calendrier avec les jours
        for (AnchorPane ap : allCalendarDays) {
            ap.getChildren().clear();
            //reset les jours du calendrier
            if(i<7) {
                //Ajout du label des jours
                ap.setBottomAnchor(dayNames[i], 0.0);
                ap.getChildren().add(dayNames[i]);
            }
            else if (calendarDate.getMonth().equals(currentMonth)) {
                ap.getChildren().add(DayViewControler.loadFromFXML(Date.valueOf(calendarDate)));
            }
            if(i>=7){
                calendarDate = calendarDate.plusDays(1);
            }
            ++i;
        }

    }


    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPane> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPane> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}
