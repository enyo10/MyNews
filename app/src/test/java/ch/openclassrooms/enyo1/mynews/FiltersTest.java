package ch.openclassrooms.enyo1.mynews;

import org.junit.Test;

import ch.openclassrooms.enyo1.mynews.utils.Filters;

import static org.junit.Assert.*;

public class FiltersTest {

    @Test
    public void addValue() {
        Filters filters =new Filters();
        assertEquals("New created filter has size null",filters.getSelectedValues().size(),0);
        filters.addSelectedCategory("Sport");
        filters.addSelectedCategory("Sport");
        filters.addSelectedCategory("Travel");

        assertEquals("We add three values, and the size will be 2",filters.getSelectedValues().size(),2);
    }

    @Test
    public void removeValue() {

        Filters filters =new Filters();
        assertEquals("New created filter has size null",filters.getSelectedValues().size(),0);
        filters.addSelectedCategory("Politic");
        filters.addSelectedCategory("Sport");
        filters.addSelectedCategory("Travel");
        filters.addSelectedCategory("Business");

        assertEquals("After adding 4 value, the size must be 4",filters.getSelectedValues().size(),4);

        filters.removeSelectedCategory("Politic");
        filters.removeSelectedCategory("Travel");

        assertEquals("After removing two value, the size still two",filters.getSelectedValues().size(),2);
    }

    @Test
    public void getFilters() {
    }
}