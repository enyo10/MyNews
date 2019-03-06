package ch.openclassrooms.enyo1.mynews.utils;

/**
 * This class is a simple date formatter.
 */
public class DateFormatter {
    /**
     * This method take a date as String and return a date as string in form dd/mm/yy
     * @param date, the date to be transform
     * @return date, String value of the transformed date.
     */

    public static String formatDate(String date){
                                                    // 2019-05-56
        String DD = date.substring(8,10);            // Day
        String MM = date.substring(5,7);            // Month
        String YY = date.substring(2,4);            // Year

        return DD+"/"+MM+"/"+YY;                    // JJ/MM/AA

    }
}
