package jp.co.e2.dogage.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

/**
 * メディア・ファイル系の便利なものをまとめたクラス
 */
public class MediaUtils {
    /**
     * URIからファイルパスを取得する
     *
     * @param context コンテキスト
     * @param uri URI
     * @return String path ファイルパス
     */
    public static String getPathFromUri(Context context, Uri uri) {
        String path = null;

        if (uri != null) {
            if ("content".equals(uri.getScheme())) {
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
     * ファイルパスからURIを取得する
     *
     * @param context コンテキスト
     * @param path ファイルパス
     * @return Uri
     */
    public static Uri getUriFromPath(Context context, String path) {
        return Uri.fromFile(new File(context.getExternalFilesDir(null), path));
    }

    /**
     * 外部ストレージが使用できるかどうか
     *
     * @return boolean 外部ストレージが使える/使えない
     */
    public static boolean IsExternalStorageAvailableAndWritable() {
        boolean externalStorageAvailable;
        boolean externalStorageWritable;
        String state = Environment.getExternalStorageState();

        switch (state) {
            case Environment.MEDIA_MOUNTED:
                externalStorageAvailable = externalStorageWritable = true;
                break;
            case Environment.MEDIA_MOUNTED_READ_ONLY:
                externalStorageAvailable = true;
                externalStorageWritable = false;
                break;
            default:
                externalStorageAvailable = externalStorageWritable = false;
                break;
        }

        return externalStorageAvailable && externalStorageWritable;
    }

    /**
     * メディアスキャンを実行
     *
     * 4.4から使用できないので、使わないこと！
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
                throw new IOException("failed to delete the file");
            }
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tmp : files) {
                deleteDirFile(tmp);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete the file");
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
    public static String geFileName(String path) throws FileNotFoundException {
        File file = new File(path);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found");
        }

        return file.getName();
    }

    /**
     * ファイルパスから拡張子を返す
     *
     * @param path ファイルパス
     * @return String
     */
    public static String getFileExt(String path) throws FileNotFoundException {
        String name = geFileName(path);

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
    public static String getMimeType(String path) throws FileNotFoundException {
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
     */
    public static File getBinaryFile(String path) throws FileNotFoundException {
        File file = new File(path);

        if (!file.exists()) {
            throw new FileNotFoundException("file not found");
        }

        return file;
    }

    /**
     * ファイルのコピー
     *
     * @param inputPath コピー元のファイルパス
     * @param outputPath コピー先のファイルパス
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

    /**
     * ファイルの保存
     *
     * @param tmpFile 一時ファイル
     * @param outputPath コピー先のファイルパス
     */
    public static void saveFile(File tmpFile, String outputPath) throws IOException {
        File outputFile = new File(outputPath);

        InputStream in = new FileInputStream(tmpFile);
        OutputStream out = new FileOutputStream(outputFile);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) >= 0) {
            out.write(buffer, 0, bytesRead);
        }

        in.close();
        out.close();
    }

    /**
     * ディレクトリを作成する
     *
     * @param context コンテキスト
     * @param dirName ディレクトリ名
     * @return String
     */
    public static File createDirPath(Context context, String dirName) {
        String path;

        //外部ストレージが使用可能
        if (MediaUtils.IsExternalStorageAvailableAndWritable()) {
            path = context.getExternalFilesDir(dirName).toString();
        }
        //外部ストレージは使用不可
        else {
            path = context.getFilesDir().toString() + "/" + dirName;
        }

        File file = new File(path);

        //フォルダが存在しなければ作成
        if (!file.exists()) {
            file.mkdir();
        }

        return  file;
    }
}