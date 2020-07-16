package com.cicconi.events;

import com.cicconi.events.utils.DateFormatter;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateFormatterUnitTest {
    @Test
    public void timestamp_is_formatted_to_date_string() {
        long startDate = 1483347600000L;
        long endDate = 1609326000000L;
        String expectedFinalDate = String.format("%s - %s", "02/01/17", "30/12/20");

        assertEquals(expectedFinalDate, DateFormatter.format(startDate, endDate));
    }
}