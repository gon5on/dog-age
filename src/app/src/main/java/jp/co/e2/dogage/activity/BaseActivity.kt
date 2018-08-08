package jp.co.e2.dogage.activity

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import jp.co.e2.dogage.R

/**
 * 基底アクテビティ
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * ツールバーをセット
     */
    protected fun setToolbar() {
        //ツールバーをアクションバーとして扱う
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }

        //タイトル非表示
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    /**
     * 戻る矢印付きのツールバーをセット
     */
    protected fun setBackArrowToolbar() {
        setToolbar()

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}