package com.skyman.billiarddata.etc.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;

import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.skyman.billiarddata.R;

public class DrawableUtil {

    /* method : 빨간색 원에 문자열을 넣은 원을 생성 */
    public static Drawable getRedCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.calendar_event_day_win);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    } // End of method

    /* method : 파란색 원에 문자열 넣은 원을 생성 */
    public static Drawable getBlueCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.calendar_event_day_loss);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    } // End of method

    /* method : 파란색, 빨간색 원에 문자열을 넣은 원을 생성 */
    public static Drawable getBlueOrRedCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.calendar_event_day_two_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 8);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    } // End of method

    /* method : 세 개의 점을 생성 */
    public static Drawable getThreeDots(Context context){
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_three_icons);

        //Add padding to too large icon
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    } // End of method

    private DrawableUtil() {
    }

}