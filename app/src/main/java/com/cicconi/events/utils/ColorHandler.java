package com.cicconi.events.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import androidx.annotation.ColorInt;
import com.cicconi.events.R;

public class ColorHandler {

    static public int getFavoriteIconColor(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorSecondary, typedValue, true);
        @ColorInt int colorSecondary = typedValue.data;

        return colorSecondary;
    }

    static public int getNonFavoriteIconColor(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        @ColorInt int colorSurface = typedValue.data;

        return colorSurface;
    }
}
