package com.tmrnk.gongon.dogage.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tmrnk.gongon.dogage.common.DateUtils;

/**
 * 犬マスタへのデータアクセスオブジェクト
 * 
 * @access public
 */
public class DogMasterDao extends AppDao
{
    //カテゴリ
    public static final Integer CATEGORY_SMALL = 1;
    public static final Integer CATEGORY_MEDIUM = 2;
    public static final Integer CATEGORY_LARGE = 3;

    //種類選択ダイアログ用ラベル
    public static final String LABEL_SMALL = "小型犬";
    public static final String LABEL_MEDIUM = "中型犬";
    public static final String LABEL_LARGE = "大型犬";
    public static final String LABEL_OTHER = "その他";

    // テーブル名
    public static final String TABLE_NAME = "dog_masters";

    // カラム名
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_KIND_NAME = "kind_name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_OTHER_FLAG = "other_flag";
    public static final String COLUMN_CREATED = "created";
    public static final String COLUMN_MODIFIED = "modified";

    //CREATE TABLE文
    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + "               INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_KIND_NAME + "        TEXT                NOT NULL," +
                    COLUMN_CATEGORY + "         INTEGER             NOT NULL," +
                    COLUMN_OTHER_FLAG + "       INTEGER             NOT NULL," +
                    COLUMN_CREATED + "          TEXT                NOT NULL," +
                    COLUMN_MODIFIED + "         TEXT                NOT NULL" +
                    ")";

    private Context mContext;                                           //コンテキスト

    /**
     * コンストラクタ
     * 
     * @param Context context コンテキスト
     * @access public
     */
    public DogMasterDao(Context context)
    {
        mContext = context;
    }

    /**
     * 初期データを投入
     * 
     * @param SQLiteDatabase db
     * @return void
     * @access public
     */
    public void insertInit(SQLiteDatabase db)
    {
        try {
            save(db, setDogMasterEntity("ミニチュアダックスフント", CATEGORY_SMALL, 0));
            save(db, setDogMasterEntity("トイカッププードル", CATEGORY_SMALL, 0));
            save(db, setDogMasterEntity("小型犬", CATEGORY_SMALL, 1));
            save(db, setDogMasterEntity("ゴールデンレトリバー", CATEGORY_MEDIUM, 0));
            save(db, setDogMasterEntity("ラブラドールレトリバー", CATEGORY_MEDIUM, 0));
            save(db, setDogMasterEntity("中型犬", CATEGORY_MEDIUM, 1));
            save(db, setDogMasterEntity("ビッグビッグドッグ", CATEGORY_LARGE, 0));
            save(db, setDogMasterEntity("大型犬", CATEGORY_LARGE, 1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 犬マスタエンティティにデータをセットする
     * 
     * @param String name
     * @param Integer category
     * @param Integer otherFlag
     * @return void
     * @access private
     */
    private DogMasterEntity setDogMasterEntity(String name, Integer category, Integer otherFlag)
    {
        DogMasterEntity data = new DogMasterEntity();

        data.setKindName(name);
        data.setCategory(category);
        data.setOtherFlag(otherFlag);

        return data;
    }

    /**
     * インサート・アップデート
     * 
     * @param SQLiteDatabase db
     * @param DogMasterEntity data
     * @return Boolean
     * @access public
     */
    public Boolean save(SQLiteDatabase db, DogMasterEntity data) throws Exception
    {
        long ret;

        ContentValues cv = new ContentValues();
        put(cv, COLUMN_KIND_NAME, data.getKindName());
        put(cv, COLUMN_CATEGORY, data.getCategory());
        put(cv, COLUMN_OTHER_FLAG, data.getOtherFlag());
        put(cv, COLUMN_MODIFIED, new DateUtils().format(DateUtils.FMT_DATETIME));

        if (data.getId() == null) {
            put(cv, COLUMN_CREATED, new DateUtils().format(DateUtils.FMT_DATETIME));
            ret = db.insert(TABLE_NAME, "", cv);
        } else {
            String[] param = new String[] { String.valueOf(data.getId()) };
            ret = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", param);
        }

        return (ret != -1) ? true : false;
    }

    /**
     * 全件データを取得する
     * 
     * @param SQLiteDatabase db
     * @return ArrayList<DogMasterEntity> data
     * @access public
     */
    public ArrayList<DogMasterEntity> findAll(SQLiteDatabase db)
    {
        ArrayList<DogMasterEntity> data = new ArrayList<DogMasterEntity>();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("SELECT * FROM %s ", TABLE_NAME));
        sb.append(String.format("ORDER BY %s, %s", COLUMN_CATEGORY, COLUMN_OTHER_FLAG));

        Cursor cursor = db.rawQuery(sb.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                DogMasterEntity values = new DogMasterEntity();
                values.setId(getInteger(cursor, COLUMN_ID));
                values.setKindName(getString(cursor, COLUMN_KIND_NAME));
                values.setCategory(getInteger(cursor, COLUMN_CATEGORY));
                values.setOtherFlag(getInteger(cursor, COLUMN_OTHER_FLAG));
                values.setCreated(getString(cursor, COLUMN_CREATED));
                values.setModified(getString(cursor, COLUMN_MODIFIED));
                data.add(values);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return data;
    }

    /**
     * 種類選択ダイアログ用のデータを取得する
     * 
     * @param SQLiteDatabase db
     * @return ArrayList<DogMasterEntity> data
     * @access public
     */
    public ArrayList<DogMasterEntity> findForKindSelectDialog(SQLiteDatabase db)
    {
        ArrayList<DogMasterEntity> data = new ArrayList<DogMasterEntity>();

        ArrayList<DogMasterEntity> tmp = findAll(db);

        //最初に小型犬のラベルをセット
        DogMasterEntity values = new DogMasterEntity();
        values.setKindName(LABEL_SMALL);
        values.setLabelFlag(1);
        data.add(values);

        for (int i = 0; i < tmp.size(); i++) {
            DogMasterEntity tmp2 = tmp.get(i);

            //中型犬のラベルをセット
            if (i != 0 && tmp2.getCategory() == CATEGORY_MEDIUM && tmp2.getCategory() != tmp.get(i - 1).getCategory()) {
                values = new DogMasterEntity();
                values.setKindName(LABEL_MEDIUM);
                values.setLabelFlag(1);
                data.add(values);
            }
            //大型犬のラベルをセット
            if (i != 0 && tmp2.getCategory() == CATEGORY_LARGE && tmp2.getCategory() != tmp.get(i - 1).getCategory()) {
                values = new DogMasterEntity();
                values.setKindName(LABEL_LARGE);
                values.setLabelFlag(1);
                data.add(values);
            }

            values = new DogMasterEntity();
            values.setId(tmp.get(i).getId());
            values.setLabelFlag(tmp.get(i).getLabelFlag());

            if (tmp2.getOtherFlag() == 1) {
                values.setKindName(LABEL_OTHER + tmp.get(i).getKindName());
            } else {
                values.setKindName(tmp.get(i).getKindName());
            }

            data.add(values);
        }

        return data;
    }
}
