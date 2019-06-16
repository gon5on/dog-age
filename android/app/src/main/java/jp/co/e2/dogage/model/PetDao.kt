package jp.co.e2.dogage.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import jp.co.e2.dogage.common.DateHelper
import jp.co.e2.dogage.common.LogUtils
import jp.co.e2.dogage.common.MediaUtils
import jp.co.e2.dogage.entity.PetEntity
import java.util.*

/**
 * ペットテーブルへのデータアクセスオブジェクト
 */
class PetDao(private val context: Context) : BaseDao() {

    companion object {
        // テーブル名
        const val TABLE_NAME = "pets"

        // カラム名
        const val COLUMN_ID = "pets_id"
        const val COLUMN_NAME = "pets_name"
        const val COLUMN_BIRTHDAY = "pets_birthday"
        const val COLUMN_KIND = "pets_kind"
        const val COLUMN_PHOTO_FLG = "pets_photo_flg"
        const val COLUMN_ARCHIVE_DATE = "pets_archive_date"
        const val COLUMN_ORDER = "pets_order"
        const val COLUMN_CREATED = "pets_created"
        const val COLUMN_MODIFIED = "pets_modified"

        //CREATE TABLE文
        const val CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + "               INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + "             TEXT        NOT NULL," +
                COLUMN_BIRTHDAY + "         TEXT        NOT NULL," +
                COLUMN_KIND + "             INTEGER     NOT NULL," +
                COLUMN_PHOTO_FLG + "        INTEGER     NOT NULL        default 0," +
                COLUMN_ARCHIVE_DATE + "     TEXT," +
                COLUMN_ORDER + "            INTEGER," +
                COLUMN_CREATED + "          TEXT        NOT NULL," +
                COLUMN_MODIFIED + "         TEXT        NOT NULL" +
                ")"

        //ALTER TABLE文
        const val ALTER_TABLE_SQL = "ALTER TABLE " + TABLE_NAME +
                " ADD " + COLUMN_PHOTO_FLG + "INTEGER NOT NULL DEFAULT 0"

        const val ALTER_TABLE_SQL2 = "ALTER TABLE " + TABLE_NAME + " ADD " + COLUMN_ARCHIVE_DATE + "TEXT"

        const val ALTER_TABLE_SQL3 = "ALTER TABLE " + TABLE_NAME + " ADD " + COLUMN_ORDER + "INTEGER"
    }

    /**
     * インサート・アップデート
     *
     * @param db SQLiteDatabase
     * @param data データ
     * @return boolean 結果
     */
    @Throws(Exception::class)
    fun save(db: SQLiteDatabase, data: PetEntity): Boolean {
        val ret: Int

        val cv = ContentValues()
        put(cv, COLUMN_NAME, data.name)
        put(cv, COLUMN_BIRTHDAY, data.birthday)
        put(cv, COLUMN_KIND, data.kind)
        put(cv, COLUMN_PHOTO_FLG, data.photoFlg)
        put(cv, COLUMN_ORDER, data.order)
        put(cv, COLUMN_ARCHIVE_DATE, data.archiveDate)
        put(cv, COLUMN_MODIFIED, DateHelper().format(DateHelper.FMT_DATETIME))

        if (data.id == null) {
            //インサート
            put(cv, COLUMN_CREATED, DateHelper().format(DateHelper.FMT_DATETIME))
            ret = db.insert(TABLE_NAME, "", cv).toInt()

            data.id = ret
        } else {
            //アップデート
            val param = arrayOf(data.id.toString())
            ret = db.update(TABLE_NAME, cv, "$COLUMN_ID=?", param)
        }

        //画像保存
        if (data.photoFlg) {
            if (data.savePhotoUri != null) {
                val tmpPath = data.savePhotoUri!!.path
                val savePath = data.getImgFilePath(context)

                LogUtils.d("tmp file path : ", tmpPath)
                LogUtils.d("save file path : ", savePath)

                MediaUtils.copyFile(tmpPath, savePath)
            }
        } else {
            MediaUtils.deleteDirFile(data.getImgFilePath(context))
        }

        return (ret != -1)
    }

