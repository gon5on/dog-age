package jp.co.e2.dogage.model;

import java.util.ArrayList;

import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.MediaUtils;
import jp.co.e2.dogage.config.Config;
import jp.co.e2.dogage.entity.PetEntity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ペットテーブルへのデータアクセスオブジェクト
 * 
 * @access public
 */
public class PetDao extends BaseDao
{
    // テーブル名
    public static final String TABLE_NAME = "pets";

    // カラム名
    public static final String COLUMN_ID = "pets_id";
    public static final String COLUMN_NAME = "pets_name";
    public static final String COLUMN_BIRTHDAY = "pets_birthday";
    public static final String COLUMN_KIND = "pets_kind";
    public static final String COLUMN_PHOTO_FLG = "pets_photo_flg";
    public static final String COLUMN_ARCHIVE_DATE = "pets_archive_date";
    public static final String COLUMN_CREATED = "pets_created";
    public static final String COLUMN_MODIFIED = "pets_modified";

    //CREATE TABLE文
    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + "               INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + "             TEXT        NOT NULL," +
                    COLUMN_BIRTHDAY + "         TEXT        NOT NULL," +
                    COLUMN_KIND + "             INTEGER     NOT NULL," +
                    COLUMN_PHOTO_FLG + "        INTEGER     NOT NULL        default 0," +
                    COLUMN_ARCHIVE_DATE + "     TEXT," +
                    COLUMN_CREATED + "          TEXT        NOT NULL," +
                    COLUMN_MODIFIED + "         TEXT        NOT NULL" +
                    ")";

    //ALTER TABLE文
    public static final String ALTER_TABLE_SQL = "ALTER TABLE " + TABLE_NAME +
            " ADD " + COLUMN_PHOTO_FLG + "INTEGER NOT NULL DEFAULT 0";

    public static final String ALTER_TABLE_SQL2 = "ALTER TABLE " + TABLE_NAME + " ADD " + COLUMN_ARCHIVE_DATE + "TEXT";

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

        Integer savedId;

        ContentValues cv = new ContentValues();
        put(cv, COLUMN_NAME, data.getName());
        put(cv, COLUMN_BIRTHDAY, data.getBirthday());
        put(cv, COLUMN_KIND, data.getKind());
        put(cv, COLUMN_PHOTO_FLG, data.getPhotoFlg());
        put(cv, COLUMN_ARCHIVE_DATE, data.getArchiveDate());
        put(cv, COLUMN_MODIFIED, new DateHelper().format(DateHelper.FMT_DATETIME));

        if (data.getId() == null) {
            put(cv, COLUMN_CREATED, new DateHelper().format(DateHelper.FMT_DATETIME));
            ret = db.insert(TABLE_NAME, "", cv);
            savedId = (int) ret;
        } else {
            String[] param = new String[] { String.valueOf(data.getId()) };
            ret = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", param);
            savedId = data.getId();
        }

        //画像保存
        if (data.getPhotoFlg() == 1) {
            if (data.getPhotoUri() != null) {
                String tmpPath = MediaUtils.getPathFromUri(mContext, data.getPhotoUri());
                String savePath = Config.getImgDirPath(mContext) + "/" + Config.getImgFileName(savedId);
                MediaUtils.copyFile(tmpPath, savePath);
            }
        } else {
            MediaUtils.deleteDirFile(Config.getImgDirPath(mContext) + "/" + Config.getImgFileName(savedId));
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
                data.setPhotoFlg(getInteger(cursor, COLUMN_PHOTO_FLG));
                data.setArchiveDate(getString(cursor, COLUMN_ARCHIVE_DATE));
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
        sb.append(String.format("ORDER BY %s DESC", COLUMN_ID));

        Cursor cursor = db.rawQuery(sb.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                PetEntity values = new PetEntity();
                values.setId(getInteger(cursor, COLUMN_ID));
                values.setName(getString(cursor, COLUMN_NAME));
                values.setBirthday(getString(cursor, COLUMN_BIRTHDAY));
                values.setKind(getInteger(cursor, COLUMN_KIND));
                values.setPhotoFlg(getInteger(cursor, COLUMN_PHOTO_FLG));
                values.setArchiveDate(getString(cursor, COLUMN_ARCHIVE_DATE));
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
