package jp.co.e2.dogage.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.isseiaoki.simplecropview.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.ImgHelper;
import jp.co.e2.dogage.common.LogUtils;
import jp.co.e2.dogage.common.MediaUtils;
import jp.co.e2.dogage.common.Utils;
import jp.co.e2.dogage.config.Config;
import jp.co.e2.dogage.dialog.DatePickerDialog;
import jp.co.e2.dogage.dialog.ErrorDialog;
import jp.co.e2.dogage.dialog.KindSelectDialog;
import jp.co.e2.dogage.dialog.PhotoSelectDialog;
import jp.co.e2.dogage.entity.DogMasterEntity;
import jp.co.e2.dogage.entity.PetEntity;
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper;
import jp.co.e2.dogage.model.PetDao;
import jp.co.e2.dogage.validate.ValidateDate;
import jp.co.e2.dogage.validate.ValidateHelper;
import jp.co.e2.dogage.validate.ValidateLength;
import jp.co.e2.dogage.validate.ValidateRequire;

/**
 * トリミング画面アクテビティ
 */
public class TrimmingActivity extends BaseActivity {
    public static final String TRIMMING_RESULT = "trimming_result";
    public static final String PATH = "path";

    /**
     * ファクトリーメソッド
     *
     * @param activity アクティビティ
     * @param path 画像パス
     * @return intent インテント
     */
    public static Intent getInstance(Activity activity, String path) {
        Intent intent = new Intent(activity, TrimmingActivity.class);
        intent.putExtra(PATH, path);

        return intent;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        if (savedInstanceState == null) {
            //アクションバーをセットする
            setBackArrowToolbar();

            String path = getIntent().getStringExtra(PATH);

            TrimmingFragment fragment = new TrimmingFragment();
            Bundle args = new Bundle();
            args.putString(PATH, path);
            fragment.setArguments(args);

            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * InputFragment
     */
    public static class TrimmingFragment extends Fragment {
        private View mView = null;

        /**
         * ${inheritDoc}
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mView = inflater.inflate(R.layout.fragment_trimming, container, false);

            // fragment再生成抑止
            setRetainInstance(true);

            //コンテンツをセット
            setContent();

            //イベントセット
            setClickEvent();

            return mView;
        }

        /**
         * コンテンツをセットする
         */
        private void setContent() {
            //元画像取得
            Bitmap bitmap = null;
            try {
                String path = getArguments().getString(PATH);
                ImgHelper imgHelper = new ImgHelper(path);
                bitmap = imgHelper.getBitmap();
            } catch (IOException e) {
                e.printStackTrace();

                Intent data = new Intent();
                data.putExtra(TRIMMING_RESULT, false);
                getActivity().setResult(RESULT_OK, data);
                getActivity().finish();
                return;
            }

            //画像をセット
            CropImageView cropImageView = (CropImageView) mView.findViewById(R.id.cropImageView);
            cropImageView.setImageBitmap(bitmap);
            cropImageView.setFrameColor(getResources().getColor(R.color.trimming_guide));
            cropImageView.setHandleColor(getResources().getColor(R.color.trimming_guide));
            cropImageView.setGuideColor(getResources().getColor(R.color.trimming_guide));
        }

        /**
         * クリックイベントをセットする
         */
        private void setClickEvent() {
            final CropImageView cropImageView = (CropImageView) mView.findViewById(R.id.cropImageView);

            //回転ボタン
            Button buttonRotate = (Button) mView.findViewById(R.id.buttonRotate);
            buttonRotate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                }
            });

            //OKボタン
            Button buttonOk = (Button) mView.findViewById(R.id.buttonOk);
            buttonOk.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bitmap bitmap = cropImageView.getCroppedBitmap();
                        ImgHelper imgHelper = new ImgHelper(bitmap);
                        imgHelper.saveJpg(Config.getImgTmpFilePath(getActivity()));
                    } catch (IOException e) {
                        e.printStackTrace();

                        Intent data = new Intent();
                        data.putExtra(TRIMMING_RESULT, false);
                        getActivity().setResult(RESULT_OK, data);
                        getActivity().finish();
                        return;
                    }

                    Intent data = new Intent();
                    data.putExtra(TRIMMING_RESULT, true);
                    getActivity().setResult(RESULT_OK, data);
                    getActivity().finish();
                }
            });
        }
    }
}