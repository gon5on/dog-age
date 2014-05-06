package com.tmrnk.gongon.dogage.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.tmrnk.gongon.dogage.common.DateUtils;

/**
 * 日付系バリデーションクラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 * limit … MAX・MIN値
 */
public class ValidateDate
{
    public static final String ERROR_MSG_FORMAT = "%sは正しい形式ではありません。";
    public static final String ERROR_MSG_FUTURE = "%sに未来の日付は指定できません。";
    public static final String ERROR_MSG_PAST = "%sに過去の日付は指定できません。";

    private Validate mValidate;              //バリデーションクラス

    /**
     * コンストラクタ
     * 
     * @param Validate validate バリデーションクラス
     */
    public ValidateDate(Validate validate)
    {
        mValidate = validate;
    }

    /**
     * 正しい日付かどうかチェック
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String format フォーマット
     * @return void
     * @access public
     */
    public void check(String value, String name, String msgFull, String format)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            sdf.parse(value);

        } catch (ParseException e) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_PAST, name));
            }
            e.printStackTrace();
        }
    }

    /**
     * 未来日かどうかチェック
     * 
     * ※今日を指定されたらエラー
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @throws ParseException
     * @access private
     */
    public void isFuture(String value, String name, String msgFull) throws ParseException
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (compareTo(value) >= 0) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_PAST, name));
            }
        }
    }

    /**
     * 未来日かどうかチェック
     * 
     * ※今日を指定されてもエラーにしない
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @throws ParseException
     * @access public
     */
    public void isFutureAllowToday(String value, String name, String msgFull) throws ParseException
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (compareTo(value) > 0) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_PAST, name));
            }
        }
    }

    /**
     * 過去日かどうかチェック
     * 
     * ※今日を指定されたらエラー
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @throws ParseException
     * @access public
     */
    public void isPast(String value, String name, String msgFull) throws ParseException
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (compareTo(value) <= 0) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_FUTURE, name));
            }
        }
    }

    /**
     * 過去日かどうかチェック
     * 
     * ※今日を指定されてもエラーではない
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @throws ParseException
     * @access public
     */
    public void isPastAllowToday(String value, String name, String msgFull) throws ParseException
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (compareTo(value) < 0) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_FUTURE, name));
            }
        }
    }

    /**
     * 今日と指定日を比べる
     * 
     * @param String value 値
     * @return Integer compareTo()の結果
     * @throws ParseException
     * @access private
     */
    private Integer compareTo(String value) throws ParseException
    {
        //今日のミリ秒を取得
        DateUtils dateUtils = new DateUtils();
        dateUtils.clearHour();
        Long boader = dateUtils.get().getTimeInMillis();

        //指定日のミリ秒を取得
        Long date = new DateUtils(value, DateUtils.FMT_DATE).get().getTimeInMillis();

        return boader.compareTo(date);
    }
}
