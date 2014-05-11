package com.tmrnk.gongon.dogage.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;

import com.tmrnk.gongon.dogage.common.DateUtils;

/**
 * ペットエンティティクラス
 * 
 * @access public
 */
public class PetEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer mId;                                        //ID
    private String mName;                                       //名前
    private String mBirthday;                                   //誕生日
    private Integer mKind;                                      //種類
    private String mKindName;                                   //種類名
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
     * 名前をセット
     * 
     * @param String value
     * @return void
     * @access public
     */
    public void setName(String value)
    {
        mName = value;
    }

    /**
     * 名前を返す
     * 
     * @return String mName
     * @access public
     */
    public String getName()
    {
        return mName;
    }

    /**
     * 誕生日をセット
     * 
     * @param String value
     * @return void
     * @access public
     */
    public void setBirthday(String value)
    {
        mBirthday = value;
    }

    /**
     * 誕生日を返す
     * 
     * @return String mBirthday
     * @access public
     */
    public String getBirthday()
    {
        return mBirthday;
    }

    /**
     * 表示用誕生日を返す
     * 
     * @return String
     * @access public
     */
    public String getDispBirthday()
    {
        String birthdayDisp = "";

        try {
            birthdayDisp = new DateUtils(mBirthday, DateUtils.FMT_DATE).format(DateUtils.FMT_DATE_JP) + "生まれ";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return birthdayDisp;
    }

    /**
     * ペット年齢を返す
     * 
     * @return String
     * @access public
     */
    public String getPetAge()
    {
        Integer year = 0;
        Integer month = 0;

        try {
            DateUtils birthday = new DateUtils(mBirthday, DateUtils.FMT_DATE);
            birthday.clearHour();

            DateUtils today = new DateUtils();
            today.clearHour();

            DateUtils age = new DateUtils(today.get().getTimeInMillis() - birthday.get().getTimeInMillis());
            year = age.get().get(Calendar.YEAR) - 1970;
            month = age.get().get(Calendar.MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return year + "歳" + month + "ヶ月";
    }

    /**
     * 人間年齢を返す
     * 
     * @return String
     * @access public
     */
    public String getHumanAge()
    {
        String humanAge = "";

        humanAge = "3歳くらい";     //TODO

        return humanAge;
    }

    /**
     * 生まれたからの日数を返す
     * 
     * @return String
     * @access public
     */
    public String getDaysFromBorn()
    {
        Integer days = 0;

        try {
            DateUtils birthday = new DateUtils(mBirthday, DateUtils.FMT_DATE);
            birthday.clearHour();

            DateUtils today = new DateUtils();
            today.clearHour();

            long diff = today.get().getTimeInMillis() - birthday.get().getTimeInMillis();
            days = (int) (diff / (60 * 60 * 24 * 1000)) + 1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.format("%1$,3d日目", days);
    }

    /**
     * 種類をセット
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public void setKind(Integer value)
    {
        mKind = value;
    }

    /**
     * 種類を返す
     * 
     * @return Integer mKind
     * @access public
     */
    public Integer getKind()
    {
        return mKind;
    }

    /**
     * 種類名をセット
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
     * 種類名を返す
     * 
     * @return String mKindName
     * @access public
     */
    public String getKindName()
    {
        return mKindName;
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
