package jp.co.e2.dogage.validate;

/**
 * 整数バリデーションクラス
 *
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateInt {
    public static final String ERROR_MSG_INT = "%sは整数で入力してください。";

    public static final String MATCH_INT = "^[+-]?[0-9]+$";

    /**
     * 整数チェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, String value, String name, String msgFull) {
        if (!validate.getResult()) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (!value.matches(MATCH_INT)) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_INT, name));
            }
        }
    }

    /**
     * 整数チェック（String）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, String value, String name) {
        check(validate, value, name, null);
    }

    /**
     * 整数チェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, Integer value, String name, String msgFull) {
        if (!validate.getResult()) {
            return;
        }
        if (value == null || String.valueOf(value).length() == 0) {
            return;
        }

        check(validate, String.valueOf(value), name, msgFull);
    }

    /**
     * 整数チェック（Int）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, Integer value, String name) {
        check(validate, value, name, null);
    }
}
