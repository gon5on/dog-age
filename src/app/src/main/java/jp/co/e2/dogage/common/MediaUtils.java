package jp.co.e2.dogage.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

/**
 * メディア・ファイル系の便利なものをまとめたクラス
 */
public class MediaUtils {
    /**
     * URIからファイルパスを取得する
     *
     * @param context コンテキスト
     * @param uri     URI
     * @return String path ファイルパス
     */
    public static String getPathFromUri(Context context, Uri uri) {
        String path = null;

        if (uri != null) {
            if (uri.getScheme().equals("content")) {
                String[] param = {android.provider.MediaStore.Images.ImageColumns.DATA};
                Cursor cursor = context.getContentResolver().query(uri, param, null, null, null);
                cursor.moveToFirst();
                path = cursor.getString(0);
                cursor.close();
            } else {
                path = uri.getPath();
            }
        }

        return path;
    }

    /**
     * URIからファイルパスを取得する
     *
     * @param context コンテキスト
     * @param uri     URI
     * @return String path ファイルパス
     */
    public static Uri getUriFromPath(Context context, String path) {
        Uri uri = null;

        try {
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[] { BaseColumns._ID },
                    MediaStore.Images.ImageColumns.DATA + " LIKE ?",
                    new String[] { path },
                    null
            );
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(BaseColumns._ID);
            long id = cursor.getLong(idx);
            cursor.close();

            uri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + id);
        }
        catch (Exception e) {
            e.printStackTrace();

            ContentValues values = new ContentValues();
            ContentResolver contentResolver = context.getContentResolver();
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, path);
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            getUriFromPath(context, path);
        }

        return uri;
    }

    /**
     * 外部ストレージが使用できるかどうか
     *
     * @return boolean 外部ストレージが使える/使えない
     */
    public static boolean IsExternalStorageAvailableAndWriteable() {
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
     * android4.4からこの方法が使えなくなったので使わないこと！
     *
     * @param context コンテキスト
     */
    @Deprecated
    public static void mediaScan(Context context) {
        String url = "file://" + Environment.getExternalStorageDirectory();
        Uri uri = Uri.parse(url);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, uri));
    }

    /**
     * ファイル/ディレクトリを削除する（中身があってもOK）
     *
     * @param file ファイル/ディレクトリオブジェクト
     */
    public static void deleteDirFile(File file) throws IOException {
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            if (!file.delete()) {
                throw new IOException("ファイルの削除に失敗しました");
            }
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tmp: files){
                deleteDirFile(tmp);
            }
            if (!file.delete()) {
                throw new IOException("ファイルの削除に失敗しました");
            }
        }
    }

    /**
     * ファイル/ディレクトリを削除する（中身があってもOK）
     *
     * @param path ファイル/ディレクトリパス
     */
    public static void deleteDirFile(String path) throws IOException {
        deleteDirFile(new File(path));
    }

    /**
     * ファイルパスからファイル名を返す
     *
     * @param path ファイルパス
     * @return String
     */
    public static String geFileName(String path) {
        File file = new File(path);

        if (!file.exists()) {
            return null;
        }

        return file.getName();
    }

    /**
     * ファイルパスから拡張子を返す
     *
     * @param path ファイルパス
     * @return String
     */
    public static String getFileExt(String path) {
        String name = geFileName(path);

        if (name == null) {
            return null;
        }

        int lastDotPosition = name.lastIndexOf(".");
        if (lastDotPosition != -1) {
            return name.substring(lastDotPosition + 1);
        }

        return null;
    }

    /**
     * ファイルパスからMIMEタイプを返す
     *
     * @param path ファイルパス
     * @return String
     */
    public static String getMimeType(String path) {
        String ext = getFileExt(path);

        if (ext == null) {
            return null;
        }

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.toLowerCase());
    }

    /**
     * バイナリファイルを返す
     *
     * @param path ファイルパス
     * @return File
     * @throws FileNotFoundException
     */
    public static File getBinaryFile(String path) throws FileNotFoundException {
        File file = new File(path);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        return file;
    }

    /**
     * ファイルのコピー
     *
     * @param inputPath コピー元のファイルパス
     * @param outputPath コピー先のファイルパス
     * @throws IOException
     */
    public static void copyFile(String inputPath, String outputPath) throws IOException {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        FileInputStream inputFileStream = new FileInputStream(inputFile);
        FileOutputStream outputFileStream = new FileOutputStream(outputFile);
        FileChannel inputChannel = inputFileStream.getChannel();
        FileChannel outputChannel = outputFileStream.getChannel();
        inputChannel.transferTo(0, inputChannel.size(), outputChannel);

        inputFileStream.close();
        outputFileStream.close();
        inputChannel.close();
        outputChannel.close();
    }
}
