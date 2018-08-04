package jp.co.e2.dogage.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 */
public class ImgHelper {
    private String mPath = null;                       //画像ファイルパス
    private Bitmap mBitmap = null;                     //bitmap画像

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     * @param uri     画像のURI
     */
    public ImgHelper(Context context, Uri uri) throws Exception {
        if (uri == null) {
            throw new NullPointerException("uri null");
        }

        //URIをファイルパスに変換
        mPath = MediaUtils.getPathFromUri(context, uri);

        if (mPath == null) {
            throw new NullPointerException("path null");
        }
    }

    /**
     * コンストラクタ
     *
     * @param path 画像のパス
     */
    public ImgHelper(String path) {
        if (path == null) {
            throw new NullPointerException("path null");
        }

        mPath = path;
    }

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     * @param resId   リソースID
     */
    public ImgHelper(Context context, Integer resId) {
        Resources resources = context.getResources();
        mBitmap = BitmapFactory.decodeResource(resources, resId);

        if (mBitmap == null) {
            throw new NullPointerException("could not get bitmap from resource id");
        }
    }

    /**
     * コンストラクタ
     *
     * @param bitmap ビットマップ
     */
    public ImgHelper(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("bitmap null");
        }

        mBitmap = bitmap;
    }

    /**
     * 画像が存在するか
     *
     * @return boolean
     */
    public boolean exist() {
        return mBitmap != null || new File(mPath).exists();
    }

    /**
     * 丸にくりぬいたビットマップ画像を返す
     *
     * @return Bitmap
     */
    public Bitmap getCircleBitmap() throws IOException {
        return getCircleBitmap(getBitmap());
    }

    /**
     * 丸にくりぬいてリサイズしたビットマップ画像を返す
     *
     * @param height 高さピクセル
     * @param width  幅ピクセル
     * @return Bitmap
     */
    public Bitmap getResizeCircleBitmap(Integer height, Integer width) throws IOException {
        return getCircleBitmap(getResizeBitmap(height, width));
    }

    /**
     * 丸にくりぬいたビットマップ画像を返す
     *
     * @param bitmap ビットマップ
     * @return Bitmap
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) throws IOException {
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
     * @param radius 角丸にしたいピクセル数
     * @return Bitmap
     */
    public Bitmap getKadomaruBitmap(Integer radius) throws IOException {
        return getKadomaruBitmap(getBitmap(), radius);
    }

    /**
     * 角丸にくりぬいてリサイズしたビットマップ画像を返す
     *
     * @param height 高さピクセル
     * @param width  幅ピクセル
     * @param radius 角丸にしたいピクセル数
     * @return Bitmap
     */
    public Bitmap getResizeKadomaruBitmap(Integer height, Integer width, Integer radius) throws IOException {
        return getKadomaruBitmap(getResizeBitmap(height, width), radius);
    }

    /**
     * 角丸にくりぬいたビットマップ画像を返す
     *
     * @param radius 角丸にしたいピクセル数
     * @return Bitmap
     */
    private Bitmap getKadomaruBitmap(Bitmap bitmap, Integer radius) throws IOException {
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
     * ビットマップ画像を返す
     *
     * @return Bitmap
     */
    public Bitmap getBitmap() throws IOException {
        if (mBitmap != null) {
            return mBitmap;
        }

        //ファイルが存在しない
        if (!new File(mPath).exists()) {
            throw new FileNotFoundException("file not found");
        }

        Bitmap bitmap = null;

        FileInputStream in = new FileInputStream(mPath);
        bitmap = BitmapFactory.decodeStream(in);
        in.close();

        //画像が読み込めていなければ例外を返す
        if (bitmap == null) {
            throw new IOException("picture can't be read");
        }

        return bitmap;
    }

    /**
     * リサイズしたビットマップ画像を返す
     *
     * @param height 高さピクセル
     * @param width  幅ピクセル
     * @return Bitmap
     */
    public Bitmap getResizeBitmap(Integer height, Integer width) throws IOException {
        //一旦、リサイズしたいサイズに一番近い2のべき乗のサイズでbitmapを読み込む
        Bitmap bitmap = getPreResizeBitmap(height, width);

        //縮尺を計算
        Float scale = Math.min((float) width / bitmap.getWidth(), (float) height / bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        //指定サイズでbitmapを作り直す
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        //画像が読み込めていなければ例外を返す
        if (resizeBitmap == null) {
            throw new IOException("picture can't be read");
        }

        return resizeBitmap;
    }

    /**
     * リサイズしたいサイズに一番近い2のべき乗で読み込んだビットマップ画像を返す
     * （out of memory対策）
     *
     * @param height 高さピクセル
     * @param width  幅ピクセル
     * @return Bitmap
     */
    private Bitmap getPreResizeBitmap(Integer height, Integer width) throws IOException {
        //画像がすでに読み込んである場合は、それを返す
        if (mBitmap != null) {
            return mBitmap;
        }

        //ファイルが存在しない
        if (!new File(mPath).exists()) {
            throw new FileNotFoundException("file not found");
        }

        //画像ファイル自体は読み込まずに、高さなどのプロパティのみを取得する
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mPath, options);

        //縮小比率を取得する
        options.inSampleSize = calcScale(options, height, width);

        //リサイズしたビットマップを作成
        options.inJustDecodeBounds = false;
        Bitmap resizeBitmap = BitmapFactory.decodeFile(mPath, options);

        //画像が読み込めていなければ例外を返す
        if (resizeBitmap == null) {
            throw new IOException("picture can't be read");
        }

        return resizeBitmap;
    }

    /**
     * オリジナルとリサイズ後の画像高さ・幅から縮小比率を取得する
     *
     * @param options  オリジナル画像の縦横幅セット
     * @param reHeight リサイズ後の高さ
     * @param reWidth  リサイズ後の幅
     * @return Integer inSampleSize 縮小比率
     */
    private int calcScale(BitmapFactory.Options options, Integer reHeight, Integer reWidth) {
        Integer oriHeight = options.outHeight;
        Integer oriWidth = options.outWidth;
        Integer scale = 1;

        if (oriHeight > reHeight || oriWidth > reWidth) {
            if (oriHeight > oriWidth) {
                scale = Math.round((float) oriHeight / (float) reHeight);
            } else {
                scale = Math.round((float) oriWidth / (float) reWidth);
            }
        }

        return scale;
    }

    /**
     * 画像をjpgで保存する
     *
     * @param dirPath  保存先パス
     * @param filename 保存画像名
     * @return boolean 成功/失敗
     */
    public Boolean saveJpg(String dirPath, String filename) throws IOException {
        //画像読み込み
        Bitmap img = getBitmap();

        //画像保存
        return saveJpg(dirPath, filename, img);
    }

    /**
     * リサイズして画像をjpgで保存する
     *
     * @param dirPath  保存先パス
     * @param filename 保存画像名
     * @param height   高さピクセル
     * @param weight   幅ピクセル
     * @return boolean 成功/失敗
     */
    public Boolean saveResizeJpg(String dirPath, String filename, Integer height, Integer weight) throws IOException {
        //画像読み込み
        Bitmap img = getResizeBitmap(height, weight);

        //画像保存
        return saveJpg(dirPath, filename, img);
    }

    /**
     * 画像をjpgで保存する
     *
     * @param filePath  保存先パス
     * @return boolean 成功/失敗
     */
    public Boolean saveJpg(String filePath) throws IOException {
        //画像読み込み
        Bitmap img = getBitmap();

        //画像保存
        return saveJpg(filePath, img);
    }

    /**
     * リサイズして画像をjpgで保存する
     *
     * @param filePath  保存先パス
     * @param height   高さピクセル
     * @param weight   幅ピクセル
     * @return boolean 成功/失敗
     */
    public Boolean saveResizeJpg(String filePath, Integer height, Integer weight) throws IOException {
        //画像読み込み
        Bitmap img = getResizeBitmap(height, weight);

        //画像保存
        return saveJpg(filePath, img);
    }

    /**
     * 保存前フォルダ存在確認
     *
     * @param dirPath  保存先パス
     * @param filename 保存画像名
     * @param img      ビットマップ画像
     * @return boolean 成功/失敗
     */
    private Boolean saveJpg(String dirPath, String filename, Bitmap img) throws IOException {
        //保存先のフォルダ存在確認
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if(!dir.mkdir()) {
                throw new IOException("failed in making of a directory");
            }
        }

        //パスを生成
        String filePath = dir.getAbsolutePath() + "/" + filename;

        //実際の保存
        return saveJpg(filePath, img);
    }

    /**
     * 実際の保存処理
     *
     * @param filePath  保存先パス
     * @param img      ビットマップ画像
     * @return boolean 成功/失敗
     */
    private Boolean saveJpg(String filePath, Bitmap img) throws IOException {
        FileOutputStream out = new FileOutputStream(filePath, false);
        img.compress(CompressFormat.JPEG, 100, out);
        out.flush();
        out.close();

        return true;
    }
}
