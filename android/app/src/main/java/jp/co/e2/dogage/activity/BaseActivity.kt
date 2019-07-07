package jp.co.e2.dogage.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
        findViewById<Toolbar>(R.id.toolbar).apply {
            setSupportActionBar(this)
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

    /**
     * タイトルをセットする
     */
    protected fun setActionBarTitle(title: String) {
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = title
    }
}