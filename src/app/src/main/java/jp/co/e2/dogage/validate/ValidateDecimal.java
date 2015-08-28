package jp.co.e2.dogage.validate;

/**
 * 少数バリデーションクラス
 *
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 * point … 小数点第何位を指定したい場合に指定、指定しない場合はnull
 */
public class ValidateDecimal {
    public static final String ERROR_MSG_DOUBLE = "%sは少数で入力してください。";
    public static final String ERROR_MSG_DOUBLE_POINT = "%sは小数点第%s位までで入力してください。";

    public static final String MATCH_DOUBLE = "^([0-9]\\d*|0)(\\.\\d+)?$";
    public static final String MATCH_DOUBLE_POINT = "^([0-9]\\d*|0)(\\.\\d{0,POINT})?$";

    /**
     * 少数形式チェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, String value, String name, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        check(validate, value, name, msgFull, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, String value, String name) {
        check(validate, value, name, null);
    }

    /**
     * 少数形式チェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, Integer value, String name, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        check(validate, String.valueOf(value), name, msgFull, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, Integer value, String name) {
        check(validate, value, name, null);
    }

    /**
     * 少数形式チェック（Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, Float value, String name, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        check(validate, String.valueOf(value), name, msgFull, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, Float value, String name) {
        check(validate, value, name, null);
    }

    /**
     * 少数形式チェック（Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, Double value, String name, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        check(validate, String.valueOf(value), name, msgFull, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, Double value, String name) {
        check(validate, value, name, null);
    }

    /**
     * 正規表現でチェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     * @param pattern  正規表現パターン
     * @param msg      デフォルトエラーメッセージ
     */
    private static void check(ValidateHelper validate, String value, String name, String msgFull, String pattern, String msg) {
        if (!value.matches(pattern)) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(msg, name));
            }
        }
    }

    /**
     * 少数形式と桁数チェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     * @param msgFull  エラーメッセージ全文
     */
    public static void checkPoint(ValidateHelper validate, String value, String name, Integer point, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        checkPoint(validate, value, name, point, msgFull, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式と桁数チェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     */
    public static void checkPoint(ValidateHelper validate, String value, String name, Integer point) {
        checkPoint(validate, value, name, point, null);
    }

    /**
     * 少数形式と桁数チェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     * @param msgFull  エラーメッセージ全文
     */
    public static void checkPoint(ValidateHelper validate, Integer value, String name, Integer point, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        checkPoint(validate, String.valueOf(value), name, point, msgFull, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式と桁数チェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     */
    public static void checkPoint(ValidateHelper validate, Integer value, String name, Integer point) {
        checkPoint(validate, value, name, point, null);
    }

    /**
     * 少数形式と桁数チェック（Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     * @param msgFull  エラーメッセージ全文
     */
    public static void checkPoint(ValidateHelper validate, Float value, String name, Integer point, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        checkPoint(validate, String.valueOf(value), name, point, msgFull, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式と桁数チェック（Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     */
    public static void checkPoint(ValidateHelper validate, Float value, String name, Integer point) {
        checkPoint(validate, value, name, point, null);
    }

    /**
     * 少数形式と桁数チェック（Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     * @param msgFull  エラーメッセージ全文
     */
    public static void checkPoint(ValidateHelper validate, Double value, String name, Integer point, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        checkPoint(validate, String.valueOf(value), name, point, msgFull, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式と桁数チェック（Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     */
    public static void checkPoint(ValidateHelper validate, Double value, String name, Integer point) {
        checkPoint(validate, value, name, point, null);
    }

    /**
     * 正規表現でチェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param point    小数点第何位
     * @param msgFull  エラーメッセージ全文
     * @param pattern  正規表現パターン
     * @param msg      デフォルトエラーメッセージ
     */
    private static void checkPoint(ValidateHelper validate, String value, String name, Integer point, String msgFull, String pattern, String msg) {
        //正規表現の小数点部分を置換しておく
        pattern = pattern.replaceAll("POINT", String.valueOf(point));

        if (!value.matches(pattern)) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(msg, name, point));
            }
        }
    }
}
