package ch.heigvd.pro.controller.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;

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
}
