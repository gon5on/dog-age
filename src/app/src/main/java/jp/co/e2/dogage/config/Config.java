package jp.co.e2.dogage.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.MediaUtils;
import jp.co.e2.dogage.entity.DogMasterEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

/**
 * 設定ファイル
 */
public class Config {
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

    //ログ出力フラグ
    public static final boolean LOG_FLG = true;

    //犬種数
    public static final int KIND_NUM = 197;

    //カテゴリ
    public static final int CATEGORY_SMALL = 1;                                 //小型犬
    public static final int CATEGORY_MEDIUM = 2;                                //中型犬
    public static final int CATEGORY_LARGE = 3;                                 //大型犬

    //0～1歳までに1ヶ月で取る年齢
    public static final Double AGE_OF_MONTH_UNTIL_ONE_YEAR_SMALL = 1.333;       //小型犬
    public static final Double AGE_OF_MONTH_UNTIL_ONE_YEAR_MEDIUM = 1.333;      //中型犬
    public static final Double AGE_OF_MONTH_UNTIL_ONE_YEAR_LARGE = 1.0;         //大型犬

    //1～2歳までに1ヶ月で取る年齢
    public static final Double AGE_OF_MONTH_UNTIL_TWO_YEAR_SMALL = 0.666;       //小型犬
    public static final Double AGE_OF_MONTH_UNTIL_TWO_YEAR_MEDIUM = 0.5;        //中型犬
    public static final Double AGE_OF_MONTH_UNTIL_TWO_YEAR_LARGE = 0.5;         //大型犬

    //2歳以上で1ヶ月で取る年齢
    public static final Double AGE_OF_MONTH_OVER_TWO_YEAR_SMALL = 0.333;        //小型犬
    public static final Double AGE_OF_MONTH_OVER_TWO_YEAR_MEDIUM = 0.417;       //中型犬
    public static final Double AGE_OF_MONTH_OVER_TWO_YEAR_LARGE = 0.5833;       //大型犬

    //犬種マスタ
    public static HashMap<Integer, DogMasterEntity> mDogMasterMap;
    public static ArrayList<DogMasterEntity> mDogMasterList;

    //画像tmpフォルダ名
    public static String TMP_DIR_NAME = "tmp";
    public static String TMP_DIR_FILENAME = "tmp.jpg";

    //画像dp
    public static int KADOMARU_DP = 20;                                         //角丸画像の角丸サイズ
    public static int PHOTO_INPUT_DP = 150;                                     //入力画面の写真
    public static int PHOTO_THUMB_DP = 80;                                      //サムネイル写真
    public static int PHOTO_BIG_DP = 310;                                       //拡大写真

    //インテント判別
    public static final int INTENT_CODE_CAMERA = 1;                             //カメラ起動
    public static final int INTENT_CODE_GALLERY = 2;                            //ギャラリー起動
    public static final int INTENT_CODE_TRIMMING = 3;                           //画像トリミング

    //プリファレンス
    public static final String PREF_NOTIFICATION = "notification";              //誕生日・命日の通知

    //アラーム
    public static int ALARM_HOUR = 17;                                          //アラームの時間
    public static int ALARM_MINUTE = 17;                                        //アラームの時間

    /**
     * 犬種マスタを配列で返す
     *
     * @return ArrayList<DogMasterEntity>
     */
    @SuppressLint("UseSparseArrays")
    public static HashMap<Integer, DogMasterEntity> getDogMastersMap(Context context) {
        if (mDogMasterMap == null) {
            mDogMasterMap = new HashMap<>();

            Resources res = context.getResources();

            for (int i = 0; i < Config.KIND_NUM; i++) {
                Integer resId = res.getIdentifier("dog" + (i + 1), "array", context.getPackageName());
                String[] dogArray = res.getStringArray(resId);
                DogMasterEntity dogEntity = setDogMasterEntity(dogArray);

                mDogMasterMap.put(dogEntity.getId(), dogEntity);
            }
        }

        return mDogMasterMap;
    }

