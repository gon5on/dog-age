package jp.co.e2.dogage.config

/**
 * 設定ファイル
 */
class Config {
    /*
    ↓enumで書くとこうなる
    public enum Kind {
        small (1, 1.333, 0.666, 0.333),
        medium (2, 1.333, 0.5, 0.417),
        large (3, 1.0, 0.5, 0.5833);

        public Integer category;
        public Double ageOfMonthUntilOneYear;
        public Double ageOfMonthUntilTwoYear;
        public Double ageOfMonthOverTwoYear;

        Kind(Integer category, Double ageOfMonthUntilOneYear, Double ageOfMonthUntilTwoYear, Double ageOfMonthOverTwoYear){
            this.category = category;
            this.ageOfMonthUntilOneYear = ageOfMonthUntilOneYear;
            this.ageOfMonthUntilTwoYear = ageOfMonthUntilTwoYear;
            this.ageOfMonthOverTwoYear = ageOfMonthOverTwoYear;
        }
    }*/

    companion object {
        //ログ出力フラグ
        const val LOG_FLG = true

        //犬種数
        const val KIND_NUM = 194

        //カテゴリ
        const val CATEGORY_SMALL = 1                                //小型犬
        const val CATEGORY_MEDIUM = 2                               //中型犬
        const val CATEGORY_LARGE = 3                                //大型犬

        //0～1歳までに1ヶ月で取る年齢
        const val AGE_OF_MONTH_UNTIL_ONE_YEAR_SMALL = 1.333         //小型犬
        const val AGE_OF_MONTH_UNTIL_ONE_YEAR_MEDIUM = 1.333        //中型犬
        const val AGE_OF_MONTH_UNTIL_ONE_YEAR_LARGE = 1.0           //大型犬

        //1～2歳までに1ヶ月で取る年齢
        const val AGE_OF_MONTH_UNTIL_TWO_YEAR_SMALL = 0.666         //小型犬
        const val AGE_OF_MONTH_UNTIL_TWO_YEAR_MEDIUM = 0.5          //中型犬
        const val AGE_OF_MONTH_UNTIL_TWO_YEAR_LARGE = 0.5           //大型犬

        //2歳以上で1ヶ月で取る年齢
        const val AGE_OF_MONTH_OVER_TWO_YEAR_SMALL = 0.333          //小型犬
        const val AGE_OF_MONTH_OVER_TWO_YEAR_MEDIUM = 0.417         //中型犬
        const val AGE_OF_MONTH_OVER_TWO_YEAR_LARGE = 0.5833         //大型犬

        //画像dp
        const val PHOTO_INPUT_DP = 150                              //入力画面の写真
        const val PHOTO_THUMB_DP = 80                               //サムネイル写真
        const val PHOTO_BIG_DP = 400                                //拡大写真

        //インテント判別
        const val INTENT_CODE_CAMERA = 1                            //カメラ起動
        const val INTENT_CODE_GALLERY = 2                           //ギャラリー起動

        //プリファレンス
        const val PREF_BIRTH_NOTIFY_FLG = "birth_notify_flg"        //誕生日の通知
        const val PREF_ARCHIVE_NOTIFY_FLG = "archive_notify_flg"    //命日の通知

        //アラーム
        const val ALARM_HOUR = 12                                   //アラームの時間
        const val ALARM_MINUTE = 0                                  //アラームの時間

        //E2のURL
        const val OFFICIAL_LINK = "https://www.e-2.co.jp"
    }
}
