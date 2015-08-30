package jp.co.e2.dogage.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Daoの基底クラス
 */
public class BaseDao {
    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    public ContentValues put(ContentValues cv, String key, String value) {
        if (value != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    public ContentValues put(ContentValues cv, String key, Integer value) {
        if (value != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    public ContentValues put(ContentValues cv, String key, Double value) {
        if (value != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    public ContentValues put(ContentValues cv, String key, Float value) {
        if (value != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    public ContentValues put(ContentValues cv, String key, Long value) {
        if (value != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     *
     * @param cv ContentValues
     * @param key カラム名
     * @param value 値
     * @return ContentValues cv
     */
    public ContentValues put(ContentValues cv, String key, Boolean value) {
        if (value != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Integer value
     */
    public Integer getInteger(Cursor cursor, String key) {
        Integer value = null;

        Integer i = cursor.getColumnIndex(key);

        if (!cursor.isNull(i)) {
            value = cursor.getInt(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return String value
     */
    public String getString(Cursor cursor, String key) {
        String value = null;

        Integer i = cursor.getColumnIndex(key);

        if (!cursor.isNull(i)) {
            value = cursor.getString(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Double value
     */
    public Double getDouble(Cursor cursor, String key) {
        Double value = null;

        Integer i = cursor.getColumnIndex(key);

        if (!cursor.isNull(i)) {
            value = cursor.getDouble(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Float value
     */
    public Float getFloat(Cursor cursor, String key) {
        Float value = null;

        Integer i = cursor.getColumnIndex(key);

        if (!cursor.isNull(i)) {
            value = cursor.getFloat(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Long value
     */
    public Long getLong(Cursor cursor, String key) {
        Long value = null;

        Integer i = cursor.getColumnIndex(key);

        if (!cursor.isNull(i)) {
            value = cursor.getLong(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     *
     * @param cursor Cursor
     * @param key カラム名
     * @return Boolean value
     */
    public Boolean getBoolean(Cursor cursor, String key) {
        Boolean value = null;

        if (getInteger(cursor, key) != null) {
            value = getInteger(cursor, key).equals(1);
        }

        return value;
    }
}
