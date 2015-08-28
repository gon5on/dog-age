package jp.co.e2.dogage.validate;

/**
 * 必須バリデーションクラス
 *
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateRequire {
    public static final String ERROR_MSG_REQUIRE = "%sを入力してください。";
    public static final String ERROR_MSG_REQUIRE_SELECT = "%sを選択してください。";

    /**
     * String型必須チェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, String value, String name, String msgFull) {
        if (!validate.getResult(value)) {
            return;
        }

        if (value == null || value.length() == 0) {
            setErrorMsg(validate, name, msgFull);
        }
    }

    /**
     * String型必須チェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, String value, String name) {
        check(validate, value, name, null);
    }

    /**
     * Int型必須チェック
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

        if (value == null || String.valueOf(value).length() == 0) {
            setErrorMsg(validate, name, msgFull);
        }
    }

    /**
     * Int型必須チェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, Integer value, String name) {
        check(validate, value, name, null);
    }

    /**
     * Double型必須チェック
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
        if (value == null || String.valueOf(value).length() == 0) {
            setErrorMsg(validate, name, msgFull);
        }
    }

    /**
     * Double型必須チェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, Double value, String name) {
        check(validate, value, name, null);
    }

    /**
     * Float型必須チェック
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
        if (value == null || String.valueOf(value).length() == 0) {
            setErrorMsg(validate, name, msgFull);
        }
    }

    /**
     * Float型必須チェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, Float value, String name) {
        check(validate, value, name, null);
    }

    /**
     * セレクトボックス必須チェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void check(ValidateHelper validate, boolean[] value, String name, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }

        Boolean flg = false;

        for (int i = 0; i < value.length; i++) {
            if (value[i]) {
                flg = true;
                break;
            }
        }

        if (!flg) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_REQUIRE, name));
            }
        }
    }

    /**
     * セレクトボックス必須チェック
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void check(ValidateHelper validate, boolean[] value, String name) {
        check(validate, value, name, null);
    }

    /**
     * エラーメッセージセット
     *
     * @param validate バリデートクラス
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    private static void setErrorMsg(ValidateHelper validate, String name, String msgFull) {
        if (msgFull != null) {
            validate.error(name, msgFull);
        } else {
            validate.error(name, String.format(ERROR_MSG_REQUIRE, name));
        }
    }
}
