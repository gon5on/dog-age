package jp.co.e2.dogage.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * 画像処理に関してのものをまとめたクラス
 * 
 * @access public
 */
public class ImgUtils
{
    private static String mPath;                       //画像ファイルパス

    /**
     * コンストラクタ
     * 
     * @param Context context コンテキスト
     * @param Uri uri 画像のURI
     * @access public
     */
    public ImgUtils(Context context, Uri uri)
    {
        mPath = MediaUtils.getPathFromUri(context, uri);        //URIをファイルパスに変換
    }

    /**
     * コンストラクタ
     * 
     * @param String path 画像のパス
     * @access public
     */
    public ImgUtils(String path)
    {
        mPath = path;
    }

    /**
     * オリジナルのビットマップ画像を返す
     * 
     * @return Bitmap
     * @throws IOException
     * @access public
     */
    public Bitmap getOriginImg() throws IOException
    {
        Bitmap originBitmap = null;

        FileInputStream in = new FileInputStream(mPath);
        originBitmap = BitmapFactory.decodeStream(in);
        in.close();

        return originBitmap;
    }

    /**
     * リサイズしたビットマップ画像を返す
     * 
     * @param Integer height 高さピクセル
     * @param Integer weight 幅ピクセル
     * @return Bitmap
     * @access public
     */
    public Bitmap getResizeImg(Integer height, Integer weight)
    {
        //画像ファイル自体は読み込まずに、高さなどのプロパティのみを取得する
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mPath, options);

        //縮小比率を取得する
        options.inSampleSize = calceInSampleSize(options, height, weight);

        //リサイズしたビットマップを作成
        options.inJustDecodeBounds = false;
        Bitmap resizeBitmap = BitmapFactory.decodeFile(mPath, options);

        return resizeBitmap;
    }

    /**
     * オリジナルとリサイズ後の画像高さ・幅から縮小比率を取得する
     * 
     * @param BitmapFactory.Options options オリジナル画像の縦横幅セット
     * @param Integer reHeight リサイズ後の高さ
     * @param Integer reWidth リサイズ後の幅
     * @return Integer inSampleSize 縮小比率
     * @access private
     */
    private static int calceInSampleSize(BitmapFactory.Options options, Integer reHeight, Integer reWidth)
    {
        Integer oriHeight = options.outHeight;
        Integer oriWidth = options.outWidth;
        Integer inSampleSize = 1;

        if (oriHeight > reHeight || oriWidth > reWidth) {
            if (oriWidth > oriWidth) {
                inSampleSize = Math.round((float) oriHeight / (float) reHeight);
            } else {
                inSampleSize = Math.round((float) oriWidth / (float) reWidth);
            }
        }

        return inSampleSize;
    }

    /**
     * リサイズして画像をjpgで保存する
     * 
     * @param String dirPath 保存先パス
     * @param String name 保存画像名
     * @param Integer height 高さピクセル
     * @param Integer weight 幅ピクセル
     * @return boolean 成功/失敗
     * @throws IOException
     * @access public
     */
    public Boolean saveResizeImg(String dirPath, String name, Integer height, Integer weight) throws IOException
    {
        //画像読み込み
        Bitmap img = getResizeImg(height, weight);

        //画像保存
        return saveImgJpg(dirPath, name, img);
    }

    /**
     * オリジナル画像をjpgで保存する
     * 
     * @param String dirPath 保存先パス
     * @param String name 保存画像名
     * @return boolean 成功/失敗
     * @throws IOException
     * @access public
     */
    public Boolean saveOrgImgJpg(String dirPath, String name) throws IOException
    {
        //画像読み込み
        Bitmap img = getOriginImg();

        //画像保存
        return saveImgJpg(dirPath, name, img);
    }

    /**
     * 実際の保存処理
     * 
     * @param String dir_path 保存先パス
     * @param String name 保存画像名
     * @param Bitmap img ビットマップ画像
     * @return boolean 成功/失敗
     * @throws IOException
     * @access private
     */
    private Boolean saveImgJpg(String dirPath, String name, Bitmap img) throws IOException
    {
        //保存先のフォルダ存在確認
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        //jpgで保存
        String filePath = dir.getAbsolutePath() + "/" + name;
        FileOutputStream out = new FileOutputStream(filePath, false);
        img.compress(CompressFormat.JPEG, 100, out);
        out.flush();
        out.close();

        return true;
    }
}
