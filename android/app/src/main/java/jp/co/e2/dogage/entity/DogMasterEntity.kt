package jp.co.e2.dogage.entity

import jp.co.e2.dogage.config.Config
import java.io.Serializable

/**
 * 犬マスタエンティティクラス
 */
data class DogMasterEntity(
        val id: Int,                //ID
        val kind: String,           //犬種
        val furigana: String,       //犬種フリガナ
        val category: Int           //サイズカテゴリ
) : Serializable {

    companion object {
        private val LINE_PATTERN = arrayOf(
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
        )
    }

    //頭文字の行番号
    var initialLine: Int = -1
        get() {
            if (field == -1) {
                val initial = furigana.substring(0, 1)

                for (i in LINE_PATTERN.indices) {
                    if (initial.matches(LINE_PATTERN[i].toRegex())) {
                        field = i
                        break
                    }
                }
            }

            return field
        }

    //0～1歳までに1ヶ月で取る年齢
    val ageOfMonthUntilOneYear: Double
        get() {
            return when (category) {
                Config.CATEGORY_SMALL -> Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_SMALL
                Config.CATEGORY_MEDIUM -> Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_MEDIUM
                else -> Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_LARGE
            }
        }

    //1～2歳までに1ヶ月で取る年齢
    val ageOfMonthUntilTwoYear: Double
        get() {
            return when (category) {
                Config.CATEGORY_SMALL -> Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_SMALL
                Config.CATEGORY_MEDIUM -> Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_MEDIUM
                else -> Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_LARGE
            }
        }

    //2歳以上1年間で取る年齢
    val ageOfMonthOverTwoYear: Double
        get() {
            return when (category) {
                Config.CATEGORY_SMALL -> Config.AGE_OF_MONTH_OVER_TWO_YEAR_SMALL
                Config.CATEGORY_MEDIUM -> Config.AGE_OF_MONTH_OVER_TWO_YEAR_MEDIUM
                else -> Config.AGE_OF_MONTH_OVER_TWO_YEAR_LARGE
            }
        }
}
