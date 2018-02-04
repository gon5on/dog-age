package jp.co.e2.dogage.activity;

import jp.co.e2.dogage.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * アプリについてアクテビティ
 */
public class AboutActivity extends BaseActivity {
    /**
     * ファクトリーメソッドもどき
     *
     * @param activity アクテビティ
     * @return intent
     */
    public static Intent newInstance(Activity activity) {
        return new Intent(activity, AboutActivity.class);
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
            getFragmentManager().beginTransaction().add(R.id.container, AboutFragment.newInstance()).commit();
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
     * AboutFragment
     */
    public static class AboutFragment extends Fragment {
        private View mView = null;

        /**
         * ファクトリーメソッド
         *
         * @return AboutFragment
         */
        public static AboutFragment newInstance() {
            Bundle args = new Bundle();

            AboutFragment fragment = new AboutFragment();
            fragment.setArguments(args);

            return fragment;
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mView = inflater.inflate(R.layout.fragment_about, container, false);

            //スクロールビューのオーバースクロールで端の色を変えないように
            container.setOverScrollMode(View.OVER_SCROLL_NEVER);

            return mView;
        }
    }
}
