package com.cicconi.events.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class DateFormatter {

    @NotNull
    static public String format(long timeStampStart, long timeStampEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
        Date startDate = (new Date(timeStampStart));
        Date endDate = (new Date(timeStampEnd));
        String startDateStr = sdf.format(startDate);
        String endDateStr = sdf.format(endDate);

        return String.format("%s - %s", startDateStr, endDateStr);
    }
}
