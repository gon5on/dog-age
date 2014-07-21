package jp.co.e2.dogage.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.entity.DogMasterEntity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

/**
 * 設定ファイル
 * 
 * @access public
 */
public class Config
{
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

    //犬種数
    public static final Integer KIND_NUM = 101;

    //カテゴリ
    public static final Integer CATEGORY_SMALL = 1;                             //小型犬
    public static final Integer CATEGORY_MEDIUM = 2;                            //中型犬
    public static final Integer CATEGORY_LARGE = 3;                             //大型犬

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

    /**
     * 犬種マスタを配列で返す
     * 
     * @return ArrayList<DogMasterEntity>
     * @access private
     */
    @SuppressLint("UseSparseArrays")
    public static HashMap<Integer, DogMasterEntity> getDogMastersMap(Context context)
    {
        if (mDogMasterMap == null) {
            mDogMasterMap = new HashMap<Integer, DogMasterEntity>();

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
     * @access private
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<DogMasterEntity> getDogMastersList(Context context)
    {
        if (mDogMasterList == null) {
            mDogMasterList = new ArrayList<DogMasterEntity>();

            String[] inicialLabelList = context.getResources().getStringArray(R.array.initial_label_list);

            //犬種マスタを取得してソート
            HashMap<Integer, DogMasterEntity> tmp2 = Config.getDogMastersMap(context);
            ArrayList<DogMasterEntity> tmp = new ArrayList<DogMasterEntity>(tmp2.values());
            Collections.sort(tmp, new DogMasterKindComparator());
            Collections.sort(tmp, new DogMasterInicialLineComparator());

            for (int i = 0; i < tmp.size(); i++) {
                //頭文字行のラベルを追加
                if (i == 0 || tmp.get(i).getInitialLine() != tmp.get(i - 1).getInitialLine()) {
                    DogMasterEntity values = new DogMasterEntity();
                    values.setKind(inicialLabelList[tmp.get(i).getInitialLine()]);
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
     * @param Integer String[] array
     * @return void
     * @access private
     */
    private static DogMasterEntity setDogMasterEntity(String[] array)
    {
        DogMasterEntity data = new DogMasterEntity();

        data.setId(Integer.parseInt(array[0]));
        data.setKind(array[1]);
        data.setFurigana(array[2]);
        data.setCategory(Integer.parseInt(array[3]));

        return data;
    }

    /**
     * DogMasterEntiryのArrayListを種類順に並べる
     * 
     * @return void
     * @access private
     */
    @SuppressWarnings("rawtypes")
    private static class DogMasterKindComparator implements java.util.Comparator
    {
        public int compare(Object s, Object t)
        {
            DogMasterEntity before = (DogMasterEntity) s;
            DogMasterEntity after = (DogMasterEntity) t;

            return before.getFurigana().compareTo(after.getFurigana());
        }
    }

    /**
     * DogMasterEntiryのArrayListをあかさたな順に並べる
     * 
     * @return void
     * @access private
     */
    @SuppressWarnings("rawtypes")
    private static class DogMasterInicialLineComparator implements java.util.Comparator
    {
        public int compare(Object s, Object t)
        {
            DogMasterEntity before = (DogMasterEntity) s;
            DogMasterEntity after = (DogMasterEntity) t;

            return before.getInitialLine().compareTo(after.getInitialLine());
        }
    }
}
