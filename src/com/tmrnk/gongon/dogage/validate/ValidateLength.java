package com.tmrnk.gongon.dogage.validate;

/**
 * 文字長チェッククラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 * length … 文字長
 */
public class ValidateLength
{
    public static final String ERROR_MSG_LENGTH_MIN = "%sは%s文字以上で入力してください。";
    public static final String ERROR_MSG_LENGTH_MAX = "%sは%s文字以下で入力してください。";

    private Validate mValidate;              //バリデーションクラス

    /**
     * コンストラクタ
     * 
     * @param Validate validate バリデーションクラス
     */
    public ValidateLength(Validate validate)
    {
        mValidate = validate;
    }

    /**
     * 文字長MAXチェック（String）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer length 文字長
     * @return void
     * @access public
     */
    public void maxCheck(String value, String name, String msgFull, Integer length)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (value.length() > length) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_LENGTH_MAX, name, length));
            }
        }
    }

    /**
     * 文字長MAXチェック（Int）
     * 
     * @param Int value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer length 最長文字長
     * @return void
     * @access public
     */
    public void maxCheck(Integer value, String name, String msgFull, Integer length)
    {
        maxCheck(String.valueOf(value), name, msgFull, length);
    }

    /**
     * 文字長MAXチェック（Double）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer length 最長文字長
     * @return void
     * @access public
     */
    public void maxCheck(Double value, String name, String msgFull, Integer length)
    {
        maxCheck(String.valueOf(value), name, msgFull, length);
    }

    /**
     * 文字長MAXチェック（float）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer length 最長文字長
     * @return void
     * @access public
     */
    public void maxCheck(float value, String name, String msgFull, Integer length)
    {
        maxCheck(String.valueOf(value), name, msgFull, length);
    }

    /**
     * 文字長MINチェック（String）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer length 最短文字長
     * @return void
     * @access public
     */
    public void minCheck(String value, String name, String msgFull, Integer length)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (value.length() < length) {
            if (msgFull != null) {
                mValidate.error(msgFull);
            } else {
                mValidate.error(String.format(ERROR_MSG_LENGTH_MIN, name, length));
            }
        }
    }

    /**
     * 文字長MINチェック（Int）
     * 
     * @param Int value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer length 最短文字長
     * @return void
     * @access public
     */
    public void minCheck(Integer value, String name, String msgFull, Integer length)
    {
        minCheck(String.valueOf(value), name, msgFull, length);
    }

    /**
     * 文字長MINチェック（Double）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer length 最短文字長
     * @return void
     * @access public
     */
    public void minCheck(Double value, String name, String msgFull, Integer length)
    {
        minCheck(String.valueOf(value), name, msgFull, length);
    }

    /**
     * 文字長MINチェック（Float）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer length 最短文字長
     * @return void
     * @access public
     */
    public void minCheck(Float value, String name, String msgFull, Integer length)
    {
        minCheck(String.valueOf(value), name, msgFull, length);
    }
}
