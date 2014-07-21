package jp.co.e2.dogage.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * プリファレンスについて便利なものをまとめたクラス
 * 
 * newしなくても使える
 * 
 * @access public
 */
public class PrefarenceUtils
{
    /**
     * プリファレンスからInt型の値を取得
     * 
     * @param Context context
     * @param String name 名前
     * @param Integer defo デフォルト値
     * @return Integer
     * @access public
     */
    public static Integer get(Context context, String name, Integer defo)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(name, defo);
    }

    /**
     * プリファレンスからString型の値を取得
     * 
     * @param Context context
     * @param String name 名前
     * @param String defo デフォルト値
     * @return String
     * @access public
     */
    public static String get(Context context, String name, String defo)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(name, defo);
    }

    /**
     * プリファレンスからFloat型の値を取得
     * 
     * @param Context context
     * @param String name 名前
     * @param Float defo デフォルト値
     * @return Long
     * @access public
     */
    public static Float get(Context context, String name, Float defo)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getFloat(name, defo);
    }

    /**
     * プリファレンスからLong型の値を取得
     * 
     * @param Context context
     * @param String name 名前
     * @param Long defo デフォルト値
     * @return Long
     * @access public
     */
    public static Long get(Context context, String name, Long defo)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(name, defo);
    }

    /**
     * プリファレンスからBoolean型の値を取得
     * 
     * @param Context context
     * @param String name 名前
     * @param boolean defo デフォルト値
     * @return Boolean
     * @access public
     */
    public static Boolean get(Context context, String name, boolean defo)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(name, defo);
    }

    /**
     * プリファレンスにInt型の値を保存
     * 
     * @param Context context
     * @param String name 名前
     * @param Integer value 保存する値
     * @return void
     * @access public
     */
    public static void save(Context context, String name, Integer value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(name, value).commit();
    }

    /**
     * プリファレンスにString型の値を保存
     * 
     * @param Context context
     * @param String name 名前
     * @param String value 保存する値
     * @return void
     * @access public
     */
    public static void save(Context context, String name, String value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(name, value).commit();
    }

    /**
     * プリファレンスにFloat型の値を保存
     * 
     * @param Context context
     * @param String name 名前
     * @param Float value 保存する値
     * @return void
     * @access public
     */
    public static void save(Context context, String name, Float value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putFloat(name, value).commit();
    }

    /**
     * プリファレンスにLong型の値を保存
     * 
     * @param Context context
     * @param String name 名前
     * @param Long value 保存する値
     * @return void
     * @access public
     */
    public static void save(Context context, String name, Long value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(name, value).commit();
    }

    /**
     * プリファレンスにBoolean型の値を保存
     * 
     * @param Context context
     * @param String name 名前
     * @param boolean value 保存する値
     * @return void
     * @access public
     */
    public static void save(Context context, String name, Boolean value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(name, value).commit();
    }

    /**
     * プリファレンスの値を消す
     * 
     * @param Context context
     * @param String name 名前
     * @return void
     * @access public
     */
    public static void delete(Context context, String name)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().remove(name).commit();
    }

    /**
     * プリファレンスの値を全て消す
     * 
     * @param Context context
     * @return void
     * @access public
     */
    public static void deleteAll(Context context)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().clear().commit();
    }
}
