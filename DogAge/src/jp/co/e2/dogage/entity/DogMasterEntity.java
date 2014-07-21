package jp.co.e2.dogage.entity;

import java.io.Serializable;

import jp.co.e2.dogage.config.Config;

/**
 * 犬マスタエンティティクラス
 * 
 * @access public
 */
public class DogMasterEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static final String[] LINE_PATTERN = {
            "^[あ-お]+$",
            "^[か-こが-ご]+$",
            "^[さ-そざ-ぞ]+$",
            "^[た-とだ-ど]+$",
            "^[な-の]+$",
            "^[は-ほば-ぼぱ-ぽ]+$",
            "^[ま-も]+$",
            "^[や-よ]+$",
            "^[ら-ろ]+$",
            "^[わ-ん]+$"
    };

    private Integer mId;                                        //ID
    private String mKind;                                       //種類名
    private String mFurigana;                                   //ふりがな
    private Integer mInitialLine;                               //頭文字の行番号
    private Integer mCategory;                                  //カテゴリ

    /**
     * IDをセット
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public void setId(Integer value)
    {
        mId = value;
    }

    /**
     * IDを返す
     * 
     * @return Integer mId
     * @access public
     */
    public Integer getId()
    {
        return mId;
    }

    /**
     * 種類をセット
     * 
     * @param String value
     * @return void
     * @access public
     */
    public void setKind(String value)
    {
        mKind = value;
    }

    /**
     * 種類を返す
     * 
     * @return String mKind
     * @access public
     */
    public String getKind()
    {
        return mKind;
    }

    /**
     * カテゴリをセット
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public void setCategory(Integer value)
    {
        mCategory = value;
    }

    /**
     * カテゴリを返す
     * 
     * @return Integer mCategory
     * @access public
     */
    public Integer getCategory()
    {
        return mCategory;
    }

    /**
     * ふりがなをセットする
     * 
     * @param String value
     * @return void
     * @access public
     */
    public void setFurigana(String value)
    {
        mFurigana = value;
    }

    /**
     * ふりがなをセット返す
     * 
     * @return String value
     * @access public
     */
    public String getFurigana()
    {
        return mFurigana;
    }

    /**
     * 頭文字の行番号を取得する
     * 
     * @return Integer
     * @access public
     */
    public Integer getInitialLine()
    {
        if (mInitialLine == null) {
            String initial = mFurigana.substring(0, 1);

            for (int i = 0; i < LINE_PATTERN.length; i++) {
                if (initial.matches(LINE_PATTERN[i])) {
                    mInitialLine = i;
                    break;
                }
            }
        }

        return mInitialLine;
    }

    /**
     * 0～1歳までに1ヶ月で取る年齢
     * 
     * @return Double
     * @access public
     */
    public Double getAgeOfMonthUntilOneYear()
    {
        if (getCategory() == Config.CATEGORY_SMALL) {
            return Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_SMALL;
        }
        else if (getCategory() == Config.CATEGORY_MEDIUM) {
            return Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_MEDIUM;
        }
        else {
            return Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_LARGE;
        }
    }

    /**
     * 1～2歳までに1ヶ月で取る年齢
     * 
     * @return Double
     * @access public
     */
    public Double getAgeOfMonthUntilTwoYear()
    {
        if (getCategory() == Config.CATEGORY_SMALL) {
            return Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_SMALL;
        }
        else if (getCategory() == Config.CATEGORY_MEDIUM) {
            return Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_MEDIUM;
        }
        else {
            return Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_LARGE;
        }
    }

    /**
     * 2歳以上1年間で取る年齢をセット
     * 
     * @return Double
     * @access public
     */
    public Double getAgeOfMonthOverTwoYear()
    {
        if (getCategory() == Config.CATEGORY_SMALL) {
            return Config.AGE_OF_MONTH_OVER_TWO_YEAR_SMALL;
        }
        else if (getCategory() == Config.CATEGORY_MEDIUM) {
            return Config.AGE_OF_MONTH_OVER_TWO_YEAR_MEDIUM;
        }
        else {
            return Config.AGE_OF_MONTH_OVER_TWO_YEAR_LARGE;
        }
    }
}
