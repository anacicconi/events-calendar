package com.cicconi.events.utils;

import android.content.Context;
import com.cicconi.events.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import timber.log.Timber;

public class DateFormatter {

    static public String format(Context context, long timeStamp) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            Timber.i("Not able to parse event date%s", timeStamp);

            return context.getString(R.string.unknown);
        }
    }
}
