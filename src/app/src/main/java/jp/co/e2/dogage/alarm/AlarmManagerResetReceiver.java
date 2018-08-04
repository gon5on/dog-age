package jp.co.e2.dogage.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import jp.co.e2.dogage.common.LogUtils;

/**
 * アラームマネージャがリセットされてしまうタイミングで呼ばれる
 *
 * adb shell am broadcast -a android.intent.action.BOOT_COMPLETED
 * adb shell am broadcast -a android.intent.action.TIME_SET
 * adb shell am broadcast -a android.intent.action.TIMEZONE_CHANGED
 * adb shell am broadcast -a android.intent.action.DATE_CHANGED
*/
public class AlarmManagerResetReceiver extends BroadcastReceiver {
    /**
     * ${inheritDoc}
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.v("AlarmManagerResetReceiver", "Re register to alarm manager by reset!");

        //アラーム再設定
        new SetAlarmManager(context).set();
    }
}