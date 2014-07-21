package jp.co.e2.dogage.validate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * バリデートクラス
 * 
 * 簡単な使い方と注意事項。
 * バリデート対象の値を変えるたびにset()を忘れず呼ぶこと。
 * 同じ値に複数のバリデートをかける場合、エラーがあったらその後のバリデートは実行されません。
 * 各バリデートへの引数は、各バリデートクラス参照。
 * 
 * Validate v = new Validate();
 * 
 * ValidateRequire.check(v, value, "名前");
 * ValidateLength.max(v, value, "名前", 30);
 * 
 * if(v.getResult() == false){
 * .....
 * }
 * 
 * @access public
 */
public class Validate
{
    private static Boolean mResult = true;                  //バリデート結果
    private static HashMap<String, String> mErrorMsg;       //エラーメッセージ

    /**
     * コンストラクタ
     * 
     * @access public
     */
    public Validate()
    {
        mResult = true;
        mErrorMsg = new HashMap<String, String>();
    }

    /**
     * バリデート結果がエラーのため、エラーメッセージを追加する
     * 
     * @param String name 変数名
     * @param String msg エラーメッセージ
     * @return void
     * @access public
     */
    public void error(String name, String msg)
    {
        mResult = false;

        mErrorMsg.put(name, msg);
    }

    /**
     * 渡された変数名のデータに関するエラーが既に存在しているか
     * 
     * @param String name 変数名
     * @return boolean エラーの有無
     * @access public
     */
    public Boolean getResult(String name)
    {
        return (mErrorMsg.containsKey(name) == true) ? false : true;
    }

    /**
     * バリデート結果を返す
     * 
     * @return boolean result バリデート結果
     * @access public
     */
    public Boolean getResult()
    {
        return mResult;
    }

    /**
     * エラ―文言を返す（マップ）
     * 
     * @return HashMap<String, String> mErrorMsg
     * @access public
     */
    public HashMap<String, String> getErrorMsgMap()
    {
        return mErrorMsg;
    }

    /**
     * エラ―文言を返す（リスト）
     * 
     * @return ArrayList<String>
     * @access public
     */
    public ArrayList<String> getErrorMsgList()
    {
        return new ArrayList<String>(mErrorMsg.values());
    }
}
