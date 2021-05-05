package ch.heigvd.pro.view;

import ch.heigvd.pro.connexion.dbConnexion;
import ch.heigvd.pro.model.ModelEvenement;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ch.heigvd.pro.controller.DayViewControler;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CalendarGridView {

    private VBox view;
    private ArrayList<AnchorPane> allCalendarDays = new ArrayList<>(35);
    private YearMonth currentYearMonth;

    /**
     * Constructor
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public CalendarGridView(YearMonth yearMonth) throws IOException, SQLException, ClassNotFoundException {
        currentYearMonth = yearMonth;

        // Création du tableau du calendrier
        GridPane calendar = new GridPane();
        AnchorPane.setBottomAnchor(calendar, 0.0);
        AnchorPane.setTopAnchor(calendar, 0.0);
        AnchorPane.setLeftAnchor(calendar, 0.0);
        AnchorPane.setRightAnchor(calendar, 0.0);
        calendar.setHgap(20);
        calendar.setVgap(20);

        for (int i = 0; i < 7; i++) {
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
    public void populateCalendar(YearMonth yearMonth) throws IOException, SQLException, ClassNotFoundException {
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

        // Récupère les rappels du mois
        ArrayList <ModelEvenement> rappels = selectRappelPerMonth(yearMonth);

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
                LocalDate finalCalendarDate = calendarDate;
                ap.getChildren().add(DayViewControler.loadFromFXML(Date.valueOf(calendarDate),
                        rappels.stream().filter(r ->
                            r.getEcheance().equals(java.sql.Date.valueOf(finalCalendarDate))
                        ).collect(Collectors.toList())));
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

    public static ArrayList<ModelEvenement> selectRappelPerMonth(YearMonth yearMonth) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> rappels = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        PreparedStatement preparedStatement = conn.prepareStatement(dbConnexion.SELECT_QUERY_RAPPEL_PER_MONTH);

        preparedStatement.setString(1, String.valueOf(yearMonth.getMonth().getValue())); //date du debut

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            rappels.add(new ModelEvenement(rs.getInt("idEvenement"),
                    rs.getString("titre"),
                    rs.getDate("dateEcheance"),
                    rs.getString("heure"),
                    rs.getString("description"),
                    rs.getString("contenu"),
                    rs.getString("lien")));
        }
        return rappels;
    }
}
