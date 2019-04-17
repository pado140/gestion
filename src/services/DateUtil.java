/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Padovano
 */
public class DateUtil {
    
    private static final String DATE_PATTERN="dd-MM-yyyy";
    
    private static DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern(DATE_PATTERN);
    
    public static String format(LocalDate date){
        if(date==null)
            return null;
        return dateFormat.format(date);
    }
    
    public static LocalDate parse(String date){
        try{
            return dateFormat.parse(date, LocalDate::from);
        }catch(DateTimeParseException e){
            return null;
        }
    }
    
    public static boolean isValid(String date){
        return DateUtil.parse(date)!=null;
    }
}
