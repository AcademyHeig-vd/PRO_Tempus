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
    public boolean verifyEntryDate(String date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu", Locale.FRANCE)
                .withResolverStyle(ResolverStyle.STRICT);
        VerifyDate verifyDate = new VerifyDate(dateTimeFormatter);
        return verifyDate.isValid(date);
    }

    /**
     * Méthode vérifiant que la date de début est plus petite que la date de fin
     * @param dateBegin - date de début entrée par l'utilisateur
     * @param dateEnd - date d'échéance entrée par l'utilisateur
     * @return - booléen si la date de début est plus petite que la date de fin
     */
    public boolean verifyDateBeginSmallerDateEnd(String dateBegin, String dateEnd) throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy");
        Date d1 = sdformat.parse(dateBegin);
        Date d2 = sdformat.parse(dateEnd);
        return d1.compareTo(d2) < 0;
    }

    /**
     * Méthode vérifiant si l'heure entrée par un utilisateur est valide ou non
     * @param hour - heure entrée par l'utilisateur
     * @return - booléen si l'heure est valide ou non
     */
    public static boolean verifyEntryHour(String hour) {
        String patternHour = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern p = Pattern.compile(patternHour);
        Matcher m = p.matcher(hour);
        return m.matches();
    }

    /**
     * Méthode vérifiant que l'heure de début est plus petite que l'heure de fin
     * @param hourBeginString - heure de début entrée par l'utilisateur
     * @param hourEndString - heure de fin entrée par l'utilisateur
     * @return - booléen si l'heure de début est plus petite que l'heure de fin
     */
    public static boolean verifyHourBeginSmallerHourEnd(String hourBeginString, String hourEndString) {
        String[] hourBeginSplitted = hourBeginString.split(":");
        String[] hourEndSplitted = hourEndString.split(":");
        int hourBegin = Integer.parseInt(hourBeginSplitted[0]);
        int minuteBegin = Integer.parseInt(hourBeginSplitted[1]);
        int hourEnd = Integer.parseInt(hourEndSplitted[0]);
        int minuteEnd = Integer.parseInt(hourEndSplitted[1]);

        if(hourBegin < hourEnd){
            return true;
        } else return hourBegin == hourEnd && minuteBegin < minuteEnd;
    }

    /**
     * Méthode vérifiant si l'acronyme entré par un utilisateur est valide ou non
     * @param acronym - acronyme entré par l'utilisateur
     * @return - booléen si l'acronyme est valide ou non
     */
    public static boolean verifyEntryAcronym(String acronym) {
        String patternAcronym = "^[A-Z]{3}$";
        Pattern p = Pattern.compile(patternAcronym);
        Matcher m = p.matcher(acronym);
        return m.matches();
    }

    /**
     * Méthode vérifiant si l'adresse mail entrée par un utilisateur est valide ou non
     * @param mail - adresse mail entré par l'utilisateur
     * @return - booléen si le mail est valide ou non
     */
    public static boolean verifyEntryMail(String mail) {
        String patternMail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(patternMail);
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    /**
     * Méthode vérifiant si l'URL entré par un utilisateur est valide ou non
     * @param link - lien entré par l'utilisateur
     * @return - booléen si le lien est valide ou non
     */
    public static boolean verifyEntryLink(String link) {
        try {
            new URL(link).toURI();
            return true;
        }
        catch (URISyntaxException | MalformedURLException exception) {
            return false;
        }
    }
}
