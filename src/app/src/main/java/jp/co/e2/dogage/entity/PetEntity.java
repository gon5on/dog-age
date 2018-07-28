package jp.co.e2.dogage.entity;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.ImgHelper;
import jp.co.e2.dogage.common.MediaUtils;
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
    private boolean mPhotoFlg;                                  //写真有無フラグ
    private Uri mSavePhotoUri;                                  //保存対象の写真Uri
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
        if (getKind() == null) {
            return null;
        }

        if (mDogMaster != null) {
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
     * @param context コンテキスト
     * @return String
     */
    public String getDispBirthday(Context context) {
        String birthdayDisp = "";

        try {
            String unit = context.getString(R.string.born);
            birthdayDisp = new DateHelper(mBirthday, DateHelper.FMT_DATE).format(DateHelper.FMT_DATE_JP) + unit;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return birthdayDisp;
    }

    /**
     * 表示用ペット年齢を返す
     *
     * @param context コンテキスト
     * @return String
     */
    public String getPetAgeDisp(Context context) {
        Integer[] age = calcPetAge();

        String ageFormat = context.getString(R.string.age_format);

        return String.format(ageFormat, age[0], age[1]);
    }

    /**
     * ペット年齢を返す
     *
     * @return int
     */
    public int getPetAge() {
        Integer[] age = calcPetAge();

        return age[0];
    }

    /**
     * 人間年齢を返す
     *
     * @param context コンテキスト
     * @return String
     */
    public String getHumanAge(Context context) {
        Double humanAge = 0.0;

        //犬年齢を取得
        Integer[] age = calcPetAge();
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

        return Math.round(humanAge) + context.getString(R.string.age_about);
    }

    /**
     * 生まれたからの日数を返す
     *
     * @param context コンテキスト
     * @return String
     */
    public String getDaysFromBorn(Context context) {
        Integer days = 0;

        try {
            DateHelper birthday = new DateHelper(mBirthday, DateHelper.FMT_DATE);
            birthday.clearHour();

            DateHelper today;
            if (mArchiveDate != null) {
                today = new DateHelper(mArchiveDate, DateHelper.FMT_DATE);
            } else {
                today = new DateHelper();
            }
            today.clearHour();

            long diff = today.getMilliSecond() - birthday.getMilliSecond();
            days = (int) (diff / (60 * 60 * 24 * 1000)) + 1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String unit = context.getString((getArchiveDate() != null) ? R.string.day : R.string.day_count);

        return String.format(Locale.getDefault(), "%1$,3d", days) + unit;
    }

    /**
     * 種類をセット
     *
     * @param value 値
     */
    public void setKind(Integer value) {
        mKind = value;

        mDogMaster = null;
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
        String other = context.getString(R.string.other);

        //種類にその他という文字列が入っていたら、その他という文字列を取り除く
        if (getDogMaster(context).getKind().contains(other)) {
            return getDogMaster(context).getKind().replaceAll(other, "");
        } else {
            return getDogMaster(context).getKind();
        }
    }

    /**
     * 写真フラグをセット
     *
     * @param value 値
     */
    public void setPhotoFlg(boolean value) {
        mPhotoFlg = value;
    }

    /**
     * 写真フラグを返す
     *
     * @return mPhotoFlg
     */
    public boolean getPhotoFlg() {
        return mPhotoFlg;
    }

    /**
     * 保存対象の画像URIを返す
     *
     * @return mSavePhotoUri
     */
    public Uri getSavePhotoUri() {
        return mSavePhotoUri;
    }

    /**
     * 保存対象の画像パスをセットする
     *
     * @param value 値
     */
    public void setSavePhotoUri(Uri value) {
        mSavePhotoUri = value;
    }

    /**
     * 拡大写真用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     */
    public Bitmap getPhotoBig(Context context) throws IOException {
        Integer size = AndroidUtils.dpToPixel(context, Config.PHOTO_BIG_DP);

        ImgHelper imgHelper = new ImgHelper(getImgFilePath(context));
        return imgHelper.getResizeKadomaruBitmap(size, size, Config.getKadomaruPixcel(context));
    }

    /**
     * サムネイル用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     */
    public Bitmap getPhotoThumb(Context context) throws IOException {
        Integer size = AndroidUtils.dpToPixel(context, Config.PHOTO_THUMB_DP);

        ImgHelper imgHelper = new ImgHelper(getImgFilePath(context));
        return imgHelper.getResizeCircleBitmap(size, size);
    }

    /**
     * 入力画面用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     */
    public Bitmap getPhotoInput(Context context) throws Exception {
        Integer size = AndroidUtils.dpToPixel(context, Config.PHOTO_INPUT_DP);

        ImgHelper imgHelper;

        //保存対象画像が取得できた場合は、それを採用（activity再生成対応）
        if (mSavePhotoUri != null) {
            imgHelper = new ImgHelper(context, mSavePhotoUri);
        } else {
            imgHelper = new ImgHelper(getImgFilePath(context));
        }

        return imgHelper.getResizeKadomaruBitmap(size, size, Config.getKadomaruPixcel(context));
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
     * @param context コンテキスト
     * @return String
     */
    public String getDispArchiveDate(Context context) {
        String archiveDateDisp = "";

        try {
            String unit = context.getString(R.string.death);
            archiveDateDisp = new DateHelper(mArchiveDate, DateHelper.FMT_DATE).format(DateHelper.FMT_DATE_JP) + unit;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return archiveDateDisp;
    }

    /**
     * 亡くなってから何年かを返す
     *
     * @return int
     */
    public int getArchiveAge() {
        int age = 0;

        if (mArchiveDate == null) {
            return age;
        }

        try {
            DateHelper archiveDateHelper = new DateHelper(mArchiveDate, DateHelper.FMT_DATE);
            archiveDateHelper.clearHour();

            DateHelper todayDateHelper = new DateHelper();
            todayDateHelper.addDay(1);    //Nヶ月目の日の翌日にならないとNヶ月がカウントアップしないので、Nヶ月目の日にカウントアップするように調整
            todayDateHelper.clearHour();

            DateHelper ageDateHelper = new DateHelper(todayDateHelper.getMilliSecond() - archiveDateHelper.getMilliSecond());
            age = ageDateHelper.getYear() - 1970;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return age;
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

    /**
     * ペット年齢を計算する
     *
     * @return Integer[]
     */
    private Integer[] calcPetAge() {
        Integer year = 0;
        Integer month = 0;

        try {
            DateHelper birthday = new DateHelper(mBirthday, DateHelper.FMT_DATE);
            birthday.clearHour();

            DateHelper today;
            if (mArchiveDate != null) {
                today = new DateHelper(mArchiveDate, DateHelper.FMT_DATE);
            } else {
                today = new DateHelper();
            }
            today.addDay(1);    //Nヶ月目の日の翌日にならないとNヶ月がカウントアップしないので、Nヶ月目の日にカウントアップするように調整
            today.clearHour();

            DateHelper age = new DateHelper(today.getMilliSecond() - birthday.getMilliSecond());
            year = age.getYear() - 1970;
            month = age.getMonth();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Integer[]{year, month};
    }

    /**
     * 画像保存ファイルパス
     *
     * @param context コンテキスト
     * @return String
     */
    public String getImgFilePath(Context context) {
        return getImgDirPath(context) + "/" + getImgFileName(getId());
    }

    /**
     * 画像保存ディレクトリパスを返す
     *
     * @param context コンテキスト
     * @return String
     */
    private String getImgDirPath(Context context) {
        String path;

        //外部ストレージが使用可能
        if (MediaUtils.IsExternalStorageAvailableAndWritable()) {
            path = context.getExternalFilesDir(null).toString();
        }
        //外部ストレージは使用不可
        else {
            path = context.getFilesDir().toString();
        }

        return path;
    }

    /**
     * 画像ファイル名を生成
     *
     * @param id ID
     * @return String
     */
    private String getImgFileName(Integer id) {
        return "dog_" + id + ".jpg";
    }
}
