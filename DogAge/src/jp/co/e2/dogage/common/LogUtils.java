package jp.co.e2.dogage.common;

import android.util.Log;

/**
 * ログのヘルパークラス
 * 
 * 出力するしないをフラグで切り替え可能
 * 
 * @access public
 */
public class LogUtils
{
    private static final String TAG = "####";
    private static final Integer DISP_FLG = 1;      //このフラグでログを出力するかどうかを決められる、リリース時は0にすること

    /**
     * varboseログ
     * 
     * @param String tag
     * @param String value
     * @return void
     * @access public
     */
    public static void v(String tag, String value)
    {
        if (DISP_FLG == 1) {
            Log.v(tag, value);
        }
    }

    /**
     * debugログ
     * 
     * @param String tag
     * @param String value
     * @return void
     * @access public
     */
    public static void d(String tag, String value)
    {
        if (DISP_FLG == 1) {
            Log.d(tag, value);
        }
    }

    /**
     * infoログ
     * 
     * @param String tag
     * @param String value
     * @return void
     * @access public
     */
    public static void i(String tag, String value)
    {
        if (DISP_FLG == 1) {
            Log.i(tag, value);
        }
    }

    /**
     * warnログ
     * 
     * @param String tag
     * @param String value
     * @return void
     * @access public
     */
    public static void w(String tag, String value)
    {
        if (DISP_FLG == 1) {
            Log.w(tag, value);
        }
    }

    /**
     * errorログ
     * 
     * @param String tag
     * @param String value
     * @return void
     * @access public
     */
    public static void e(String tag, String value)
    {
        if (DISP_FLG == 1) {
            Log.e(tag, value);
        }
    }

    /**
     * varboseログ（タグ固定ver）
     * 
     * @param String value
     * @return void
     * @access public
     */
    public static void v(String value)
    {
        if (DISP_FLG == 1) {
            Log.v(TAG, value);
        }
    }

    /**
     * debugログ（タグ固定ver）
     * 
     * @param String value
     * @return void
     * @access public
     */
    public static void d(String value)
    {
        if (DISP_FLG == 1) {
            Log.d(TAG, value);
        }
    }

    /**
     * infoログ（タグ固定ver）
     * 
     * @param String value
     * @return void
     * @access public
     */
    public static void i(String value)
    {
        if (DISP_FLG == 1) {
            Log.i(TAG, value);
        }
    }

    /**
     * warnログ（タグ固定ver）
     * 
     * @param String value
     * @return void
     * @access public
     */
    public static void w(String value)
    {
        if (DISP_FLG == 1) {
            Log.w(TAG, value);
        }
    }

    /**
     * errorログ（タグ固定ver）
     * 
     * @param String value
     * @return void
     * @access public
     */
    public static void e(String value)
    {
        if (DISP_FLG == 1) {
            Log.e(TAG, value);
        }
    }

