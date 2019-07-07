package jp.co.e2.dogage.common;

import android.util.Log;

import jp.co.e2.dogage.config.Config;

/**
 * ログのラッパークラス
 *
 * 出力するしないをフラグで切り替え可能
 */
public class LogUtils {
    private static final String TAG = "####";
    private static final boolean LOG_FLG = Config.LOG_FLG;           //このフラグでログを出力するかどうかを決められる、リリース時は0にすること

    /**
     * verboseログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void v(String tag, String value) {
        if (LOG_FLG) {
            Log.v(tag, value);
        }
    }

    /**
     * debugログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void d(String tag, String value) {
        if (LOG_FLG) {
            Log.d(tag, value);
        }
    }

    /**
     * infoログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void i(String tag, String value) {
        if (LOG_FLG) {
            Log.i(tag, value);
        }
    }

    /**
     * warnログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void w(String tag, String value) {
        if (LOG_FLG) {
            Log.w(tag, value);
        }
    }

    /**
     * errorログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void e(String tag, String value) {
        if (LOG_FLG) {
            Log.e(tag, value);
        }
    }

    /**
     * verboseログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void v(String value) {
        if (LOG_FLG) {
            Log.v(TAG, value);
        }
    }

    /**
     * debugログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void d(String value) {
        if (LOG_FLG) {
            Log.d(TAG, value);
        }
    }

    /**
     * infoログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void i(String value) {
        if (LOG_FLG) {
            Log.i(TAG, value);
        }
    }

    /**
     * warnログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void w(String value) {
        if (LOG_FLG) {
            Log.w(TAG, value);
        }
    }

    /**
     * errorログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void e(String value) {
        if (LOG_FLG) {
            Log.e(TAG, value);
        }
    }

    /**
     * verboseログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void v(String tag, Integer value) {
        if (LOG_FLG) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void d(String tag, Integer value) {
        if (LOG_FLG) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void i(String tag, Integer value) {
        if (LOG_FLG) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void w(String tag, Integer value) {
        if (LOG_FLG) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void e(String tag, Integer value) {
        if (LOG_FLG) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * verboseログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void v(Integer value) {
        if (LOG_FLG) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void d(Integer value) {
        if (LOG_FLG) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void i(Integer value) {
        if (LOG_FLG) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void w(Integer value) {
        if (LOG_FLG) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void e(Integer value) {
        if (LOG_FLG) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * verboseログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void v(String tag, Boolean value) {
        if (LOG_FLG) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void d(String tag, Boolean value) {
        if (LOG_FLG) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void i(String tag, Boolean value) {
        if (LOG_FLG) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void w(String tag, Boolean value) {
        if (LOG_FLG) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void e(String tag, Boolean value) {
        if (LOG_FLG) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * verboseログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void v(Boolean value) {
        if (LOG_FLG) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void d(Boolean value) {
        if (LOG_FLG) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void i(Boolean value) {
        if (LOG_FLG) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void w(Boolean value) {
        if (LOG_FLG) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void e(Boolean value) {
        if (LOG_FLG) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * verboseログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void v(String tag, long value) {
        if (LOG_FLG) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void d(String tag, long value) {
        if (LOG_FLG) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void i(String tag, long value) {
        if (LOG_FLG) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void w(String tag, long value) {
        if (LOG_FLG) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void e(String tag, long value) {
        if (LOG_FLG) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * verboseログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void v(long value) {
        if (LOG_FLG) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void d(long value) {
        if (LOG_FLG) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void i(long value) {
        if (LOG_FLG) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void w(long value) {
        if (LOG_FLG) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void e(long value) {
        if (LOG_FLG) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * verboseログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void v(String tag, Double value) {
        if (LOG_FLG) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void d(String tag, Double value) {
        if (LOG_FLG) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void i(String tag, Double value) {
        if (LOG_FLG) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void w(String tag, Double value) {
        if (LOG_FLG) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void e(String tag, Double value) {
        if (LOG_FLG) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * verboseログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void v(Double value) {
        if (LOG_FLG) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void d(Double value) {
        if (LOG_FLG) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void i(Double value) {
        if (LOG_FLG) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void w(Double value) {
        if (LOG_FLG) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void e(Double value) {
        if (LOG_FLG) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * verboseログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void v(String tag, float value) {
        if (LOG_FLG) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void d(String tag, float value) {
        if (LOG_FLG) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void i(String tag, float value) {
        if (LOG_FLG) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void w(String tag, float value) {
        if (LOG_FLG) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void e(String tag, float value) {
        if (LOG_FLG) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * verboseログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void v(float value) {
        if (LOG_FLG) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void d(float value) {
        if (LOG_FLG) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void i(float value) {
        if (LOG_FLG) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void w(float value) {
        if (LOG_FLG) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void e(float value) {
        if (LOG_FLG) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * verboseログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void v(String tag, Object value) {
        if (LOG_FLG) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void d(String tag, Object value) {
        if (LOG_FLG) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void i(String tag, Object value) {
        if (LOG_FLG) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void w(String tag, Object value) {
        if (LOG_FLG) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void e(String tag, Object value) {
        if (LOG_FLG) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * verboseログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void v(Object value) {
        if (LOG_FLG) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void d(Object value) {
        if (LOG_FLG) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void i(Object value) {
        if (LOG_FLG) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void w(Object value) {
        if (LOG_FLG) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void e(Object value) {
        if (LOG_FLG) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * verboseログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void v(String tag, CharSequence value) {
        if (LOG_FLG) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void d(String tag, CharSequence value) {
        if (LOG_FLG) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void i(String tag, CharSequence value) {
        if (LOG_FLG) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void w(String tag, CharSequence value) {
        if (LOG_FLG) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     *
     * @param tag タグ
     * @param value 値
     */
    public static void e(String tag, CharSequence value) {
        if (LOG_FLG) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * verboseログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void v(CharSequence value) {
        if (LOG_FLG) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void d(CharSequence value) {
        if (LOG_FLG) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void i(CharSequence value) {
        if (LOG_FLG) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void w(CharSequence value) {
        if (LOG_FLG) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     *
     * @param value 値
     */
    public static void e(CharSequence value) {
        if (LOG_FLG) {
            Log.e(TAG, String.valueOf(value));
        }
    }
}
