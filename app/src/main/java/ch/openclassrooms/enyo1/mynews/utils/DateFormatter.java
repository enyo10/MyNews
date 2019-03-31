package ch.openclassrooms.enyo1.mynews.utils;

/**
 * This class is a simple date formatter.
 */
public class DateFormatter {
    /**
     * This method take a date as String and return a date as string in form dd/mm/yy
     * @param date in format 2018-05-08T12:37:10-04:00 , the date to be transform
     * @return date in format DD/MM/YY in , String value of the transformed date.
     */

    public static String formatDate(String date){
                                                    // 2019-05-16
        String DD = date.substring(8,10);            // Day
        String MM = date.substring(5,7);            // Month
        String YY = date.substring(2,4);            // Year

        return DD+"/"+MM+"/"+YY;                    // DD/MM/YY

    }

    /**
     *
     * @param date, a date on format DD/MM/YYYY, to transform.
     * @return date, on format yyyyMMdd.
     */
    public static String dateFormatYYYYMMJJ(String date){
        //03/03/2019
        /*String YYYY = date.substring(0,4);           // Year
        String MM = date.substring(5,7);             // Month
        String DD = date.substring(8,10);   */         // Day

        String dd =date.substring(0,2);
        String mm =date.substring(3,5);
        String yyyy=date.substring(6);

        return yyyy+mm+dd;
    }

    /**
     *
     * Return date in format string JJ/MM/AA
     *
     * @param date
     *          String starting with a date in the format YYYYMMDD
     *
     * @return String Date in format DD/MM/YY
     */
    public static String dateReformat(String date){

        String DD = date.substring(6,8);            // Day
        String MM = date.substring(4,6);            // Month
        String YY = date.substring(2,4);            // Year

        return DD+"/"+MM+"/"+YY;                    // JJ/MM/AA
    }
}
