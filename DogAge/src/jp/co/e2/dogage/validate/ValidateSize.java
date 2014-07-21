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
public class ValidateSize
{
    public static final String ERROR_MSG_SIZE_MIN = "%sは%s以上で入力してください。";
    public static final String ERROR_MSG_SIZE_MAX = "%sは%s以下で入力してください。";

    /**
     * MAXチェック（値→String、MAX→Int）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String limit MAX文字数
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void maxCheck(Validate validate, String value, String name, Integer limit, String msgFull)
    {
        maxCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), msgFull);
    }

    /**
     * MAXチェック（値→String、MAX→Int）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String limit MAX文字数
     * @return void
     * @access public
     */
    public static void maxCheck(Validate validate, String value, String name, Integer limit)
    {
        maxCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), null);
    }

    /**
     * MAXチェック（値→String、MAX→Float）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Float limit MAX文字数
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void maxCheck(Validate validate, String value, String name, Float limit, String msgFull)
    {
        maxCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), msgFull);
    }

    /**
     * MAXチェック（値→String、MAX→Float）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Float limit MAX文字数
     * @return void
     * @access public
     */
    public static void maxCheck(Validate validate, String value, String name, Float limit)
    {
        maxCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), null);
    }

    /**
     * MAXチェック（値→String、MAX→Double）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Double limit MAX文字数
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void maxCheck(Validate validate, String value, String name, Double limit, String msgFull)
    {
        maxCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), msgFull);
    }

    /**
     * MAXチェック（値→String、MAX→Double）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Double limit MAX文字数
     * @return void
     * @access public
     */
    public static void maxCheck(Validate validate, String value, String name, Double limit)
    {
        maxCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), null);
    }

    /**
     * MAXチェック（値→Double、MAX→Double）
     * 
     * @param Validate validate バリデートクラス
     * @param Double value 値
     * @param Double limit MAX文字数
     * @return boolen バリデート結果
     * @access private
     */
    private static void maxCheck(Validate validate, Double value, String name, Double limit, String msgFull)
    {
        if (validate.getResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }

        if ((value <= limit) == false) {
            setErrorMsgMax(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MINチェック（値→String、MIN→Int）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String limit MIN文字数
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(Validate validate, String value, String name, Integer limit, String msgFull)
    {
        minCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), msgFull);
    }

    /**
     * MINチェック（値→String、MIN→Int）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param String limit MIN文字数
     * @return void
     * @access public
     */
    public static void minCheck(Validate validate, String value, String name, Integer limit)
    {
        minCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), null);
    }

    /**
     * MINチェック（値→String、MIN→Float）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Float limit MIN文字数
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(Validate validate, String value, String name, Float limit, String msgFull)
    {
        minCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), msgFull);
    }

    /**
     * MINチェック（値→String、MIN→Float）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Float limit MIN文字数
     * @return void
     * @access public
     */
    public static void minCheck(Validate validate, String value, String name, Float limit)
    {
        minCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), null);
    }

    /**
     * MINチェック（値→String、MIN→Double）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Double limit MIN文字数
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(Validate validate, String value, String name, Double limit, String msgFull)
    {
        minCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), msgFull);
    }

    /**
     * MINチェック（値→String、MIN→Double）
     * 
     * @param Validate validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Double limit MIN文字数
     * @return void
     * @access public
     */
    public static void minCheck(Validate validate, String value, String name, Double limit)
    {
        minCheck(validate, Double.valueOf(value), name, Double.valueOf(limit), null);
    }

    /**
     * MINチェック（値→Double、MIN→Double）
     * 
     * @param Validate validate バリデートクラス
     * @param Double value 値
     * @param Double limit MIN文字数
     * @return boolen バリデート結果
     * @access private
     */
    private static void minCheck(Validate validate, Double value, String name, Double limit, String msgFull)
    {
        if (validate.getResult() == false) {
            return;
        }
        if (value == null) {
            return;
        }

        if ((value <= limit) == false) {
            setErrorMsgMin(validate, name, msgFull, String.valueOf(limit));
        }
    }

    /**
     * MAXエラーメッセージセット
     * 
     * @param Validate validate バリデートクラス
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit 何文字
     * @return void
     * @access private
     */
    private static void setErrorMsgMax(Validate validate, String name, String msgFull, String limit)
    {
        if (msgFull != null) {
            validate.error(name, msgFull);
        } else {
            validate.error(name, String.format(ERROR_MSG_SIZE_MAX, name, limit));
        }
    }

    /**
     * MINエラーメッセージセット
     * 
     * @param Validate validate バリデートクラス
     * @param String name 変数名
     * @param String msgFull エラーメッセージ全文
     * @param String limit 何文字
     * @return void
     * @access private
     */
    private static void setErrorMsgMin(Validate validate, String name, String msgFull, String limit)
    {
        if (msgFull != null) {
            validate.error(name, msgFull);
        } else {
            validate.error(name, String.format(ERROR_MSG_SIZE_MIN, name, limit));
        }
    }
}
