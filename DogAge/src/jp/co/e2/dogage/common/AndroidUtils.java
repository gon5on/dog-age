package jp.co.e2.dogage.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Android独自の便利なものまとめたクラス
 * 
 * newしなくても使える
 * 
 * @access public
 */
public class AndroidUtils
{
    /**
     * トースト表示（短い）
     * 
     * @param Context context コンテキスト
     * @return void
     * @access public
     */
    public static void showToastS(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * トースト表示（長い）
     * 
     * @param Context context コンテキスト
     * @return void
     * @access public
     */
    public static void showToastL(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * アプリバージョン名を取得する
     * 
     * @param Context context コンテキスト
     * @return String versionName バージョン名
     * @access public
     */
    public static String getVerName(Context context)
    {
        String versionName = null;

        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            versionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    /**
     * アプリバージョンコードを取得する
     * 
     * @param Context context コンテキスト
     * @return Integer versionCode バージョンコード
     * @access public
     */
    public static Integer getVerCode(Context context)
    {
        Integer versionCode = null;

        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            versionCode = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * アプリのマーケットURIを取得する
     * 
     * @param Context context コンテキスト
     * @return Uri マーケットのURI
     * @access public
     */
    public static Uri getMargetUri(Context context)
    {
        return Uri.parse("market://details?id=" + context.getPackageName());
    }

    /**
     * dp→pixelに変換
     * 
     * @param Context context
     * @param Integer value
     * @return Integer pixel
     * @access public
     */
    public static Integer dpToPixel(Context context, Integer value)
    {
        double doubleValue = value;

        return dpToPixel(context, doubleValue);
    }

    /**
     * dp→pixelに変換
     * 
     * @param Context context
     * @param Double value
     * @return Integer pixel
     * @access public
     */
    public static Integer dpToPixel(Context context, Double value)
    {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (value * density + 0.5f);
    }

    /**
     * ウィンドウの横幅を返す
     * 
     * @param Context context
     * @return Integer pixel
     * @access public
     */
    public static Integer getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    /**
     * ウィンドウの縦幅を返す
     * 
     * @param Context context
     * @return Integer pixel
     * @access public
     */
    public static Integer getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }
}
