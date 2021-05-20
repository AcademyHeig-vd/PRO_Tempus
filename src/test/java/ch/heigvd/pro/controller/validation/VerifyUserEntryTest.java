package ch.heigvd.pro.controller.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class VerifyUserEntryTest {
    static  private VerifyDate verifyDate;
    @BeforeAll
    static void setUp(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu", Locale.FRANCE)
                .withResolverStyle(ResolverStyle.STRICT);
        verifyDate = new VerifyDate(dateTimeFormatter);

    }
    @Test
    @DisplayName("verify entry")
    void verifyEntryDate() {
        boolean cond=verifyDate.isValid("11-11-2020");
        assertFalse(cond,"date format is wrong");
    }

    @Test
    @DisplayName("verify dates range")
    void verifyDateBeginSmallerDateEnd() throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date d1 = sdformat.parse("11.12.1950");
        java.util.Date d2 = sdformat.parse("11.10.1947");
       int cond = d1.compareTo(d2);
       assertFalse(cond<0,"end date bigger than start date");
    }

    @Test
    @DisplayName("verify hour format")
    void verifyEntryHour() {
        boolean cond = VerifyUserEntry.verifyEntryHour("23:99");
        assertFalse(cond,"wrong hour setting");
    }

    @Test
    @DisplayName("verify hour range")
    void verifyHourBeginSmallerHourEnd() {
        boolean cond = VerifyUserEntry.verifyHourBeginSmallerHourEnd("23:23","22:22");
        assertFalse(cond,"wrong hour range");
    }

    @Test
    @DisplayName("testing acronyme format")
    void verifyEntryAcronym() {
        boolean cond = VerifyUserEntry.verifyEntryAcronym("CLNM");
        assertFalse(cond,"wrong acronym range");
    }

    @Test
    @DisplayName("verify emails")
    void verifyEntryMail() {
        boolean cond = VerifyUserEntry.verifyEntryMail("pro.com");
        assertFalse(cond,"wrong email format");
    }

    @Test
    @DisplayName("verify links")
    void verifyEntryLink() {
        boolean cond = VerifyUserEntry.verifyEntryLink("this is not a link");
        assertFalse(cond,"wrong link format");
    }
}