package jp.co.e2.dogage.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import jp.co.e2.dogage.R
import jp.co.e2.dogage.activity.PetAgeActivity
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.common.DateHelper
import jp.co.e2.dogage.common.LogUtils
import jp.co.e2.dogage.common.PreferenceUtils
import jp.co.e2.dogage.config.Config
import jp.co.e2.dogage.entity.PetEntity
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper
import jp.co.e2.dogage.model.PetDao
import kotlin.collections.ArrayList

/**
 * アラームマネージャレシーバー
 */
class AlarmManagerReceiver : BroadcastReceiver() {

    companion object {
        private const val DISP_DATE_FORMAT = "M/d"
        private const val TYPE_BIRTHDAY = 1
        private const val TYPE_ARCHIVE= 2

        private const val CHANNEL_ID = "channel_1"
    }

    /**
     * ${inheritDoc}
     */
    override fun onReceive(context: Context, intent: Intent) {
        //次回のデイリーチェックのプッシュメッセージをセットする
        SetAlarmManager(context).set()

        val date = intent.getStringExtra(SetAlarmManager.DATE)

        showArchiveDateNotify(context, date)
        showBirthdayNotify(context, date)
    }

    /**
     * 誕生日を通知する
     *
     * @param context コンテキスト
     * @param date 日付
     */
    private fun showBirthdayNotify(context: Context, date: String) {
        //誕生日通知フラグが立っていない場合は何もしない
        if (!PreferenceUtils.get(context, Config.PREF_BIRTH_NOTIFY_FLG, true)) {
            LogUtils.d("Birthday notify flg OFF!!!")
            return
        }

        //データ取得
        lateinit var list: ArrayList<PetEntity>

        BaseSQLiteOpenHelper(context).writableDatabase.use {
            val petDao = PetDao(context)
            list = petDao.findByBirthday(it, date)
        }

        if (list.size == 0) {
            LogUtils.d("Birthday notify data empty!!!")
            return
        }

        //通知を表示する
        for (data in list) {
            //生まれた当日の場合は通知しない
            if (data.petAge == 0) {
                LogUtils.d("Born that day!!!  id=" + data.id)
                return
            }

            LogUtils.d("Birthday Notify!!!  id=" + data.id)
            notification(context, TYPE_BIRTHDAY, data)
        }
    }

    /**
     * 命日を通知する
     *
     * @param context コンテキスト
     * @param date 日付
     */
    private fun showArchiveDateNotify(context: Context, date: String) {
        //命日通知フラグが立っていない場合は何もしない
        if (!PreferenceUtils.get(context, Config.PREF_ARCHIVE_NOTIFY_FLG, true)) {
            LogUtils.d("Archive date notify flg OFF!!!")
            return
        }

        //データ取得
        lateinit var list: ArrayList<PetEntity>

        BaseSQLiteOpenHelper(context).writableDatabase.use {
            val petDao = PetDao(context)
            list = petDao.findByArchiveDate(it, date)
        }

        if (list.size == 0) {
            LogUtils.d("Archive date notify data empty!!!")
            return
        }

        //通知を表示する
        for (data in list) {
            //命日当日の場合は通知しない
            if (data.archiveDate != null && data.archiveAge == 0) {
                LogUtils.d("Die that day!!!  id=" + data.id)
                return
            }

            LogUtils.d("Archive Notify!!!  id=" + data.id)
            notification(context, TYPE_ARCHIVE, data)
        }
    }

    /**
     * 通知を生成
     *
     * @param context コンテキスト
     * @param type タイプ
     * @param data ペットデータ
     */
    private fun notification(context: Context, type: Int, data: PetEntity) {
        val title: String
        val message: String
        val largeIcon: Bitmap

        if (type == TYPE_BIRTHDAY) {
            val formatTitle = context.getString(R.string.notify_birthday)
            title = String.format(formatTitle, DateHelper().format(DISP_DATE_FORMAT), data.name)
            val formatMessage = context.getString(R.string.notify_birthday_sub)
            message = String.format(formatMessage, data.petAge)
            val largeIconOrg = BitmapFactory.decodeResource(context.resources, R.drawable.ic_notification_large_birth)
            largeIcon = AndroidUtils.setBitmapColor(largeIconOrg, ContextCompat.getColor(context, R.color.pink))
        } else {
            val formatTitle = context.getString(R.string.notify_archive)
            title = String.format(formatTitle, DateHelper().format(DISP_DATE_FORMAT), data.name)
            val formatMessage = context.getString(R.string.notify_archive_sub)
            message = String.format(formatMessage, data.archiveAge)
            val largeIconOrg = BitmapFactory.decodeResource(context.resources, R.drawable.ic_notification_large_archive)
            largeIcon = AndroidUtils.setBitmapColor(largeIconOrg, ContextCompat.getColor(context, R.color.lightBrown))
        }

        //チャンネル登録
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notify_channel_name)
            val descriptionText = context.getString(R.string.notify_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText

            val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val intent = PetAgeActivity.newInstance(context, getPageNum(context, data.id!!))
        val pendingIntent = PendingIntent.getActivity(context, data.id!!, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
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
     * IDからページ数を取得する
     */
    private fun getPageNum(context: Context, id: Int): Int {
        var pageNum = 0
        var list = ArrayList<PetEntity>()

        BaseSQLiteOpenHelper(context).writableDatabase.use {
            val petDao = PetDao(context)
            list = petDao.findAll(it)

        }

        if (list.size != 0) {
            var i = 0
            for (data in list) {
                if (data.id == id) {
                    pageNum = i
                    break
                }

                i++
            }
        }

        return pageNum
    }
}