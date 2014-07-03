package com.tmrnk.gongon.dogage.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.annotation.SuppressLint;

import com.tmrnk.gongon.dogage.entity.DogMasterEntity;

/**
 * 設定ファイル
 * 
 * @access public
 */
public class Config
{
    //カテゴリ
    public static final Integer CATEGORY_SMALL = 1;                 //小型犬
    public static final Integer CATEGORY_MEDIUM = 2;                //中型犬
    public static final Integer CATEGORY_LARGE = 3;                 //大型犬

    //ラベル
    public static final String LABEL_SMALL = "小型犬";
    public static final String LABEL_MEDIUM = "中型犬";
    public static final String LABEL_LARGE = "大型犬";

    //0～2歳までに1年間で取る年齢
    public static final Double BEFORE_TWO_AGE_SMALL = 12.5;         //小型犬
    public static final Double BEFORE_TWO_AGE_MEDIUM = 10.5;        //中型犬
    public static final Double BEFORE_TWO_AGE_LAEGE = 9.0;          //大型犬

    //年齢計算方法境目
    public static final Integer AGE_CAL_BORDER = 1;

    //犬種マスタ
    public static final Integer DOG_MASTER_ID1 = 1;
    public static final String DOG_MASTER_KIND1 = "ミニチュアダックスフント";
    public static final Double DOG_MASTER_BEFORE_AGE1 = 4.32;
    public static final Integer DOG_MASTER_CATEGORY1 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID2 = 2;
    public static final String DOG_MASTER_KIND2 = "ボーダーテリア";
    public static final Double DOG_MASTER_BEFORE_AGE2 = 4.47;
    public static final Integer DOG_MASTER_CATEGORY2 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID3 = 3;
    public static final String DOG_MASTER_KIND3 = "ラサ・アプソ";
    public static final Double DOG_MASTER_BEFORE_AGE3 = 4.49;
    public static final Integer DOG_MASTER_CATEGORY3 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID4 = 4;
    public static final String DOG_MASTER_KIND4 = "シーズー";
    public static final Double DOG_MASTER_BEFORE_AGE4 = 4.78;
    public static final Integer DOG_MASTER_CATEGORY4 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID5 = 5;
    public static final String DOG_MASTER_KIND5 = "ウィペット・ミディアム";
    public static final Double DOG_MASTER_BEFORE_AGE5 = 5.30;
    public static final Integer DOG_MASTER_CATEGORY5 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID6 = 6;
    public static final String DOG_MASTER_KIND6 = "チワワ";
    public static final Double DOG_MASTER_BEFORE_AGE6 = 4.87;
    public static final Integer DOG_MASTER_CATEGORY6 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID7 = 7;
    public static final String DOG_MASTER_KIND7 = "ウエスト・ハイランド・ホワイト・テリア";
    public static final Double DOG_MASTER_BEFORE_AGE7 = 4.96;
    public static final Integer DOG_MASTER_CATEGORY7 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID8 = 8;
    public static final String DOG_MASTER_KIND8 = "ビーグル";
    public static final Double DOG_MASTER_BEFORE_AGE8 = 5.20;
    public static final Integer DOG_MASTER_CATEGORY8 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID9 = 9;
    public static final String DOG_MASTER_KIND9 = "ミニチュア・シュナウザー";
    public static final Double DOG_MASTER_BEFORE_AGE9 = 5.46;
    public static final Integer DOG_MASTER_CATEGORY9 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID10 = 10;
    public static final String DOG_MASTER_KIND10 = "コッカー・スパニエル";
    public static final Double DOG_MASTER_BEFORE_AGE10 = 5.55;
    public static final Integer DOG_MASTER_CATEGORY10 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID11 = 11;
    public static final String DOG_MASTER_KIND11 = "キャバリア・キングチャールズ・スパニエル";
    public static final Double DOG_MASTER_BEFORE_AGE11 = 5.77;
    public static final Integer DOG_MASTER_CATEGORY11 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID12 = 12;
    public static final String DOG_MASTER_KIND12 = "パグ";
    public static final Double DOG_MASTER_BEFORE_AGE12 = 5.95;
    public static final Integer DOG_MASTER_CATEGORY12 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID13 = 13;
    public static final String DOG_MASTER_KIND13 = "フレンチブルドッグ";
    public static final Double DOG_MASTER_BEFORE_AGE13 = 7.65;
    public static final Integer DOG_MASTER_CATEGORY13 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID14 = 14;
    public static final String DOG_MASTER_KIND14 = "その他小型犬";
    public static final Double DOG_MASTER_BEFORE_AGE14 = 4.0;
    public static final Integer DOG_MASTER_CATEGORY14 = CATEGORY_SMALL;

