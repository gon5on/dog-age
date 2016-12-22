package jp.co.e2.dogage.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.e2.dogage.R;

import android.app.Fragment;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * アプリについてアクテビティ
 */
public class AboutActivity extends BaseActivity {
    private static final String PATTERN = "PixelKit";
    private static final String URL = "http://pixelkit.com/";

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

            getFragmentManager().beginTransaction().add(R.id.container, new AboutFragment()).commit();
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
         * ${inheritDoc}
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mView = inflater.inflate(R.layout.fragment_about, container, false);

            // fragment再生成抑止
            setRetainInstance(true);


            //スクロールビューのオーバースクロールで端の色を変えないように
            container.setOverScrollMode(View.OVER_SCROLL_NEVER);

            return mView;
        }
    }
}
