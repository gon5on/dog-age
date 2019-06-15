package jp.co.e2.dogage.entity

import android.app.Activity
import android.content.Context
import android.net.Uri
import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.DateHelper
import jp.co.e2.dogage.common.MediaUtils
import jp.co.e2.dogage.config.AppApplication
import java.io.File
import java.io.Serializable
import java.text.ParseException
import java.util.*

/**
 * ペットエンティティクラス
 */
data class PetEntity(
        var id: Int? = null,                    //ペットID
        var name: String? = null,               //名前
        var birthday: String? = null,           //誕生日
        var kind: Int? = null,                  //犬種ID
        var photoFlg: Boolean = false,          //写真フラグ
        var savePhotoUri: Uri? = null,          //保存対象写真URI
        var archiveDate: String? = null,        //死亡日
        var order: Int? = null,                 //表示順
        var created: String? = null,            //作成日時
        var modified: String? = null            //最終更新日時
) : Serializable {

    //ペット年齢を返す
    val petAge: Int
        get() {
            val age = calcPetAge()

            return age[0]
        }

    //亡くなってから何年か
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

        when {
            year < 1 -> //1年目まで
                humanAge += month * getDogMaster(activity).ageOfMonthUntilOneYear
            year < 2 -> {
                //2年目まで
                humanAge += year * (getDogMaster(activity).ageOfMonthUntilOneYear * 12)
                humanAge += month * getDogMaster(activity).ageOfMonthUntilTwoYear
            }
            else -> {
                //2年目以上
                humanAge += getDogMaster(activity).ageOfMonthUntilOneYear * 12
                humanAge += getDogMaster(activity).ageOfMonthUntilTwoYear * 12
                humanAge += (year - 2) * (getDogMaster(activity).ageOfMonthOverTwoYear * 12)
                humanAge += month * getDogMaster(activity).ageOfMonthOverTwoYear
            }
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

            val today: DateHelper = if (archiveDate != null) {
                DateHelper(archiveDate, DateHelper.FMT_DATE)
            } else {
                DateHelper()
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
     * @param activity
     * @return String
     */
    fun getKindDisp(activity: Activity): String {
        return getDogMaster(activity).kind
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
     * 画像保存ファイルパス
     *
     * @param context コンテキスト
     * @return String
     */
    fun getImgFilePath(context: Context): String {
        return getImgDirPath(context) + "/" + getImgFileName(id!!)
    }

    /**
     * 画像保存ファイルURI
     *
     * @param context コンテキスト
     * @return String
     */
    fun getImgFileUri(context: Context): Uri {
        return Uri.fromFile(File(getImgFilePath(context)))
    }

    /**
     * 犬マスタを取得する
     *
     * @param activity
     * @return DogMasterEntity
     */
    private fun getDogMaster(activity: Activity): DogMasterEntity {
        return (activity.application as AppApplication).dogMasterMap[kind]!!
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

            val today: DateHelper = if (archiveDate != null) {
                DateHelper(archiveDate, DateHelper.FMT_DATE)
            } else {
                DateHelper()
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
     * 画像保存ディレクトリパスを返す
     *
     * @param context コンテキスト
     * @return String
     */
    private fun getImgDirPath(context: Context): String {
        return if (MediaUtils.IsExternalStorageAvailableAndWritable()) {
            //外部ストレージが使用可能
            context.getExternalFilesDir(null)!!.toString()
        } else {
            //外部ストレージは使用不可
            context.filesDir.toString()
        }
    }

    /**
     * 画像ファイル名を生成
     *
     * @param id ID
     * @return String
     */
    private fun getImgFileName(id: Int): String {
        return "dog_$id.jpg"
    }
}
