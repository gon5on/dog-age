package com.tmrnk.gongon.dogage.validate;

/**
 * 最大・最小系バリデーションクラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 * limit … MAX・MIN値
 */
public class ValidateSize
{
    public static final String ERROR_MSG_SIZE_MIN = "%sは%s以上で入力してください。";
    public static final String ERROR_MSG_SIZE_MAX = "%sは%s以下で入力してください。";

    private Validate mValidate;              //バリデーションクラス

    /**
     * コンストラクタ
     * 
     * @param Validate validate バリデーションクラス
     */
    public ValidateSize(Validate validate)
    {
        mValidate = validate;
    }

    /**
     * MAXチェック（値→String、MAX→String）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(String value, String name, String msgFull, String limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, limit);
        }
    }

    /**
     * MAXチェック（値→String、MAX→Integer）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(String value, String name, String msgFull, Integer limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→String、MAX→Float）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Float limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(String value, String name, String msgFull, Float limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→String、MAX→Double）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Double limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(String value, String name, String msgFull, Double limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }
        if (maxCheck(Double.valueOf(value), limit) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Integer、MAX→String）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Integer value, String name, String msgFull, String limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, limit);
        }
    }

    /**
     * MAXチェック（値→Integer、MAX→Integer）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Integer value, String name, String msgFull, Integer limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Integer、MAX→Float）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Float limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Integer value, String name, String msgFull, Float limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Integer、MAX→Double）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Double limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Integer value, String name, String msgFull, Double limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(Double.valueOf(value), limit) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Float、MAX→String）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Float value, String name, String msgFull, String limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, limit);
        }
    }

    /**
     * MAXチェック（値→Float、MAX→Integer）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Float value, String name, String msgFull, Integer limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Float、MAX→Float）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Float limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Float value, String name, String msgFull, Float limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Float、MAX→Double）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Double limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Float value, String name, String msgFull, Double limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(Double.valueOf(value), limit) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Double、MAX→String）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Double value, String name, String msgFull, String limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(value, Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, limit);
        }
    }

    /**
     * MAXチェック（値→Double、MAX→Integer）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Double value, String name, String msgFull, Integer limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(value, Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Double、MAX→Float）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Float limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Double value, String name, String msgFull, Float limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(value, Double.valueOf(limit)) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック（値→Double、MAX→Double）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Double limit MAX文字数
     * @return void
     * @access public
     */
    public void maxCheck(Double value, String name, String msgFull, Double limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (maxCheck(value, limit) == false) {
            setErrorMsgMax(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXチェック
     * 
     * @param Double value 値
     * @param Double limit MAX文字数
     * @return boolen バリデート結果
     * @access private
     */
    private Boolean maxCheck(Double value, Double limit)
    {
        return value <= limit;
    }

    /**
     * MINチェック（値→String、MIN→String）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(String value, String name, String msgFull, String limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, limit);
        }
    }

    /**
     * MINチェック（値→String、MIN→Integer）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(String value, String name, String msgFull, Integer limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→String、MIN→Float）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Float limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(String value, String name, String msgFull, Float limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→String、MIN→Double）
     * 
     * @param String value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Double limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(String value, String name, String msgFull, Double limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }
        if (minCheck(Double.valueOf(value), limit) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Integer、MIN→String）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Integer value, String name, String msgFull, String limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, limit);
        }
    }

    /**
     * MINチェック（値→Integer、MIN→Integer）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Integer value, String name, String msgFull, Integer limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Integer、MIN→Float）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Float limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Integer value, String name, String msgFull, Float limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Integer、MIN→Double）
     * 
     * @param Integer value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Double limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Integer value, String name, String msgFull, Double limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(Double.valueOf(value), limit) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Float、MIN→String）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Float value, String name, String msgFull, String limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, limit);
        }
    }

    /**
     * MINチェック（値→Float、MIN→Integer）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Float value, String name, String msgFull, Integer limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Float、MIN→Float）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Float limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Float value, String name, String msgFull, Float limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(Double.valueOf(value), Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Float、MIN→Double）
     * 
     * @param Float value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Double limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Float value, String name, String msgFull, Double limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(Double.valueOf(value), limit) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Double、MIN→String）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Double value, String name, String msgFull, String limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(value, Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, limit);
        }
    }

    /**
     * MINチェック（値→Double、MIN→Integer）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Integer limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Double value, String name, String msgFull, Integer limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(value, Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Double、MIN→Float）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Float limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Double value, String name, String msgFull, Float limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(value, Double.valueOf(limit)) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→Double、MIN→Double）
     * 
     * @param Double value 値
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param Double limit MIN文字数
     * @return void
     * @access public
     */
    public void minCheck(Double value, String name, String msgFull, Double limit)
    {
        if (mValidate.getValueResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }
        if (minCheck(value, limit) == false) {
            setErrorMsgMin(name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック
     * 
     * @param Double value 値
     * @param Double limit MIN文字数
     * @return boolen バリデート結果
     * @access private
     */
    private Boolean minCheck(Double value, Double limit)
    {
        return limit <= value;
    }

    /**
     * MAXエラーメッセージセット
     * 
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit 何文字
     * @return void
     * @access private
     */
    private void setErrorMsgMax(String name, String msgFull, String limit)
    {
        if (msgFull != null) {
            mValidate.error(msgFull);
        } else {
            mValidate.error(String.format(ERROR_MSG_SIZE_MAX, name, limit));
        }
    }

    /**
     * MINエラーメッセージセット
     * 
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit 何文字
     * @return void
     * @access private
     */
    private void setErrorMsgMin(String name, String msgFull, String limit)
    {
        if (msgFull != null) {
            mValidate.error(msgFull);
        } else {
            mValidate.error(String.format(ERROR_MSG_SIZE_MIN, name, limit));
        }
    }
}
