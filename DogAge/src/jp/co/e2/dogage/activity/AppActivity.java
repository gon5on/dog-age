package jp.co.e2.dogage.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 基底アクテビティ
 * 
 * @access public
 */
public class AppActivity extends FragmentActivity
{
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

        //タイトルとアイコン非表示
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);

        if (savedInstanceState == null) {
        }
    }
}