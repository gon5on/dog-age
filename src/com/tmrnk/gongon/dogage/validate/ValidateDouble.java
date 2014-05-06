package com.tmrnk.gongon.dogage.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * int系バリデーションクラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 * point … 小数点第何位を指定したい場合に指定、指定しない場合はnull
 */
public class ValidateDouble
{
    public static final String ERROR_MSG_DOUBLE = "%sは少数で入力してください。";
    public static final String ERROR_MSG_DOUBLE_POINT = "%sは小数点第%s位までで入力してください。";

    private Validate mValidate;              //バリデーションクラス

    /**
     * コンストラクタ
     * 
     * @param Validate validate バリデーションクラス
     */
    public ValidateDouble(Validate validate)
    {
        mValidate = validate;
    }

    /**
     * 少数形式チェック（String）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(String value, String name, String msgFull)
    {
        String pattern = "^([0-9]\\d*|0)(\\.\\d+)?$";

        if (check(value, pattern) == false) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_DOUBLE, name));
            }
        }
    }

    /**
     * 少数形式チェック（Int）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Integer value, String name, String msgFull)
    {
        check(String.valueOf(value), name, msgFull);
    }

    /**
     * 少数形式チェック（Float）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Float value, String name, String msgFull)
    {
        check(String.valueOf(value), name, msgFull);
    }

    /**
     * 少数形式チェック（Double）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Double value, String name, String msgFull)
    {
        check(String.valueOf(value), name, msgFull);
    }

    /**
     * 少数形式チェック
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer point 小数点第何位
     * @return void
     * @access public
     */
    public void check(String value, String name, String msgFull, Integer point)
    {
        String pattern = "^([0-9]\\d*|0)(\\.\\d{" + point + "})?$";

        if (check(value, pattern) == false) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_DOUBLE_POINT, name, point));
            }
        }
    }

    /**
     * 少数形式チェック（Int）
     * 
     * @param Int value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer point 小数点第何位
     * @return void
     * @access public
     */
    public void check(Integer value, String name, String msgFull, Integer point)
    {
        check(String.valueOf(value), name, msgFull, point);
    }

    /**
     * 少数形式チェック（Float）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer point 小数点第何位
     * @return void
     * @access public
     */
    public void check(Float value, String name, String msgFull, Integer point)
    {
        check(String.valueOf(value), name, msgFull, point);
    }

    /**
     * 正規表現でチェック
     * 
     * @param String value 値
     * @param String patternStr パターン
     * @return Boolean バリデート結果
     * @access private
     */
    private Boolean check(String value, String patternStr)
    {
        if (mValidate.getValueResult() == false) {
            return true;
        }
        if (value == null || value.length() == 0) {
            return true;
        }

        Pattern pattern = java.util.regex.Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
