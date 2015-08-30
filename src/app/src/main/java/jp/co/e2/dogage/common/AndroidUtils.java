package jp.co.e2.dogage.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Android独自の便利なものまとめたクラス
 *
 * newしなくても使える
 */
public class AndroidUtils {
    private static final AtomicInteger mNextGeneratedId = new AtomicInteger(1);

    /**
     * トースト表示（短い）
     *
     * @param context コンテキスト
     */
    public static void showToastS(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * トースト表示（長い）
     *
     * @param context コンテキスト
     */
    public static void showToastL(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * アプリバージョン名を取得する
     *
     * @param context コンテキスト
     * @return String versionName バージョン名
     */
    public static String getVerName(Context context) {
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
     * @param context コンテキスト
     * @return Integer versionCode バージョンコード
     */
    public static Integer getVerCode(Context context) {
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
     * @param context コンテキスト
     * @return Uri マーケットのURI
     */
    public static Uri getMargetUri(Context context) {
        return Uri.parse("market://details?id=" + context.getPackageName());
    }

    /**
     * dp→pixelに変換
     *
     * @param context コンテキスト
     * @param value dp
     * @return Integer pixel
     */
    public static Integer dpToPixel(Context context, Integer value) {
        double doubleValue = value;

        return dpToPixel(context, doubleValue);
    }

    /**
     * dp→pixelに変換
     *
     * @param context コンテキスト
     * @param value dp
     * @return Integer pixel
     */
    public static Integer dpToPixel(Context context, Double value) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (value * density + 0.5f);
    }

    /**
     * ウィンドウの横幅を返す
     *
     * @param context コンテキスト
     * @return Integer ウインドウ横幅
     */
    public static Integer getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    /**
     * ウィンドウの縦幅を返す
     *
     * @param context コンテキスト
     * @return Integer ウインドウ縦幅
     */
    public static Integer getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    /**
     * 被らないリソースIDを生成する
     *
     * @return int リソースID
     */
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (;;) {
                final int result = mNextGeneratedId.get();
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1;        // Roll over to 1, not 0.
                if (mNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }
}
