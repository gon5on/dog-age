package jp.co.e2.dogage.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * プリファレンスについて便利なものをまとめたクラス
 *
 * newしなくても使える
 */
public class PreferenceUtils {
    /**
     * プリファレンスからInt型の値を取得
     *
     * @param context コンテキスト
     * @param name    名前
     * @param defo    デフォルト値
     * @return Integer
     */
    public static Integer get(Context context, String name, Integer defo) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(name, defo);
    }

    /**
     * プリファレンスからString型の値を取得
     *
     * @param context コンテキスト
     * @param name    名前
     * @param defo    デフォルト値
     * @return String
     */
    public static String get(Context context, String name, String defo) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(name, defo);
    }

    /**
     * プリファレンスからFloat型の値を取得
     *
     * @param context コンテキスト
     * @param name    名前
     * @param defo    デフォルト値
     * @return Long
     */
    public static Float get(Context context, String name, Float defo) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getFloat(name, defo);
    }

    /**
     * プリファレンスからLong型の値を取得
     *
     * @param context コンテキスト
     * @param name    名前
     * @param defo    デフォルト値
     * @return Long
     */
    public static Long get(Context context, String name, Long defo) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(name, defo);
    }

    /**
     * プリファレンスからBoolean型の値を取得
     *
     * @param context コンテキスト
     * @param name    名前
     * @param defo    デフォルト値
     * @return Boolean
     */
    public static Boolean get(Context context, String name, boolean defo) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(name, defo);
    }

    /**
     * プリファレンスにInt型の値を保存
     *
     * @param context コンテキスト
     * @param name    名前
     * @param value   保存する値
     */
    public static void save(Context context, String name, Integer value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(name, value).apply();
    }

    /**
     * プリファレンスにString型の値を保存
     *
     * @param context コンテキスト
     * @param name    名前
     * @param value   保存する値
     */
    public static void save(Context context, String name, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(name, value).apply();
    }

    /**
     * プリファレンスにFloat型の値を保存
     *
     * @param context コンテキスト
     * @param name    名前
     * @param value   保存する値
     */
    public static void save(Context context, String name, Float value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putFloat(name, value).apply();
    }

    /**
     * プリファレンスにLong型の値を保存
     *
     * @param context コンテキスト
     * @param name    名前
     * @param value   保存する値
     */
    public static void save(Context context, String name, Long value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(name, value).apply();
    }

    /**
     * プリファレンスにBoolean型の値を保存
     *
     * @param context コンテキスト
     * @param name    名前
     * @param value   保存する値
     */
    public static void save(Context context, String name, Boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(name, value).apply();
    }

    /**
     * プリファレンスの値を消す
     *
     * @param context コンテキスト
     * @param name    名前
     */
    public static void delete(Context context, String name) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().remove(name).apply();
    }

    /**
     * プリファレンスの値を全て消す
     *
     * @param context コンテキスト
     */
    public static void deleteAll(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().clear().apply();
    }
}
