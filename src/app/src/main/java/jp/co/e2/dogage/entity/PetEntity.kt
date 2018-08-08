package jp.co.e2.dogage.entity

import android.app.Activity
import java.io.IOException
import java.io.Serializable
import java.text.ParseException
import java.util.Locale

import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.common.DateHelper
import jp.co.e2.dogage.common.ImgHelper
import jp.co.e2.dogage.common.MediaUtils
import jp.co.e2.dogage.config.Config

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import jp.co.e2.dogage.config.AppApplication

/**
 * ペットエンティティクラス
 */
class PetEntity : Serializable {

    private var mDogMaster: DogMasterEntity? = null     //該当の犬種マスタ

    var id: Int? = null                                 //ID
    var name: String? = null                            //名前
    var birthday: String? = null                        //誕生日
    var photoFlg: Boolean = false                       //写真有無フラグ
    var savePhotoUri: Uri? = null                       //保存対象の写真Uri
    var archiveDate: String? = null                     //アーカイブ日付
    var created: String? = null                         //作成日時
    var modified: String? = null                        //更新日時

    var kind: Int? = null                               //種類
        set(value) {
            field = value

            mDogMaster = null
        }

    /**
     * ペット年齢を返す
     *
     * @return int
     */
    val petAge: Int
        get() {
            val age = calcPetAge()

            return age[0]
        }

