package com.tmrnk.gongon.dogage.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tmrnk.gongon.dogage.common.DateUtils;

/**
 * ペットテーブルへのデータアクセスオブジェクト
 * 
 * @access public
 */
public class PetDao extends AppDao
{
    // テーブル名
    public static final String TABLE_NAME = "pets";

    // カラム名
    public static final String COLUMN_ID = "pets_id";
    public static final String COLUMN_NAME = "pets_name";
    public static final String COLUMN_BIRTHDAY = "pets_birthday";
    public static final String COLUMN_KIND = "pets_kind";
    public static final String COLUMN_CREATED = "pets_created";
    public static final String COLUMN_MODIFIED = "pets_modified";

    //CREATE TABLE文
    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + "               INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + "             TEXT                NOT NULL," +
                    COLUMN_BIRTHDAY + "         TEXT                NOT NULL," +
                    COLUMN_KIND + "             INTEGER             NOT NULL," +
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
    public PetDao(Context context)
    {
        mContext = context;
    }

    /**
     * インサート・アップデート
     * 
     * @param SQLiteDatabase db
     * @param PetEntity data
     * @return void
     * @access public
     */
    public boolean save(SQLiteDatabase db, PetEntity data) throws Exception
    {
        long ret;

        ContentValues cv = new ContentValues();
        put(cv, COLUMN_NAME, data.getName());
        put(cv, COLUMN_BIRTHDAY, data.getBirthday());
        put(cv, COLUMN_KIND, data.getKind());
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
     * IDからデータを取得する
     * 
     * @param SQLiteDatabase db
     * @param Integer id
     * @return PetEntity values
     * @access public
     */
    public PetEntity findById(SQLiteDatabase db, Integer id)
    {
        PetEntity data = new PetEntity();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("SELECT * FROM %s ", TABLE_NAME));
        sb.append(String.format("WHERE %s = ?", COLUMN_ID));

        String[] param = new String[] { String.valueOf(id) };

        Cursor cursor = db.rawQuery(sb.toString(), param);

        if (cursor.moveToFirst()) {
            do {
                data.setId(getInteger(cursor, COLUMN_ID));
                data.setName(getString(cursor, COLUMN_NAME));
                data.setBirthday(getString(cursor, COLUMN_BIRTHDAY));
                data.setKind(getInteger(cursor, COLUMN_KIND));
                data.setCreated(getString(cursor, COLUMN_CREATED));
                data.setModified(getString(cursor, COLUMN_MODIFIED));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return data;
    }

    /**
     * 全件データを取得する
     * 
     * @param SQLiteDatabase db
     * @return ArrayList<PetEntity> data
     * @access public
     */
    public ArrayList<PetEntity> findAll(SQLiteDatabase db)
    {
        ArrayList<PetEntity> data = new ArrayList<PetEntity>();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("SELECT * FROM %s ", TABLE_NAME));
        sb.append(String.format("LEFT JOIN %s ", DogMasterDao.TABLE_NAME));
        sb.append(String.format("ON %s.%s = %s.%s ", TABLE_NAME, COLUMN_KIND, DogMasterDao.TABLE_NAME, DogMasterDao.COLUMN_ID));
        sb.append(String.format("ORDER BY %s.%s", TABLE_NAME, COLUMN_ID));

        Cursor cursor = db.rawQuery(sb.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                PetEntity values = new PetEntity();
                values.setId(getInteger(cursor, COLUMN_ID));
                values.setName(getString(cursor, COLUMN_NAME));
                values.setBirthday(getString(cursor, COLUMN_BIRTHDAY));
                values.setKind(getInteger(cursor, COLUMN_KIND));
                values.setKindName(getString(cursor, DogMasterDao.COLUMN_KIND_NAME));
                values.setCreated(getString(cursor, COLUMN_CREATED));
                values.setModified(getString(cursor, COLUMN_MODIFIED));
                data.add(values);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return data;
    }

    /**
     * レコード削除
     * 
     * @return boolean
     * @access public
     */
    public Boolean deleteById(SQLiteDatabase db, Integer id)
    {
        String[] param = new String[] { String.valueOf(id) };

        Integer ret = db.delete(TABLE_NAME, COLUMN_ID + " = ?", param);

        return (ret != -1) ? true : false;
    }
}
