package jp.co.e2.dogage.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.isseiaoki.simplecropview.CropImageView;

import java.io.IOException;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.ImgHelper;
import jp.co.e2.dogage.config.Config;

/**
 * トリミング画面アクテビティ
 */
public class TrimmingActivity extends BaseActivity {
    public static final String TRIMMING_RESULT = "trimming_result";
    private static final String PARAM_PATH = "path";

    /**
     * ファクトリーメソッドもどき
     *
     * @param activity アクティビティ
     * @param path 画像パス
     * @return intent インテント
     */
    public static Intent getInstance(Activity activity, String path) {
        Intent intent = new Intent(activity, TrimmingActivity.class);
        intent.putExtra(PARAM_PATH, path);

        return intent;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        //アクションバーをセットする
        setBackArrowToolbar();

        if (savedInstanceState == null) {
            Fragment fragment = TrimmingFragment.newInstance(getIntent().getStringExtra(PARAM_PATH));
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
         *  ファクトリーメソッド
         *
         * @param path パス
         * @return TrimmingFragment
         */
        public static TrimmingFragment newInstance(String path) {
            Bundle args = new Bundle();
            args.putString(PARAM_PATH, path);

            TrimmingFragment fragment = new TrimmingFragment();
            fragment.setArguments(args);

            return fragment;
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mView = inflater.inflate(R.layout.fragment_trimming, container, false);

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
            Bitmap bitmap;
            try {
                String path = getArguments().getString(PARAM_PATH);
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
            CropImageView cropImageView = mView.findViewById(R.id.cropImageView);
            cropImageView.setImageBitmap(bitmap);
            cropImageView.setFrameColor(getResources().getColor(R.color.green));
            cropImageView.setHandleColor(getResources().getColor(R.color.green));
            cropImageView.setGuideColor(getResources().getColor(R.color.green));
        }

        /**
         * クリックイベントをセットする
         */
        private void setClickEvent() {
            final CropImageView cropImageView = mView.findViewById(R.id.cropImageView);

            //回転ボタン
            Button buttonRotate = mView.findViewById(R.id.buttonRotate);
            buttonRotate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                }
            });

            //OKボタン
            Button buttonOk = mView.findViewById(R.id.buttonOk);
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