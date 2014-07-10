package com.tmrnk.gongon.dogage.validate;

/**
 * 少数バリデーションクラス
 * 
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 * point … 小数点第何位を指定したい場合に指定、指定しない場合はnull
 */
public class ValidateDecimal
{
    public static final String ERROR_MSG_DOUBLE = "%sは少数で入力してください。";
    public static final String ERROR_MSG_DOUBLE_POINT = "%sは小数点第%s位までで入力してください。";

    public static final String MATCH_DOUBLE = "^([0-9]\\d*|0)(\\.\\d+)?$";
    public static final String MATCH_DOUBLE_POINT = "^([0-9]\\d*|0)(\\.\\d{POINT})?$";

    /**
     * 少数形式チェック（String）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Validate validate, String value, String name, String msgFull)
    {
        checkDecimal(validate, value, name, msgFull, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（String）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @return void
     * @access public
     */
    public void check(Validate validate, String value, String name)
    {
        checkDecimal(validate, value, name, null, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Int）
     * 
     * @param Validate validate バリデートクラス
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Validate validate, Integer value, String name, String msgFull)
    {
        checkDecimal(validate, String.valueOf(value), name, msgFull, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Int）
     * 
     * @param Validate validate バリデートクラス
     * @param Integer value 値
     * @param String name 変数名
     * @return void
     * @access public
     */
    public void check(Validate validate, Integer value, String name)
    {
        checkDecimal(validate, String.valueOf(value), name, null, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Float）
     * 
     * @param Validate validate バリデートクラス
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Validate validate, Float value, String name, String msgFull)
    {
        checkDecimal(validate, String.valueOf(value), name, msgFull, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Float）
     * 
     * @param Validate validate バリデートクラス
     * @param Float value 値
     * @param String name 変数名
     * @return void
     * @access public
     */
    public void check(Validate validate, Float value, String name)
    {
        checkDecimal(validate, String.valueOf(value), name, null, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Double）
     * 
     * @param Validate validate バリデートクラス
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Validate validate, Double value, String name, String msgFull)
    {
        checkDecimal(validate, String.valueOf(value), name, msgFull, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 少数形式チェック（Double）
     * 
     * @param Validate validate バリデートクラス
     * @param Double value 値
     * @param String name 変数名
     * @return void
     * @access public
     */
    public void check(Validate validate, Double value, String name)
    {
        checkDecimal(validate, String.valueOf(value), name, null, MATCH_DOUBLE, ERROR_MSG_DOUBLE);
    }

    /**
     * 正規表現でチェック
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String pattern 正規表現パターン
     * @param String msg デフォルトエラーメッセージ
     * @return void
     * @access private
     */
    private void checkDecimal(Validate validate, String value, String name, String msgFull, String pattern, String msg)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (value.matches(pattern) == false) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(msg, name));
            }
        }
    }

    /**
     * 少数形式チェック（String）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Validate validate, String value, String name, Integer point, String msgFull)
    {
        checkCecimalPoint(validate, value, name, point, msgFull, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式チェック（String）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @return void
     * @access public
     */
    public void check(Validate validate, String value, String name, Integer point)
    {
        checkCecimalPoint(validate, value, name, point, null, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式チェック（Int）
     * 
     * @param Validate validate バリデートクラス
     * @param Int value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Validate validate, Integer value, String name, Integer point, String msgFull)
    {
        checkCecimalPoint(validate, String.valueOf(value), name, point, msgFull, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式チェック（Int）
     * 
     * @param Validate validate バリデートクラス
     * @param Int value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @return void
     * @access public
     */
    public void check(Validate validate, Integer value, String name, Integer point)
    {
        checkCecimalPoint(validate, String.valueOf(value), name, point, null, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式チェック（Float）
     * 
     * @param Validate validate バリデートクラス
     * @param Float value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Validate validate, Float value, String name, Integer point, String msgFull)
    {
        checkCecimalPoint(validate, String.valueOf(value), name, point, msgFull, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式チェック（Float）
     * 
     * @param Validate validate バリデートクラス
     * @param Float value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @return void
     * @access public
     */
    public void check(Validate validate, Float value, String name, Integer point)
    {
        checkCecimalPoint(validate, String.valueOf(value), name, point, null, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式チェック（Double）
     * 
     * @param Validate validate バリデートクラス
     * @param Double value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Validate validate, Double value, String name, Integer point, String msgFull)
    {
        checkCecimalPoint(validate, String.valueOf(value), name, point, msgFull, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 少数形式チェック（Double）
     * 
     * @param Validate validate バリデートクラス
     * @param Double value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @return void
     * @access public
     */
    public void check(Validate validate, Double value, String name, Integer point)
    {
        checkCecimalPoint(validate, String.valueOf(value), name, point, null, MATCH_DOUBLE_POINT, ERROR_MSG_DOUBLE_POINT);
    }

    /**
     * 正規表現でチェック
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Integer point 小数点第何位
     * @param String msgFull エラーメッセージ全文
     * @param String pattern 正規表現パターン
     * @param String msg デフォルトエラーメッセージ
     * @return void
     * @access private
     */
    private void checkCecimalPoint(Validate validate, String value, String name, Integer point, String msgFull, String pattern, String msg)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        //正規表現の小数点部分を置換しておく
        pattern = pattern.replaceAll("POINT", String.valueOf(point));

        if (value.matches(pattern) == false) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(msg, name, point));
            }
        }
    }
}
