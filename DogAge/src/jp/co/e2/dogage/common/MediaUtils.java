package jp.co.e2.dogage.common;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * メディア・ファイル系の便利なものをまとめたクラス
 * 
 * @access public
 */
public class MediaUtils
{
    /**
     * URIからファイルパスを取得する
     * 
     * @param Context context コンテキスト
     * @param Uri uri URI
     * @return String path ファイルパス
     * @access public
     */
    public static String getPathFromUri(Context context, Uri uri)
    {
        String path = null;

        if (uri != null && "content".equals(uri.getScheme())) {
            String[] param = { android.provider.MediaStore.Images.ImageColumns.DATA };
            Cursor cursor = context.getContentResolver().query(uri, param, null, null, null);
            cursor.moveToFirst();
            path = cursor.getString(0);
            cursor.close();
        } else {
            path = uri.getPath();
        }

        return path;
    }

    /**
     * 外部ストレージが使用できるかどうか
     * 
     * @return boolen 外部ストレージが使える/使えない
     * @access public
     */
    public static boolean IsExternalStorageAvailableAndWriteable()
    {
        boolean externalStorageAvailable = false;
        boolean externalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else {
            externalStorageAvailable = externalStorageWriteable = false;
        }

        return externalStorageAvailable && externalStorageWriteable;
    }

    /**
     * メディアスキャンを実行
     * 
     * @param Context context コンテキスト
     * @return void
     * @access public
     */
    public static void mediaScan(Context context)
    {
        String url = "file://" + Environment.getExternalStorageDirectory();
        Uri uri = Uri.parse(url);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, uri));
    }

    /**
     * ファイル/ディレクトリを削除する（中身があってもOK）
     * 
     * @param File file ファイル/ディレクトリオブジェクト
     * @return void
     * @access public
     */
    public static void deleteDirFile(File file)
    {
        if (file.exists() == false) {
            return;
        }

        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDirFile(files[i]);
            }
            file.delete();
        }
    }

    /**
     * ファイル/ディレクトリを削除する（中身があってもOK）
     * 
     * @param String path ファイル/ディレクトリパス
     * @return void
     * @access public
     */
    public static void deleteDirFile(String path)
    {
        deleteDirFile(new File(path));
    }
}
