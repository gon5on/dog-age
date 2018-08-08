package jp.co.e2.dogage.activity

import jp.co.e2.dogage.R

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

/**
 * アプリについてアクテビティ
 */
class AboutActivity : BaseActivity() {

    companion object {
        /**
         * ファクトリーメソッドもどき
         *
         * @param activity アクテビティ
         * @return intent
         */
        fun newInstance(activity: Activity): Intent {
            return Intent(activity, AboutActivity::class.java)
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_common)

        //アクションバーをセットする
        setBackArrowToolbar()

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.container, AboutFragment.newInstance()).commit()
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * AboutFragment
     */
    class AboutFragment : Fragment() {
        private var mView: View? = null

        companion object {
            /**
             * ファクトリーメソッド
             *
             * @return AboutFragment
             */
            fun newInstance(): AboutFragment {
                val args = Bundle()

                val fragment = AboutFragment()
                fragment.arguments = args

                return fragment
            }
        }

        /**
         * ${inheritDoc}
         */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
            super.onCreate(savedInstanceState)

            mView = inflater.inflate(R.layout.fragment_about, container, false)

            //スクロールビューのオーバースクロールで端の色を変えないように
            container!!.overScrollMode = View.OVER_SCROLL_NEVER

            return mView
        }
    }
}
