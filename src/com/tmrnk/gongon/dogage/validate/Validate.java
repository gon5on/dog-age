package com.tmrnk.gongon.dogage.validate;

import java.util.ArrayList;

/**
 * バリデートクラス
 * 
 * 簡単な使い方と注意事項。
 * バリデート対象の値を変えるたびにset()を忘れず呼ぶこと。
 * 同じ値に複数のバリデートをかける場合、エラーがあったらその後のバリデートは実行されません。
 * 各バリデートへの引数は、各バリデートクラス参照。
 * 
 * Validate validate = new Validate();
 * validate.set();
 * validate.require.check("山田太郎", "名前", null);
 * validate.max.check("山田太郎", "名前", null, 30);
 * 
 * validate.set();
 * validate.require.check("東京都", null, "都道府県は必須になってますので、お手数ですがご入力ください。");
 * 
 * @access public
 */
public class Validate
{
    private static Boolean mResult = true;                  //全体のバリデート結果
    private static Boolean mResultValue = true;             //各値ごとのバリデート結果
    private static ArrayList<String> mErrorMsg;             //エラーメッセージ

    public ValidateRequire require;                         //必須系バリデーションクラス
    public ValidateInt isInt;                               //int系バリデーションクラス
    public ValidateDouble isDouble;                         //double系バリデーションクラス
    public ValidateSize size;                               //最大値・最小値系バリデーションクラス
    public ValidateLength length;                           //文字長系バリデーションクラス
    public ValidateDate date;                               //日付系バリデーションクラス

    /**
     * コンストラクタ
     * 
     * @access public
     */
    public Validate()
    {
        mResult = true;
        mResultValue = true;
        mErrorMsg = new ArrayList<String>();

        require = new ValidateRequire(this);
        isInt = new ValidateInt(this);
        isDouble = new ValidateDouble(this);
        size = new ValidateSize(this);
        length = new ValidateLength(this);
        date = new ValidateDate(this);
    }

    /**
     * 値をバリデート
     * 新たな値に対してバリデートを行う際に呼ぶこと
     * 
     * @return void
     * @access public
     */
    public void set()
    {
        mResultValue = true;
    }

    /**
     * バリデート結果がエラーのため、エラーメッセージを追加する
     * 
     * @param msg
     * @param msg_full
     */
    public void error(String msg)
    {
        mResult = false;
        mResultValue = false;

        mErrorMsg.add(msg);
    }

    /**
     * 現在バリデート中の値に対しての結果を返す
     * 
     * @return boolean result_value バリデート結果
     * @access public
     */
    public Boolean getValueResult()
    {
        return mResultValue;
    }

    /**
     * 全体のバリデート結果を返す
     * 
     * @return boolean result バリデート結果
     * @access public
     */
    public Boolean getResult()
    {
        return mResult;
    }

    /**
     * エラ―文言を返す
     * 
     * @return ArrayList<String> error_msg
     * @access public
     */
    public ArrayList<String> getErrorMsg()
    {
        return mErrorMsg;
    }

    /**
     * 結果をセット（独自バリデートの結果を追加する場合に使用）
     * 
     * @param Boolean add_result 結果
     * @return void
     * @access public
     */
    public void setResult(Boolean add_result)
    {
        if (add_result == false) {
            mResultValue = false;
            mResult = false;
        }
    }

    /**
     * エラ―文言を追加（独自バリデートの結果を追加する場合に使用）
     * 
     * @param String add_msg 独自バリデートのメッセージ
     * @return void
     * @access public
     */
    public void addErrorMsg(String add_msg)
    {
        mErrorMsg.add(add_msg);
    }
}
