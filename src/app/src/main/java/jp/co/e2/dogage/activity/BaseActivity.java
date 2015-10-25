package jp.co.e2.dogage.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import jp.co.e2.dogage.R;

/**
 * 基底アクテビティ
 */
public class BaseActivity extends ActionBarActivity {
    /**
     * アクションバーをセット
     *
     * @param backFlg 戻るボタン有無
     */
    protected void setActionbar(boolean backFlg) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        if (backFlg) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }
    }
}