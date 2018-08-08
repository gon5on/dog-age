package jp.co.e2.dogage.entity

import java.io.Serializable

import jp.co.e2.dogage.config.Config

/**
 * 犬マスタエンティティクラス
 */
class DogMasterEntity(val id: Int, val kind: String, val furigana: String, val category: Int) : Serializable {

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

    var initialLine: Int = -1                    //頭文字の行番号を取得する
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

    val ageOfMonthUntilOneYear: Double             //0～1歳までに1ヶ月で取る年齢
        get() {
            when (category) {
                Config.CATEGORY_SMALL -> return Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_SMALL
                Config.CATEGORY_MEDIUM -> return Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_MEDIUM
                else -> return Config.AGE_OF_MONTH_UNTIL_ONE_YEAR_LARGE
            }
        }

    val ageOfMonthUntilTwoYear: Double             //1～2歳までに1ヶ月で取る年齢
        get() {
            when (category) {
                Config.CATEGORY_SMALL -> return Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_SMALL
                Config.CATEGORY_MEDIUM -> return Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_MEDIUM
                else -> return Config.AGE_OF_MONTH_UNTIL_TWO_YEAR_LARGE
            }
        }

    val ageOfMonthOverTwoYear: Double              //2歳以上1年間で取る年齢
        get() {
            when (category) {
                Config.CATEGORY_SMALL -> return Config.AGE_OF_MONTH_OVER_TWO_YEAR_SMALL
                Config.CATEGORY_MEDIUM -> return Config.AGE_OF_MONTH_OVER_TWO_YEAR_MEDIUM
                else -> return Config.AGE_OF_MONTH_OVER_TWO_YEAR_LARGE
            }
        }
}
