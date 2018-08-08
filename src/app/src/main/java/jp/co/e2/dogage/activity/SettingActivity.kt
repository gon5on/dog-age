package jp.co.e2.dogage.activity

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.common.PreferenceUtils
import jp.co.e2.dogage.config.Config

/**
 * 設定アクテビティ
 */
class SettingActivity : BaseActivity() {

    companion object {
        /**
         * ファクトリーメソッドもどき
         *
         * @param activity アクテビティ
         * @return intent
         */
        fun newInstance(activity: Activity): Intent {
            return Intent(activity, SettingActivity::class.java)
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
            fragmentManager.beginTransaction().add(R.id.container, SettingFragment.newInstance()).commit()
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
     * SettingFragment
     */
    class SettingFragment : Fragment() {
        private var mView: View? = null

        companion object {
            /**
             * ファクトリーメソッド
             *
             * @return SettingFragment
             */
            fun newInstance(): SettingFragment {
                val args = Bundle()

                val fragment = SettingFragment()
                fragment.arguments = args

                return fragment
            }
        }

        /**
         * ${inheritDoc}
         */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
            super.onCreate(savedInstanceState)

            mView = inflater.inflate(R.layout.fragment_setting, container, false)

            //コンテンツセット
            setContent()

            //イベントセット
            setClickEvent()

            return mView
        }

        /**
         * コンテンツをセットする
         */
        private fun setContent() {
            //誕生日通知チェックボックス
            val checkBoxBirthNotify = mView!!.findViewById<CheckBox>(R.id.checkBoxBirthNotification)
            checkBoxBirthNotify.isChecked = PreferenceUtils.get(activity, Config.PREF_BIRTH_NOTIFY_FLG, true)

            //命日通知チェックボックス
            val checkBoxArchiveNotify = mView!!.findViewById<CheckBox>(R.id.checkBoxArchiveNotification)
            checkBoxArchiveNotify.isChecked = PreferenceUtils.get(activity, Config.PREF_ARCHIVE_NOTIFY_FLG, true)

            //アプリバージョン
            val textViewVer = mView!!.findViewById<TextView>(R.id.textViewVer)
            textViewVer.text = AndroidUtils.getVerName(activity)
        }

        /**
         * クリックイベントをセットする
         */
        private fun setClickEvent() {
            //誕生日通知チェックボックス
            val checkBoxBirthNotify = mView!!.findViewById<CheckBox>(R.id.checkBoxBirthNotification)
            checkBoxBirthNotify.setOnCheckedChangeListener { buttonView, isChecked ->
                changeNotifySetting(Config.PREF_BIRTH_NOTIFY_FLG, isChecked) }

            //誕生日通知チェックボックス
            val checkBoxArchiveNotify = mView!!.findViewById<CheckBox>(R.id.checkBoxArchiveNotification)
            checkBoxArchiveNotify.setOnCheckedChangeListener { buttonView, isChecked ->
                changeNotifySetting(Config.PREF_ARCHIVE_NOTIFY_FLG, isChecked) }

            //年齢計算について
            val constraintLayoutCalcAge = mView!!.findViewById<ConstraintLayout>(R.id.constraintLayoutCalcAge)
            constraintLayoutCalcAge.setOnClickListener {
                startActivity(AboutActivity.newInstance(activity))
            }

            //E2リンク
            val buttonProducedBy = mView!!.findViewById<Button>(R.id.buttonProducedBy)
            buttonProducedBy.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(Config.OFFICIAL_LINK))
                startActivity(i)
            }
        }

        /**
         * チェックボックスの設定を保存する
         *
         * @param name プリファレンス保存名
         * @param value プリファレンス保存値
         */
        private fun changeNotifySetting(name: String, value: Boolean) {
            PreferenceUtils.save(activity, name, value)

            AndroidUtils.showSuccessSnackBarS(mView, getString(R.string.change_settings))
        }
    }
}
