package com.tmrnk.gongon.dogage.validate;

/**
 * 必須チェッククラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateRequire
{
    public static final String ERROR_MSG_REQUIRE = "%sを入力してください。";

    private Validate mValidate;              //バリデーションクラス

    /**
     * コンストラクタ
     * 
     * @param Validate validate バリデーションクラス
     */
    public ValidateRequire(Validate validate)
    {
        mValidate = validate;
    }

    /**
     * String型必須チェック
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(String value, String name, String msgFull)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            setErrorMsg(name, msgFull);
        }
    }

    /**
     * Int型必須チェック
     * 
     * @param Int value 値
     * @param String name 変数名
     * @param String msg_full エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Integer value, String name, String msgFull)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            setErrorMsg(name, msgFull);
        }
    }

    /**
     * Double型必須チェック
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msg_full エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Double value, String name, String msgFull)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            setErrorMsg(name, msgFull);
        }
    }

    /**
     * Float型必須チェック
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(Float value, String name, String msgFull)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            setErrorMsg(name, msgFull);
        }
    }

    /**
     * セレクトボックス必須チェック
     * 
     * @param boolean[] value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public void check(boolean[] value, String name, String msgFull)
    {
        Boolean flg = false;

        for (int i = 0; i < value.length; i++) {
            if (value[i] == true) {
                flg = true;
                break;
            }
        }

        if (flg == false) {
            setErrorMsg(name, msgFull);
        }
    }

    /**
     * エラーメッセージセット
     * 
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access private
     */
    private void setErrorMsg(String name, String msgFull)
    {
        if (msgFull != null) {
            mValidate.error(msgFull);
        } else {
            mValidate.error(String.format(ERROR_MSG_REQUIRE, name));
        }
    }
}
