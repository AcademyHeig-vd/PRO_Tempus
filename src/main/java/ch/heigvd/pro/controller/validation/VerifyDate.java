package ch.heigvd.pro.controller.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VerifyDate {
    private final DateTimeFormatter dateFormatter;

    public VerifyDate(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public boolean isValid(String dateStr) {
        try {
            this.dateFormatter.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
