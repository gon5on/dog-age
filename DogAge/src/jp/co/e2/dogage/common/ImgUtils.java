package jp.co.e2.dogage.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;

/**
 * 画像処理に関してのものをまとめたクラス
 * 
 * @access public
 */
public class ImgUtils
{
    private static String mPath;                       //画像ファイルパス
    private static Bitmap mBitmap;                     //bitmap画像

    /**
     * コンストラクタ
     * 
     * @param Context context コンテキスト
     * @param Uri uri 画像のURI
     * @access public
     */
    public ImgUtils(Context context, Uri uri)
    {
        //URIをファイルパスに変換
        mPath = MediaUtils.getPathFromUri(context, uri);
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
     * コンストラクタ
     * 
     * @param Contxt context
     * @param Integer resId リソースID
     * @access public
     */
    public ImgUtils(Context context, Integer resId)
    {
        Resources resources = context.getResources();
        mBitmap = BitmapFactory.decodeResource(resources, resId);
    }

    /**
     * コンストラクタ
     * 
     * @param Bitmap bitmap ビットマップ
     * @access public
     */
    public ImgUtils(Bitmap bitmap)
    {
        mBitmap = bitmap;
    }

    /**
     * オリジナルのビットマップ画像を返す
     * 
     * @return Bitmap
     * @throws IOException
     * @access public
     */
    public Bitmap getBitmap() throws IOException
    {
        if (mBitmap != null) {
            return mBitmap;
        }

        Bitmap bitmap = null;

        FileInputStream in = new FileInputStream(mPath);
        bitmap = BitmapFactory.decodeStream(in);
        in.close();

        return bitmap;
    }

    /**
     * 丸にくりぬいたビットマップ画像を返す
     * 
     * @return Bitmap
     * @throws IOException
     * @access public
     */
    public Bitmap getCircleBitmap() throws IOException
    {
        return getCircleBitmap(getBitmap());
    }

    /**
     * 丸にくりぬいてリサイズしたビットマップ画像を返す
     * 
     * @param Integer height 高さピクセル
     * @param Integer width 幅ピクセル
     * @return Bitmap
     * @throws IOException
     * @access public
     */
    public Bitmap getResizeCircleBitmap(Integer height, Integer width) throws IOException
    {
        return getCircleBitmap(getResizeBitmap(height, width));
    }

    /**
     * 丸にくりぬいたビットマップ画像を返す
     * 
     * @param Integer height 高さピクセル
     * @param Integer width 幅ピクセル
     * @return Bitmap
     * @throws IOException
     * @access private
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) throws IOException
    {
        Integer height = bitmap.getHeight();
        Integer width = bitmap.getWidth();

        Bitmap circleBitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        int circleCenter = circleBitmap.getWidth() / 2;

        Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(circleCenter, circleCenter, circleCenter, paint);

        return circleBitmap;
    }

    /**
     * 角丸にくりぬいたビットマップ画像を返す
     * 
     * @param Integer radius 角丸にしたいピクセル数
     * @return Bitmap
     * @throws IOException
     * @access public
     */
    public Bitmap getKadomaruBitmap(Integer radius) throws IOException
    {
        return getKadomaruBitmap(getBitmap(), radius);
    }

    /**
     * 角丸にくりぬいてリサイズしたビットマップ画像を返す
     * 
     * @param Integer height 高さピクセル
     * @param Integer width 幅ピクセル
     * @param Integer radius 角丸にしたいピクセル数
     * @return Bitmap
     * @throws IOException
     * @access public
     */
    public Bitmap getResizeKadomaruBitmap(Integer height, Integer width, Integer radius) throws IOException
    {
        return getKadomaruBitmap(getResizeBitmap(height, width), radius);
    }

    /**
     * 角丸にくりぬいたビットマップ画像を返す
     * 
     * @param Integer radius 角丸にしたいピクセル数
     * @return Bitmap bitmap
     * @return Bitmap
     * @throws IOException
     * @access public
     */
    public Bitmap getKadomaruBitmap(Bitmap bitmap, Integer radius) throws IOException
    {
        Integer height = bitmap.getHeight();
        Integer width = bitmap.getWidth();

        Bitmap clipArea = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap kadomaruBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(clipArea);
        c.drawRoundRect(new RectF(0, 0, width, height), radius, radius, new Paint(Paint.ANTI_ALIAS_FLAG));

        Canvas canvas = new Canvas(kadomaruBitmap);
        Paint paint = new Paint();
        canvas.drawBitmap(clipArea, 0, 0, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, new Rect(0, 0, width, height), new Rect(0, 0, width, height), paint);

        return kadomaruBitmap;
    }

    /**
     * リサイズしたビットマップ画像を返す
     * 
     * @param Integer height 高さピクセル
     * @param Integer width 幅ピクセル
     * @return Bitmap
     * @access public
     */
    public Bitmap getResizeBitmap(Integer height, Integer width)
    {
        //一旦、リサイズしたいサイズに一番近い2のべき乗で読み込む
        Bitmap bitmap = getPreResizeBitmap(height, width);

        //縮尺を計算
        Float scale = Math.min((float) width / bitmap.getWidth(), (float) height / bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        //指定サイズでbitmapを作り直す
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return resizeBitmap;
    }

    /**
     * リサイズしたいサイズに一番近い2のべき乗で読み込んだビットマップ画像を返す
     * 
     * @param Integer height 高さピクセル
     * @param Integer width 幅ピクセル
     * @return Bitmap
     * @access private
     */
    private Bitmap getPreResizeBitmap(Integer height, Integer width)
    {
        //画像がすでに読み込んである場合は、それを返す
        if (mBitmap != null) {
            return mBitmap;
        }

        //画像ファイル自体は読み込まずに、高さなどのプロパティのみを取得する
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mPath, options);

        //縮小比率を取得する
        options.inSampleSize = calceScale(options, height, width);

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
    private static int calceScale(BitmapFactory.Options options, Integer reHeight, Integer reWidth)
    {
        Integer oriHeight = options.outHeight;
        Integer oriWidth = options.outWidth;
        Integer scale = 1;

        if (oriHeight > reHeight || oriWidth > reWidth) {
            if (oriWidth > oriWidth) {
                scale = Math.round((float) oriHeight / (float) reHeight);
            } else {
                scale = Math.round((float) oriWidth / (float) reWidth);
            }
        }

        return scale;
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
    public Boolean saveResizeJpg(String dirPath, String name, Integer height, Integer weight) throws IOException
    {
        //画像読み込み
        Bitmap img = getResizeBitmap(height, weight);

        //画像保存
        return saveJpg(dirPath, name, img);
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
    public Boolean saveOrgJpg(String dirPath, String name) throws IOException
    {
        //画像読み込み
        Bitmap img = getBitmap();

        //画像保存
        return saveJpg(dirPath, name, img);
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
    private Boolean saveJpg(String dirPath, String name, Bitmap img) throws IOException
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
