package jp.co.e2.dogage.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;

import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.LogUtils;
import jp.co.e2.dogage.common.PreferenceUtils;
import jp.co.e2.dogage.config.Config;
import jp.co.e2.dogage.entity.PetEntity;
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper;
import jp.co.e2.dogage.model.PetDao;

/**
 * アラームをセットするクラス
 */
public class SetAlarmManager {
    public static final String DATE = "date";

    private Context mContext;

    /**
     * コンストラクタ
     */
    public SetAlarmManager(Context context) {
        mContext = context;
    }

    /**
     * アラームをセットする
     */
    public void set() {
        //通知OFFであれば、何もしない
        if (!PreferenceUtils.get(mContext, Config.PREF_NOTIFICATION, true)) {
            return;
        }

        //次回アラーム時刻を取得
        try {
            DateHelper dateHelper = getNextAlarm();

            //アラームマネージャセット
            Intent intent = new Intent(mContext, AlarmManagerReceiver.class);
            intent.putExtra(DATE, dateHelper.format("MM-dd"));
            PendingIntent sender = PendingIntent.getBroadcast(mContext, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, dateHelper.getMilliSecond(), sender);

            LogUtils.v("SetAlarmManager", "push = " + dateHelper.format(DateHelper.FMT_DATETIME));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 次回アラーム時刻を取得
     *
     * @return dateHelper
     */
    private DateHelper getNextAlarm() throws ParseException {
        ArrayList<PetEntity> data = getPetList();
        DateHelper date = null;

        for (PetEntity val : data) {
            if (val.getArchiveDate() != null) {
                date = compare(date, val.getArchiveDate());
            } else {
                date = compare(date, val.getBirthday());
            }
        }

        return date;
    }

    /**
     * 日付を比較して一番近い未来のアラーム時刻を取得
     *
     * @param dateHelper 暫定アラーム日付
     * @param tmpDateStr 比較対象日付
     * @return dateHelper 暫定アラーム日付
     */
    private DateHelper compare(DateHelper dateHelper, String tmpDateStr) throws ParseException {
        DateHelper todayDateHelper = new DateHelper();

        //今年の誕生日 or 命日の日付をセット
        DateHelper tmpDateHelper = new DateHelper(tmpDateStr, DateHelper.FMT_DATE);
        tmpDateHelper.clearHour();
        tmpDateHelper.setYear(todayDateHelper.getYear());
        tmpDateHelper.setHour(Config.ALARM_HOUR);
        tmpDateHelper.setMin(Config.ALARM_MINUTE);

        //今年の日付がすでに過ぎていたら、来年の日付をセット
        if (tmpDateHelper.getMilliSecond() < todayDateHelper.getMilliSecond()) {
            tmpDateHelper.setYear(todayDateHelper.getYear() + 1);
        }

        //暫定のアラーム日付が空だったら、無条件で差し替える
        if (dateHelper == null) {
            dateHelper = new DateHelper(tmpDateHelper.getCalendar());
        }
        //暫定のアラーム日付より近い未来だったら、差し替える
        else if (tmpDateHelper.getMilliSecond() < dateHelper.getMilliSecond()) {
            dateHelper = new DateHelper(tmpDateHelper.getCalendar());
        }

        ///////////////////////////////////////
        dateHelper = new DateHelper();
        dateHelper.addSec(20);
        //AndroidUtils.showToastL(mContext, dateHelper.format(DateHelper.FMT_DATETIME));
        ///////////////////////////////////////

        return dateHelper;
    }

    /**
     * ペット情報一覧を取得
     *
     * @return data ペット一覧
     */
    private ArrayList<PetEntity> getPetList() {
        ArrayList<PetEntity> data = null;
        SQLiteDatabase db = null;

        try {
            BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(mContext);
            db = helper.getWritableDatabase();

            PetDao petDao = new PetDao(mContext);
            data = petDao.findAll(db);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return data;
    }
}