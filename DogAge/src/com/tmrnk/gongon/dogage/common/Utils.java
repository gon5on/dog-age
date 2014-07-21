package com.tmrnk.gongon.dogage.common;

import java.util.ArrayList;

/**
 * 便利なものまとめたクラス
 * 
 * newしなくても使える
 * 
 * @access public
 */
public class Utils
{
    /**
     * Implode
     * 
     * @param ArrayList<String> list 文字列配列
     * @param String delimiter デリミタ
     * @return String 連結文字列
     * @access public
     */
    public static String implode(ArrayList<String> list, String delimiter)
    {
        StringBuilder sb = new StringBuilder();

        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                if (sb.length() != 0) {
                    sb.append(delimiter);
                }

                sb.append(list.get(i));
            }
        }

        return sb.toString();
    }

    /**
     * オブジェクトをString型に変換する
     * 
     * @param Object value
     * @return String
     * @access public
     */
    public static String objToString(Object value)
    {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    /**
     * オブジェクトをInteger型に変換する
     * 
     * @param Object value
     * @return Integer
     * @access public
     */
    public static Integer objToInteger(Object value)
    {
        if (value == null) {
            return null;
        }
        return Integer.parseInt(String.valueOf(value));
    }

    /**
     * オブジェクトをDouble型に変換する
     * 
     * @param Object value
     * @return Integer
     * @access public
     */
    public static Double objToDouble(Object value)
    {
        if (value == null) {
            return null;
        }
        return Double.parseDouble(String.valueOf(value));
    }

    /**
     * オブジェクトをFloat型に変換する
     * 
     * @param Object value
     * @return Integer
     * @access public
     */
    public static Float objToFloat(Object value)
    {
        if (value == null) {
            return null;
        }
        return Float.parseFloat(String.valueOf(value));
    }

    /**
     * オブジェクトをLong型に変換する
     * 
     * @param Object value
     * @return Integer
     * @access public
     */
    public static Long objToLong(Object value)
    {
        if (value == null) {
            return null;
        }
        return Long.parseLong(String.valueOf(value));
    }
}
