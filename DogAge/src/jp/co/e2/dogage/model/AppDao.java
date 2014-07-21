package jp.co.e2.dogage.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Daoの基底クラス
 * 
 * @access public
 */
public class AppDao
{
    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     * 
     * @param ContentValues
     * @param String key
     * @param String value
     * @return ContentValues cv
     * @access public
     */
    public ContentValues put(ContentValues cv, String key, String value)
    {
        if (key != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     * 
     * @param ContentValues
     * @param String key
     * @param Integer value
     * @return ContentValues cv
     * @access public
     */
    public ContentValues put(ContentValues cv, String key, Integer value)
    {
        if (key != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     * 
     * @param ContentValues
     * @param String key
     * @param Double value
     * @return ContentValues cv
     * @access public
     */
    public ContentValues put(ContentValues cv, String key, Double value)
    {
        if (key != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     * 
     * @param ContentValues
     * @param String key
     * @param Float value
     * @return ContentValues cv
     * @access public
     */
    public ContentValues put(ContentValues cv, String key, Float value)
    {
        if (key != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     * 
     * @param ContentValues
     * @param String key
     * @param Long value
     * @return ContentValues cv
     * @access public
     */
    public ContentValues put(ContentValues cv, String key, Long value)
    {
        if (key != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、ContentValuesに値を入れる
     * 
     * @param ContentValues
     * @param String key
     * @param Boolean value
     * @return ContentValues cv
     * @access public
     */
    public ContentValues put(ContentValues cv, String key, Boolean value)
    {
        if (key != null) {
            cv.put(key, value);
        } else {
            cv.putNull(key);
        }

        return cv;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     * 
     * @param Cursor cursor
     * @param String key
     * @return Integer value
     * @access public
     */
    public Integer getInteger(Cursor cursor, String key)
    {
        Integer value = null;

        Integer i = cursor.getColumnIndex(key);

        if (cursor.isNull(i) == false) {
            value = cursor.getInt(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     * 
     * @param Cursor cursor
     * @param String key
     * @return String value
     * @access public
     */
    public String getString(Cursor cursor, String key)
    {
        String value = null;

        Integer i = cursor.getColumnIndex(key);

        if (cursor.isNull(i) == false) {
            value = cursor.getString(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     * 
     * @param Cursor cursor
     * @param String key
     * @return Double value
     * @access public
     */
    public Double getDouble(Cursor cursor, String key)
    {
        Double value = null;

        Integer i = cursor.getColumnIndex(key);

        if (cursor.isNull(i) == false) {
            value = cursor.getDouble(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     * 
     * @param Cursor cursor
     * @param String key
     * @return Float value
     * @access public
     */
    public Float getFloat(Cursor cursor, String key)
    {
        Float value = null;

        Integer i = cursor.getColumnIndex(key);

        if (cursor.isNull(i) == false) {
            value = cursor.getFloat(i);
        }

        return value;
    }

    /**
     * NULLかどうかを判定して、Cursorから値を取得する
     * 
     * @param Cursor cursor
     * @param String key
     * @return Long value
     * @access public
     */
    public Long getLong(Cursor cursor, String key)
    {
        Long value = null;

        Integer i = cursor.getColumnIndex(key);

        if (cursor.isNull(i) == false) {
            value = cursor.getLong(i);
            cursor.getLong(i);
        }

        return value;
    }
}
