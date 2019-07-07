package jp.co.e2.dogage.common;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * 各種ストレージ領域を取得する
 */
public class StorageUtils {
    /**
     * アプリ専用の内部領域のパスを取得する
     * 
     * /data/data/xxx.xxx.xxx/files/
     *
     * @param context コンテキスト
     * @return パス
     */
    public static String getInternalFilesDirPath(Context context) throws IOException {
        return getInternalFilesDirPathCommon(context, null);
    }

    /**
     * アプリ専用の内部領域のパスを取得する（ディレクトリあり）
     * 
     * /data/data/xxx.xxx.xxx/files/yyy/zzz
     *
     * @param context コンテキスト
     * @param dirName フォルダ名
     * @return パス
     */
    public static String getInternalFilesDirPath(Context context, String dirName) throws IOException {
        return getInternalFilesDirPathCommon(context, dirName);
    }





    /**
     * アプリ専用の内部キャッシュ領域のパスを取得する
     * 
     * /data/data/xxx.xxx.xxx/cache/
     *
     * @param context コンテキスト
     * @return パス
     */
    public static String getInternalCacheDirPath(Context context) throws IOException {
        return getInternalCacheDirPathCommon(context, null);
    }

    /**
     * アプリ専用の内部キャッシュ領域のパスを取得する（ディレクトリあり）
     * 
     * /data/data/xxx.xxx.xxx/cache/yyy/zzz
     *
     * @param context コンテキスト
     * @param dirName フォルダ名
     * @return パス
     */
    public static String getInternalCacheDirPath(Context context, String dirName) throws IOException {
        return getInternalCacheDirPathCommon(context, dirName);
    }





    /**
     * アプリ専用の外部領域のパスを取得する
     *
     * /storage/emulated/0/Android/data/xxx.xxx.xxx/files/
     *
     * @param context コンテキスト
     * @return パス
     */
    public static String getExternalFilesDirPath(Context context) throws IOException {
        return getExternalFilesDirPathCommon(context, null);
    }

    /**
     * アプリ専用の外部領域のパスを取得する（ディレクトリあり）
     *
     * /storage/emulated/0/Android/data/xxx.xxx.xxx/files/yyy/zzz
     *
     * @param context コンテキスト
     * @param dirName フォルダ名
     * @return パス
     */
    public static String getExternalFilesDirPath(Context context, String dirName) throws IOException {
        return getExternalFilesDirPathCommon(context, dirName);
    }





    /**
     * アプリ専用の外部キャッシュ領域のパスを取得する
     *
     * /storage/emulated/0/Android/data/xxx.xxx.xxx/files/
     *
     * @param context コンテキスト
     * @return パス
     */
    public static String getExternalCacheDirPath(Context context) throws IOException {
        return getExternalCacheDirPathCommon(context, null);
    }

    /**
     * アプリ専用の外部キャッシュ領域のパスを取得する（ディレクトリあり）
     *
     * /storage/emulated/0/Android/data/xxx.xxx.xxx/files/yyy/zzz
     *
     * @param context コンテキスト
     * @param dirName フォルダ名
     * @return パス
     */
    public static String getExternalCacheDirPath(Context context, String dirName) throws IOException {
        return getExternalCacheDirPathCommon(context, dirName);
    }





    /**
     * アンインストールで消えない外部領域のパスを取得する
     *
     * /storage/emulated/0/
     *
     * @return パス
     */
    public static String getExternalStorageDirPath() throws IOException {
        return getExternalStorageDirPathCommon(null, false);
    }

    /**
     * アンインストールで消えない外部領域のパスを取得する（ディレクトリあり）
     *
     * /storage/emulated/0/yyy/zzz/
     *
     * @param dirName フォルダ名
     * @return パス
     */
    public static String getExternalStorageDirPath(String dirName) throws IOException {
        return getExternalStorageDirPathCommon(dirName, false);
    }

    /**
     * アンインストールで消えない外部領域のパスを取得する（.nomediaあり）
     *
     * /storage/emulated/0/yyy/zzz/.nomedia
     *
     * @param dirName フォルダ名
     * @return パス
     */
    public static String getExternalStorageDirPathWithNoMedia(String dirName) throws IOException {
        return getExternalStorageDirPathCommon(dirName, true);
    }





    /**
     * アプリ専用の内部領域のパスを取得する
     * 
     * /data/data/xxx.xxx.xxx/files/～
     *
     * @param context    コンテキスト
     * @param dirName    フォルダ名
     * @return パス
     */
    private static String getInternalFilesDirPathCommon(Context context, String dirName) throws IOException {
        return getStorageDirPathCommon(context.getFilesDir(), dirName, false);
    }

    /**
     * アプリ専用の内部キャッシュ領域のパスを取得する
     * 
     * /data/data/xxx.xxx.xxx/cache/～
     *
     * @param context    コンテキスト
     * @param dirName    フォルダ名
     * @return パス
     */
    private static String getInternalCacheDirPathCommon(Context context, String dirName) throws IOException {
        return getStorageDirPathCommon(context.getCacheDir(), dirName, false);
    }

    /**
     * アプリ専用の外部領域のパスを取得する
     * 
     * /storage/emulated/0/Android/data/xxx.xxx.xxx/files/～
     *
     * @param context    コンテキスト
     * @param dirName    フォルダ名
     * @return パス
     */
    private static String getExternalFilesDirPathCommon(Context context, String dirName) throws IOException {
        return getStorageDirPathCommon(context.getExternalFilesDir(dirName), null, false);
    }

    /**
     * アプリ専用の外部キャッシュ領域のパスを取得する
     * 
     * /storage/emulated/0/Android/data/xxx.xxx.xx/cache/～
     *
     * @param context    コンテキスト
     * @param dirName    フォルダ名
     * @return パス
     */
    private static String getExternalCacheDirPathCommon(Context context, String dirName) throws IOException {
        return getStorageDirPathCommon(context.getExternalCacheDir(), dirName, false);
    }

    /**
     * アンインストールで消えない外部領域のパスを取得する
     *
     * /storage/emulated/0/～
     *
     * @param dirName フォルダ名
     * @param noMediaFlg .nomediaファイル作成の有無
     * @return パス
     */
    private static String getExternalStorageDirPathCommon(String dirName, boolean noMediaFlg) throws IOException {
        String status = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(status)) {
            throw new IOException();
        }

        return getStorageDirPathCommon(Environment.getExternalStorageDirectory(), dirName, noMediaFlg);
    }

    /**
     * 各種ストレージ領域のパスを取得する
     *
     * @param file       ベースになる領域のファイルオブジェクト
     * @param dirName    フォルダ名
     * @param noMediaFlg .nomediaファイル作成の有無
     * @return パス
     */
    private static String getStorageDirPathCommon(File file, String dirName, boolean noMediaFlg) throws IOException {
        //ディレクトリ作成
        if (dirName != null) {
            file = new File(file.getPath() + "/" + dirName);

            if (!file.exists()) {
                if (!file.mkdirs()) {
                    throw new IOException();
                }
            }
        }

        //.nomediaファイル作成
        if (noMediaFlg) {
            File noMediaFile = new File(file + "/.nomedia");

            if (!noMediaFile.exists()) {
                if (!noMediaFile.createNewFile()) {
                    throw new IOException();
                }
            }
        }

        return file.getPath();
    }
}