    /**
     * 全件データを取得する
     *
     * @param db SQLiteDatabase
     * @return ArrayList<PetEntity> data
    */
    fun findAll(db: SQLiteDatabase): ArrayList<PetEntity> {
        val data = ArrayList<PetEntity>()

        val sb = String.format("SELECT * FROM %s ORDER BY %s ASC, %s DESC", TABLE_NAME, COLUMN_ORDER, COLUMN_ID)

        val cursor = db.rawQuery(sb, null)

        if (cursor.moveToFirst()) {
            do {
                val values = PetEntity()
                values.id = getInteger(cursor, COLUMN_ID)
                values.name = getString(cursor, COLUMN_NAME)
                values.birthday = getString(cursor, COLUMN_BIRTHDAY)
                values.kind = getInteger(cursor, COLUMN_KIND)
                values.photoFlg = getBoolean(cursor, COLUMN_PHOTO_FLG) ?: false
                values.archiveDate = getString(cursor, COLUMN_ARCHIVE_DATE)
                values.order = getInteger(cursor, COLUMN_ORDER)
                values.created = getString(cursor, COLUMN_CREATED)
                values.modified = getString(cursor, COLUMN_MODIFIED)
                data.add(values)

            } while (cursor.moveToNext())
        }
        cursor.close()

        return data
    }

    /**
     * 誕生日からデータを取得する
     *
     * ※命日が存在しているデータは対象外
     *
     * @param db SQLiteDatabase
     * @param date 年月日
     * @return ArrayList<PetEntity> data
     */
    fun findByBirthday(db: SQLiteDatabase, date: String): ArrayList<PetEntity> {
        val data = ArrayList<PetEntity>()

        val sb = String.format("SELECT * FROM %s WHERE %s IS NULL AND %s LIKE '%%-%s' ORDER BY %s ASC, %s DESC",
                TABLE_NAME, COLUMN_ARCHIVE_DATE, COLUMN_BIRTHDAY, date, COLUMN_ORDER, COLUMN_ID)

        val cursor = db.rawQuery(sb, null)

        if (cursor.moveToFirst()) {
            do {
                val values = PetEntity()
                values.id = getInteger(cursor, COLUMN_ID)
                values.name = getString(cursor, COLUMN_NAME)
                values.birthday = getString(cursor, COLUMN_BIRTHDAY)
                values.kind = getInteger(cursor, COLUMN_KIND)
                values.photoFlg = getBoolean(cursor, COLUMN_PHOTO_FLG) ?: false
                values.archiveDate = getString(cursor, COLUMN_ARCHIVE_DATE)
                values.order = getInteger(cursor, COLUMN_ORDER)
                values.created = getString(cursor, COLUMN_CREATED)
                values.modified = getString(cursor, COLUMN_MODIFIED)
                data.add(values)

            } while (cursor.moveToNext())
        }
        cursor.close()

        return data
    }

    /**
     * 命日からデータを取得する
     *
     * @param db SQLiteDatabase
     * @param date 年月日
     * @return ArrayList<PetEntity> data
     */
    fun findByArchiveDate(db: SQLiteDatabase, date: String): ArrayList<PetEntity> {
        val data = ArrayList<PetEntity>()

        val sb = String.format("SELECT * FROM %s WHERE %s IS NOT null AND %s LIKE '%%-%s' ORDER BY %s ASC, %s DESC",
                TABLE_NAME, COLUMN_ARCHIVE_DATE, COLUMN_ARCHIVE_DATE, date, COLUMN_ORDER, COLUMN_ID)

        val cursor = db.rawQuery(sb, null)

        if (cursor.moveToFirst()) {
            do {
                val values = PetEntity()
                values.id = getInteger(cursor, COLUMN_ID)
                values.name = getString(cursor, COLUMN_NAME)
                values.birthday = getString(cursor, COLUMN_BIRTHDAY)
                values.kind = getInteger(cursor, COLUMN_KIND)
                values.photoFlg = getBoolean(cursor, COLUMN_PHOTO_FLG) ?: false
                values.archiveDate = getString(cursor, COLUMN_ARCHIVE_DATE)
                values.order = getInteger(cursor, COLUMN_ORDER)
                values.created = getString(cursor, COLUMN_CREATED)
                values.modified = getString(cursor, COLUMN_MODIFIED)
                data.add(values)

            } while (cursor.moveToNext())
        }
        cursor.close()

        return data
    }

    /**
     * レコード削除
     *
     * @param db SQLiteDatabase
     * @param id ID
     * @return boolean
     */
    fun deleteById(db: SQLiteDatabase, id: Int?): Boolean {
        val param = arrayOf(id.toString())

        val ret = db.delete(TABLE_NAME, "$COLUMN_ID = ?", param)

        return (ret != -1)
    }

    /**
     * 表示順を保存
     *
     * @param tmp データ
     * @param db SQLiteDatabase
     */
    fun updateOrder(db: SQLiteDatabase, tmp: ArrayList<PetEntity>?): Boolean  {
        val data = tmp ?: findAll(db)
        var index = 0

        data.forEach { entity ->
            index++
            entity.order = index

            if (!save(db, entity)) {
                return false
            }
        }

        return true
    }
}
