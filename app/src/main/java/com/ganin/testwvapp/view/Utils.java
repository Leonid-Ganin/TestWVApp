package com.ganin.testwvapp.view;

import android.content.Context;

import com.ganin.testwvapp.R;
import com.ganin.testwvapp.model.Settings;
import com.ganin.testwvapp.model.T5KWeeks;

public class Utils {

    public static String formatTimeText(int time, Context context) {
        if (time < 60) {
            return String.format("%s %s", time, context.getString(R.string.seconds));
        } else {
            int formattedTime = time / 60;
            return String.format("%s %s", formattedTime, context.getString(R.string.minutes));
        }
    }

    public static String getRunInfo(T5KWeeks week, Context context) {
        String speed = String.format("%s %s", week.getRunSpeed(), Settings.SPEED_UNIT);
        return String.format("%s %s (%s)", context.getString(R.string.run),
                formatTimeText(week.getSecondsToRun(), context), speed);
    }

    public static String getWalkInfo(T5KWeeks week, Context context) {
        String speed = String.format("%s %s", week.getWalkSpeed(), Settings.SPEED_UNIT);
        return String.format("%s %s (%s)", context.getString(R.string.walk),
                formatTimeText(week.getSecondsToWalk(), context), speed);
    }
}
