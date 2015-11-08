package jp.co.e2.dogage.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日時関連の便利なものをまとめたクラス
 */
public class DateHelper {
    //フォーマット
    public static final String FMT_DATE = "yyyy-MM-dd";
    public static final String FMT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATE_SLASH = "yyyy/MM/dd";
    public static final String FMT_DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String FMT_DATE_DOT = "yyyy.MM.dd";
    public static final String FMT_DATETIME_DOT = "yyyy.MM.dd HH:mm:ss";
    public static final String FMT_DATE_JP = "yyyy年MM月dd日";
    public static final String FMT_DATETIME_JP = "yyyy年MM月dd日 HH時mm分";
    public static final String FMT_DATETIME_JP_FULL = "yyyy年MM月dd日 HH時mm分ss秒";
    public static final String FMT_DATE_NO_UNIT = "yyyyMMdd";

    private Calendar mCal = null;

    /**
     * コンストラクタ
     */
    public DateHelper() {
        mCal = Calendar.getInstance();
    }

    /**
     * コンストラクタ
     *
     * @param cal カレンダークラス
     */
    public DateHelper(Calendar cal) {
        mCal = cal;
    }

    /**
     * コンストラクタ
     *
     * @param time ミリ秒
     */
    public DateHelper(Long time) {
        mCal = Calendar.getInstance();
        mCal.clear();
        mCal.setTimeInMillis(time);
    }

    /**
     * コンストラクタ
     *
     * @param date Dateクラス
     */
    public DateHelper(Date date) {
        mCal = Calendar.getInstance();
        mCal.clear();
        mCal.setTime(date);
    }

    /**
     * コンストラクタ
     *
     * @param strDate 日付文字列
     * @param format 日付文字列の形式
     * @throws ParseException
     */
    public DateHelper(String strDate, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(strDate);

        mCal = Calendar.getInstance();
        mCal.clear();
        mCal.setTime(date);
    }

    /**
     * 時間以降をクリアする（日付だけを使いたい場合に使用）
     */
    public void clearHour() {
        mCal.set(Calendar.HOUR_OF_DAY, 0);
        mCal.set(Calendar.MINUTE, 0);
        mCal.set(Calendar.SECOND, 0);
        mCal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 年をセットする
     *
     * @param val 年
     */
    public void setYear(int val) {
        mCal.set(Calendar.YEAR, val);
    }

    /**
     * 月をセットする
     *
     * @param val 月
     */
    public void setMonth(int val) {
        mCal.set(Calendar.MONTH, (val - 1));
    }

    /**
     * 日をセットする
     *
     * @param val 日
     */
    public void setDay(int val) {
        mCal.set(Calendar.DAY_OF_WEEK, val);
    }

    /**
     * 時間をセットする
     *
     * @param val 日
     */
    public void setHour(int val) {
        mCal.set(Calendar.HOUR_OF_DAY, val);
    }

    /**
     * 分をセットする
     *
     * @param val 日
     */
    public void setMin(int val) {
        mCal.set(Calendar.MINUTE, val);
    }

    /**
     * 分をセットする
     *
     * @param val 日
     */
    public void setSec(int val) {
        mCal.set(Calendar.SECOND, val);
    }

    /**
     * 年の加減算
     *
     * マイナスを指定すれば過去が計算可能
     * 足さない部分には0を指定する
     *
     * @param add 何年後
     */
    public void addYear(Integer add) {
        add(add, 0, 0, 0, 0, 0);
    }

    /**
     * 月の加減算
     *
     * マイナスを指定すれば過去が計算可能
     * 足さない部分には0を指定する
     *
     * @param add 何か月後
     */
    public void addMonth(Integer add) {
        add(0, add, 0, 0, 0, 0);
    }

    /**
     * 日の加減算
     *
     * マイナスを指定すれば過去が計算可能
     * 足さない部分には0を指定する
     *
     * @param add 何日後
     */
    public void addDay(Integer add) {
        add(0, 0, add, 0, 0, 0);
    }

    /**
     * 時の加減算
     *
     * マイナスを指定すれば過去が計算可能
     * 足さない部分には0を指定する
     *
     * @param add 何時間後
     */
    public void addHour(Integer add) {
        add(0, 0, 0, add, 0, 0);
    }

    /**
     * 分の加減算
     *
     * マイナスを指定すれば過去が計算可能
     * 足さない部分には0を指定する
     *
     * @param add 何分後
     */
    public void addMin(Integer add) {
        add(0, 0, 0, 0, add, 0);
    }

    /**
     * 秒の加減算
     *
     * マイナスを指定すれば過去が計算可能
     * 足さない部分には0を指定する
     *
     * @param add 何秒後
     */
    public void addSec(Integer add) {
        add(0, 0, 0, 0, 0, add);
    }

    /**
     * 日時の加減算
     *
     * マイナスを指定すれば何日前が計算可能
     * 足さない部分には0を指定する
     *
     * @param addYear  何年後
     * @param addMonth 何か月後
     * @param addDay   何日後
     * @param addHour  何時間後
     * @param addMin   何分後
     * @param addSec   何秒後
     */
    public void add(Integer addYear, Integer addMonth, Integer addDay, Integer addHour, Integer addMin, Integer addSec) {
        mCal.add(Calendar.YEAR, addYear);
        mCal.add(Calendar.MONTH, addMonth);
        mCal.add(Calendar.DATE, addDay);
        mCal.add(Calendar.HOUR_OF_DAY, addHour);
        mCal.add(Calendar.MINUTE, addMin);
        mCal.add(Calendar.SECOND, addSec);
    }

    /**
     * フォーマットした日時を返す
     *
     * @param format フォーマット
     * @return String フォーマットした日付文字列
     */
    public String format(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(mCal.getTime());
    }

    /**
     * 今日までの年齢を計算する
     *
     * @return int 年齢
     */
    public int getAge() {
        int today = Integer.parseInt(new DateHelper().format(FMT_DATE_NO_UNIT));
        int born = Integer.parseInt(format(FMT_DATE_NO_UNIT));

        return (int)((today - born) / 10000);
    }

    /**
     * カレンダークラスを返す
     *
     * @return Calendar mCal
     */
    public Calendar getCalendar() {
        return mCal;
    }

    /**
     * 年を返す
     *
     * @return int
     */
    public int getYear() {
        return mCal.get(Calendar.YEAR);
    }

    /**
     * 月を返す
     *
     * @return int
     */
    public int getMonth() {
        return mCal.get(Calendar.MONTH);
    }

    /**
     * 日を返す
     *
     * @return int
     */
    public int getDay() {
        return mCal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 時間を返す
     *
     * @return int
     */
    public int getHour() {
        return mCal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 分を返す
     *
     * @return int
     */
    public int getMinute() {
        return mCal.get(Calendar.MINUTE);
    }

    /**
     * 秒を返す
     *
     * @return int
     */
    public int getSecond() {
        return mCal.get(Calendar.SECOND);
    }

    /**
     * ミリ秒を返す
     *
     * @return long
     */
    public long getMilliSecond() {
        return mCal.getTimeInMillis();
    }
}
