package com.cicconi.events;

import com.cicconi.events.utils.PhoneFomatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhoneFormatterUnitTest {
    @Test
    public void bad_characters_are_removed_from_phone() {
        String phone1 = "01.02.03.04.05";
        String phone2 = "01-02-03-04-05";
        String phone3 = "01/02/03/04/05";
        String phone4 = "0102030405   ";

        String expectedFinalPhone = "0102030405";

        assertEquals(expectedFinalPhone, PhoneFomatter.format(phone1));
        assertEquals(expectedFinalPhone, PhoneFomatter.format(phone2));
        assertEquals(expectedFinalPhone, PhoneFomatter.format(phone3));
        assertEquals(expectedFinalPhone, PhoneFomatter.format(phone4));
    }
}