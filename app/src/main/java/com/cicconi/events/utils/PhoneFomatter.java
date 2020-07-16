package com.cicconi.events.utils;

import org.jetbrains.annotations.NotNull;

public class PhoneFomatter {
    @NotNull
    static public String format(String phone) {
        return phone.replaceAll("[^\\d]", "");
    }
}
