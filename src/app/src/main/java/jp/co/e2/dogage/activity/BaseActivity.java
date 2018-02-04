package jp.co.e2.dogage.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import jp.co.e2.dogage.R;

/**
 * 基底アクテビティ
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * ツールバーをセット
     */
    protected void setToolbar() {
        //ツールバーをアクションバーとして扱う
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //タイトル非表示
        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 戻る矢印付きのツールバーをセット
     */
    protected void setBackArrowToolbar() {
        setToolbar();

        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setHomeButtonEnabled(true);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }
}