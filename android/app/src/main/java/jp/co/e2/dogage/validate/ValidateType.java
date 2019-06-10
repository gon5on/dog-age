package jp.co.e2.dogage.validate;

/**
 * 形式バリデーションクラス
 *
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateType {
    public static final String ERROR_MSG_HALF_WEIGHT_NUMERIC = "%sは半角数字で入力してください。";
    public static final String ERROR_MSG_HALF_WEIGHT_ALPHABET = "%sは半角英字で入力してください。";
    public static final String ERROR_MSG_HALF_WEIGHT_ALPHANUMERIC = "%sは半角英数字で入力してください。";
    public static final String ERROR_MSG_HIRAGANA = "%sは全角ひらがなで入力してください。";
    public static final String ERROR_MSG_KATAKANA = "%sは全角カタカナで入力してください。";

    public static final String ERROR_MSG_EMAIL = "%sを正しい形式で入力してください。";
    public static final String ERROR_MSG_URL = "%s正しい形式で入力してください。";
    public static final String ERROR_MSG_HALF_WEIGHT_CHAR = "%sを半角文字で入力してください。";

    public static final String MATCH_NUMBER = "^[0-9]+$";
    public static final String MATCH_ALPHABET = "^[a-zA-Z]+$";
    public static final String MATCH_ALPHANUMERIC = "^[a-zA-Z0-9]+$";
    public static final String MATCH_HIRAGANA = "^[ぁ-ゞー～ 　]+$";
    public static final String MATCH_KATAKANA = "^[ァ-ヶー～ 　]+$";

    public static final String MATCH_EMAIL = "([a-zA-Z0-9][a-zA-Z0-9_.+\\-]*)@(([a-zA-Z0-9][a-zA-Z0-9_\\-]*\\.)+[a-zA-Z]{2,6})";
    public static final String MATCH_URL = "^(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)$";

    /**
     * すべて半角数字かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void isHalfWeightNumeric(ValidateHelper validate, String value, String name, String msgFull) {
        match(validate, value, name, msgFull, MATCH_NUMBER, ERROR_MSG_HALF_WEIGHT_NUMERIC);
    }

    /**
     * すべて半角数字かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isHalfWeightNumeric(ValidateHelper validate, String value, String name) {
        match(validate, value, name, null, MATCH_NUMBER, ERROR_MSG_HALF_WEIGHT_NUMERIC);
    }

    /**
     * すべて半角英字かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void isHalfWeightAlphabet(ValidateHelper validate, String value, String name, String msgFull) {
        match(validate, value, name, msgFull, MATCH_ALPHABET, ERROR_MSG_HALF_WEIGHT_ALPHABET);
    }

    /**
     * すべて半角英字かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isHalfWeightAlphabet(ValidateHelper validate, String value, String name) {
        match(validate, value, name, null, MATCH_ALPHABET, ERROR_MSG_HALF_WEIGHT_ALPHABET);
    }

    /**
     * すべて半角英数字かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void isHalfWeightAlphanumeric(ValidateHelper validate, String value, String name, String msgFull) {
        match(validate, value, name, msgFull, MATCH_ALPHANUMERIC, ERROR_MSG_HALF_WEIGHT_ALPHANUMERIC);
    }

    /**
     * すべて半角英数字かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isHalfWeightAlphanumeric(ValidateHelper validate, String value, String name) {
        match(validate, value, name, null, MATCH_ALPHANUMERIC, ERROR_MSG_HALF_WEIGHT_ALPHANUMERIC);
    }

    /**
     * すべてひらがなかどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void isHiragana(ValidateHelper validate, String value, String name, String msgFull) {
        match(validate, value, name, msgFull, MATCH_HIRAGANA, ERROR_MSG_HIRAGANA);
    }

    /**
     * すべてひらがなかどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isHiragana(ValidateHelper validate, String value, String name) {
        match(validate, value, name, null, MATCH_HIRAGANA, ERROR_MSG_HIRAGANA);
    }

    /**
     * すべてカタカナかどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void isKatakana(ValidateHelper validate, String value, String name, String msgFull) {
        match(validate, value, name, msgFull, MATCH_KATAKANA, ERROR_MSG_KATAKANA);
    }

    /**
     * すべてカタカナかどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isKatakana(ValidateHelper validate, String value, String name) {
        match(validate, value, name, null, MATCH_KATAKANA, ERROR_MSG_KATAKANA);
    }

    /**
     * メールの形式かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void isEmail(ValidateHelper validate, String value, String name, String msgFull) {
        match(validate, value, name, msgFull, MATCH_EMAIL, ERROR_MSG_EMAIL);
    }

    /**
     * メールの形式かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isEmail(ValidateHelper validate, String value, String name) {
        match(validate, value, name, null, MATCH_EMAIL, ERROR_MSG_EMAIL);
    }

    /**
     * URLの形式かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void isUrl(ValidateHelper validate, String value, String name, String msgFull) {
        match(validate, value, name, msgFull, MATCH_URL, ERROR_MSG_URL);
    }

    /**
     * URLの形式かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isUrl(ValidateHelper validate, String value, String name) {
        match(validate, value, name, null, MATCH_URL, ERROR_MSG_URL);
    }

    /**
     * すべて半角文字かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     */
    public static void isHalfWeightChar(ValidateHelper validate, String value, String name) {
        isHalfWeightChar(validate, value, name, null);
    }

    /**
     * すべて半角文字かどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     */
    public static void isHalfWeightChar(ValidateHelper validate, String value, String name, String msgFull) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        char[] chars = value.toCharArray();

        for (char c : chars) {
            //半角
            if ((c <= '\u007e') ||                      // 英数字
                    (c == '\u00a5') ||                  // \記号
                    (c == '\u203e') ||                  // ~記号
                    (c >= '\uff61' && c <= '\uff9f')    // 半角カナ
                    ) {
                //何もしない
            }
            //全角
            else {
                if (msgFull != null) {
                    validate.error(name, msgFull);
                } else {
                    validate.error(name, String.format(ERROR_MSG_HALF_WEIGHT_CHAR, name));
                }
            }
        }
    }

    /**
     * マッチするかどうか
     *
     * @param validate バリデートクラス
     * @param value    値
     * @param name     変数名
     * @param msgFull  エラーメッセージ全文
     * @param pattern  正規表現パターン
     * @param msg      デフォルトエラーメッセージ
     */
    private static void match(ValidateHelper validate, String value, String name, String msgFull, String pattern, String msg) {
        if (!validate.getResult(name)) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (!value.matches(pattern)) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(msg, name));
            }
        }
    }
}
