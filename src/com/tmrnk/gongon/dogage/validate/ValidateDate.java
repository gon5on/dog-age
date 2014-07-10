package com.tmrnk.gongon.dogage.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tmrnk.gongon.dogage.common.DateUtils;

/**
 * 日付系バリデーションクラス
 * 
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * format … フォーマット
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateDate
{
    public static final String ERROR_MSG_FORMAT = "%sは正しい形式ではありません。";
    public static final String ERROR_MSG_FUTURE = "%sに未来の日付は指定できません。";
    public static final String ERROR_MSG_PAST = "%sに過去の日付は指定できません。";

    public static final Integer OLDEST_DATE = 19000101;
    public static final Integer NEWEST_DATE = 22001231;

    /**
     * 正しい日付かどうかチェック
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String format フォーマット
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void check(Validate validate, String value, String name, String format, String msgFull)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        try {
            //日付形式に変換できるかどうか
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            Date tmp = sdf.parse(value);

            SimpleDateFormat sdf2 = new SimpleDateFormat(DateUtils.FMT_DATE_NO_UNIT);
            Integer date = Integer.parseInt(sdf2.format(tmp.getTime()));

            //指定された日付が過去未来数百年間に収まっているか
            if (date < OLDEST_DATE) {
                if (msgFull != null) {
                    validate.error(name, msgFull);
                } else {
                    validate.error(name, String.format(ERROR_MSG_FORMAT, name));
                }
            }
            if (NEWEST_DATE < date) {
                if (msgFull != null) {
                    validate.error(name, msgFull);
                } else {
                    validate.error(name, String.format(ERROR_MSG_FORMAT, name));
                }
            }
        } catch (ParseException e) {
            //日付形式に変換失敗
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_FORMAT, name));
            }
        }
    }

    /**
     * 正しい日付かどうかチェック
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String format フォーマット
     * @return void
     * @access public
     */
    public static void check(Validate validate, String value, String name, String format)
    {
        check(validate, value, name, format, null);
    }

    /**
     * 未来日かどうかチェック
     * 
     * ※今日を指定されたらエラー
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String format フォーマット
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access private
     */
    public static void isFuture(Validate validate, String value, String name, String format, String msgFull)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (compareTo(value, format) >= 0) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_PAST, name));
            }
        }
    }

    /**
     * 未来日かどうかチェック
     * 
     * ※今日を指定されたらエラー
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @return void
     * @access private
     */
    public static void isFuture(Validate validate, String value, String name)
    {
        isFuture(validate, value, name, DateUtils.FMT_DATE, null);
    }

    /**
     * 未来日かどうかチェック
     * 
     * ※今日を指定されてもエラーにしない
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String format フォーマット
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void isFutureAllowToday(Validate validate, String value, String name, String format, String msgFull)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (compareTo(value, format) > 0) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_PAST, name));
            }
        }
    }

    /**
     * 未来日かどうかチェック
     * 
     * ※今日を指定されてもエラーにしない
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @return void
     * @access public
     */
    public static void isFutureAllowToday(Validate validate, String value, String name)
    {
        isFutureAllowToday(validate, value, name, DateUtils.FMT_DATE, null);
    }

    /**
     * 過去日かどうかチェック
     * 
     * ※今日を指定されたらエラー
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String format フォーマット
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void isPast(Validate validate, String value, String name, String format, String msgFull)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (compareTo(value, format) <= 0) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_FUTURE, name));
            }
        }
    }

    /**
     * 過去日かどうかチェック
     * 
     * ※今日を指定されたらエラー
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @return void
     * @access public
     */
    public static void isPast(Validate validate, String value, String name)
    {
        isPast(validate, value, name, DateUtils.FMT_DATE, null);
    }

    /**
     * 過去日かどうかチェック
     * 
     * ※今日を指定されてもエラーではない
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String format フォーマット
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void isPastAllowToday(Validate validate, String value, String name, String format, String msgFull)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (compareTo(value, format) < 0) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_FUTURE, name));
            }
        }
    }

    /**
     * 過去日かどうかチェック
     * 
     * ※今日を指定されてもエラーではない
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @return void
     * @access public
     */
    public static void isPastAllowToday(Validate validate, String value, String name)
    {
        isPastAllowToday(validate, value, name, DateUtils.FMT_DATE, null);
    }

    /**
     * 今日と指定日を比べる
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @return Integer compareTo()の結果
     * @access private
     */
    private static Integer compareTo(String value, String format)
    {
        Integer ret = null;

        try {
            //今日のミリ秒を取得
            DateUtils dateUtils = new DateUtils();
            dateUtils.clearHour();
            Long boader = dateUtils.get().getTimeInMillis();

            //指定日のミリ秒を取得
            Long date = new DateUtils(value, format).get().getTimeInMillis();

            //比較
            ret = boader.compareTo(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
