package jp.co.e2.dogage.alarm

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat

import java.util.ArrayList

import jp.co.e2.dogage.R
import jp.co.e2.dogage.activity.PetAgeActivity
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.common.DateHelper
import jp.co.e2.dogage.common.PreferenceUtils
import jp.co.e2.dogage.config.Config
import jp.co.e2.dogage.entity.PetEntity
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper
import jp.co.e2.dogage.model.PetDao

/**
 * アラームマネージャレシーバー
 */
class AlarmManagerReceiver : BroadcastReceiver() {
    companion object {
        const val DISP_DATE_FORMAT = "MM/dd"
    }

    /**
     * ${inheritDoc}
     */
    override fun onReceive(context: Context, intent: Intent) {
        //次回のデイリーチェックのプッシュメッセージをセットする
        SetAlarmManager(context).set()

        //通知設定がOFF
        if (!PreferenceUtils.get(context, Config.PREF_BIRTH_NOTIFY_FLG, true)) {
            return
        }

        //今日が誕生日・命日のデータを取得する
        val data = getDispPetData(context, intent)
        if (data == null || data.size == 0) {
            return
        }

        //通知を表示する
        for (`val` in data) {
            notification(context, `val`)
        }
    }

    /**
     * 通知を生成
     *
     * @param context コンテキスト
     * @param data ペットデータ
     */
    private fun notification(context: Context, data: PetEntity) {
        //命日・誕生日当日の場合は、通知しない
        if (data.archiveDate != null && data.archiveAge == 0) {
            return
        }
        if (data.petAge == 0) {
            return
        }

        val title: String
        val message: String
        val largeIcon: Bitmap

        if (data.archiveDate != null) {
            val formatTitle = context.getString(R.string.notify_archive)
            title = String.format(formatTitle, DateHelper().format(DISP_DATE_FORMAT), data.name)
            val formatMessage = context.getString(R.string.notify_archive_sub)
            message = String.format(formatMessage, data.archiveAge)
            val largeIconOrg = BitmapFactory.decodeResource(context.resources, R.drawable.ic_notification_large_archive)
            largeIcon = AndroidUtils.setBitmapColor(largeIconOrg, ContextCompat.getColor(context, R.color.lightBrown))
        } else {
            val formatTitle = context.getString(R.string.notify_birthday)
            title = String.format(formatTitle, DateHelper().format(DISP_DATE_FORMAT), data.name)
            val formatMessage = context.getString(R.string.notify_birthday_sub)
            message = String.format(formatMessage, data.petAge)
            val largeIconOrg = BitmapFactory.decodeResource(context.resources, R.drawable.ic_notification_large)
            largeIcon = AndroidUtils.setBitmapColor(largeIconOrg, ContextCompat.getColor(context, R.color.pink))
        }

        val intent = Intent(context, PetAgeActivity::class.java)
        intent.putExtra(PetAgeActivity.PARAM_ID, data.id)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, "channel_id")
        builder.setSmallIcon(R.drawable.ic_notification)
        builder.setLargeIcon(largeIcon)
        builder.setContentTitle(title)
        builder.setContentText(message)
        builder.setContentIntent(pendingIntent)

        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.setBigContentTitle(title)
        bigTextStyle.bigText(message)
        builder.setStyle(bigTextStyle)
        builder.setDefaults(Notification.DEFAULT_ALL)

        val manager = NotificationManagerCompat.from(context)
        manager.notify(data.id!!, builder.build())
    }

    /**
     * 表示するデータを取得
     *
     * @param context コンテキスト
     * @param intent インテント
     * @return data ペット一覧
     */
    private fun getDispPetData(context: Context, intent: Intent): ArrayList<PetEntity>? {
        val date = intent.getStringExtra(SetAlarmManager.DATE)

        var data: ArrayList<PetEntity>? = null
        var db: SQLiteDatabase? = null

        try {
            val helper = BaseSQLiteOpenHelper(context)
            db = helper.writableDatabase

            val petDao = PetDao(context)
            data = petDao.findByBirthdayOrArchiveDate(db, date)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db?.close()
        }

        return data
    }
}