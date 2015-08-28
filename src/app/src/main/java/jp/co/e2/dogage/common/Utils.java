package jp.co.e2.dogage.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;

/**
 * 便利なものまとめたクラス
 *
 * newしなくても使える
 *
 * @access public
 */
public class Utils {
    /**
     * Implode
     *
     * @param list      文字列配列
     * @param delimiter デリミタ
     * @return String 連結文字列
     * @access public
     */
    public static String implode(ArrayList<String> list, String delimiter) {
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
     * Implode
     *
     * @param list      文字列配列
     * @param delimiter デリミタ
     * @return String 連結文字列
     * @access public
     */
    public static String implode(String[] list, String delimiter) {
        StringBuilder sb = new StringBuilder();

        if (list != null && list.length != 0) {
            for (int i = 0; i < list.length; i++) {
                if (sb.length() != 0) {
                    sb.append(delimiter);
                }

                sb.append(list[i]);
            }
        }

        return sb.toString();
    }

    /**
     * Implode
     *
     * @param list      文字列配列
     * @param delimiter デリミタ
     * @return String 連結文字列
     * @access public
     */
    public static String implode(Integer[] list, String delimiter) {
        StringBuilder sb = new StringBuilder();

        if (list != null && list.length != 0) {
            for (int i = 0; i < list.length; i++) {
                if (sb.length() != 0) {
                    sb.append(delimiter);
                }

                sb.append(list[i]);
            }
        }

        return sb.toString();
    }

    /**
     * オブジェクトをString型に変換する
     *
     * @param value
     * @return String
     * @access public
     */
    public static String objToString(Object value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    /**
     * オブジェクトをInteger型に変換する
     *
     * @param value
     * @return Integer
     * @access public
     */
    public static Integer objToInteger(Object value) {
        if (value == null) {
            return null;
        }
        return Integer.parseInt(String.valueOf(value));
    }

    /**
     * オブジェクトをDouble型に変換する
     *
     * @param value
     * @return Integer
     * @access public
     */
    public static Double objToDouble(Object value) {
        if (value == null) {
            return null;
        }
        return Double.parseDouble(String.valueOf(value));
    }

    /**
     * オブジェクトをFloat型に変換する
     *
     * @param value
     * @return Integer
     * @access public
     */
    public static Float objToFloat(Object value) {
        if (value == null) {
            return null;
        }
        return Float.parseFloat(String.valueOf(value));
    }

    /**
     * オブジェクトをLong型に変換する
     *
     * @param value
     * @return Integer
     * @access public
     */
    public static Long objToLong(Object value) {
        if (value == null) {
            return null;
        }
        return Long.parseLong(String.valueOf(value));
    }

    /**
     * ファイルからテキストを読みだす
     *
     * @param context
     * @param resId   ファイルのリソースID
     * @return Integer
     * @throws IOException
     * @access public
     */
    public static String readTextFile(Context context, Integer resId) throws IOException {
        InputStream is = null;
        BufferedReader br = null;

        StringBuilder sb = new StringBuilder();

        try {
            is = context.getResources().openRawResource(resId);
            br = new BufferedReader(new InputStreamReader(is));

            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return sb.toString();
    }

    /**
     * ひらがなをカタカナに変換
     *
     * @return String
     * @access public
     */
    public static String hiragana2katakana(String s) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            char code = s.charAt(i);

            if ((code >= 0x3041) && (code <= 0x3093)) {
                buf.append((char) (code + 0x60));
            } else {
                buf.append(code);
            }
        }
        return buf.toString();
    }
}
