package jp.co.e2.dogage.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 基底アクテビティ
 */
public class BaseActivity extends FragmentActivity {
    /**
     * ${inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //タイトルとアイコン非表示
        if (getActionBar() != null) {
            getActionBar().setDisplayShowTitleEnabled(false);
            getActionBar().setDisplayShowHomeEnabled(false);
        }
    }
}