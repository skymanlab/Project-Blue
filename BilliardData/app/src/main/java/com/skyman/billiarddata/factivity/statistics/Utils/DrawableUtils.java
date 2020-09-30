package com.skyman.billiarddata.factivity.statistics.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;

import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.skyman.billiarddata.R;

public class DrawableUtils {
    public static Drawable getRedCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_red_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }
    public static Drawable getBlueCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_blue_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }

    public static Drawable getThreeDots(Context context){
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_three_icons);

        //Add padding to too large icon
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    }

    private DrawableUtils() {
    }

}