    /**
     * varboseログ
     * 
     * @param String tag
     * @param Integer value
     * @return void
     * @access public
     */
    public static void v(String tag, Integer value)
    {
        if (DISP_FLG == 1) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     * 
     * @param String tag
     * @param Integer value
     * @return void
     * @access public
     */
    public static void d(String tag, Integer value)
    {
        if (DISP_FLG == 1) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     * 
     * @param String tag
     * @param Integer value
     * @return void
     * @access public
     */
    public static void i(String tag, Integer value)
    {
        if (DISP_FLG == 1) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     * 
     * @param String tag
     * @param Integer value
     * @return void
     * @access public
     */
    public static void w(String tag, Integer value)
    {
        if (DISP_FLG == 1) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     * 
     * @param String tag
     * @param Integer value
     * @return void
     * @access public
     */
    public static void e(String tag, Integer value)
    {
        if (DISP_FLG == 1) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * varboseログ（タグ固定ver）
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public static void v(Integer value)
    {
        if (DISP_FLG == 1) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public static void d(Integer value)
    {
        if (DISP_FLG == 1) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public static void i(Integer value)
    {
        if (DISP_FLG == 1) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public static void w(Integer value)
    {
        if (DISP_FLG == 1) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     * 
     * @param Integer value
     * @return void
     * @access public
     */
    public static void e(Integer value)
    {
        if (DISP_FLG == 1) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * varboseログ
     * 
     * @param String tag
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void v(String tag, Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     * 
     * @param String tag
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void d(String tag, Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     * 
     * @param String tag
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void i(String tag, Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     * 
     * @param String tag
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void w(String tag, Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     * 
     * @param String tag
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void e(String tag, Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * varboseログ（タグ固定ver）
     * 
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void v(Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     * 
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void d(Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     * 
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void i(Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     * 
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void w(Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     * 
     * @param Boolean value
     * @return void
     * @access public
     */
    public static void e(Boolean value)
    {
        if (DISP_FLG == 1) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * varboseログ
     * 
     * @param String tag
     * @param long value
     * @return void
     * @access public
     */
    public static void v(String tag, long value)
    {
        if (DISP_FLG == 1) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     * 
     * @param String tag
     * @param long value
     * @return void
     * @access public
     */
    public static void d(String tag, long value)
    {
        if (DISP_FLG == 1) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     * 
     * @param String tag
     * @param long value
     * @return void
     * @access public
     */
    public static void i(String tag, long value)
    {
        if (DISP_FLG == 1) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     * 
     * @param String tag
     * @param long value
     * @return void
     * @access public
     */
    public static void w(String tag, long value)
    {
        if (DISP_FLG == 1) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     * 
     * @param String tag
     * @param long value
     * @return void
     * @access public
     */
    public static void e(String tag, long value)
    {
        if (DISP_FLG == 1) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * varboseログ（タグ固定ver）
     * 
     * @param long value
     * @return void
     * @access public
     */
    public static void v(long value)
    {
        if (DISP_FLG == 1) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     * 
     * @param long value
     * @return void
     * @access public
     */
    public static void d(long value)
    {
        if (DISP_FLG == 1) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     * 
     * @param long value
     * @return void
     * @access public
     */
    public static void i(long value)
    {
        if (DISP_FLG == 1) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     * 
     * @param long value
     * @return void
     * @access public
     */
    public static void w(long value)
    {
        if (DISP_FLG == 1) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     * 
     * @param long value
     * @return void
     * @access public
     */
    public static void e(long value)
    {
        if (DISP_FLG == 1) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * varboseログ
     * 
     * @param String tag
     * @param Double value
     * @return void
     * @access public
     */
    public static void v(String tag, Double value)
    {
        if (DISP_FLG == 1) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     * 
     * @param String tag
     * @param Double value
     * @return void
     * @access public
     */
    public static void d(String tag, Double value)
    {
        if (DISP_FLG == 1) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     * 
     * @param String tag
     * @param Double value
     * @return void
     * @access public
     */
    public static void i(String tag, Double value)
    {
        if (DISP_FLG == 1) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     * 
     * @param String tag
     * @param Double value
     * @return void
     * @access public
     */
    public static void w(String tag, Double value)
    {
        if (DISP_FLG == 1) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     * 
     * @param String tag
     * @param Double value
     * @return void
     * @access public
     */
    public static void e(String tag, Double value)
    {
        if (DISP_FLG == 1) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * varboseログ（タグ固定ver）
     * 
     * @param Double value
     * @return void
     * @access public
     */
    public static void v(Double value)
    {
        if (DISP_FLG == 1) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     * 
     * @param Double value
     * @return void
     * @access public
     */
    public static void d(Double value)
    {
        if (DISP_FLG == 1) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     * 
     * @param Double value
     * @return void
     * @access public
     */
    public static void i(Double value)
    {
        if (DISP_FLG == 1) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     * 
     * @param Double value
     * @return void
     * @access public
     */
    public static void w(Double value)
    {
        if (DISP_FLG == 1) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     * 
     * @param Double value
     * @return void
     * @access public
     */
    public static void e(Double value)
    {
        if (DISP_FLG == 1) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * varboseログ
     * 
     * @param String tag
     * @param float value
     * @return void
     * @access public
     */
    public static void v(String tag, float value)
    {
        if (DISP_FLG == 1) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     * 
     * @param String tag
     * @param float value
     * @return void
     * @access public
     */
    public static void d(String tag, float value)
    {
        if (DISP_FLG == 1) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     * 
     * @param String tag
     * @param float value
     * @return void
     * @access public
     */
    public static void i(String tag, float value)
    {
        if (DISP_FLG == 1) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     * 
     * @param String tag
     * @param float value
     * @return void
     * @access public
     */
    public static void w(String tag, float value)
    {
        if (DISP_FLG == 1) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     * 
     * @param String tag
     * @param float value
     * @return void
     * @access public
     */
    public static void e(String tag, float value)
    {
        if (DISP_FLG == 1) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * varboseログ（タグ固定ver）
     * 
     * @param float value
     * @return void
     * @access public
     */
    public static void v(float value)
    {
        if (DISP_FLG == 1) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     * 
     * @param float value
     * @return void
     * @access public
     */
    public static void d(float value)
    {
        if (DISP_FLG == 1) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     * 
     * @param float value
     * @return void
     * @access public
     */
    public static void i(float value)
    {
        if (DISP_FLG == 1) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     * 
     * @param float value
     * @return void
     * @access public
     */
    public static void w(float value)
    {
        if (DISP_FLG == 1) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     * 
     * @param float value
     * @return void
     * @access public
     */
    public static void e(float value)
    {
        if (DISP_FLG == 1) {
            Log.e(TAG, String.valueOf(value));
        }
    }

    /**
     * varboseログ
     * 
     * @param String tag
     * @param Object value
     * @return void
     * @access public
     */
    public static void v(String tag, Object value)
    {
        if (DISP_FLG == 1) {
            Log.v(tag, String.valueOf(value));
        }
    }

    /**
     * debugログ
     * 
     * @param String tag
     * @param Object value
     * @return void
     * @access public
     */
    public static void d(String tag, Object value)
    {
        if (DISP_FLG == 1) {
            Log.d(tag, String.valueOf(value));
        }
    }

    /**
     * infoログ
     * 
     * @param String tag
     * @param Object value
     * @return void
     * @access public
     */
    public static void i(String tag, Object value)
    {
        if (DISP_FLG == 1) {
            Log.i(tag, String.valueOf(value));
        }
    }

    /**
     * warnログ
     * 
     * @param String tag
     * @param Object value
     * @return void
     * @access public
     */
    public static void w(String tag, Object value)
    {
        if (DISP_FLG == 1) {
            Log.w(tag, String.valueOf(value));
        }
    }

    /**
     * errorログ
     * 
     * @param String tag
     * @param Object value
     * @return void
     * @access public
     */
    public static void e(String tag, Object value)
    {
        if (DISP_FLG == 1) {
            Log.e(tag, String.valueOf(value));
        }
    }

    /**
     * varboseログ（タグ固定ver）
     * 
     * @param Object value
     * @return void
     * @access public
     */
    public static void v(Object value)
    {
        if (DISP_FLG == 1) {
            Log.v(TAG, String.valueOf(value));
        }
    }

    /**
     * debugログ（タグ固定ver）
     * 
     * @param Object value
     * @return void
     * @access public
     */
    public static void d(Object value)
    {
        if (DISP_FLG == 1) {
            Log.d(TAG, String.valueOf(value));
        }
    }

    /**
     * infoログ（タグ固定ver）
     * 
     * @param Object value
     * @return void
     * @access public
     */
    public static void i(Object value)
    {
        if (DISP_FLG == 1) {
            Log.i(TAG, String.valueOf(value));
        }
    }

    /**
     * warnログ（タグ固定ver）
     * 
     * @param Object value
     * @return void
     * @access public
     */
    public static void w(Object value)
    {
        if (DISP_FLG == 1) {
            Log.w(TAG, String.valueOf(value));
        }
    }

    /**
     * errorログ（タグ固定ver）
     * 
     * @param Object value
     * @return void
     * @access public
     */
    public static void e(Object value)
    {
        if (DISP_FLG == 1) {
            Log.e(TAG, String.valueOf(value));
        }
    }
}