    public static final Integer DOG_MASTER_ID15 = 15;
    public static final String DOG_MASTER_KIND15 = "スパニエル";
    public static final Double DOG_MASTER_BEFORE_AGE15 = 5.46;
    public static final Integer DOG_MASTER_CATEGORY15 = CATEGORY_MEDIUM;

    public static final Integer DOG_MASTER_ID16 = 16;
    public static final String DOG_MASTER_KIND16 = "ラブラドールレトリバー";
    public static final Double DOG_MASTER_BEFORE_AGE16 = 5.74;
    public static final Integer DOG_MASTER_CATEGORY16 = CATEGORY_MEDIUM;

    public static final Integer DOG_MASTER_ID17 = 17;
    public static final String DOG_MASTER_KIND17 = "ゴールデンレトリバー";
    public static final Double DOG_MASTER_BEFORE_AGE17 = 5.74;
    public static final Integer DOG_MASTER_CATEGORY17 = CATEGORY_MEDIUM;

    public static final Integer DOG_MASTER_ID18 = 18;
    public static final String DOG_MASTER_KIND18 = "スタッフォードシャー・ブル・テリア";
    public static final Double DOG_MASTER_BEFORE_AGE18 = 5.33;
    public static final Integer DOG_MASTER_CATEGORY18 = CATEGORY_MEDIUM;

    public static final Integer DOG_MASTER_ID19 = 19;
    public static final String DOG_MASTER_KIND19 = "ブルドッグ";
    public static final Double DOG_MASTER_BEFORE_AGE19 = 13.42;
    public static final Integer DOG_MASTER_CATEGORY19 = CATEGORY_MEDIUM;

    public static final Integer DOG_MASTER_ID20 = 20;
    public static final String DOG_MASTER_KIND20 = "その他中型犬";
    public static final Double DOG_MASTER_BEFORE_AGE20 = 5.0;
    public static final Integer DOG_MASTER_CATEGORY20 = CATEGORY_MEDIUM;

    public static final Integer DOG_MASTER_ID21 = 21;
    public static final String DOG_MASTER_KIND21 = "ジャーマン・シェパード・ドッグ";
    public static final Double DOG_MASTER_BEFORE_AGE21 = 7.84;
    public static final Integer DOG_MASTER_CATEGORY21 = CATEGORY_LARGE;

    public static final Integer DOG_MASTER_ID22 = 22;
    public static final String DOG_MASTER_KIND22 = "ボクサー";
    public static final Double DOG_MASTER_BEFORE_AGE22 = 8.90;
    public static final Integer DOG_MASTER_CATEGORY22 = CATEGORY_LARGE;

    public static final Integer DOG_MASTER_ID23 = 23;
    public static final String DOG_MASTER_KIND23 = "その他大型犬";
    public static final Double DOG_MASTER_BEFORE_AGE23 = 7.0;
    public static final Integer DOG_MASTER_CATEGORY23 = CATEGORY_LARGE;

