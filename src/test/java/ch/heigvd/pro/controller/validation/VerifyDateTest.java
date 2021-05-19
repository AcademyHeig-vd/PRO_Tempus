package ch.heigvd.pro.controller.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class VerifyDateTest {

    @Test
    @DisplayName("verify user entry")
    void isValid() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu", Locale.FRANCE)
                .withResolverStyle(ResolverStyle.STRICT);
        VerifyDate verifyDate = new VerifyDate(dateTimeFormatter);
        boolean cond=verifyDate.estValide("11-11-2020");
        assertFalse(cond,"date format is wrong");
    }
}