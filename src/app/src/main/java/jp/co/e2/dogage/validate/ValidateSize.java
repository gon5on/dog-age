package jp.co.e2.dogage.validate;

/**
 * 最大・最小系バリデーションクラス
 *
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * limit … MAX・MIN値
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateSize {
    public static final String ERROR_MSG_SIZE_MIN = "%sは%s以上で入力してください。";
    public static final String ERROR_MSG_SIZE_MAX = "%sは%s以下で入力してください。";

    /**
     * MAXチェック（値→Integer、MAX→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Integer limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (Double.valueOf(limit) < Double.valueOf(value)) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Integer、MAX→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Integer limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MAXチェック（値→Integer、MAX→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Float limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (Double.valueOf(limit) < Double.valueOf(value)) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Integer、MAX→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Float limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MAXチェック（値→Integer、MAX→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Double limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (limit < Double.valueOf(value)) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Integer、MAX→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Double limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MAXチェック（値→String、MAX→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Integer limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (Double.valueOf(limit) < Double.valueOf(value)) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→String、MAX→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Integer limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MAXチェック（値→String、MAX→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Float limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (Double.valueOf(limit) < Double.valueOf(value)) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→String、MAX→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Float limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MAXチェック（値→String、MAX→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Double limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (limit < Double.valueOf(value)) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→String、MAX→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Double limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MAXチェック（値→Double、MAX→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Integer limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (Double.valueOf(limit) < value) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Double、MAX→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Integer limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MAXチェック（値→Double、MAX→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Float limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (Double.valueOf(limit) < value) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Double、MAX→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Float limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MAXチェック（値→Double、MAX→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Double limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (limit < value) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Double、MAX→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MAX文字数
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Double limit) {
        maxCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→Integer、MIN→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Integer limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (Double.valueOf(value) < Double.valueOf(limit)) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Integer、MIN→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Integer limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→Integer、MIN→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Float limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (Double.valueOf(value) < Double.valueOf(limit)) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Integer、MIN→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Float limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→Integer、MIN→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Double limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (Double.valueOf(value) < limit) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Integer、MIN→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Double limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→String、MIN→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Integer limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (Double.valueOf(value) < Double.valueOf(limit)) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→String、MIN→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Integer limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→String、MIN→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Float limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (Double.valueOf(value) < Double.valueOf(limit)) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→String、MIN→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Float limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→String、MIN→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Double limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (Double.valueOf(value) < limit) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→String、MIN→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Double limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→Double、MIN→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Integer limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (value < Double.valueOf(limit)) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Double、MIN→Integer）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Integer limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→Double、MIN→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Float limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (value < Double.valueOf(limit)) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Double、MIN→Float）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Float limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MINチェック（値→Double、MIN→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     * @param msgFull  エラーメッセージ全文
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Double limit, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null) {
            return;
        }

        if (value < limit) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Double、MIN→Double）
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param limit    MIN文字数
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Double limit) {
        minCheck(validate, value, name, limit, null);
    }

    /**
     * MAXエラーメッセージセット
     *
     * @param validate バリデートクラス
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     * @param limit    何文字
     */
    private static void setErrorMsgMax(ValidateHelper validate, String name, String msgFull, String limit) {
        if (msgFull != null) {
            validate.error(name, msgFull);
        } else {
            validate.error(name, String.format(ERROR_MSG_SIZE_MAX, name, limit));
        }
    }

    /**
     * MINエラーメッセージセット
     *
     * @param validate バリデートクラス
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     * @param limit    何文字
     */
    private static void setErrorMsgMin(ValidateHelper validate, String name, String msgFull, String limit) {
        if (msgFull != null) {
            validate.error(name, msgFull);
        } else {
            validate.error(name, String.format(ERROR_MSG_SIZE_MIN, name, limit));
        }
    }
}
