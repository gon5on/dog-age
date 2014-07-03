package com.tmrnk.gongon.dogage.entity;

import java.io.Serializable;

import com.tmrnk.gongon.dogage.config.Config;

/**
 * 犬マスタエンティティクラス
 * 
 * @access public
 */
public class DogMasterEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer mId;                                        //ID
    private String mKind;                                       //種類名
    private Integer mCategory;                                  //カテゴリ
    private Double mOverThreeAge;                               //3歳以上、1年で取る歳
    private Integer mLabelFlag = 0;                             //ラベルフラグ(種類選択ダイアログでしか使わない)
    private String mCreated;                                    //作成日時
    private String mModified;                                   //更新日時

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
     * 2歳以上1年間で取る年齢を返す
     * 
     * @return Double
     * @access public
     */
    public Double getBeforeTwoAge()
    {
        if (getCategory() == Config.CATEGORY_SMALL) {
            return Config.BEFORE_TWO_AGE_SMALL;
        }
        else if (getCategory() == Config.CATEGORY_MEDIUM) {
            return Config.BEFORE_TWO_AGE_MEDIUM;
        } else {
            return Config.BEFORE_TWO_AGE_LAEGE;
        }
    }

    /**
     * 2歳以上1年間で取る年齢をセット
     * 
     * @param String value
     * @return void
     * @access public
     */
    public void setOverThreeAge(Double value)
    {
        mOverThreeAge = value;
    }

    /**
     * 2歳以上1年間で取る年齢を返す
     * 
     * @return String mOverThreeAge
     * @access public
     */
    public Double getOverThreeAge()
    {
        return mOverThreeAge;
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
     * その他フラグを返す
     * 
     * @return Integer mOtherFlag
     * @access public
     */
    public Integer getOtherFlag()
    {
        //種類にその他という文字列が入っていたら、その他フラグを立てる
        if (getKind().indexOf("その他") != -1) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * ラベルフラグをセット
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public void setLabelFlag(Integer value)
    {
        mLabelFlag = value;
    }

    /**
     * ラベルフラグを返す
     * 
     * @return Integer mLabelFlag
     * @access public
     */
    public Integer getLabelFlag()
    {
        return mLabelFlag;
    }

    /**
     * 作成日時をセット
     * 
     * @param String value
     * @return void
     * @access public
     */
    public void setCreated(String value)
    {
        mCreated = value;
    }

    /**
     * 作成日時を返す
     * 
     * @return String mCreated
     * @access public
     */
    public String getCreated()
    {
        return mCreated;
    }

    /**
     * 更新日時をセット
     * 
     * @param String value
     * @return void
     * @access public
     */
    public void setModified(String value)
    {
        mModified = value;
    }

    /**
     * 更新日時を返す
     * 
     * @return String mModified
     * @access public
     */
    public String Modified()
    {
        return mModified;
    }
}