    /**
     * 亡くなってから何年かを返す
     *
     * @return int
     */
    //Nヶ月目の日の翌日にならないとNヶ月がカウントアップしないので、Nヶ月目の日にカウントアップするように調整
    val archiveAge: Int
        get() {
            var age = 0

            if (archiveDate == null) {
                return age
            }

            try {
                val archiveDateHelper = DateHelper(archiveDate, DateHelper.FMT_DATE)
                archiveDateHelper.clearHour()

                val todayDateHelper = DateHelper()
                todayDateHelper.addDay(1)
                todayDateHelper.clearHour()

                val ageDateHelper = DateHelper(todayDateHelper.milliSecond - archiveDateHelper.milliSecond)
                age = ageDateHelper.year - 1970

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return age
        }


    /**
     * 該当の犬種マスタを返す
     *
     * @param activity Activity
     * @return DogMasterEntity mDogMaster
     */
    private fun getDogMaster(activity: Activity): DogMasterEntity? {
        if (kind == null) {
            return null
        }

        if (mDogMaster != null) {
            return mDogMaster
        }

        val dogMasters = (activity.application as AppApplication).dogMasterMap
        mDogMaster = dogMasters!!.get(kind!!)

        return mDogMaster
    }

    /**
     * 表示用誕生日を返す
     *
     * @param context コンテキスト
     * @return String
     */
    fun getDispBirthday(context: Context): String {
        var birthdayDisp = ""

        try {
            val unit = context.getString(R.string.born)
            birthdayDisp = DateHelper(birthday, DateHelper.FMT_DATE).format(DateHelper.FMT_DATE_JP) + unit
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return birthdayDisp
    }

    /**
     * 表示用ペット年齢を返す
     *
     * @param context コンテキスト
     * @return String
     */
    fun getPetAgeDisp(context: Context): String {
        val age = calcPetAge()

        val ageFormat = context.getString(R.string.age_format)

        return String.format(ageFormat, age[0], age[1])
    }

    /**
     * 人間年齢を返す
     *
     * @param  activity Activity
     * @return String
     */
    fun getHumanAge(activity: Activity): String {
        var humanAge = 0.0

        //犬年齢を取得
        val age = calcPetAge()
        val year = age[0]
        val month = age[1]

        if (year < 1) {
            //1年目まで
            humanAge += month * getDogMaster(activity)!!.ageOfMonthUntilOneYear
        } else if (year < 2) {
            //2年目まで
            humanAge += year * (getDogMaster(activity)!!.ageOfMonthUntilOneYear * 12)
            humanAge += month * getDogMaster(activity)!!.ageOfMonthUntilTwoYear
        } else {
            //2年目以上
            humanAge += getDogMaster(activity)!!.ageOfMonthUntilOneYear * 12
            humanAge += getDogMaster(activity)!!.ageOfMonthUntilTwoYear * 12
            humanAge += (year - 2) * (getDogMaster(activity)!!.ageOfMonthOverTwoYear * 12)
            humanAge += month * getDogMaster(activity)!!.ageOfMonthOverTwoYear
        }

        return Math.round(humanAge).toString() + activity.getString(R.string.age_about)
    }

    /**
     * 生まれたからの日数を返す
     *
     * @param context コンテキスト
     * @return String
     */
    fun getDaysFromBorn(context: Context): String {
        var days = 0

        try {
            val birthday = DateHelper(this.birthday, DateHelper.FMT_DATE)
            birthday.clearHour()

            val today: DateHelper
            if (archiveDate != null) {
                today = DateHelper(archiveDate, DateHelper.FMT_DATE)
            } else {
                today = DateHelper()
            }
            today.clearHour()

            val diff = today.milliSecond - birthday.milliSecond
            days = (diff / (60 * 60 * 24 * 1000)).toInt() + 1

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val unit = context.getString(if (archiveDate != null) R.string.day else R.string.day_count)

        return String.format(Locale.getDefault(), "%1$,3d", days) + unit
    }

    /**
     * 表示用種類を返す
     *
     * @param activity Activity
     * @return String
     */
    fun getKindDisp(activity: Activity): String {
        val other = activity.getString(R.string.other)

        //種類にその他という文字列が入っていたら、その他という文字列を取り除く
        return if (getDogMaster(activity)!!.kind.contains(other)) {
            getDogMaster(activity)!!.kind.replace(other.toRegex(), "")
        } else {
            getDogMaster(activity)!!.kind
        }
    }

    /**
     * 拡大写真用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     */
    fun getPhotoBig(context: Context): Bitmap {
        val size = AndroidUtils.dpToPixel(context, Config.PHOTO_BIG_DP)

        val imgHelper = ImgHelper(getImgFilePath(context))
        return imgHelper.getResizeKadomaruBitmap(size, size, AndroidUtils.dpToPixel(context, Config.KADOMARU_DP))
    }

    /**
     * サムネイル用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     */
    @Throws(IOException::class)
    fun getPhotoThumb(context: Context): Bitmap {
        val size = AndroidUtils.dpToPixel(context, Config.PHOTO_THUMB_DP)

        val imgHelper = ImgHelper(getImgFilePath(context))
        return imgHelper.getResizeCircleBitmap(size, size)
    }

    /**
     * 入力画面用ビットマップを返す
     *
     * @param context context
     * @return Bitmap
     */
    fun getPhotoInput(context: Context): Bitmap {
        val size = AndroidUtils.dpToPixel(context, Config.PHOTO_INPUT_DP)

        val imgHelper: ImgHelper

        //保存対象画像が取得できた場合は、それを採用（activity再生成対応）
        if (savePhotoUri != null) {
            imgHelper = ImgHelper(context, savePhotoUri)
        } else {
            imgHelper = ImgHelper(getImgFilePath(context))
        }

        return imgHelper.getResizeKadomaruBitmap(size, size, AndroidUtils.dpToPixel(context, Config.KADOMARU_DP))
    }

    /**
     * 表示用アーカイブ日付を返す
     *
     * @param context コンテキスト
     * @return String
     */
    fun getDispArchiveDate(context: Context): String {
        var archiveDateDisp = ""

        try {
            val unit = context.getString(R.string.death)
            archiveDateDisp = DateHelper(archiveDate, DateHelper.FMT_DATE).format(DateHelper.FMT_DATE_JP) + unit
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return archiveDateDisp
    }

    /**
     * ペット年齢を計算する
     *
     * @return Integer[]
     */
    private fun calcPetAge(): Array<Int> {
        var year = 0
        var month = 0

        try {
            val birthday = DateHelper(this.birthday, DateHelper.FMT_DATE)
            birthday.clearHour()

            val today: DateHelper
            if (archiveDate != null) {
                today = DateHelper(archiveDate, DateHelper.FMT_DATE)
            } else {
                today = DateHelper()
            }
            today.addDay(1)    //Nヶ月目の日の翌日にならないとNヶ月がカウントアップしないので、Nヶ月目の日にカウントアップするように調整
            today.clearHour()

            val age = DateHelper(today.milliSecond - birthday.milliSecond)
            year = age.year - 1970
            month = age.month

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return arrayOf(year, month)
    }

    /**
     * 画像保存ファイルパス
     *
     * @param context コンテキスト
     * @return String
     */
    private fun getImgFilePath(context: Context): String {
        return getImgDirPath(context) + "/" + getImgFileName(id)
    }

    /**
     * 画像保存ディレクトリパスを返す
     *
     * @param context コンテキスト
     * @return String
     */
    private fun getImgDirPath(context: Context): String {
        val path: String

        if (MediaUtils.IsExternalStorageAvailableAndWritable()) {
            //外部ストレージが使用可能
            path = context.getExternalFilesDir(null)!!.toString()
        } else {
            //外部ストレージは使用不可
            path = context.filesDir.toString()
        }

        return path
    }

    /**
     * 画像ファイル名を生成
     *
     * @param id ID
     * @return String
     */
    private fun getImgFileName(id: Int?): String {
        return "dog_$id.jpg"
    }
}
