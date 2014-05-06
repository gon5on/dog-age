package com.tmrnk.gongon.dogage.model;

import java.io.Serializable;

/**
 * 犬マスタエンティティクラス
 * 
 * @access public
 */
public class DogMasterEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer mId;                                        //ID
    private String mKindName;                                   //種類名
    private Integer mCategory;                                  //カテゴリ
    private Integer mOtherFlag;                                 //その他フラグ
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
    public void setKindName(String value)
    {
        mKindName = value;
    }

    /**
     * 種類を返す
     * 
     * @return String mKindName
     * @access public
     */
    public String getKindName()
    {
        return mKindName;
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
     * その他フラグをセット
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public void setOtherFlag(Integer value)
    {
        mOtherFlag = value;
    }

    /**
     * その他フラグを返す
     * 
     * @return Integer mOtherFlag
     * @access public
     */
    public Integer getOtherFlag()
    {
        return mOtherFlag;
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
