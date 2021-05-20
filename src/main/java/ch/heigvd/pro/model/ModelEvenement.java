/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ModelEvenement.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Modèle pour les événements
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;


//TODO mettre en simpleStringProperty lors de l'initialisation pour pouvoir passer une string au constructeur
public class ModelEvenement {
    private final int id;
    private String title;
    private Date dateEnd;
    private String hour;
    private String description;
    private String content;
    private String link;
    private String typeEvent;


    /**
     * Constructeur
     * @param id
     * @param title
     * @param dateEnd
     * @param hour
     * @param description
     * @param content
     * @param link
     */
    public ModelEvenement(int id, String title, Date dateEnd, String hour, String description, String content, String link){
        this.id = id;
        this.description = description;
        this.title = title;
        this.dateEnd = dateEnd;
        this.hour = hour;
        this.content = content;
        this.link = link;
    }

    /**
     * Méthode permettant de récupérer tous les événements d'une certaine date
     * @param day
     * @return liste des événements
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<ModelEvenement> getAllEventPerDay(Date day) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> events = ModelTableRappel.selectRappelPerDay(day);
        for(ModelEvenement event : events) {
            event.typeEvent = "Rappel";
        }
        for(ModelEvenement event : getEventFromLessonAndPeriod(day))
        {
            event.typeEvent = "Période";
            events.add(event);
        }
        return events;
    }

    /**
     * Méthode permettant de récupérer les cours et périodes
     * @param dayParameter
     * @return liste des événements
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ArrayList<ModelEvenement> getEventFromLessonAndPeriod(Date dayParameter) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> events = new ArrayList<>();
        ArrayList<ModelTablePeriode> periods = ModelTablePeriode.getAllPeriodIn(dayParameter);
        ModelTablePeriode.Jour day;
        for (ModelTablePeriode period : periods){
            day = ModelTablePeriode.Jour.LUNDI;
            day = day.getJour(period.getJourSemaine());
            //getDayOfWeek from 1 to 7 and jour.ordinal 0 to 6
            if (day != null && dayParameter.toLocalDate().getDayOfWeek().getValue() == day.ordinal() + 1){
                events.add(new ModelEvenement(period.getId(), period.getNom(), dayParameter,
                        period.getHeureDebut() + " à " + period.getHeureFin(),
                        "Salle : " + period.getSalle(), null, null));
            }
        }
        return events;
    }

    /**
     * Getters et Setters
     */
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }


    /**
     * Méthode permettant de savoir si un événement est un rappel
     * @return
     */
    public boolean isRappel() {
        return typeEvent.equals("Rappel");
    }
    /*

    public static void deleteRappel(int id) throws SQLException, ClassNotFoundException {
        // Connexion a la database
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();

        //suppression du rappel
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Evenement where idEvenement = ?");
        stmt.setInt(1, id);
        stmt.execute();

    }

    public static List<Evenement> initializeAllRappel(Date date) throws SQLException, ClassNotFoundException {
        List<Evenement> rappels = new ArrayList<>();
        String SQL = "SELECT * FROM pro.Rappel INNER JOIN Evenement ON Rappel.idEvenement = Evenement.idEvenement";

        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(SQL);
        while(rs.next()){
            rappels.add(new Evenement(rs.getInt("idEvenement"), rs.getString("titre"), rs.getDate("dateEcheance"), rs.getString("heure"), rs.getString("description"), rs.getString("contenu"), rs.getString("lien")));
        }
        return rappels;

    }*/

}
