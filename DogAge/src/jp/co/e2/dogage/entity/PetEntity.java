package jp.co.e2.dogage.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

import jp.co.e2.dogage.common.DateUtils;
import jp.co.e2.dogage.config.Config;
import android.content.Context;

/**
 * ペットエンティティクラス
 * 
 * @access public
 */
public class PetEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private DogMasterEntity mDogMaster;                         //該当の犬種マスタ

    private Integer mId;                                        //ID
    private String mName;                                       //名前
    private String mBirthday;                                   //誕生日
    private Integer mKind;                                      //種類
    private String mCreated;                                    //作成日時
    private String mModified;                                   //更新日時

    /**
     * 該当の犬種マスタを返す
     * 
     * @param Context context
     * @return DogMasterEntity mDogMaster
     * @access public
     */
    public DogMasterEntity getDogMaster(Context context)
    {
        if (getId() == null || mDogMaster != null) {
            return mDogMaster;
        }

        HashMap<Integer, DogMasterEntity> dogMasters = Config.getDogMastersMap(context);
        mDogMaster = dogMasters.get(getKind());

        return mDogMaster;
    }

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
     * @access private
     */
    private Integer[] getPetAge()
    {
        Integer year = 0;
        Integer month = 0;

        try {
            DateUtils birthday = new DateUtils(mBirthday, DateUtils.FMT_DATE);
            birthday.clearHour();

            DateUtils today = new DateUtils();
            today.addDay(1);    //Nヶ月目の日の翌日にならないとNヶ月がカウントアップしないので、Nヶ月目の日にカウントアップするように調整
            today.clearHour();

            DateUtils age = new DateUtils(today.get().getTimeInMillis() - birthday.get().getTimeInMillis());
            year = age.get().get(Calendar.YEAR) - 1970;
            month = age.get().get(Calendar.MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Integer[] { year, month };
    }

    /**
     * ペット年齢を返す
     * 
     * @return String
     * @access public
     */
    public String getPetAgeDisp()
    {
        Integer[] age = getPetAge();

        return age[0] + "歳" + age[1] + "ヶ月";
    }

    /**
     * 人間年齢を返す
     * 
     * @return String
     * @access public
     */
    public String getHumanAge(Context context)
    {
        Double humanAge = 0.0;

        //犬年齢を取得
        Integer[] age = getPetAge();
        Integer year = age[0];
        Integer month = age[1];

        //1年目まで
        if (year < 1) {
            humanAge += month * getDogMaster(context).getAgeOfMonthUntilOneYear();
        }
        //2年目まで
        else if (year < 2) {
            humanAge += year * (getDogMaster(context).getAgeOfMonthUntilOneYear() * 12);
            humanAge += month * getDogMaster(context).getAgeOfMonthUntilTwoYear();
        }
        //2年目以上
        else {
            humanAge += getDogMaster(context).getAgeOfMonthUntilOneYear() * 12;
            humanAge += getDogMaster(context).getAgeOfMonthUntilTwoYear() * 12;
            humanAge += (year - 2) * (getDogMaster(context).getAgeOfMonthOverTwoYear() * 12);
            humanAge += month * getDogMaster(context).getAgeOfMonthOverTwoYear();
        }

        return Math.round(humanAge) + "歳くらい";
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
     * 表示用種類を返す
     * 
     * @param Context context
     * @return String
     * @access public
     */
    public String getKindDisp(Context context)
    {
        //種類にその他という文字列が入っていたら、その他フラグを立てる
        if (getDogMaster(context).getKind().indexOf("その他") != -1) {
            return getDogMaster(context).getKind().replaceAll("その他", "");
        } else {
            return getDogMaster(context).getKind();
        }
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