    /**
     * 犬種マスタをハッシュマップで返す
     * 
     * @param SQLiteDatabase db
     * @return HashMap<Integer, DogMasterEntity>
     * @access public
     */
    @SuppressLint("UseSparseArrays")
    public static HashMap<Integer, DogMasterEntity> getDogMastersMap()
    {
        HashMap<Integer, DogMasterEntity> dogMaster = new HashMap<Integer, DogMasterEntity>();

        dogMaster.put(DOG_MASTER_ID1, setDogMasterEntity(DOG_MASTER_ID1, DOG_MASTER_KIND1, DOG_MASTER_BEFORE_AGE1, DOG_MASTER_CATEGORY1));
        dogMaster.put(DOG_MASTER_ID2, setDogMasterEntity(DOG_MASTER_ID2, DOG_MASTER_KIND2, DOG_MASTER_BEFORE_AGE2, DOG_MASTER_CATEGORY2));
        dogMaster.put(DOG_MASTER_ID3, setDogMasterEntity(DOG_MASTER_ID3, DOG_MASTER_KIND3, DOG_MASTER_BEFORE_AGE3, DOG_MASTER_CATEGORY3));
        dogMaster.put(DOG_MASTER_ID4, setDogMasterEntity(DOG_MASTER_ID4, DOG_MASTER_KIND4, DOG_MASTER_BEFORE_AGE4, DOG_MASTER_CATEGORY4));
        dogMaster.put(DOG_MASTER_ID5, setDogMasterEntity(DOG_MASTER_ID5, DOG_MASTER_KIND5, DOG_MASTER_BEFORE_AGE5, DOG_MASTER_CATEGORY5));
        dogMaster.put(DOG_MASTER_ID6, setDogMasterEntity(DOG_MASTER_ID6, DOG_MASTER_KIND6, DOG_MASTER_BEFORE_AGE6, DOG_MASTER_CATEGORY6));
        dogMaster.put(DOG_MASTER_ID7, setDogMasterEntity(DOG_MASTER_ID7, DOG_MASTER_KIND7, DOG_MASTER_BEFORE_AGE7, DOG_MASTER_CATEGORY7));
        dogMaster.put(DOG_MASTER_ID8, setDogMasterEntity(DOG_MASTER_ID8, DOG_MASTER_KIND8, DOG_MASTER_BEFORE_AGE8, DOG_MASTER_CATEGORY8));
        dogMaster.put(DOG_MASTER_ID9, setDogMasterEntity(DOG_MASTER_ID9, DOG_MASTER_KIND9, DOG_MASTER_BEFORE_AGE9, DOG_MASTER_CATEGORY9));
        dogMaster.put(DOG_MASTER_ID10, setDogMasterEntity(DOG_MASTER_ID10, DOG_MASTER_KIND10, DOG_MASTER_BEFORE_AGE10, DOG_MASTER_CATEGORY10));
        dogMaster.put(DOG_MASTER_ID11, setDogMasterEntity(DOG_MASTER_ID11, DOG_MASTER_KIND11, DOG_MASTER_BEFORE_AGE11, DOG_MASTER_CATEGORY11));
        dogMaster.put(DOG_MASTER_ID12, setDogMasterEntity(DOG_MASTER_ID12, DOG_MASTER_KIND12, DOG_MASTER_BEFORE_AGE12, DOG_MASTER_CATEGORY12));
        dogMaster.put(DOG_MASTER_ID13, setDogMasterEntity(DOG_MASTER_ID13, DOG_MASTER_KIND13, DOG_MASTER_BEFORE_AGE13, DOG_MASTER_CATEGORY13));
        dogMaster.put(DOG_MASTER_ID14, setDogMasterEntity(DOG_MASTER_ID14, DOG_MASTER_KIND14, DOG_MASTER_BEFORE_AGE14, DOG_MASTER_CATEGORY14));
        dogMaster.put(DOG_MASTER_ID15, setDogMasterEntity(DOG_MASTER_ID15, DOG_MASTER_KIND15, DOG_MASTER_BEFORE_AGE15, DOG_MASTER_CATEGORY15));
        dogMaster.put(DOG_MASTER_ID16, setDogMasterEntity(DOG_MASTER_ID16, DOG_MASTER_KIND16, DOG_MASTER_BEFORE_AGE16, DOG_MASTER_CATEGORY16));
        dogMaster.put(DOG_MASTER_ID17, setDogMasterEntity(DOG_MASTER_ID17, DOG_MASTER_KIND17, DOG_MASTER_BEFORE_AGE17, DOG_MASTER_CATEGORY17));
        dogMaster.put(DOG_MASTER_ID18, setDogMasterEntity(DOG_MASTER_ID18, DOG_MASTER_KIND18, DOG_MASTER_BEFORE_AGE18, DOG_MASTER_CATEGORY18));
        dogMaster.put(DOG_MASTER_ID19, setDogMasterEntity(DOG_MASTER_ID19, DOG_MASTER_KIND19, DOG_MASTER_BEFORE_AGE19, DOG_MASTER_CATEGORY19));
        dogMaster.put(DOG_MASTER_ID20, setDogMasterEntity(DOG_MASTER_ID20, DOG_MASTER_KIND20, DOG_MASTER_BEFORE_AGE20, DOG_MASTER_CATEGORY20));
        dogMaster.put(DOG_MASTER_ID21, setDogMasterEntity(DOG_MASTER_ID21, DOG_MASTER_KIND21, DOG_MASTER_BEFORE_AGE21, DOG_MASTER_CATEGORY21));
        dogMaster.put(DOG_MASTER_ID22, setDogMasterEntity(DOG_MASTER_ID22, DOG_MASTER_KIND22, DOG_MASTER_BEFORE_AGE22, DOG_MASTER_CATEGORY22));
        dogMaster.put(DOG_MASTER_ID23, setDogMasterEntity(DOG_MASTER_ID23, DOG_MASTER_KIND23, DOG_MASTER_BEFORE_AGE23, DOG_MASTER_CATEGORY23));

        return dogMaster;
    }

