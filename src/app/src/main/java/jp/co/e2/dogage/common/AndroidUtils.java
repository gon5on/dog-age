package jp.co.e2.dogage.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
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
     * 成功時のスナックバーを表示する
     *
     * @param view ビュー
     * @param text テキスト
     */
    public static void showSuccessSnackBarS(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.rgb(60,179,113));

        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.rgb(255, 255, 255));

        snackbar.show();
    }

    /**
     * 失敗時のスナックバーを表示する
     *
     * @param view ビュー
     * @param text テキスト
     */
    public static void showErrorSnackBarS(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.rgb(220, 78, 78));

        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.rgb(255, 255, 255));

        snackbar.show();
    }

    /**
     * ビットマップ画像に色を付ける
     *
     * @param bitmap ビットマップ
     * @param color カラーコード
     * @return ビットマップ
     */
    public static Bitmap setBitmapColor(Bitmap bitmap, int color) {
        //mutable化する
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        bitmap.recycle();

        Canvas myCanvas = new Canvas(mutableBitmap);

        int myColor = mutableBitmap.getPixel(0,0);
        ColorFilter filter = new LightingColorFilter(myColor, color);

        Paint pnt = new Paint();
        pnt.setColorFilter(filter);
        myCanvas.drawBitmap(mutableBitmap,0,0,pnt);

        return mutableBitmap;
    }

    /**
     * 外部ストレージに書き込めるか？
     *
     * @return boolean
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();

        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    /**
     * 外部ストレージから読み込めるか？
     *
     * @return boolean
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();

        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    /**
     * 写真をギャラリーに追加する
     *
     * @param context コンテキスト
     * @param path パス
     */
    public static void addPhotoToGallery(Context context, String path) {
        if (path == null) {
            return;
        }
        if (!new File(path).exists()) {
            return;
        }

        Uri uri = Uri.fromFile(new File(path));

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);

        context.sendBroadcast(intent);
    }
}
