package com.tmrnk.gongon.dogage.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tmrnk.gongon.dogage.R;

/**
 * アプリについてアクテビティ
 * 
 * @access public
 */
public class AboutActivity extends AppActivity
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
        setContentView(R.layout.activity_about);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new AboutFragment()).commit();
        }
    }

    /**
     * onCreateOptionsMenu
     * 
     * @param Menu menu
     * @return Boolean
     * @access public
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //初回起動以外だったラ、アクションバーに戻るボタンをセット
        if (getIntent().getIntExtra("initFlag", 0) == 0) {
            getMenuInflater().inflate(R.menu.input, menu);
        }

        return true;
    }

    /**
     * onOptionsItemSelected
     * 
     * @param MenuItem item
     * @return Boolean
     * @access public
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Integer id = item.getItemId();

        if (id == R.id.action_back) {
            finish();
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

            return mView;
        }
    }
}
