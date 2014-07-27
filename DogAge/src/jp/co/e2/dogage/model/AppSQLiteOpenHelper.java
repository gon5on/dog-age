package jp.co.e2.dogage.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelperのラッパークラス
 * 
 * @access public
 */
public class AppSQLiteOpenHelper extends SQLiteOpenHelper
{
    public static final Integer VERSION1 = 1;
    public static final Integer VERSION2 = 2;

    public static final String DB_NAME = "database.db";             //データベース名
    public static final int DB_VERSION = VERSION2;                  //データベースバージョン

    private Context mContext;                                       //コンテキスト

    /**
     * コンストラクタ
     * 
     * @param Context context コンテキスト
     * @access public
     */
    public AppSQLiteOpenHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);

        mContext = context;
    }

    /**
     * onCreate
     * 
     * @param SQLiteDatabase db
     * @return void
     * @access public
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            db.beginTransaction();

            //テーブル作成
            db.execSQL(PetDao.CREATE_TABLE_SQL);

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }

    /**
     * onUpgrade
     * 
     * @param SQLiteDatabase db
     * @param Integer oldVersion 前のバージョン
     * @param Integer newVersion 新しいバージョン
     * @return void
     * @access public
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //ペットテーブルに写真カラムを追加
        if (oldVersion == VERSION1 && newVersion == VERSION2) {
            db.execSQL(PetDao.ALTER_TABLE_SQL);
        }
    }
}