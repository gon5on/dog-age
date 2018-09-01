package jp.co.e2.dogage.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * SQLiteOpenHelperのラッパークラス
 */
class BaseSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val VERSION1 = 1
        private const val VERSION2 = 2

        private const val DB_NAME = "database.db"             //データベース名
        private const val DB_VERSION = VERSION2               //データベースバージョン
    }

    /**
     * onCreate
     *
     * @param db SQLiteDatabase
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()

        //テーブル作成
        db.execSQL(PetDao.CREATE_TABLE_SQL)

        db.setTransactionSuccessful()
    }

    /**
     * onUpgrade
     *
     * @param db SQLiteDatabase
     * @param oldVersion 前のバージョン
     * @param newVersion 新しいバージョン
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //ペットテーブルに写真カラムを追加
        if (oldVersion == VERSION1 && newVersion == VERSION2) {
            db.execSQL(PetDao.ALTER_TABLE_SQL)
            db.execSQL(PetDao.ALTER_TABLE_SQL2)
        }
    }
}