    /**
     * 犬種マスタをリストで返す
     * 
     * @return ArrayList<DogMasterEntity>
     * @access private
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<DogMasterEntity> getDogMastersList()
    {
        ArrayList<DogMasterEntity> data = new ArrayList<DogMasterEntity>();

        //犬種マスタを取得してソート
        HashMap<Integer, DogMasterEntity> tmp2 = Config.getDogMastersMap();
        ArrayList<DogMasterEntity> tmp = new ArrayList<DogMasterEntity>(tmp2.values());
        Collections.sort(tmp, new DogMasterKindComparator());
        Collections.sort(tmp, new DogMasterOtherFlagComparator());
        Collections.sort(tmp, new DogMasterCategoryComparator());

        for (int i = 0; i < tmp.size(); i++) {
            //小型犬・中型犬・大型犬のラベルを追加
            if (i == 0 || tmp.get(i).getCategory() != tmp.get(i - 1).getCategory()) {
                DogMasterEntity values = new DogMasterEntity();
                values.setLabelFlag(1);

                if (i == 0) {
                    values.setKind(LABEL_SMALL);
                } else if (tmp.get(i).getCategory() == Config.CATEGORY_MEDIUM) {
                    values.setKind(LABEL_MEDIUM);
                } else {
                    values.setKind(LABEL_LARGE);
                }
                data.add(values);
            }

            // 犬種をセット
            data.add(tmp.get(i));
        }

        return data;
    }

    /**
     * 犬マスタエンティティにデータをセットする
     * 
     * @param Integer id
     * @param String name
     * @param Double overThreeAge
     * @param Integer category
     * @param Integer otherFlag
     * @return void
     * @access private
     */
    private static DogMasterEntity setDogMasterEntity(Integer id, String kind, Double overThreeAge, Integer category)
    {
        DogMasterEntity data = new DogMasterEntity();

        data.setId(id);
        data.setKind(kind);
        data.setOverThreeAge(overThreeAge);
        data.setCategory(category);

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

            return before.getKind().compareTo(after.getKind());
        }
    }

    /**
     * DogMasterEntiryのArrayListをその他フラグ順に並べる
     * 
     * @return void
     * @access private
     */
    @SuppressWarnings("rawtypes")
    private static class DogMasterOtherFlagComparator implements java.util.Comparator
    {
        public int compare(Object s, Object t)
        {
            DogMasterEntity before = (DogMasterEntity) s;
            DogMasterEntity after = (DogMasterEntity) t;

            return before.getOtherFlag().compareTo(after.getOtherFlag());
        }
    }

    /**
     * DogMasterEntiryのArrayListをカテゴリ順に並べる
     * 
     * @return void
     * @access private
     */
    @SuppressWarnings("rawtypes")
    private static class DogMasterCategoryComparator implements java.util.Comparator
    {
        public int compare(Object s, Object t)
        {
            DogMasterEntity before = (DogMasterEntity) s;
            DogMasterEntity after = (DogMasterEntity) t;

            return before.getCategory().compareTo(after.getCategory());
        }
    }
}
