package jp.co.e2.dogage.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import jp.co.e2.dogage.common.DateHelper
import jp.co.e2.dogage.common.LogUtils
import jp.co.e2.dogage.config.Config
import jp.co.e2.dogage.entity.PetEntity
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper
import jp.co.e2.dogage.model.PetDao
import java.text.ParseException
import java.util.*

/**
 * アラームをセットするクラス
 */
class SetAlarmManager(private val context: Context) {

    companion object {
        const val DATE = "date"
        const val DATE_FORMAT = "MM-dd"
    }

    //次回アラーム時刻
    private val nextAlarm: DateHelper?
        get() {
            var date: DateHelper? = null

            if (petList.size != 0) {
                for (entity in petList) {
                    date = if (entity.archiveDate != null) {
                        compare(date, entity.archiveDate!!)
                    } else {
                        compare(date, entity.birthday!!)
                    }
                }
            }

            /////////////////////////////////////DEBUG
            // date = DateHelper()
            // date.addSec(40)
            // AndroidUtils.showToastL(context, date.format(DateHelper.FMT_DATETIME))
            ///////////////////////////////////////

            return date
        }

    /**
     * ペット情報一覧を取得
     *
     * @return data ペット一覧
     */
    private val petList: ArrayList<PetEntity>
        get() {
            lateinit var data: ArrayList<PetEntity>

            BaseSQLiteOpenHelper(context).writableDatabase.use {
                val petDao = PetDao(context)
                data = petDao.findAll(it)
            }

            return data
        }

    /**
     * アラームをセットする
     */
    fun set() {
        //アラーム時刻が
        if (nextAlarm == null) {
            return
        }

        //次回アラーム時刻を取得
        try {
            //アラームマネージャセット
            val intent = Intent(context, AlarmManagerReceiver::class.java)
            intent.putExtra(DATE, nextAlarm!!.format(DATE_FORMAT))
            val sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, nextAlarm!!.milliSecond, sender)

            LogUtils.v("push = " + nextAlarm!!.format(DateHelper.FMT_DATETIME))

        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    /**
     * 日付を比較して一番近い未来のアラーム時刻を取得
     *
     * @param dateHelper 暫定アラーム日付
     * @param compareDateStr 比較対象日付
     * @return dateHelper 暫定アラーム日付
     */
    private fun compare(dateHelper: DateHelper?, compareDateStr: String): DateHelper? {
        val todayDateHelper = DateHelper()

        //今年の誕生日 or 命日の日付をセット
        val tmpDateHelper = DateHelper(compareDateStr, DateHelper.FMT_DATE)
        tmpDateHelper.clearHour()
        tmpDateHelper.year = todayDateHelper.year
        tmpDateHelper.hour = Config.ALARM_HOUR
        tmpDateHelper.setMin(Config.ALARM_MINUTE)

        //今年の日付がすでに過ぎていたら、来年の日付をセット
        if (tmpDateHelper.milliSecond < todayDateHelper.milliSecond) {
            tmpDateHelper.year = todayDateHelper.year + 1
        }

        var newDateHelper: DateHelper? = null

        if (dateHelper == null) {
            //暫定のアラーム日付が空だったら、無条件で差し替える
            newDateHelper = DateHelper(tmpDateHelper.calendar)
        }
        else if (tmpDateHelper.milliSecond < dateHelper.milliSecond) {
            //暫定のアラーム日付より近い未来だったら、差し替える
            newDateHelper = DateHelper(tmpDateHelper.calendar)
        }

        return newDateHelper
    }
}