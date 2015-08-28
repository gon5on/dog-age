package jp.co.e2.dogage.validate;

/**
 * 文字長バリデーションクラス
 *
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * length … 文字長
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateLength {
    public static final String ERROR_MSG_LENGTH_MIN = "%sは%s文字以上で入力してください。";
    public static final String ERROR_MSG_LENGTH_MAX = "%sは%s文字以下で入力してください。";

    /**
     * 文字長MAXチェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   文字長
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Integer length, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (value.length() > length) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_LENGTH_MAX, name, length));
            }
        }
    }

    /**
     * 文字長MAXチェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   文字長
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Integer length) {
        maxCheck(validate, value, name, length, null);
    }

    /**
     * 文字長MAXチェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最長文字長
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Integer length, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        maxCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MAXチェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最長文字長
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Integer length) {
        maxCheck(validate, value, name, length, null);
    }

    /**
     * 文字長MAXチェック（Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最長文字長
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Integer length, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        maxCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MAXチェック（Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最長文字長
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Integer length) {
        maxCheck(validate, value, name, length, null);
    }

    /**
     * 文字長MAXチェック（Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最長文字長
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Float value, String name, Integer length, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        maxCheck(validate, String.valueOf(value), name, length, null);
    }

    /**
     * 文字長MAXチェック（Float）
     *
     * @param validate バリデートクラス
     * @param value    値c
     * @param name     変数名
     * @param length   最長文字長
     */
    public static void maxCheck(ValidateHelper validate, Float value, String name, Integer length) {
        maxCheck(validate, value, name, length, null);
    }

    /**
     * 文字長MINチェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最短文字長
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Integer length, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (value.length() < length) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_LENGTH_MIN, name, length));
            }
        }
    }

    /**
     * 文字長MINチェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最短文字長
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Integer length) {
        minCheck(validate, value, name, length, null);
    }

    /**
     * 文字長MINチェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最短文字長
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Integer length, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        minCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MINチェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最短文字長
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Integer length) {
        minCheck(validate, value, name, length, null);
    }

    /**
     * 文字長MINチェック（Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最短文字長
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Integer length, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        minCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MINチェック（Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最短文字長
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Integer length) {
        minCheck(validate, value, name, length, null);
    }

    /**
     * 文字長MINチェック（Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最短文字長
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Float value, String name, Integer length, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        minCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MINチェック（Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param length   最短文字長
     */
    public static void minCheck(ValidateHelper validate, Float value, String name, Integer length) {
        minCheck(validate, value, name, length, null);
    }
}
