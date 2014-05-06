package com.tmrnk.gongon.dogage.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 整数チェッククラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateInt
{
    public static final String ERROR_MSG_INT = "%sは整数で入力してください。";

    private Validate mValidate;              //バリデーションクラス

    /**
     * コンストラクタ
     * 
     * @param Validate validate バリデーションクラス
     */
    public ValidateInt(Validate validate)
    {
        mValidate = validate;
    }

    /**
     * 整数チェック（String）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msg_full エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(String value, String name, String msgFull)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        Pattern pattern = java.util.regex.Pattern.compile("^[+-]?[0-9]+$");
        Matcher matcher = pattern.matcher(value);

        if (matcher.matches() == false) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_INT, name));
            }
        }
    }

    /**
     * 整数チェック（Int）
     * 
     * @param Int value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Integer value, String name, String msgFull)
    {
        check(String.valueOf(value), name, msgFull);
    }
}