    /**
     * 犬種マスタをリストで返す
     *
     * @return ArrayList<DogMasterEntity>
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<DogMasterEntity> getDogMastersList(Context context) {
        if (mDogMasterList == null) {
            mDogMasterList = new ArrayList<>();

            String[] initialLabelList = context.getResources().getStringArray(R.array.initial_label_list);

            //犬種マスタを取得してソート
            HashMap<Integer, DogMasterEntity> tmp2 = Config.getDogMastersMap(context);
            ArrayList<DogMasterEntity> tmp = new ArrayList<DogMasterEntity>(tmp2.values());
            Collections.sort(tmp, new DogMasterKindComparator());
            Collections.sort(tmp, new DogMasterInitialLineComparator());

            for (int i = 0; i < tmp.size(); i++) {
                //頭文字行のラベルを追加
                if (i == 0 || !tmp.get(i).getInitialLine().equals(tmp.get(i - 1).getInitialLine())) {
                    DogMasterEntity values = new DogMasterEntity();
                    values.setKind(initialLabelList[tmp.get(i).getInitialLine()]);
                    mDogMasterList.add(values);
                }

                // 犬種をセット
                mDogMasterList.add(tmp.get(i));
            }
        }

        return mDogMasterList;
    }

    /**
     * 犬マスタエンティティにデータをセットする
     *
     * @param array 犬マスタ配列
     * @return data 犬マスタエンティティ
     */
    private static DogMasterEntity setDogMasterEntity(String[] array) {
        DogMasterEntity data = new DogMasterEntity();

        data.setId(Integer.parseInt(array[0]));
        data.setKind(array[1]);
        data.setFurigana(array[2]);
        data.setCategory(Integer.parseInt(array[3]));

        return data;
    }

    /**
     * DogMasterEntityのArrayListを種類順に並べる
     */
    @SuppressWarnings("rawtypes")
    private static class DogMasterKindComparator implements java.util.Comparator {
        public int compare(Object s, Object t) {
            DogMasterEntity before = (DogMasterEntity) s;
            DogMasterEntity after = (DogMasterEntity) t;

            return before.getFurigana().compareTo(after.getFurigana());
        }
    }

    /**
     * DogMasterEntityのArrayListをあかさたな順に並べる
     */
    @SuppressWarnings("rawtypes")
    private static class DogMasterInitialLineComparator implements java.util.Comparator {
        public int compare(Object s, Object t) {
            DogMasterEntity before = (DogMasterEntity) s;
            DogMasterEntity after = (DogMasterEntity) t;

            return before.getInitialLine().compareTo(after.getInitialLine());
        }
    }

    /**
     * 画像保存パスを返す
     *
     * @param context コンテキスト
     * @return String
     */
    public static String getImgDirPath(Context context) {
        String path = "";

        //外部ストレージが使用可能
        if (MediaUtils.IsExternalStorageAvailableAndWriteable()) {
            path = context.getExternalFilesDir(null).toString();
        }
        //外部ストレージは使用不可
        else {
            path = context.getFilesDir().toString();
        }

        return path;
    }

    /**
     * 画像保存tmpパスを返す
     *
     * @param context コンテキスト
     * @return String
     */
    public static String getImgTmpDirPath(Context context) {
        String path;

        //外部ストレージが使用可能
        if (MediaUtils.IsExternalStorageAvailableAndWriteable()) {
            path = context.getExternalFilesDir(TMP_DIR_NAME).toString();
        }
        //外部ストレージは使用不可
        else {
            path = context.getFilesDir().toString() + "/" + TMP_DIR_NAME;
        }

        //フォルダが存在しなければ作成
        if (!new File(path).exists()) {
            (new File(path)).mkdir();
        }


        return path;
    }

    /**
     * 画像保存tmpファイル名を返す
     *
     * @param context コンテキスト
     * @return String
     */
    public static String getImgTmpFilePath(Context context) {
        return getImgTmpDirPath(context) + "/" + TMP_DIR_FILENAME;
    }

    /**
     * 画像ファイル名を生成
     *
     * @param id ID
     * @return String
     */
    public static String getImgFileName(Integer id) {
        return "dog_" + id + ".jpg";
    }

    /**
     * 角丸にするピクセル数を取得
     *
     * @param context コンテキスト
     * @return Integer
     */
    public static Integer getKadomaruPixcel(Context context) {
        return AndroidUtils.dpToPixel(context, KADOMARU_DP);
    }
}