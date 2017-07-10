package com.cgi.timereport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main {


    public static void main(String[] args) {

        // Explore Date Object

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        System.out.println(date);

        // Converting String to Date
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        try {
            Date date1 = sdf1.parse(dateInString);
            System.out.println(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get current date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();
        System.out.println(dateFormat.format(date2));

        // Convert Calendar to Date
        Calendar calendar = Calendar.getInstance();
        Date date3 = calendar.getTime();

        // Get current date time
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        Calendar calendar1 = new GregorianCalendar(2013,1,28,13,24,56);
        System.out.println(sdf2.format(calendar1.getTime()));

        // Simple Calendar Example
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        int year = calendar1.get(Calendar.YEAR);
        int month = calendar1.get(Calendar.MONTH);
        int dayOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);
        int weekofYear = calendar1.get(Calendar.WEEK_OF_YEAR);
        int weekOfMonth = calendar1.get(Calendar.WEEK_OF_MONTH);

    }
}
