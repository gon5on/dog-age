package jp.co.e2.dogage.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.co.e2.dogage.R

/**
 * 年齢計算方法についてアクテビティ
 */
class HowToCalcActivity : BaseActivity() {
    /**
     * ${inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_common)

        //アクションバーをセットする
        setBackArrowToolbar()

        //タイトルをセットする
        setActionBarTitle(getString(R.string.how_to_calc))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, HowToCalcFragment()).commit()
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
     * HowToCalcFragment
     */
    class HowToCalcFragment : Fragment() {
        /**
         * ${inheritDoc}
         */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            super.onCreate(savedInstanceState)

            return inflater.inflate(R.layout.fragment_how_to_calc, container, false)
        }
    }
}
