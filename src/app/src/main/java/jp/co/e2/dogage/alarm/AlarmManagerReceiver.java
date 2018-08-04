package jp.co.e2.dogage.alarm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.activity.PetAgeActivity;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.PreferenceUtils;
import jp.co.e2.dogage.config.Config;
import jp.co.e2.dogage.entity.PetEntity;
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper;
import jp.co.e2.dogage.model.PetDao;

/**
 * アラームマネージャレシーバー
 */
public class AlarmManagerReceiver extends BroadcastReceiver {
    public static String DISP_DATE_FORMAT = "MM/dd";

    /**
     * ${inheritDoc}
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //次回のデイリーチェックのプッシュメッセージをセットする
        new SetAlarmManager(context).set();

        //通知設定がOFF
        if (!PreferenceUtils.get(context, Config.PREF_BIRTH_NOTIFY_FLG, true)) {
            return;
        }

        //今日が誕生日・命日のデータを取得する
        ArrayList<PetEntity> data = getDispPetData(context, intent);
        if (data == null || data.size() == 0) {
            return;
        }

        //通知を表示する
        for (PetEntity val : data) {
            notification(context, val);
        }
    }

    /**
     * 通知を生成
     *
     * @param context コンテキスト
     * @param data ペットデータ
     */
    private void notification(Context context, PetEntity data) {
        //命日・誕生日当日の場合は、通知しない
        if (data.getArchiveDate() != null && data.getArchiveAge() == 0) {
            return;
        }
        if (data.getPetAge() == 0) {
            return;
        }

        String title;
        String message;
        Bitmap largeIcon;

        if (data.getArchiveDate() != null) {
            title = context.getString(R.string.notify_archive);
            title = String.format(title, new DateHelper().format(DISP_DATE_FORMAT), data.getName());
            message = context.getString(R.string.notify_archive_sub);
            message = String.format(message, data.getArchiveAge());
            largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_large_archive);
            largeIcon = AndroidUtils.setBitmapColor(largeIcon, ContextCompat.getColor(context, R.color.lightBrown));
        }
        else {
            title = context.getString(R.string.notify_birthday);
            title = String.format(title, new DateHelper().format(DISP_DATE_FORMAT), data.getName());
            message = context.getString(R.string.notify_birthday_sub);
            message = String.format(message, data.getPetAge());
            largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_large);
            largeIcon = AndroidUtils.setBitmapColor(largeIcon, ContextCompat.getColor(context, R.color.pink));
        }

        Intent intent = new Intent(context, PetAgeActivity.class);
        intent.putExtra(PetAgeActivity.PARAM_ID, data.getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,  "channel_id");
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setLargeIcon(largeIcon);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setContentIntent(pendingIntent);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(title);
        bigTextStyle.bigText(message);
        builder.setStyle(bigTextStyle);

        builder.setDefaults(Notification.DEFAULT_ALL);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(data.getId(), builder.build());
    }

    /**
     * 表示するデータを取得
     *
     * @param context コンテキスト
     * @param intent インテント
     * @return data ペット一覧
     */
    private ArrayList<PetEntity> getDispPetData(Context context, Intent intent) {
        String date = intent.getStringExtra(SetAlarmManager.DATE);

        ArrayList<PetEntity> data = null;
        SQLiteDatabase db = null;

        try {
            BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(context);
            db = helper.getWritableDatabase();

            PetDao petDao = new PetDao(context);
            data = petDao.findByBirthdayOrArchiveDate(db, date);

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