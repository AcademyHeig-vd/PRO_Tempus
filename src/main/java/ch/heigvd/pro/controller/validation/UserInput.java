package ch.heigvd.pro.controller.validation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInput {
    //private static final String patternDate = "^[0-3][0-9].[0-3][0-9].(?:[0-9][0-9])?[0-9][0-9]$";
    private static final String patternDate = "dd.MM.yyyy";

    public static boolean checkDate(String date){
        SimpleDateFormat pattern = new SimpleDateFormat(patternDate);
        String dateString = pattern.format(new Date());
        //Date newDate = pattern.parse(date);
        /*Pattern pattern = Pattern.compile(patternDate);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();*/
    }
}
