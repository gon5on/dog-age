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
 * 
 * @access public
 */
public class AboutActivity extends AppActivity
{
    private static final String PATTERN = "PixelKit";
    private static final String URL = "http://pixelkit.com/";

    /**
     * onCreate
     * 
     * @param Bundle savedInstanceState
     * @return void
     * @access protected
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new AboutFragment()).commit();
        }

        //アクションバーに戻るボタンをセット
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setLogo(R.drawable.ic_back);
    }

    /**
     * onOptionsItemSelected
     * 
     * @param MenuItem item
     * @return void
     * @access protected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * AboutFragment
     * 
     * @access public
     */
    public static class AboutFragment extends Fragment
    {
        private View mView = null;

        /**
         * onCreateView
         * 
         * @param LayoutInflater inflater
         * @param ViewGroup container
         * @param Bundle savedInstanceState
         * @return View
         * @access public
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            // fragment再生成抑止
            setRetainInstance(true);

            mView = inflater.inflate(R.layout.fragment_about, container, false);

            //ライセンスにリンクを貼る
            linkLisence();

            //スクロールビューのオーバースクロールで端の色を変えないように
            container.setOverScrollMode(View.OVER_SCROLL_NEVER);

            return mView;
        }

        /**
         * ライセンスにリンクを貼る
         * 
         * @return void
         * @access private
         */
        private void linkLisence()
        {
            TextView textViewCC = (TextView) mView.findViewById(R.id.textViewCC);

            Pattern pattern = Pattern.compile(PATTERN);
            Linkify.TransformFilter filter = new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return URL;
                }
            };

            Linkify.addLinks(textViewCC, pattern, URL, null, filter);
        }
    }
}
