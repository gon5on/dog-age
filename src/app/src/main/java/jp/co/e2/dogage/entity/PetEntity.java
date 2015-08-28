package jp.co.e2.dogage.entity;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.ImgHelper;
import jp.co.e2.dogage.config.Config;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * ペットエンティティクラス
 */
public class PetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private DogMasterEntity mDogMaster;                         //該当の犬種マスタ

    private Integer mId;                                        //ID
    private String mName;                                       //名前
    private String mBirthday;                                   //誕生日
    private Integer mKind;                                      //種類
    private Integer mPhotoFlg;                                  //写真フラグ
    private Uri mPhotoUri;                                      //写真URI
    private String mArchiveDate;                                //アーカイブ日付
    private String mCreated;                                    //作成日時
    private String mModified;                                   //更新日時

    /**
     * 該当の犬種マスタを返す
     *
     * @param context context
     * @return DogMasterEntity mDogMaster
     */
    public DogMasterEntity getDogMaster(Context context) {
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
     * @param value 値
     */
    public void setId(Integer value) {
        mId = value;
    }

    /**
     * IDを返す
     *
     * @return Integer mId
     */
    public Integer getId() {
        return mId;
    }

    /**
     * 名前をセット
     *
     * @param value 値
     */
    public void setName(String value) {
        mName = value;
    }

    /**
     * 名前を返す
     *
     * @return String mName
     */
    public String getName() {
        return mName;
    }

    /**
     * 誕生日をセット
     *
     * @param value 値
     */
    public void setBirthday(String value) {
        mBirthday = value;
    }

    /**
     * 誕生日を返す
     *
     * @return String mBirthday
     */
    public String getBirthday() {
        return mBirthday;
    }

    /**
     * 表示用誕生日を返す
     *
     * @return String
     */
    public String getDispBirthday() {
        String birthdayDisp = "";

        try {
            birthdayDisp = new DateHelper(mBirthday, DateHelper.FMT_DATE).format(DateHelper.FMT_DATE_JP) + "生まれ";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return birthdayDisp;
    }

    /**
     * ペット年齢を返す
     *
     * @return String
     */
    private Integer[] getPetAge() {
        Integer year = 0;
        Integer month = 0;

        try {
            DateHelper birthday = new DateHelper(mBirthday, DateHelper.FMT_DATE);
            birthday.clearHour();

            DateHelper today = null;
            if (mArchiveDate != null) {
                today = new DateHelper(mArchiveDate, DateHelper.FMT_DATE);
            } else {
                today = new DateHelper();
            }
            today.addDay(1);    //Nヶ月目の日の翌日にならないとNヶ月がカウントアップしないので、Nヶ月目の日にカウントアップするように調整
            today.clearHour();

            DateHelper age = new DateHelper(today.get().getTimeInMillis() - birthday.get().getTimeInMillis());
            year = age.get().get(Calendar.YEAR) - 1970;
            month = age.get().get(Calendar.MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Integer[]{year, month};
    }

    /**
     * ペット年齢を返す
     *
     * @return String
     */
    public String getPetAgeDisp() {
        Integer[] age = getPetAge();

        return age[0] + "歳" + age[1] + "ヶ月";
    }

    /**
     * 人間年齢を返す
     *
     * @return String
     */
    public String getHumanAge(Context context) {
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
     */
    public String getDaysFromBorn() {
        Integer days = 0;

        try {
            DateHelper birthday = new DateHelper(mBirthday, DateHelper.FMT_DATE);
            birthday.clearHour();

            DateHelper today = null;
            if (mArchiveDate != null) {
                today = new DateHelper(mArchiveDate, DateHelper.FMT_DATE);
            } else {
                today = new DateHelper();
            }
            today.clearHour();

            long diff = today.get().getTimeInMillis() - birthday.get().getTimeInMillis();
            days = (int) (diff / (60 * 60 * 24 * 1000)) + 1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String unit = (getArchiveDate() != null) ? "日" : "日目";

        return String.format("%1$,3d", days) + unit;
    }

    /**
     * 種類をセット
     *
     * @param value 値
     */
    public void setKind(Integer value) {
        mKind = value;
    }

    /**
     * 種類を返す
     *
     * @return mKind
     */
    public Integer getKind() {
        return mKind;
    }

    /**
     * 表示用種類を返す
     *
     * @param context context
     * @return String
     */
    public String getKindDisp(Context context) {
        //種類にその他という文字列が入っていたら、その他フラグを立てる
        if (getDogMaster(context).getKind().indexOf("その他") != -1) {
            return getDogMaster(context).getKind().replaceAll("その他", "");
        } else {
            return getDogMaster(context).getKind();
        }
    }

    /**
     * 写真フラグをセット
     *
     * @param value 値
     */
    public void setPhotoFlg(Integer value) {
        mPhotoFlg = value;
    }

    /**
     * 写真フラグを返す
     *
     * @return Integer mPhotoFlg
     */
    public Integer getPhotoFlg() {
        return mPhotoFlg;
    }

    /**
     * 写真URIをセットする
     *
     * @param value 値
     */
    public void setPhotoUri(Uri value) {
        mPhotoUri = value;
    }

    /**
     * 写真URIを返す
     *
     * @return Uri mPhotoUri
     */
    public Uri getPhotoUri() {
        return mPhotoUri;
    }

    /**
     * 拡大写真用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     * @throws IOException
     */
    public Bitmap getPhotoBig(Context context) throws IOException {
        String path = Config.getImgDirPath(context) + "/" + Config.getImgFileName(getId());

        Integer size = AndroidUtils.dpToPixel(context, Config.PHOTO_BIG_DP);

        ImgHelper imgUtils = new ImgHelper(path);
        Bitmap bitmap = imgUtils.getResizeKadomaruBitmap(size, size, Config.getKadomaruPixcel(context));

        imgUtils = null;

        return bitmap;
    }

    /**
     * サムネイル用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     * @throws IOException
     */
    public Bitmap getPhotoThumb(Context context) throws IOException {
        String path = Config.getImgDirPath(context) + "/" + Config.getImgFileName(getId());

        Integer size = AndroidUtils.dpToPixel(context, Config.PHOTO_THUMB_DP);

        ImgHelper imgUtils = new ImgHelper(path);
        Bitmap bitmap = imgUtils.getResizeCircleBitmap(size, size);

        imgUtils = null;

        return bitmap;
    }

    /**
     * 入力画面用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     * @throws IOException
     */
    public Bitmap getPhotoInput(Context context) throws IOException {
        String path = Config.getImgDirPath(context) + "/" + Config.getImgFileName(getId());

        Integer size = AndroidUtils.dpToPixel(context, Config.PHOTO_INPUT_DP);

        ImgHelper imgUtils = new ImgHelper(path);
        Bitmap bitmap = imgUtils.getResizeKadomaruBitmap(size, size, Config.getKadomaruPixcel(context));

        imgUtils = null;

        return bitmap;
    }

    /**
     * アーカイブ日付をセット
     *
     * @param value 値
     */
    public void setArchiveDate(String value) {
        mArchiveDate = value;
    }

    /**
     * アーカイブ日付を返す
     *
     * @return String mArchiveDate
     */
    public String getArchiveDate() {
        return mArchiveDate;
    }

    /**
     * 表示用アーカイブ日付を返す
     *
     * @return String
     */
    public String getDispArchiveDate() {
        String archiveDateDisp = "";

        try {
            archiveDateDisp = new DateHelper(mArchiveDate, DateHelper.FMT_DATE).format(DateHelper.FMT_DATE_JP) + "永眠";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return archiveDateDisp;
    }

    /**
     * 作成日時をセット
     *
     * @param value 値
     */
    public void setCreated(String value) {
        mCreated = value;
    }

    /**
     * 作成日時を返す
     *
     * @return String mCreated
     */
    public String getCreated() {
        return mCreated;
    }

    /**
     * 更新日時をセット
     *
     * @param value 値
     */
    public void setModified(String value) {
        mModified = value;
    }

    /**
     * 更新日時を返す
     *
     * @return String mModified
     */
    public String Modified() {
        return mModified;
    }
}
