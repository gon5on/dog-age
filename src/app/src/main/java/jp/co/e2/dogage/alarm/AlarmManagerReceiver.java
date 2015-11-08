package jp.co.e2.dogage.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.activity.PetAgeActivity;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.LogUtils;
import jp.co.e2.dogage.common.PreferenceUtils;
import jp.co.e2.dogage.config.Config;
import jp.co.e2.dogage.entity.PetEntity;
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper;
import jp.co.e2.dogage.model.PetDao;

/**
 * アラームマネージャレシーバー
 */
public class AlarmManagerReceiver extends BroadcastReceiver {
    /**
     * ${inheritDoc}
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //次回のデイリーチェックのプッシュメッセージをセットする
        new SetAlarmManager(context).set();

        //通知設定がOFF
        if (!PreferenceUtils.get(context, Config.PREF_NOTIFICATION, true)) {
            return;
        }

        try {
            //今日が誕生日・命日のデータを取得する
            ArrayList<PetEntity> data = getDispPetData(context, intent);
            if (data == null || data.size() == 0) {
                return;
            }

            //通知を表示する
            for (PetEntity val : data) {
                notification(context, val);
            }
        } catch (ParseException e) {
            e.printStackTrace();
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

        String message = "";
        String subMessage = "";
        Bitmap largeIcon = null;

        if (data.getArchiveDate() != null) {
            message = context.getResources().getString(R.string.notify_archive);
            message = String.format(message, new DateHelper().format("MM/dd"), data.getName());
            subMessage = context.getResources().getString(R.string.notify_archive_sub);
            subMessage = String.format(subMessage, data.getArchiveAge());
            largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_large_archive);
        }
        else {
            message = context.getResources().getString(R.string.notify_birthday);
            message = String.format(message, new DateHelper().format("MM/dd"), data.getName());
            subMessage = context.getResources().getString(R.string.notify_birthday_sub);
            subMessage = String.format(subMessage, data.getPetAge());
            largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_large);
        }

        Intent intent = new Intent(context, PetAgeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setLargeIcon(largeIcon);
        builder.setContentTitle(message);
        builder.setContentText(subMessage);
        builder.setContentIntent(pendingIntent);
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
    private ArrayList<PetEntity> getDispPetData(Context context, Intent intent) throws ParseException {
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