package ch.openclassrooms.enyo1.mynews;

import org.junit.Test;

import ch.openclassrooms.enyo1.mynews.utils.DateFormatter;

import static org.junit.Assert.*;

public class DateFormatterTest {

    @Test
    public void dateReformatYYYYMMJJ_Unit_Test() {
        String originDate = "2019-03-14T11:38:10-04:00";         //Original date to reformat
        assertEquals("20190314", DateFormatter.dateFormatYYYYMMJJ(originDate));
    }

    @Test
    public void dateFormat_Unit_Test() {
        String originDate = "2019-03-14T11:38:10-04:00";         //Original date to reformat
        assertEquals("14/03/19", DateFormatter.formatDate(originDate));
    }


    @Test
    public void dateReformat_Unit_Test() {
        String originDate = "20190314";         //Original date to reformat
        assertEquals("14/03/19", DateFormatter.dateReformat(originDate));
    }


}