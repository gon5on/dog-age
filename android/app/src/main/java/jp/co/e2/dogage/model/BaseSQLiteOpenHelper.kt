package jp.co.e2.dogage.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction

/**
 * SQLiteOpenHelperのラッパークラス
 */
class BaseSQLiteOpenHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val VERSION1 = 1
        private const val VERSION2 = 2
        private const val VERSION3 = 3

        private const val DB_NAME = "database.db"             //データベース名
        private const val DB_VERSION = VERSION3               //データベースバージョン
    }

    /**
     * onCreate
     *
     * @param db SQLiteDatabase
     */
    override fun onCreate(db: SQLiteDatabase) {
        //テーブル作成
        db.execSQL(PetDao.CREATE_TABLE_SQL)
    }

    /**
     * onUpgrade
     *
     * @param db SQLiteDatabase
     * @param oldVersion 前のバージョン
     * @param newVersion 新しいバージョン
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < VERSION2) {
            //ペットテーブルに写真カラムを追加
            db.execSQL(PetDao.ALTER_TABLE_SQL)
            db.execSQL(PetDao.ALTER_TABLE_SQL2)
        }

        if (oldVersion < VERSION3) {
            //表示順カラムを追加
            db.execSQL(PetDao.ALTER_TABLE_SQL3)

            //現在の表示順をDBに保存する
            PetDao(context).updateOrder(db, null)
        }
    }
}