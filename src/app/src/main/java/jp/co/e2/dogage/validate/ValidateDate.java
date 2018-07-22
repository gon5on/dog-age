package jp.co.e2.dogage.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.e2.dogage.common.DateHelper;

/**
 * 日付系バリデーションクラス
 */
public class ValidateDate {
    private static final String ERROR_MSG_FORMAT = "%sは正しい形式ではありません。";
    private static final String ERROR_MSG_FUTURE = "%sに未来の日付は指定できません。";
    private static final String ERROR_MSG_PAST = "%sに過去の日付は指定できません。";

    private static final Integer OLDEST_DATE = 19000101;
    private static final Integer NEWEST_DATE = 22001231;

    /**
     * 正しい日付かどうかチェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param format   フォーマット
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, String value, String name, String format, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        try {
            //日付形式に変換できるかどうか
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            sdf.setLenient(false);
            Date tmp = sdf.parse(value);

            SimpleDateFormat sdf2 = new SimpleDateFormat(DateHelper.FMT_DATE_NO_UNIT, Locale.getDefault());
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
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param format   フォーマット
     */
    public static void check(ValidateHelper validate, String value, String name, String format) {
        check(validate, value, name, format, null);
    }

    /**
     * 未来日かどうかチェック
     *
     * ※今日を指定されたらエラー
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param format   フォーマット
     * @param msgFull  エラーメッセージ全文
     */
    public static void isFuture(ValidateHelper validate, String value, String name, String format, String msgFull) {
        if (!validate.getResult(name)) {
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
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isFuture(ValidateHelper validate, String value, String name) {
        isFuture(validate, value, name, DateHelper.FMT_DATE, null);
    }

    /**
     * 未来日かどうかチェック
     *
     * ※今日を指定されてもエラーにしない
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param format   フォーマット
     * @param msgFull  エラーメッセージ全文
     */
    public static void isFutureAllowToday(ValidateHelper validate, String value, String name, String format, String msgFull) {
        if (!validate.getResult(name)) {
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
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isFutureAllowToday(ValidateHelper validate, String value, String name) {
        isFutureAllowToday(validate, value, name, DateHelper.FMT_DATE, null);
    }

    /**
     * 過去日かどうかチェック
     *
     * ※今日を指定されたらエラー
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param format   フォーマット
     * @param msgFull  エラーメッセージ全文
     */
    public static void isPast(ValidateHelper validate, String value, String name, String format, String msgFull) {
        if (!validate.getResult(name)) {
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
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isPast(ValidateHelper validate, String value, String name) {
        isPast(validate, value, name, DateHelper.FMT_DATE, null);
    }

    /**
     * 過去日かどうかチェック
     *
     * ※今日を指定されてもエラーではない
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param format   フォーマット
     * @param msgFull  エラーメッセージ全文
     */
    public static void isPastAllowToday(ValidateHelper validate, String value, String name, String format, String msgFull) {
        if (!validate.getResult(name) ) {
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
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isPastAllowToday(ValidateHelper validate, String value, String name) {
        isPastAllowToday(validate, value, name, DateHelper.FMT_DATE, null);
    }

    /**
     * 今日と指定日を比べる
     *
     * @param value  値
     * @param format フォーマット
     * @return Integer compareTo()の結果
     */
    private static Integer compareTo(String value, String format) {
        Integer ret = null;

        try {
            //今日のミリ秒を取得
            DateHelper dateUtils = new DateHelper();
            dateUtils.clearHour();
            Long border = dateUtils.getMilliSecond();

            //指定日のミリ秒を取得
            Long date = new DateHelper(value, format).getMilliSecond();

            //比較
            ret = border.compareTo(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
