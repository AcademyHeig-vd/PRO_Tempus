/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : VerifyUserEntry.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 19.05.2021
 But         : Classe vérifiant les entrées utilisateurs
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class VerifyUserEntry {

    /**
     * Méthode vérifiant si une date entrée par un utilisateur est valide ou non
     * @param date - date entrée par l'utilisateur
     * @return - booléen si la date est valide ou pas
     */
    public boolean verificationEntreeDate(String date){
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.uuuu", Locale.FRANCE)
                .withResolverStyle(ResolverStyle.STRICT);
        VerifyDate verificationDate = new VerifyDate(formatDate);
        return verificationDate.estValide(date);
    }

    /**
     * Méthode vérifiant que la date de début est plus petite que la date de fin
     * @param dateDebut - date de début entrée par l'utilisateur
     * @param dateFin - date d'échéance entrée par l'utilisateur
     * @return - booléen si la date de début est plus petite que la date de fin
     */
    public boolean verificationDateDebutPlusPetiteQueDateFin(String dateDebut, String dateFin) throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy");
        Date d1 = sdformat.parse(dateDebut);
        Date d2 = sdformat.parse(dateFin);
        return d1.compareTo(d2) < 0;
    }

    /**
     * Méthode vérifiant si l'heure entrée par un utilisateur est valide ou non
     * @param heure - heure entrée par l'utilisateur
     * @return - booléen si l'heure est valide ou non
     */
    public static boolean verificationEntreeHeure(String heure) {
        String modeleHeure = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern p = Pattern.compile(modeleHeure);
        Matcher m = p.matcher(heure);
        return m.matches();
    }

    /**
     * Méthode vérifiant que l'heure de début est plus petite que l'heure de fin
     * @param heureDebutString - heure de début entrée par l'utilisateur
     * @param heureFinString - heure de fin entrée par l'utilisateur
     * @return - booléen si l'heure de début est plus petite que l'heure de fin
     */
    public static boolean verificationHeureDebutPlusPetiteHeureFin(String heureDebutString, String heureFinString) {
        String[] heureDebutSeparee = heureDebutString.split(":");
        String[] heureFinSeparee = heureFinString.split(":");
        int heureDebut = Integer.parseInt(heureDebutSeparee[0]);
        int minuteDebut = Integer.parseInt(heureDebutSeparee[1]);
        int heureFin = Integer.parseInt(heureFinSeparee[0]);
        int minuteFin = Integer.parseInt(heureFinSeparee[1]);

        if(heureDebut < heureFin){
            return true;
        } else return heureDebut == heureFin && minuteDebut < minuteFin;
    }

    /**
     * Méthode vérifiant si l'acronyme entré par un utilisateur est valide ou non
     * @param acronyme - acronyme entré par l'utilisateur
     * @return - booléen si l'acronyme est valide ou non
     */
    public static boolean verificationEntreeAcronyme(String acronyme) {
        String modeleAcronyme = "^[A-Z]{3}$";
        Pattern p = Pattern.compile(modeleAcronyme);
        Matcher m = p.matcher(acronyme);
        return m.matches();
    }

    /**
     * Méthode vérifiant si l'adresse mail entrée par un utilisateur est valide ou non
     * @param mail - adresse mail entré par l'utilisateur
     * @return - booléen si le mail est valide ou non
     */
    public static boolean verificationEntreeMail(String mail) {
        String modeleMail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(modeleMail);
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    /**
     * Méthode vérifiant si l'URL entré par un utilisateur est valide ou non
     * @param lien - lien entré par l'utilisateur
     * @return - booléen si le lien est valide ou non
     */
    public static boolean verificationEntreeLien(String lien) {
        try {
            new URL(lien).toURI();
            return true;
        }
        catch (URISyntaxException | MalformedURLException exception) {
            return false;
        }
    }
}
