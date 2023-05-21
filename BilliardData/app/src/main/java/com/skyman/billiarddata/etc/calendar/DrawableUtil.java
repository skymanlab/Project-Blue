package com.skyman.billiarddata.etc.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.etc.game.Record;

public class DrawableUtil {

    /**
     * 빨간색 원 위에 승리 횟수를 그린 Drawable 객체를 반환하는 메소드
     *
     * @param context
     * @param numberOfWins
     * @return
     */
    public static Drawable createRedCircle(Context context, int numberOfWins) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.calendar_event_day_win);
        Drawable text = CalendarUtils.getDrawableText(context, Integer.toString(numberOfWins), null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    } // End of method


    /**
     * 빨간색 원 위에는 승리 횟수, 파란색 원 위에는 패배 횟수를 그린 Drawable 객체를 반환하는 메소드
     *
     * @param context
     * @param numberOfWins    승리 횟수
     * @param numberOfDefeats 패배 횟수
     * @return
     */
    public static Drawable createTwoCircle(Context context, int numberOfWins, int numberOfDefeats) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.calendar_event_day_two_circle);
        Drawable text = CalendarUtils.getDrawableText(context, Integer.toString(numberOfWins) + " " + Integer.toString(numberOfDefeats), null, android.R.color.white, 8);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    } // End of method


    /**
     * 파란색 원 위에 패배 횟수를 그린 Drawable 객체를 반환하는 메소드
     *
     * @param context
     * @param numberOfDefeats 패배 횟수
     * @return
     */
    public static Drawable createBlueCircle(Context context, int numberOfDefeats) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.calendar_event_day_loss);
        Drawable text = CalendarUtils.getDrawableText(context, Integer.toString(numberOfDefeats), null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    } // End of method

    /**
     * record 객체의 winCounter와 lossCounter로 drawable type 을 구분하고, 이에 맞는 Drawable 객체를 생성하여 반환하는 메소드
     * @param context
     * @param record
     * @return
     */
    public static Drawable newInstanceByType(Context context, Record record) {

        int numberOfWis = record.getWinCounter().getValue();
        int numberOfDefeats = record.getLossCounter().getValue();

        if (numberOfWis > 0 && numberOfDefeats == 0) {

            // 승리만
            return createRedCircle(context, numberOfWis);

        } else if (numberOfWis > 0 && numberOfDefeats > 0) {

            // 승리, 패배 모두
            return createTwoCircle(context, numberOfWis, numberOfDefeats);

        } else if (numberOfWis == 0 & numberOfDefeats > 0) {

            // 패배만
            return createBlueCircle(context,numberOfDefeats);
        } else
            return null;

    }


}