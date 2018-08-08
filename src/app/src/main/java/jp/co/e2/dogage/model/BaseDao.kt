package jp.co.e2.dogage.model

import android.content.ContentValues
import android.database.Cursor

/**
 * Daoの基底クラス
 */
open class BaseDao {
    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    fun put(cv: ContentValues, key: String, value: String?): ContentValues {
        if (value != null) {
            cv.put(key, value)
        } else {
            cv.putNull(key)
        }

        return cv
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    fun put(cv: ContentValues, key: String, value: Int?): ContentValues {
        if (value != null) {
            cv.put(key, value)
        } else {
            cv.putNull(key)
        }

        return cv
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    fun put(cv: ContentValues, key: String, value: Double?): ContentValues {
        if (value != null) {
            cv.put(key, value)
        } else {
            cv.putNull(key)
        }

        return cv
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    fun put(cv: ContentValues, key: String, value: Float?): ContentValues {
        if (value != null) {
            cv.put(key, value)
        } else {
            cv.putNull(key)
        }

        return cv
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    fun put(cv: ContentValues, key: String, value: Long?): ContentValues {
        if (value != null) {
            cv.put(key, value)
        } else {
            cv.putNull(key)
        }

        return cv
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    fun put(cv: ContentValues, key: String, value: Boolean?): ContentValues {
        if (value != null) {
            cv.put(key, value)
        } else {
            cv.putNull(key)
        }

        return cv
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Integer value
     */
    fun getInteger(cursor: Cursor, key: String): Int? {
        var value: Int? = null

        val i = cursor.getColumnIndex(key)

        if (!cursor.isNull(i)) {
            value = cursor.getInt(i)
        }

        return value
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return String value
     */
    fun getString(cursor: Cursor, key: String): String? {
        var value: String? = null

        val i = cursor.getColumnIndex(key)

        if (!cursor.isNull(i)) {
            value = cursor.getString(i)
        }

        return value
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Double value
     */
    fun getDouble(cursor: Cursor, key: String): Double? {
        var value: Double? = null

        val i = cursor.getColumnIndex(key)

        if (!cursor.isNull(i)) {
            value = cursor.getDouble(i)
        }

        return value
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Float value
     */
    fun getFloat(cursor: Cursor, key: String): Float? {
        var value: Float? = null

        val i = cursor.getColumnIndex(key)

        if (!cursor.isNull(i)) {
            value = cursor.getFloat(i)
        }

        return value
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Long value
     */
    fun getLong(cursor: Cursor, key: String): Long? {
        var value: Long? = null

        val i = cursor.getColumnIndex(key)

        if (!cursor.isNull(i)) {
            value = cursor.getLong(i)
        }

        return value
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Boolean value
     */
    fun getBoolean(cursor: Cursor, key: String): Boolean? {
        var value: Boolean? = null

        if (getInteger(cursor, key) != null) {
            value = getInteger(cursor, key) == 1
        }

        return value
    }
}
