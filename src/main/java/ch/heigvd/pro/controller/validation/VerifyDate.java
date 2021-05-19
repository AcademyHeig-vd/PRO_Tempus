package ch.heigvd.pro.controller.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VerifyDate {
    private final DateTimeFormatter formatDate;

    public VerifyDate(DateTimeFormatter formatDate) {
        this.formatDate = formatDate;
    }

    public boolean estValide(String dateStr) {
        try {
            this.formatDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
